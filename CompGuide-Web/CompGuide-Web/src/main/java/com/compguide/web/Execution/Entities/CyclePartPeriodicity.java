/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 *
 * @author António
 */
public class CyclePartPeriodicity {

    private String id;
    private ArrayList<ConditionSet> stopConditionSet;
    private Integer repetitionValue;
    private String temporalUnit;
    private Double periodicityValue;
    private Duration duration;

    public CyclePartPeriodicity() {
        stopConditionSet = new ArrayList<ConditionSet>();
    }

    public static CyclePartPeriodicity fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CyclePartPeriodicity.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void addStopConditionSet(ConditionSet conditionSet) {
        this.stopConditionSet.add(conditionSet);
    }

    public Integer getRepetitionValue() {
        return repetitionValue;
    }

    public Double getPeriodicityValue() {
        return periodicityValue;
    }

    public void setPeriodicityValue(Double periodicityValue) {
        this.periodicityValue = periodicityValue;
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

    public void setStopConditionSet(ArrayList<ConditionSet> stopConditionSet) {
        this.stopConditionSet = stopConditionSet;
    }

    public boolean asRepetition() {
        if (repetitionValue != null) {
            return true;
        }

        return false;
    }

    public boolean asDuration() {
        if (duration != null) {
            return true;
        }

        return false;
    }

}
