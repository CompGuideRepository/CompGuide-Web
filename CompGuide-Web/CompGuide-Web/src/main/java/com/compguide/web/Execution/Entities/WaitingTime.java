/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Execution.Entities;

/**
 *
 * @author António
 */
public class WaitingTime {

    private Double exactWaitingTime;
    private Double minWaitingTime;
    private Double maxWaitingTime;
    private String temporalUnit;

    public Double getExactWaitingTime() {
        return exactWaitingTime;
    }

    public void setExactWaitingTime(Double exactWaitingTime) {
        this.exactWaitingTime = exactWaitingTime;
    }

    public Double getMinWaitingTime() {
        return minWaitingTime;
    }

    public void setMinWaitingTime(Double minWaitingTime) {
        this.minWaitingTime = minWaitingTime;
    }

    public Double getMaxWaitingTime() {
        return maxWaitingTime;
    }

    public void setMaxWaitingTime(Double maxWaitingTime) {
        this.maxWaitingTime = maxWaitingTime;
    }

    public String getTemporalUnit() {
        return temporalUnit;
    }

    public void setTemporalUnit(String temporalUnit) {
        this.temporalUnit = temporalUnit;
    }

    public boolean asExactValue() {
        if (exactWaitingTime != null) {
            return true;
        }
        return false;
    }

    public boolean asInterval() {
        if (minWaitingTime != null && maxWaitingTime != null) {
            return true;
        }
        return false;
    }

}
