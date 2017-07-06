/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.Condition;
import com.compguide.web.Persistence.Entities.CyclePartDefinition;
import com.compguide.web.Persistence.Entities.CyclePartPeriodicity;
import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.Entities.Periodicity;
import com.compguide.web.Persistence.SessionBeans.PeriodicityFacade;
import com.compguide.web.Persistence.Entities.StopConditionSet;
import com.compguide.web.Persistence.Entities.TemporalUnit;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author António
 */
//@Singleton
//@Startup
//@LocalBean
@Named
@SessionScoped
public class PeriodicityComposite implements Serializable {

    private Periodicity periodicity;
    private CyclePartDefinition cyclePartDefinition;
    private Duration duration;
    private TemporalUnit temporalUnit;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.PeriodicityFacade ejbPeriodicityFacade;
    @Inject
    private TemporalUnitComposite temporalUnitComposite;
    @Inject
    private DurationComposite durationComposite;
    @Inject
    private CyclePartDefinitionComposite cyclePartDefinitionComposite;
    @Inject
    private StopConditionSetComposite stopConditionSetComposite;

    public PeriodicityComposite() {
        periodicity = new Periodicity();
    }

    public void setCyclePartDefinition(Duration durationID, CyclePartPeriodicity cyclePartPeriodicityID) {
        this.cyclePartDefinition = new CyclePartDefinition(durationID, cyclePartPeriodicityID);
        this.periodicity.setCyclePartDefinitionID(cyclePartDefinition);
    }

    public void setPeriodicity(double periodicityValue, Integer repetitionValue) {
        this.periodicity = new Periodicity(periodicityValue, repetitionValue);
    }

    public void setDuration(Double minDurationValue, Double maxDurationValue,
            Double durationValue, TemporalUnit temporalDurationUnit) {
        this.duration = new Duration(minDurationValue, maxDurationValue, durationValue, temporalDurationUnit);
        this.periodicity.setDurationID(duration);
    }

    public void setPeriodicity(double periodicityValue) {
        this.periodicity = new Periodicity(periodicityValue);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        this.periodicity.setDurationID(duration);
    }

    public void setTemporalUnit(TemporalUnit temporalUnit) {
        this.temporalUnit = temporalUnit;
        this.periodicity.setTemporalUnitID(temporalUnit);
    }

    public void setCyclePartDefinition(CyclePartDefinition cyclePartDefinition) {
        this.cyclePartDefinition = cyclePartDefinition;
        this.periodicity.setCyclePartDefinitionID(cyclePartDefinition);
    }

