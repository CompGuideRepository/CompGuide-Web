package com.compguide.web.Persistence.Controllers;

import com.compguide.web.Persistence.Entities.Formula;
import com.compguide.web.Persistence.Controllers.util.JsfUtil;
import com.compguide.web.Persistence.Controllers.util.JsfUtil.PersistAction;
import com.compguide.web.Persistence.SessionBeans.FormulaFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("formulaController")
@SessionScoped
public class FormulaController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.FormulaFacade ejbFacade;
    private List<Formula> items = null;
    private Formula selected;

    public FormulaController() {
    }

    public Formula getSelected() {
        return selected;
    }

    public void setSelected(Formula selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FormulaFacade getFacade() {
        return ejbFacade;
    }

    public Formula prepareCreate() {
        selected = new Formula();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("FormulaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("FormulaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("FormulaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Formula> getItems() {
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

    public Formula getFormula(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Formula> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Formula> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Formula.class)
    public static class FormulaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FormulaController controller = (FormulaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "formulaController");
            return controller.getFormula(getKey(value));
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
            if (object instanceof Formula) {
                Formula o = (Formula) object;
                return getStringKey(o.getIdformula());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Formula.class.getName()});
                return null;
            }
        }

    }

}
