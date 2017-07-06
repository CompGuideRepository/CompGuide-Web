/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;

/**
 *
 * @author António
 */
public class CyclePartDefinition {

    private Duration duration;
    private CyclePartPeriodicity cyclePartPeriodicity;

    public CyclePartDefinition() {
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public CyclePartPeriodicity getCyclePartPeriodicity() {
        return cyclePartPeriodicity;
    }

    public void setCyclePartPeriodicity(CyclePartPeriodicity cyclePartPeriodicity) {
        this.cyclePartPeriodicity = cyclePartPeriodicity;
    }

    public static CyclePartDefinition fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CyclePartDefinition.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean asDuration() {
        if (duration != null) {
            return true;
        }

        return false;
    }

    public boolean asPeriodicity() {
        if (cyclePartPeriodicity != null) {
            return true;
        }
        return false;
    }
}
