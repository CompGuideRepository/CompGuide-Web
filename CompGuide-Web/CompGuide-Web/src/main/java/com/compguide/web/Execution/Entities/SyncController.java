package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 29-08-2013 Time: 11:58 To
 * change this template use File | Settings | File Templates.
 */
public class SyncController {

    private ArrayDeque<String> syncTask;
    private ArrayDeque<String> pendingPlans;
    private ArrayList<String> alternativeTask;
    private ArrayList<TaskTriple> nextTask;

    public SyncController() {
        syncTask = new ArrayDeque<String>();
        pendingPlans = new ArrayDeque<String>();
        nextTask = new ArrayList<TaskTriple>();
        alternativeTask = new ArrayList<String>();
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static SyncController fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SyncController.class);
    }

    public ArrayList<TaskTriple> getNextTask() {
        return nextTask;
    }

    public void setNextTask(ArrayList<TaskTriple> nextTask) {
        this.nextTask = nextTask;
    }

    public ArrayDeque<String> getPendingPlans() {
        return pendingPlans;
    }

    public void setPendingPlans(ArrayDeque<String> pendingPlans) {
        this.pendingPlans = pendingPlans;
    }

    public ArrayDeque<String> getSyncTask() {
        return syncTask;
    }

    public void setSyncTask(ArrayDeque<String> syncTask) {
        this.syncTask = syncTask;
    }

    public void addTask(TaskTriple tp) {
        this.nextTask.add(tp);
    }

    public void pushPlan(String task) {
        pendingPlans.push(task);
    }

    public void pushSync(String task) {
        syncTask.push(task);
    }

    public void updateTask(TaskTriple tp, String task) {
        int i = 0;
        for (TaskTriple tpair : nextTask) {
            if (tpair.equals(tp)) {
                nextTask.remove(tpair);
                tpair.setTask(task);
                nextTask.add(tpair);
                return;
            }
            i++;
        }
        if (i == nextTask.size() - 1) {
            tp.setTask(task);
            nextTask.add(tp);
        }
    }

    public void updateParallelAlternate(TaskTriple tp, String task) {

    }

    public Boolean isSynced(String id) {
        Boolean test = Boolean.FALSE;
        for (TaskTriple tp : nextTask) {
            if (tp.getId().equals(id)) {
                test = tp.isSync();
            }
        }
        return test;
    }

    public Boolean isAlternativeTask(String task) {
        return (this.alternativeTask.contains(task));
    }

    public void removeAlternativeTask(TaskTriple task) {

        int i = 0;
        for (TaskTriple tp : this.getNextTask()) {
            if (tp.getId().equals(task.getId()) && !task.equals(tp)) {
                nextTask.remove(i);
            }
            i++;
        }
        alternativeTask.remove(task.getId());
    }

    public void addAlternativeTask(String id) {
        this.alternativeTask.add(id);
    }

    public Boolean removeTask(TaskTriple tp) {
        int i = 0;
        for (TaskTriple taskTriple : nextTask) {
            if (taskTriple.equals(tp)) {
                nextTask.remove(i);
                return Boolean.TRUE;
            }
            i++;
        }
        return Boolean.FALSE;
    }

    public Boolean isAllowed(String id) {

        if (syncTask.peek() != null) {
            return (isSynced(id) && id.equals(this.syncTask.peek()));
        } else {
            return isSynced(id);
        }

    }

    public void pushSyncTask(String syncTask) {
        this.syncTask.push(syncTask);
    }

    public void setAlternativeTask(ArrayList<String> alternativeTask) {
        this.alternativeTask = alternativeTask;
    }
}
