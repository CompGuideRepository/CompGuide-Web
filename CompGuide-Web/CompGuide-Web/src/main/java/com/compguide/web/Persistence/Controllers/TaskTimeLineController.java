/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Controllers;

import com.compguide.web.Adapters.OutcomeAdapter;
import com.compguide.web.Adapters.TemporalElementAdapter;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.ProcessedTask;
import com.compguide.web.Execution.Entities.TaskRequest;
import com.compguide.web.Persistence.Entities.Event;
import com.compguide.web.Persistence.Entities.GuideExec;
import com.compguide.web.Persistence.Entities.Notification;
import com.compguide.web.Persistence.Entities.Outcome;
import com.compguide.web.Persistence.Entities.Periodicity;
import com.compguide.web.Persistence.Entities.ScheduleTask;
import com.compguide.web.Persistence.SessionBeans.EventFacade;
import com.compguide.web.Persistence.SessionBeans.GuideExecFacade;
import com.compguide.web.Persistence.SessionBeans.NotificationFacade;
import com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade;
import com.compguide.web.Persistence.SessionBeans.StopConditionSetFacade;
import com.compguide.web.Persistence.SessionBeans.TemporalElementFacade;
import com.compguide.web.Persistence.Entities.StopConditionSet;
import com.compguide.web.Persistence.Entities.TemporalElement;
import com.compguide.web.ServerRequest.ServiceRequest;
import com.compguide.web.Utils.CustomStyleClass;
import com.compguide.web.Utils.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.Header;
import org.primefaces.extensions.event.timeline.TimelineSelectEvent;
import org.primefaces.extensions.model.timeline.TimelineEvent;
import org.primefaces.extensions.model.timeline.TimelineModel;

/**
 *
 * @author António
 */