    public void setTemporalUnit(String value) {
        this.temporalUnit = new TemporalUnit(value);
        this.periodicity.setTemporalUnitID(temporalUnit);
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    private PeriodicityFacade getPeriodicityFacade() {
        return ejbPeriodicityFacade;
    }

    public void check() {
        Periodicity per = checkPeriodicity(periodicity);

        periodicity = per;
    }

    public PeriodicityComposite checkPeriodicity(com.compguide.web.Execution.Entities.Periodicity periodicity) {

        setPeriodicity(new Periodicity(
                periodicity.getPeriodicityValue(),
                periodicity.getRepetitionValue())
        );

        setTemporalUnit(periodicity.getTemporalUnit());

        if (periodicity.haveCyclePart()) {
            if (periodicity.getCyclePartDefinition().asPeriodicity()) {
                cyclePartDefinitionComposite.setCyclePartPeriodicity(
                        periodicity.getCyclePartDefinition().getCyclePartPeriodicity().getRepetitionValue(),
                        periodicity.getCyclePartDefinition().getCyclePartPeriodicity().getPeriodicityValue(),
                        periodicity.getCyclePartDefinition().getCyclePartPeriodicity().getDuration().getMinDurationValue(),
                        periodicity.getCyclePartDefinition().getCyclePartPeriodicity().getDuration().getMaxDurationValue(),
                        periodicity.getCyclePartDefinition().getCyclePartPeriodicity().getDuration().getDurationValue(),
                        new TemporalUnit(periodicity.getCyclePartDefinition().getCyclePartPeriodicity().getDuration().getTemporalUnit()),
                        new TemporalUnit(periodicity.getCyclePartDefinition().getCyclePartPeriodicity().getTemporalUnit()));
            }
            if (periodicity.getCyclePartDefinition().asDuration()) {
                cyclePartDefinitionComposite.setDuration(
                        periodicity.getCyclePartDefinition().getDuration().getMinDurationValue(),
                        periodicity.getCyclePartDefinition().getDuration().getMaxDurationValue(),
                        periodicity.getCyclePartDefinition().getDuration().getDurationValue(),
                        new TemporalUnit(periodicity.getCyclePartDefinition().getDuration().getTemporalUnit()));
            }

            setCyclePartDefinition(cyclePartDefinitionComposite.getCyclePartDefinition());
        }

        if (periodicity.isDuration()) {
            setDuration(periodicity.getDuration().getMinDurationValue(),
                    periodicity.getDuration().getMaxDurationValue(),
                    periodicity.getDuration().getDurationValue(),
                    new TemporalUnit(periodicity.getDuration().getTemporalUnit()));;
        }

        this.check();

        if (periodicity.isStopCondition()) {
            stopConditionSetComposite.setStopConditionSet(new StopConditionSet(this.periodicity.getPeriodicityID().toString(), Boolean.TRUE));
            stopConditionSetComposite.setPeriodicity(this.periodicity);

            for (com.compguide.web.Execution.Entities.Condition condition : periodicity.getStopConditionSet().get(0).getCondition()) {
                if (condition.getIsNumeric() == true) {
                    stopConditionSetComposite.addCondition(new Condition(
                            Double.parseDouble(condition.getValue()), null,
                            condition.getComparisonOperator(), condition.getParameterIdentifier().get(0),
                            condition.getConditionParameter(), Boolean.FALSE,
                            Boolean.FALSE, condition.getUnit()));
                } else {
                    stopConditionSetComposite.addCondition(new Condition(
                            null, condition.getValue(),
                            condition.getComparisonOperator(), condition.getParameterIdentifier().get(0),
                            condition.getConditionParameter(), Boolean.FALSE,
                            Boolean.FALSE, condition.getUnit()));
                }
            }

            stopConditionSetComposite.check();
        }

        return this;
    }

    public Periodicity checkPeriodicity(Periodicity periodicity) {
        Periodicity aux = null;

        if (periodicity.isDuration()) {
            TemporalUnit temporalUnit = temporalUnitComposite.checkTemporalUnit(
                    periodicity.getTemporalUnitID()
            );
            Duration dur = durationComposite.checkDuration(
                    periodicity.getDurationID()
            );

            periodicity.setDurationID(dur);
            periodicity.setTemporalUnitID(temporalUnit);

            if (periodicity.isCyclePart()) {
                CyclePartDefinition partDefinition = cyclePartDefinitionComposite.checkCyclePartDefinition(
                        periodicity.getCyclePartDefinitionID()
                );

                periodicity.setCyclePartDefinitionID(partDefinition);

                aux = getPeriodicityFacade().
                        findByPeriodicityValueTemporalUnitDurationAndCyclePartDefinition(
                                partDefinition, dur,
                                temporalUnit, periodicity.getPeriodicityValue()
                        );
            } else {
                aux = getPeriodicityFacade().
                        findByPeriodicityValueTemporalUnitAndDuration(
                                dur, temporalUnit, periodicity.getPeriodicityValue()
                        );
            }
        }

        if (periodicity.isRepetition()) {
            TemporalUnit temporalUnit = temporalUnitComposite.checkTemporalUnit(
                    periodicity.getTemporalUnitID()
            );

            periodicity.setTemporalUnitID(temporalUnit);

            if (periodicity.isCyclePart()) {
                CyclePartDefinition partDefinition = cyclePartDefinitionComposite.checkCyclePartDefinition(
                        periodicity.getCyclePartDefinitionID()
                );

                periodicity.setCyclePartDefinitionID(partDefinition);

                aux = getPeriodicityFacade().
                        findByPeriodicityValueTemporalUnitRepetitonValueAndCyclePartDefinition(
                                partDefinition, periodicity.getRepetitionValue(),
                                temporalUnit, periodicity.getPeriodicityValue()
                        );
            } else {
                aux = getPeriodicityFacade().findByPeriodicityValueTemporalUnitAndRepetitonValue(
                        periodicity.getRepetitionValue(), temporalUnit, periodicity.getPeriodicityValue()
                );
            }
        }

        if (aux == null) {
            getPeriodicityFacade().create(periodicity);
            aux = periodicity;
        }

        return aux;
    }

}
