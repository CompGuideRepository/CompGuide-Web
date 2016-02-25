package com.compguide.web.Persistence.Controllers;

import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.Controllers.util.JsfUtil;
import com.compguide.web.Persistence.Controllers.util.JsfUtil.PersistAction;
import com.compguide.web.Persistence.SessionBeans.DurationFacade;

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

@Named("durationController")
@SessionScoped
public class DurationController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.DurationFacade ejbFacade;
    private List<Duration> items = null;
    private Duration selected;

    public DurationController() {
    }

    public Duration getSelected() {
        return selected;
    }

    public void setSelected(Duration selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DurationFacade getFacade() {
        return ejbFacade;
    }

    public Duration prepareCreate() {
        selected = new Duration();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("DurationCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("DurationUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("DurationDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Duration> getItems() {
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

    public Duration getDuration(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Duration> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Duration> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Duration.class)
    public static class DurationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DurationController controller = (DurationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "durationController");
            return controller.getDuration(getKey(value));
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
            if (object instanceof Duration) {
                Duration o = (Duration) object;
                return getStringKey(o.getDurationID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Duration.class.getName()});
                return null;
            }
        }

    }

}
