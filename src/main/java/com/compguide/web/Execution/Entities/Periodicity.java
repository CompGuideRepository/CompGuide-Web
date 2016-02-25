package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 20-08-2013 Time: 22:53 To
 * change this template use File | Settings | File Templates.
 */
public class Periodicity {

    private String id;
    private ArrayList<ConditionSet> stopConditionSet;
    private Integer repetitionValue;
    private String temporalUnit;
    private Double periodicityValue;
    private Duration duration;
    private CyclePartDefinition cyclePartDefinition;

    public Periodicity() {
        stopConditionSet = new ArrayList<ConditionSet>();
    }

    public static Periodicity fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Periodicity.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addStopConditionSet(ConditionSet conditionSet) {
        this.stopConditionSet.add(conditionSet);
    }

    public Integer getRepetitionValue() {
        return repetitionValue;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public CyclePartDefinition getCyclePartDefinition() {
        return cyclePartDefinition;
    }

    public void setCyclePartDefinition(CyclePartDefinition cyclePartDefinition) {
        this.cyclePartDefinition = cyclePartDefinition;
    }

    public void setRepetitionValue(Integer repetitionValue) {
        this.repetitionValue = repetitionValue;
    }

    public String getTemporalUnit() {
        return temporalUnit;
    }

    public void setTemporalUnit(String temporalUnit) {
        this.temporalUnit = temporalUnit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ConditionSet> getStopConditionSet() {
        return stopConditionSet;
    }

    public Double getPeriodicityValue() {
        return periodicityValue;
    }

    public void setPeriodicityValue(Double periodicityValue) {
        this.periodicityValue = periodicityValue;
    }

    public void setStopConditionSet(ArrayList<ConditionSet> stopConditionSet) {
        this.stopConditionSet = stopConditionSet;
    }

    public boolean isRepetition() {
        if (repetitionValue != null) {
            return true;
        }

        return false;
    }

    public boolean isDuration() {
        if (duration != null) {
            return true;
        }

        return false;
    }

    public boolean isStopCondition() {
        if (stopConditionSet != null && !stopConditionSet.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean haveCyclePart() {
        if (cyclePartDefinition != null) {
            return true;
        }
        return false;
    }
}
