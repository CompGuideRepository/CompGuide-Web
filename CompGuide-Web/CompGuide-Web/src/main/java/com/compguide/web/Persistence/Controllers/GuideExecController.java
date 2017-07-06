package com.compguide.web.Persistence.Controllers;

import com.compguide.web.Execution.Entities.ProcessedTask;
import com.compguide.web.Execution.Entities.TaskQuadruple;
import com.compguide.web.Persistence.Entities.GuideExec;
import com.compguide.web.Persistence.Controllers.util.JsfUtil;
import com.compguide.web.Persistence.Controllers.util.JsfUtil.PersistAction;
import com.compguide.web.Persistence.Entities.Guideline;
import com.compguide.web.Persistence.Entities.Patient;
import com.compguide.web.Persistence.Entities.User;
import com.compguide.web.Persistence.SessionBeans.GuideExecFacade;
import com.compguide.web.Persistence.SessionBeans.UserFacade;
import com.compguide.web.owl.Parser.GuidelineHandler;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;

@Named("guideExecController")
@SessionScoped
public class GuideExecController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.GuideExecFacade ejbFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.UserFacade ejbUserFacade;
    private List<GuideExec> items = null;
    private GuideExec selected;
    private ProcessedTask processedTask;
    protected GuidelineHandler guidelineHandler;

    public GuideExecController() {
        guidelineHandler = new GuidelineHandler();
    }

    public GuideExec getSelected() {
        if (selected == null) {
            selected = new GuideExec();
        }
        return selected;
    }

    public void setSelected(GuideExec selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private GuideExecFacade getFacade() {
        return ejbFacade;
    }

    public UserFacade getUserFacade() {
        return ejbUserFacade;
    }

    public List<GuideExec> getList() {
        if (items == null) {
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userPersistence");

            items = getFacade().findByUserActiveGuidelines(
                    getUserFacade().find(user.getIduser()),
                    new Short((short) 0)
            );
        }
        return items;
    }

    public void create(ActionEvent e) {
        System.out.println("\n**************************************************************************\n");
        System.out.println("\n---------------------------------A OBTER O ATRIBUTO ----------------------------------------------------\n");

        Patient patient = null;
        Guideline guideline = null;

        if (e.getComponent().getAttributes().get("guideline") instanceof Guideline) {
            System.out.println("\n-------------------------------Guideline-------------------------\n");
            guideline = (Guideline) e.getComponent().getAttributes().get("guideline");
            System.out.println(guideline.toString());
        }

        if (e.getComponent().getAttributes().get("patient") instanceof Patient) {
            System.out.println("\n----------------------------------Patient-------------------------------\n");
            patient = (Patient) e.getComponent().getAttributes().get("patient");
            System.out.println(patient.toString());
        }

        Gson gson = new Gson();

        guidelineHandler.loadGuideline();
        String firstPlan = guidelineHandler.getClinicalPracticeGuideline(guideline.getIdentifier()).getPlan().getId();

        com.compguide.web.Execution.Entities.TaskController controller = new com.compguide.web.Execution.Entities.TaskController();
        controller.addTask(new TaskQuadruple(firstPlan, "-1", "-1", "-1"));

        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userPersistence");

        selected.setNextTasks(gson.toJson(controller));
        selected.setIdpatient(patient);
        selected.setIdguideline(guideline);
        selected.setIduser(user);
        selected.setStart(new Date());
        selected.setCompleted(new Short((short) 0));

        FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().put("refreshModel", true);
        FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().put("refreshTimeline", true);

        selected.setIdguideexec(null);
        create();

        System.out.println("\n**************************************************************************\n");

    }

    public void displayMessage(FacesMessage.Severity severity, String summary, String details) {
        FacesMessage msg = new FacesMessage(severity, summary, details);

        FacesContext.getCurrentInstance().addMessage("message", msg);
    }

    public GuideExec prepareCreate() {
        selected = new GuideExec();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("GuideExecCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("GuideExecUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("GuideExecDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<GuideExec> getItems() {
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

    public GuideExec getGuideExec(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<GuideExec> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<GuideExec> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void passValues(ActionEvent e) {
        GuideExec guideExec = null;
        if (e.getComponent().getAttributes().get("guidelineexec") instanceof GuideExec) {
            guideExec = (GuideExec) e.getComponent().getAttributes().get("guidelineexec");
            selected = guideExec;
        }

        destroy();

    }

    @FacesConverter(forClass = GuideExec.class)
    public static class GuideExecControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GuideExecController controller = (GuideExecController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "guideExecController");
            return controller.getGuideExec(getKey(value));
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
            if (object instanceof GuideExec) {
                GuideExec o = (GuideExec) object;
                return getStringKey(o.getIdguideexec());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), GuideExec.class.getName()});
                return null;
            }
        }

    }

}
