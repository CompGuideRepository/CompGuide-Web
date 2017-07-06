/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.ServerRequest;

import DataBase.Entities.User;
import com.compguide.web.Execution.Entities.ProcessedTask;
import com.compguide.web.Execution.Entities.TaskRequest;
import WebService.HttpManager;
import com.compguide.web.Persistence.Entities.Guideline;
import com.compguide.web.Persistence.Entities.Patient;
import java.util.ArrayList;
import org.apache.http.Header;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author António
 */
public class ServiceRequest {

//    private static String servicePath = "http://ec2-52-58-129-169.eu-central-1.compute.amazonaws.com/CompGuideCore/";
    private static String servicePath = "http://localhost:8080/CompGuideCore/";

    public static String requestCreateGuideExec(Header header, Guideline guideline, User user, Patient patient, String description) {
        ArrayList<BasicNameValuePair> valuePairGuideexec = new ArrayList<BasicNameValuePair>();

        valuePairGuideexec.add(new BasicNameValuePair("idguideline", guideline.getIdguideline().toString()));
        valuePairGuideexec.add(new BasicNameValuePair("iduser", user.getIduser()));
        valuePairGuideexec.add(new BasicNameValuePair("idpatient", patient.getIdpatient().toString()));
        valuePairGuideexec.add(new BasicNameValuePair("description", description));

        HttpManager http = new HttpManager();

        http.setURL(servicePath + "guideexec/");
        http.setValuePairs(valuePairGuideexec);

        String response = http.sendPut(header);

        if (http.getHttpCode() == 200) {
            return com.compguide.web.Utils.Utils.parseId(response);
        }

        return null;
    }

    public static ProcessedTask requestGetLastTask(Header header, String guideExecID) {

        HttpManager http = new HttpManager();

        http.setURL(servicePath + "execution/guideline2/last/" + guideExecID);

        ProcessedTask processedTask = new ProcessedTask();
        processedTask = (ProcessedTask) http.sendGetLastTask2(processedTask, header);

        if (processedTask == null) {
            processedTask = new ProcessedTask();
        }

        return processedTask;

    }

    public static ProcessedTask requestNextTask(Header header, ProcessedTask processedTask, TaskRequest taskRequest) {

        HttpManager http = new HttpManager();
        ArrayList<BasicNameValuePair> valuePairRequest = new ArrayList<BasicNameValuePair>();
        valuePairRequest.add(new BasicNameValuePair("request", taskRequest.toJson()));

        http.setValuePairs(valuePairRequest);

        http.setURL(servicePath + "execution/guideline2/next/");

        return (ProcessedTask) http.sendPostNext2(processedTask, header);
    }

}
