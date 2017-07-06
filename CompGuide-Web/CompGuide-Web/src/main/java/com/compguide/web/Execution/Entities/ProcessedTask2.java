package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 28-08-2013 Time: 14:29 To
 * change this template use File | Settings | File Templates.
 */
public class ProcessedTask2 {

    private ArrayList<ClinicalTask> tasks;
    private TaskController controller;

    public ProcessedTask2() {
        tasks = new ArrayList<ClinicalTask>();
        controller = new TaskController();
    }

    public static ProcessedTask2 fromJson(String json) {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        return gson.fromJson(json, ProcessedTask2.class);
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();

        return gson.toJson(this);
    }

    public TaskController getController() {
        return controller;
    }

    public void setController(TaskController controller) {
        this.controller = controller;
    }

    public ArrayList<ClinicalTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ClinicalTask> tasks) {
        this.tasks = tasks;
    }
}
