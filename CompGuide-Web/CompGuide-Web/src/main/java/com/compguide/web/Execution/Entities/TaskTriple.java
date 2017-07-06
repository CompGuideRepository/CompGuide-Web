package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 29-08-2013 Time: 12:00 To
 * change this template use File | Settings | File Templates.
 */
public class TaskTriple {

    private String task;
    private String sync;
    private String id;

    public TaskTriple() {

    }

    public TaskTriple(String task, String sync, String id) {
        this.task = task;
        this.sync = sync;
        this.id = id;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean equals(TaskTriple tp) {
        return ((this.getTask().equals(tp.getTask()) && this.getSync().equals(this.getSync())) && this.getId().equals(tp.getId()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isSync() {
        return (this.getTask().equals(this.getSync()));
    }

    public static TaskTriple fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TaskTriple.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
