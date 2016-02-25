package com.compguide.web.Persistence.Controllers;

import com.compguide.web.Persistence.Entities.Medication;
import com.compguide.web.Persistence.Controllers.util.JsfUtil;
import com.compguide.web.Persistence.Controllers.util.JsfUtil.PersistAction;
import com.compguide.web.Persistence.SessionBeans.MedicationFacade;

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

@Named("medicationController")
@SessionScoped
public class MedicationController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.MedicationFacade ejbFacade;
    private List<Medication> items = null;
    private Medication selected;

    public MedicationController() {
    }

    public Medication getSelected() {
        return selected;
    }

    public void setSelected(Medication selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MedicationFacade getFacade() {
        return ejbFacade;
    }

    public Medication prepareCreate() {
        selected = new Medication();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("MedicationCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("MedicationUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("MedicationDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Medication> getItems() {
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

    public Medication getMedication(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Medication> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Medication> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Medication.class)
    public static class MedicationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MedicationController controller = (MedicationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "medicationController");
            return controller.getMedication(getKey(value));
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
            if (object instanceof Medication) {
                Medication o = (Medication) object;
                return getStringKey(o.getIdmedication());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Medication.class.getName()});
                return null;
            }
        }

    }

}