@Named("taskTimeLineController")
@ViewScoped
@ManagedBean
@RequestScoped
public class TaskTimeLineController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade ejbScheduleTaskFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.TemporalElementFacade ejbTemporalElementFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.EventFacade ejbEventFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.NotificationFacade ejbNotificationFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.GuideExecFacade ejbGuideExecFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.StopConditionSetFacade ejbStopConditionSetFacade;
    @Inject
    private TemporalElementAdapter temporalElementAdapter;
    @Inject
    private OutcomeAdapter outcomeAdapter;

    private Event selected;
    private TimelineModel model;
    private Outcome selectedOutcome;
    private StopConditionSet selectedStopConditionSet;
    private ProcessedTask processedTask;

    @PostConstruct
    private void init() {
        model = new TimelineModel();

        List<Event> events = getEventFacade().findAll();
        Calendar cal = Calendar.getInstance();

        List<Notification> notifications = getNotificationFacade().findAll();

        for (Event event : events) {
            if (!event.getChecked()) {
                cal.setTime(event.getEndDate());
                TimelineEvent timelineEvent = new TimelineEvent(
                        event.getEventID() + " "
                        + event.getScheduleTaskID().getTaskIdentifier(),
                        cal.getTime(),
                        false,
                        "Task " + event.getScheduleTaskID().getTaskIdentifier()
                );

                timelineEvent.setStyleClass(CustomStyleClass.instance().getStyleClass());

                model.add(timelineEvent);
            }
        }
    }

    public void refresh() {
        System.out.println("====================REFRESH MODEL========================");
        
        boolean refresh = false;

        try {
            refresh = (Boolean) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("refreshTimeline");
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);

        }

        if (refresh) {
            System.out.println("================REFRESH COMPLETED===========================");
            FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().put("refreshTimeline", false);
            init();

        }
    }

    public ScheduleTaskFacade getScheduleTaskFacade() {
        return ejbScheduleTaskFacade;
    }

    public TemporalElementFacade getTemporalElementFacade() {
        return ejbTemporalElementFacade;
    }

    public EventFacade getEventFacade() {
        return ejbEventFacade;
    }

    public NotificationFacade getNotificationFacade() {
        return ejbNotificationFacade;
    }

    public GuideExecFacade getGuideExecFacade() {
        return ejbGuideExecFacade;
    }

    public StopConditionSetFacade getStopConditionSetFacade() {
        return ejbStopConditionSetFacade;
    }

    public Event getSelected() {
        return selected;
    }

    public void setSelected(Event selected) {
        this.selected = selected;
    }

    public TimelineModel getModel() {
        return model;
    }

    public void setModel(TimelineModel model) {
        this.model = model;
    }

    public ProcessedTask getProcessedTask() {
        if (processedTask == null) {
            processedTask = new ProcessedTask();
        }
        return processedTask;
    }

    public void setProcessedTask(ProcessedTask processedTask) {
        this.processedTask = processedTask;
    }

    public boolean isAction() {
        if (getProcessedTask().getTasks().size() > 0) {
            if (containsClass(processedTask, "Action")) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnd() {
        if (getProcessedTask().getTasks().size() > 0) {
            if (containsClass(processedTask, "End")) {
                return true;
            }
        }
        return false;
    }

    public boolean isQuestion() {
        if (getProcessedTask().getTasks().size() > 0) {
            if (containsClass(processedTask, "Question")) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlan() {
        if (getProcessedTask().getTasks().size() > 0) {
            if (containsClass(processedTask, "Plan")) {
                return true;
            }
        }
        return false;
    }

    public boolean isDecision() {
        if (getProcessedTask().getDecision() != null) {
            if (getProcessedTask().getDecision().getValue() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isTaskStopCondition() {
        if (selected != null) {
            TemporalElement temporalElement = getTemporalElementFacade().
                    findByScheduleTaskID(selected.getScheduleTaskID());

            if (temporalElement != null) {
                if (temporalElement.isPeriodicity()) {
                    StopConditionSet selectedStopCondition = getStopConditionSetFacade().
                            findByPeriodicityID(temporalElement.getPeriodicityID());

                    if (!selectedStopCondition.getAsked()) {
                        selectedStopConditionSet = selectedStopCondition;
                        return true;
                    }

                }
            }
        }

        return false;
    }

    public void onSelect(TimelineSelectEvent e) {
        TimelineEvent timelineEvent = e.getTimelineEvent();
        List<Event> events = getEventFacade().findAll();

        for (Event event : events) {
            if (Objects.equals(
                    Integer.parseInt(e.getTimelineEvent().getData().toString().split(" ")[0]), event.getEventID()
            )) {
                selected = event;
                FacesContext.getCurrentInstance().
                        getExternalContext().getSessionMap().put("guideexec", selected.getScheduleTaskID().getGuideExecID());
                processedTask = callServiceLastTask(selected.getScheduleTaskID().getGuideExecID());

                FacesContext.getCurrentInstance().
                        getExternalContext().getSessionMap().put("processedTask", processedTask);

//                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected event:", timelineEvent.getData().toString());
//                FacesContext.getCurrentInstance().addMessage(null, msg);
                break;
            }
        }

    }

    public void checkTask() {
        List<TimelineEvent> timelineEvents = new ArrayList<>();

        for (TimelineEvent timelineEvent : timelineEvents) {
            if (Objects.equals(
                    Integer.parseInt(timelineEvent.getData().toString().split(" ")[0]), selected.getEventID()
            )) {
                verifyTemporalConstraints(selected.getScheduleTaskID(), selected);

                if (selected.getScheduleTaskID().getCurrentNumberOfRepetitions() == 0) {
                    verifyTaskConditions(processedTask, selected.getScheduleTaskID());
                }

                incrementTaskRepetitionValue();
                selected.setChecked(true);

                Event ev = Event.clone(selected);
                ScheduleTask t = ScheduleTask.clone(selected.getScheduleTaskID());

                getScheduleTaskFacade().edit(t);
                ev.setScheduleTaskID(t);
                getEventFacade().edit(ev);

                selected = ev;

                if (Objects.equals(selected.getNumberOfRepetitions(), selected.getScheduleTaskID().getCurrentNumberOfRepetitions())) {
                    selected.getScheduleTaskID().setCompleted(true);
                    if (canRequestNextTask()) {
                        //pedir nova task
                        // esperemos que o valor de completed da task Ã¡ tenha sido alterado XD
                        requestNextTask(selected, processedTask);
                    }
                }

                deleteEventNotification(selected, getNotificationFacade().findByEventID(selected));

                model.delete(timelineEvent);
                break;
            }
        }

        displayMessage(FacesMessage.SEVERITY_INFO, "Task",
                "The Task " + selected.getScheduleTaskID().getTaskIdentifier()
                + ", wich was started at " + selected.getScheduleTaskID().getStartDate().toString()
                + ", was successfully checked");

    }

    public ProcessedTask callServiceLastTask(GuideExec guideExecID) {
        Header header = (Header) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("header");

        ProcessedTask procTask = new ProcessedTask();
        procTask = ServiceRequest.requestGetLastTask(header, guideExecID.getIdguideexec().toString());
        return procTask;
    }

    private boolean canRequestNextTask() {
        /*
         * preferenceAlternativeTask
         * verificar se fez check a alguma das Tasks recebidas em ProcessedTask
         */
        if (Objects.equals(selected.getScheduleTaskID().getTaskFormat(), "preferenceAlternativeTask")
                && selected.getScheduleTaskID().getCompleted() == true) {
            return true;
        }

        /*
         * alternativeTask
         * verificar se fez check a alguma das Tasks recebidas em ProcessedTask
         */
        if (Objects.equals(selected.getScheduleTaskID().getTaskFormat(), "alternativeTask")
                && selected.getScheduleTaskID().getCompleted() == true) {
            return true;
        }

        /*
         * nextTask
         * verificar se fez check a alguma das Tasks recebidas em ProcessedTask
         */
        if (Objects.equals(selected.getScheduleTaskID().getTaskFormat(), "nextTask")
                && selected.getScheduleTaskID().getCompleted() == true) {
            return true;
        }
        /*
         * parallelTask
         * verificar se fez check de todas as recebidas em ProcessedTask
         * So pode fazer next Task apos todas as outras terem sido check
         */
        if (Objects.equals(selected.getScheduleTaskID().getTaskFormat(), "parallelTask")) {
            List<ScheduleTask> currentTasks = getScheduleTaskFacade().
                    findByGuideExecID(selected.getScheduleTaskID().getGuideExecID());
            int numberOfCompletedTasks = 0;
            int numberOfTasksToComplete = processedTask.getTasks().size();

            for (ClinicalTask clinicalTask : processedTask.getTasks()) {
                for (ScheduleTask currentTask : currentTasks) {
                    if (currentTask.getTaskFormat().equals(clinicalTask.getTaskFormat())
                            && currentTask.getTaskType().equals(clinicalTask.getTaskType())
                            && clinicalTask.getId().equals(currentTask.getTaskIdentifier())
                            && currentTask.getCompleted()) {

                        numberOfCompletedTasks++;

                        if (numberOfCompletedTasks == numberOfTasksToComplete) {
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }

    private void verifyTemporalConstraints(ScheduleTask selected, Event selectedEvent) {
        TemporalElement temporalElement = getTemporalElementFacade().findByScheduleTaskID(selected);

        if (!temporalElement.isEmpty()) {
            if (temporalElement.isDuration()
                    && temporalElement.getDurationID().isInterval()) {
                List<Event> events = getEventFacade().findByScheduleTaskID(selected);

                for (Event event : events) {
                    if (!Objects.equals(event, selectedEvent)
                            && Objects.equals(event.getStartDate(), selectedEvent.getStartDate())) {
                        deleteEventNotification(event, getNotificationFacade().findByEventID(event));
                        getEventFacade().remove(event);
                    }
                }
            }
            if (temporalElement.isPeriodicity()
                    && temporalElement.getPeriodicityID().isDuration()
                    && temporalElement.getPeriodicityID().getDurationID().isInterval()) {
                List<Event> events = getEventFacade().findByScheduleTaskID(selected);

                boolean taskFinished = Boolean.FALSE;

                if (Objects.equals(selected.getCurrentNumberOfRepetitions(), selectedEvent.getNumberOfRepetitions())) {
                    taskFinished = Boolean.TRUE;
                }

                verifyStopConditionConstraint(taskFinished, temporalElement.getPeriodicityID());

                for (Event event : events) {
                    if (!Objects.equals(event, selectedEvent)
                            && Objects.equals(event.getStartDate(), selectedEvent.getStartDate())) {
                        deleteEventNotification(event, getNotificationFacade().findByEventID(event));
                        getEventFacade().remove(event);
                    }
                }

            }
        }
    }

    private void verifyStopConditionConstraint(Boolean taskFinished, Periodicity periodicityID) {

        StopConditionSet stopConditionSet = getStopConditionSetFacade().findByPeriodicityID(periodicityID);

        if (stopConditionSet != null) {
            if (taskFinished) {
                stopConditionSet.setAsked(Boolean.TRUE);
                stopConditionSet.setCanAsk(Boolean.FALSE);
            } else {
                stopConditionSet.setAsked(Boolean.FALSE);
                stopConditionSet.setCanAsk(Boolean.TRUE);
            }

            getStopConditionSetFacade().edit(stopConditionSet);
        }

    }

    private void verifyTaskConditions(ProcessedTask processedTask, ScheduleTask taskID) {

        List<ScheduleTask> currentTasks = getScheduleTaskFacade().
                findByGuideExecID(selected.getScheduleTaskID().getGuideExecID());
        /*
         * preferenceAlternativeTask
         * selecionou Task 
         * Apagar as outras da BD
         */
 /*
         * alternativeTask
         * selecionou Task
         * Apagar as outras
         */
        if (taskID.getTaskFormat().equals("preferenceAlternativeTask")
                && taskID.getTaskFormat().equals("alternativeTask")) {
            for (ClinicalTask clinicalTask : processedTask.getTasks()) {
                for (ScheduleTask currentTask : currentTasks) {
                    if (currentTask.getTaskFormat().equals(clinicalTask.getTaskFormat())
                            && currentTask.getTaskType().equals(clinicalTask.getTaskType())
                            && clinicalTask.getId().equals(currentTask.getTaskIdentifier())
                            && !currentTask.equals(taskID)) {

                        getScheduleTaskFacade().remove(currentTask);
                    }
                }
            }
        }

        /*
         * parallelTask
         * tem de fazer check de todas
         * So pode fazer next Task apos todas as outras terem sido check
         */
        if (taskID.getTaskFormat().equals("parallelTask")) {
        }
    }

    public void nextTask() {
        //eliminar os eventos da task
        getEventFacade().findByScheduleTaskID(selected.getScheduleTaskID());
        requestNextTask(selected, processedTask);
    }

    public void continueExecution() {
        selectedStopConditionSet.setAsked(Boolean.TRUE);
        getStopConditionSetFacade().edit(selectedStopConditionSet);
    }

    public void displayMessage(FacesMessage.Severity severity, String summary, String details) {
        FacesMessage msg = new FacesMessage(severity, summary, details);

        FacesContext.getCurrentInstance().addMessage("message", msg);
    }

    public void incrementTaskRepetitionValue() {
        int value = selected.getScheduleTaskID().getCurrentNumberOfRepetitions();
        value++;

        selected.getScheduleTaskID().setCurrentNumberOfRepetitions(value);
    }

    public void deleteEventNotification(Event event, List<Notification> notifications) {
        for (Notification notification : notifications) {
            if (Objects.equals(notification.getEventID().getEventID(), event.getEventID())) {
                notification.setEventID(event);
                getNotificationFacade().remove(notification);
                notifications.remove(notification);
                break;
            }
        }
    }

    public void requestNextTask(Event event, ProcessedTask processedTask) {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setGuideexec(
                event.getScheduleTaskID().
                getGuideExecID().getIdguideexec().
                toString()
        );
        taskRequest.setTaskQuadruple(processedTask.
                getController().getNextTask().
                get(taskNumber(event, processedTask)));

        if (containsClass(processedTask, "Question")) {
            addQuestionToRequest(processedTask, taskRequest, event);
        }

        this.processedTask = callServiceNextTask(processedTask, taskRequest);
        storeProcessedTask(event.getScheduleTaskID().getGuideExecID(), processedTask);

        // decidir se adiciono as Task na BD
        // storeProcessedTask(event.getScheduleTaskID().getIdguideexec(), processedTask);
        // fetchTemporalElementFromProcessedTask(); so posso fazer isto depois de as Tasks estarem na BD
        // provavelmente adicionar as Tasks em /last fazer pedido de /Next seguide /Last
    }

    public List<ScheduleTask> storeProcessedTask(GuideExec guideExec, ProcessedTask processedTask) {
        List<ScheduleTask> tasks = Utils.processedTaskToScheduleTaskList(guideExec, processedTask);
        List<ScheduleTask> scheduleTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            ScheduleTask scheduleTask = new ScheduleTask(tasks.get(i).getTaskType(),
                    tasks.get(i).getTaskFormat(), tasks.get(i).getTaskDescription(),
                    tasks.get(i).getTaskIdentifier(), null, tasks.get(i).getNextTask(),
                    false, 0, null, new Date(), guideExec);

            getScheduleTaskFacade().create(scheduleTask);

            ((TemporalElementAdapter) temporalElementAdapter).passObject(scheduleTask);

            TemporalElement element = (TemporalElement) ((TemporalElementAdapter) temporalElementAdapter.
                    fetchTemporalPatternFromClinicaltask(processedTask.getTasks().get(i))).getObject();

            ((OutcomeAdapter) outcomeAdapter).passObject(scheduleTask);
            Outcome outcome = (Outcome) ((OutcomeAdapter) outcomeAdapter.
                    fetchTemporalPatternFromClinicaltask(processedTask.getTasks().get(i))).getObject();

            if (!scheduleTasks.contains(scheduleTask)) {
                scheduleTasks.add(scheduleTask);
            }

            if (element != null && !element.isEmpty()) {
                element.setScheduleTaskID(scheduleTask);

                element.setTemporalElementID(null);
                getTemporalElementFacade().create(element);
            }
        }
        return scheduleTasks;
    }

    public void addQuestionToRequest(ProcessedTask processedTask, TaskRequest taskRequest, Event event) {
        for (ClinicalTask clinicalTask : processedTask.getTasks()) {
            if (Objects.equals(clinicalTask.getId(), event.getScheduleTaskID().getTaskIdentifier())) {
                for (com.compguide.web.Execution.Entities.Parameter parameter : clinicalTask.asQuestion().getParameters()) {
                    com.compguide.web.Execution.Entities.Condition condition = new com.compguide.web.Execution.Entities.Condition();
                    condition.setComparisonOperator("Equal_to");
                    condition.setValue(parameter.getId());
                    condition.setUnit(parameter.getUnit());
                    condition.setIsNumeric(parameter.getNumeric());
                    condition.setConditionParameter(parameter.getQuestionParameter());
                    condition.addParameterIdentifier(parameter.getParameterIdentifier());

                    taskRequest.addCondition(condition);
                }
            }
        }
    }

    public int taskNumber(Event event, ProcessedTask processedTask) {
        List<ClinicalTask> clinicalTasks = processedTask.getTasks();

        for (int i = 0; i < clinicalTasks.size(); i++) {
            if (Objects.equals(clinicalTasks.get(i).getId(), event.getScheduleTaskID().getTaskIdentifier())) {
                return i;
            }
        }
        return 0;
    }

    public boolean containsClass(ProcessedTask processedTask, String nameClass) {
        for (ClinicalTask task : processedTask.getTasks()) {
            if (Objects.equals(task.getTaskType(), nameClass)) {
                return true;
            }
        }
        return false;
    }

    public ProcessedTask callServiceNextTask(ProcessedTask processedTask, TaskRequest taskRequest) {
        Header header = (Header) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("header");

        return com.compguide.web.ServerRequest.ServiceRequest.requestNextTask(header, processedTask, taskRequest);
    }

}
