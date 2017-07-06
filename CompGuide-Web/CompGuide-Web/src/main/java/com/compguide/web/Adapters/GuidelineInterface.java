/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Execution.Entities.ClinicalTask;

/**
 *
 * @author António
 */
public interface GuidelineInterface {

    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task);

    public Object getObject();

    public void passObject(Object object);
}
