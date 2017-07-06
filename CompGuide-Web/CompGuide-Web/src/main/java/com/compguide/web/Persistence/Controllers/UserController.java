package com.compguide.web.Persistence.Controllers;

import WebService.HttpManager;
import com.compguide.web.Persistence.Entities.User;
import com.compguide.web.Persistence.Controllers.util.JsfUtil;
import com.compguide.web.Persistence.Controllers.util.JsfUtil.PersistAction;
import com.compguide.web.Persistence.SessionBeans.UserFacade;
import com.compguide.web.Utils.CookieHelper;
import com.compguide.web.Utils.PasswordService;
import java.io.IOException;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.*;
import org.apache.http.Header;

@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.UserFacade ejbFacade;
    private List<User> items = null;
    private List<User> filteredUsers = null;
    private User selected;
    private boolean keepLoggedIn;
    private boolean firstLogIn;
    private String confirmPassword;
    private CookieHelper cookieHelper = new CookieHelper();
    @NotNull
    @Size(min = 1, message = "Fill the password field")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = " Password must have at least 8 characters, 1 Alphabet and 1 Number.")
    private String password;
    @NotNull
    @Size(min = 1, message = "Put the beta key")
    @Pattern(regexp = "^([0-9]){4}", message = "Beta key have 4 algorithms.")
    private String betaKey;
    private DataBase.Entities.User user;

    public UserController() {
    }

    public User getSelected() {
        if (selected == null) {
            selected = new User();
        }
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    public boolean getKeepLoggedIn() {
        return keepLoggedIn;
    }

    public void setKeepLoggedIn(boolean keepLoggedIn) {
        this.keepLoggedIn = keepLoggedIn;
    }

    public boolean isFirstLogIn() {
        return firstLogIn;
    }

    public void setFirstLogIn(boolean firstLogIn) {
        this.firstLogIn = firstLogIn;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public String getBetaKey() {
        return betaKey;
    }

    public void setBetaKey(String betaKey) {
        this.betaKey = betaKey;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserFacade getFacade() {
        return ejbFacade;
    }

    public User prepareCreate() {
        selected = new User();
        initializeEmbeddableKey();
        return selected;
    }

    public String createUser() {

        selected.setUsername(selected.getName().toLowerCase() + selected.getLastname().toLowerCase());

        create();

        cookieHelper.addCookie("keepLoggedIn", "", 0);
        cookieHelper.addCookie("username", "", 0);
        cookieHelper.addCookie("password", "", 0);
        cookieHelper.addCookie("firstLogIn", "true", 999999999);

        firstLogIn = true;

        String outcome = requestSignIn();

        if (outcome.isEmpty()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Bundle").getString("IncorrectCredentials"));
        }

        return outcome;
    }

    public void create() {
        selected.setPassword(PasswordService.getInstance().encrypt(password));
        persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("UserCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        selected.setPassword(PasswordService.getInstance().encrypt(password));
        cookieHelper.addCookie("username", selected.getUsername(), 999999999);
        cookieHelper.addCookie("password", PasswordService.getInstance().encrypt(password), 999999999);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("UserUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("UserDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<User> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(selected);
                }
                if (persistAction == PersistAction.DELETE) {
                    getFacade().remove(selected);
                }
                if (persistAction == PersistAction.CREATE) {
                    if (!Objects.equals(betaKey, "0212")) {
                        throw new EJBException();
                    }
                    getFacade().create(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public User getUser(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<User> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<User> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    private String requestSignIn() {
        DataBase.Entities.User user = new DataBase.Entities.User();
        Header header = null;

        System.out.println(selected);

        if (keepLoggedIn == false) {
            user.setUsername(selected.getUsername());
            user.setPassword(PasswordService.getInstance().encrypt(password));
        } else if (firstLogIn == true) {
            user.setUsername(selected.getUsername());
            user.setPassword(PasswordService.getInstance().encrypt(password));
        } else {
            user.setUsername(selected.getUsername());
            if (selected.getPassword() != null) {
                user.setPassword(selected.getPassword());
            } else {
                user.setPassword(PasswordService.getInstance().encrypt(password));
            }
        }

        HttpManager httpManager = new HttpManager();
        httpManager.auth(user.getUsername(), user.getPassword());
        System.out.println("CODE: " + httpManager.getHttpCode());

        if (httpManager.getHttpCode() == 200) {
            HttpSession session = (HttpSession) FacesContext.
                    getCurrentInstance().getExternalContext().getSession(true);

            System.out.println("==================================================================");
            header = httpManager.getCookieLogin();

            System.out.println("==================================================================");

            if (selected.getIduser() == null) {
                selected = getFacade().findByUserName(selected.getUsername());
            }

//            session.setAttribute("userDB", user);
            FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().put("userPersistence", selected);
//            session.setAttribute("userPersistence", selected);
            FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().put("refreshModel", false);
//            session.setAttribute("refreshModel", false);
            FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().put("refreshTimeline", false);
//            session.setAttribute("refreshTimeline", false);
            FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().put("header", header);
//            session.setAttribute("header", header);

            if (keepLoggedIn == true && firstLogIn == true && httpManager.getHttpCode() == 200) {
                cookieHelper.addCookie("keepLoggedIn", "true", 999999999);
                cookieHelper.addCookie("username", selected.getUsername(), 999999999);
                cookieHelper.addCookie("password", selected.getPassword(), 999999999);
            }

            cookieHelper.addCookie("firstLogIn", "false", 999999999);

            return "schedule";
        }
        return "";
    }

    public void keepLoggedIn(ComponentSystemEvent event) throws IOException {

        Cookie cookieFirstLogIn = cookieHelper.getCookie("firstLogIn");

        if (cookieFirstLogIn != null && Objects.equals(cookieFirstLogIn.getValue(), "false")) {
            Cookie cookieUserName = cookieHelper.getCookie("username");
            Cookie cookiePassword = cookieHelper.getCookie("password");

            if (cookiePassword != null && cookieUserName != null) {
                getSelected().setUsername(cookieUserName.getValue());
                getSelected().setPassword(URLDecoder.decode(cookiePassword.getValue(), "UTF-8"));
            }

            Cookie cookie = cookieHelper.getCookie("keepLoggedIn");

            if (cookie != null) {
                keepLoggedIn = (Objects.equals(cookie.getValue(), "true"));
            } else {
                return;
            }

            String result = login();

            if (!result.isEmpty()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(
                        FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/clinical-tasks");
            } else {
                cookieHelper.addCookie("keepLoggedIn", "", 0);
                cookieHelper.addCookie("username", "", 0);
                cookieHelper.addCookie("password", "", 0);
                cookieHelper.addCookie("firstLogIn", "true", 0);
                return;
            }
        } else {
            cookieHelper.addCookie("firstLogIn", "true", 999999999);
            firstLogIn = true;
        }
    }

    public void sessionValidate(ComponentSystemEvent event) throws IOException {

        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userPersistence");

        if (user == null) {
            selected = null;
            FacesContext.getCurrentInstance().getExternalContext().redirect(
                    FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/sign-in");

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                    getExternalContext().getSession(false);

            if (session != null) {
                session.invalidate();
            }

            cookieHelper.addCookie("keepLoggedIn", "", 0);
            cookieHelper.addCookie("username", "", 0);
            cookieHelper.addCookie("password", "", 0);
            cookieHelper.addCookie("firstLogIn", "true", 0);
        }
    }

    public String login() {

        firstLogIn = (Objects.equals(cookieHelper.getCookie("firstLogIn"), "true"));
        String outcome = requestSignIn();

        if (outcome.isEmpty()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Bundle").getString("IncorrectCredentials"));
            cookieHelper.addCookie("keepLoggedIn", "", 0);
            cookieHelper.addCookie("username", "", 0);
            cookieHelper.addCookie("password", "", 0);
            cookieHelper.addCookie("firstLogIn", "true", 999999999);

            return "";
        }
        return outcome;
    }

    public String logout() {
        System.out.println("=======================================================");
        System.out.println("LOGOUT");

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();

        }

        cookieHelper.addCookie("keepLoggedIn", "", 0);
        cookieHelper.addCookie("username", "", 0);
        cookieHelper.addCookie("password", "", 0);
        cookieHelper.addCookie("firstLogIn", "true", 0);

        cookieHelper.addCookie("firstLogIn", "true", 999999999);
        firstLogIn = true;

        user = null;
        selected = null;

        System.out.println("=======================================================");

        return "logout";
    }

    public boolean isAdmin() {
        User user
                = (User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("userPersistence");

        if (user != null) {
            if (Objects.equals(user.getType(), "admin")) {
                return true;
            }
        }
        return false;
    }

    public String getUserName() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userPersistence");

        if (user != null) {
            return user.getName();
        }
        return "";
    }

    public void passValues(ActionEvent e) {
        System.out.println("\n**************************************************************************\n");
        System.out.println("\n---------------------------------A OBTER O ATRIBUTO ----------------------------------------------------\n");

        if (e.getComponent().getAttributes().get("user") instanceof User) {
            System.out.println("\n-------------------------------USER-------------------------\n");
            selected = (User) e.getComponent().getAttributes().get("user");
            System.out.println(selected.toString());
        }

        System.out.println("\n**************************************************************************\n");
    }

    public void validatePassword(ComponentSystemEvent event) {

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String pass = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmpassword");
        String confirmPass = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();
        String confirmPasswordId = uiInputConfirmPassword.getClientId();

        System.out.println("==============================================================");

        if (pass.isEmpty() || confirmPass.isEmpty()) {
            return;
        }
        if (!Objects.equals(pass, confirmPass)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords", " Passwords don't match");
            FacesContext.getCurrentInstance().addMessage(passwordId, msg);
            FacesContext.getCurrentInstance().addMessage(confirmPasswordId, msg);
            uiInputPassword.setValue("");
            uiInputConfirmPassword.setValue("");
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Passwords", " Passwords match");
            FacesContext.getCurrentInstance().addMessage(passwordId, msg);
            FacesContext.getCurrentInstance().addMessage(confirmPasswordId, msg);

        }
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getIduser());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), User.class.getName()});
                return null;
            }
        }

    }
}
