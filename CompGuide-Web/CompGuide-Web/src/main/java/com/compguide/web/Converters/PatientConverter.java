/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Converters;

import com.compguide.web.Persistence.Entities.Patient;
import com.compguide.web.Service.PatientService;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author António
 */
@FacesConverter("patientConverter")
public class PatientConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println("=====================CONVERTER======================");
        System.out.println("================GETASOBJECT METHOD==================");
        if (value != null && value.trim().length() > 0) {
            try {
                PatientService service = (PatientService) fc.getExternalContext().getApplicationMap().get("patientService");
                if (service != null) {
                    List<Patient> patients = service.getPatients();
                    for (Patient patient : patients) {
                        if (patient.getIdpatient() == Integer.parseInt(value)) {
                            System.out.println("=====================RETURN======================");
                            System.out.println("=================================================");
                            return patient;
                        }
                    }
                    System.out.println("==================RETURN NULL====================");
                    System.out.println("=================================================");
                    return null;
                }

            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid patient."));
            }
        } else {
            System.out.println("==================RETURN NULL====================");
            System.out.println("=================================================");
            return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        System.out.println("=====================CONVERTER======================");
        System.out.println("================GETASSTRING METHOD==================");
        System.out.println(uic.toString());
        if (object != null) {
            System.out.println("=====================RETURN======================");
            System.out.println("=================================================");
            return String.valueOf(((Patient) object).getIdpatient());
        } else {
            System.out.println("==================RETURN NULL====================");
            System.out.println("=================================================");
            return null;
        }
    }
}
