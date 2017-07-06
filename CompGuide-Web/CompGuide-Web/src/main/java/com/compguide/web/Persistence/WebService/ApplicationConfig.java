/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.WebService;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author António
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.compguide.web.Persistence.WebService.AutenticationFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.ConditionFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.ConditionSetFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.CyclePartDefinitionFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.CyclePartPeriodicityFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.DurationFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.EventFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.ExamFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.FormulaFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.GeneratedTaskFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.GuideExecFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.GuidelineFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.MedicationFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.NonMedicationFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.NotificationFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.ObservationFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.OutcomeFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.PatientFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.PeriodicityFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.ProcedureFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.ScheduleTaskFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.StopConditionSetFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.TaskFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.TemporalElementFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.TemporalOperatorFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.TemporalRestrictionFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.TemporalUnitFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.UserFacadeREST.class);
        resources.add(com.compguide.web.Persistence.WebService.WaitingTimeFacadeREST.class);
    }

}
