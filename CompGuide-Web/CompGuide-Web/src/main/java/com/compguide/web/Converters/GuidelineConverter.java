/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Converters;

import com.compguide.web.Persistence.Entities.Guideline;
import com.compguide.web.Service.GuidelineService;
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
@FacesConverter("guidelineConverter")
public class GuidelineConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println("=====================CONVERTER======================");
        System.out.println("================GETASOBJECT METHOD==================");
        if (value != null && value.trim().length() > 0) {
            try {
                GuidelineService service = (GuidelineService) fc.getExternalContext().getApplicationMap().get("guidelineService");
                if (service != null) {
                    List<Guideline> guidelines = service.getGuidelines();
                    for (Guideline guideline : guidelines) {
                        if (guideline.getIdguideline() == Integer.parseInt(value)) {
                            System.out.println("=====================RETURN======================");
                            System.out.println("=================================================");
                            return guideline;
                        }
                    }
                    System.out.println("==================RETURN NULL====================");
                    System.out.println("=================================================");
                    return null;
                }

            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid guideline."));
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
            return String.valueOf(((Guideline) object).getIdguideline());
        } else {
            System.out.println("==================RETURN NULL====================");
            System.out.println("=================================================");
            return null;
        }
    }
}
