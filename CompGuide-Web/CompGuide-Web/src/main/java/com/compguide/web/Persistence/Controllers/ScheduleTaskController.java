package com.compguide.web.Persistence.Controllers;

import com.compguide.web.Adapters.OutcomeAdapter;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.ProcessedTask;
import com.compguide.web.Execution.Entities.Question;
import com.compguide.web.Execution.Entities.TaskRequest;
import com.compguide.web.Adapters.TemporalElementAdapter;
import com.compguide.web.Composite.TemporalElementComposite;
import com.compguide.web.Factories.GuidelineAdapterFactory;
import com.compguide.web.Persistence.Entities.Condition;
import com.compguide.web.Persistence.Entities.ConditionSet;
import com.compguide.web.Persistence.Entities.ScheduleTask;
import com.compguide.web.Persistence.Controllers.util.JsfUtil;
import com.compguide.web.Persistence.Controllers.util.JsfUtil.PersistAction;
import com.compguide.web.Persistence.Entities.CyclePartPeriodicity;
import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.Entities.Event;
import com.compguide.web.Persistence.Entities.GuideExec;
import com.compguide.web.Persistence.Entities.Notification;
import com.compguide.web.Persistence.Entities.Outcome;
import com.compguide.web.Persistence.Entities.Periodicity;
import com.compguide.web.Persistence.SessionBeans.ConditionFacade;
import com.compguide.web.Persistence.SessionBeans.EventFacade;
import com.compguide.web.Persistence.SessionBeans.GuideExecFacade;
import com.compguide.web.Persistence.SessionBeans.NotificationFacade;
import com.compguide.web.Persistence.SessionBeans.OutcomeFacade;
import com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade;
import com.compguide.web.Persistence.SessionBeans.StopConditionSetFacade;
import com.compguide.web.Persistence.SessionBeans.TemporalElementFacade;
import com.compguide.web.Persistence.Entities.StopConditionSet;
import com.compguide.web.Persistence.Entities.TemporalElement;
import com.compguide.web.Persistence.Entities.User;
import com.compguide.web.Persistence.Entities.WaitingTime;
import com.compguide.web.ServerRequest.ServiceRequest;
import com.compguide.web.Utils.CalendarType;
import com.compguide.web.Utils.CustomStyleClass;
import com.compguide.web.Utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.http.Header;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named("scheduleTaskController")
@SessionScoped
public class ScheduleTaskController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade ejbFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.TemporalElementFacade ejbTemporalElementFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.EventFacade ejbEventFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.ConditionFacade ejbConditionFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.OutcomeFacade ejbOutcomeFacade;
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

    private List<ScheduleTask> items = new ArrayList<>();
    private List<Condition> conditions = new ArrayList<>();

    private ScheduleTask selected;
    private Event selectedEvent;

    private CalendarType calendarType = CalendarType.GLOBAL;
    private ScheduleModel eventModel;

    private Outcome selectedOutcome;
    private StopConditionSet selectedStopConditionSet;
    private DataSelected selectedDate;
    private ProcessedTask processedTask;
    private GuidelineAdapterFactory adapterFactory = GuidelineAdapterFactory.instance();
    private Question question;

    public ScheduleTaskController() {
    }

    /**
     * Metodo para inicializar a agenda. Atraves deste metodo retornamos a lista
     * de eventos e montamos nosso calendario ScheduleModel atraves de um
     * DefaultScheduleModel
     */
    @PostConstruct
    private void init() {

        eventModel = new DefaultScheduleModel();

        User user = (User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("userPersistence");

        List<GuideExec> guideExecs = getGuideExecFacade().findByUserActiveGuidelines(user, (short) 0);

        for (GuideExec guideExec : guideExecs) {
            List<ScheduleTask> list = getFacade().findByGuideExecID(guideExec);
            // a lista de tarefas em ProcessedTask ainda nao foi inserida na BD
            if (list.isEmpty()) {
                processedTask = callServiceLastTask(guideExec);

                list = storeProcessedTask(guideExec, processedTask);
            }
            addToList(items, list);
        }

        List<Notification> notifications = getNotificationFacade().findAll();

        List<DefaultScheduleEvent> eventsSchedule = new ArrayList<>();
        //percorre a lista de tasks e cria o calendario
        for (ScheduleTask task : items) {
            if (task.getCompleted() == false) {
                TemporalElement temporalElement = null;

                temporalElement = getTemporalElementFacade().findByScheduleTaskID(task);

                List<Event> eventsNotificationLocal = getEventFacade().findByScheduleTaskID(task);

                if (eventsNotificationLocal.isEmpty()) { // nao existem eventos e preciso criar
                    try {

                        eventsSchedule = processGuidelineTemporalPattern(
                                task,
                                temporalElement);

                    } catch (NullPointerException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }

                    List<DefaultScheduleEvent> aux = clone(eventsSchedule);

                    for (DefaultScheduleEvent event : eventsSchedule) {
                        Event eventNotification = new Event();
                        eventNotification.setStartDate(event.getStartDate());
                        eventNotification.setEndDate(event.getEndDate());
                        eventNotification.setPostPonedDate(event.getEndDate());
                        eventNotification.setChecked(false);
                        eventNotification.setCanCheck(Event.canCheckTask(event.getEndDate()));
                        eventNotification.setScheduleTaskID(task);
                        eventNotification.setNumberOfRepetitions(eventsSchedule.size());
                        eventNotification.setRepetitionNumber(getRepetitionNumber(aux,
                                eventNotification,
                                event)
                        );

                        Date dateNow = new Date();

                        if (dateNow.after(event.getEndDate()) == true) { // ultrapassou a data para ser verificada
                            eventNotification.setCanCheck(true);    // adiciona a opcao de verificar a task posteriormente
                            getEventFacade().create(eventNotification); // cria o evento com a opcao de se poder verificar a task

                            Notification notification = new Notification(); // adicionar a notificacao para ser visualizada na Notification List
                            notification.setEventID(eventNotification);
                            notification.setViewed(false);

                            getNotificationFacade().create(notification); // cria a notificacao

                            notifications.add(notification); // adiciona a lista em memoria 
                        } else {
                            getEventFacade().create(eventNotification); //cria o evento
                        }

                        System.out.println(eventNotification.toString()); // apenas para verificar se o id foi atualizado ao gravar na BD

                        event.setData(eventNotification.getEventID());
                        eventModel.addEvent(event);    //o evento e adicionado na lista

                    }
                } else { // ja existem os eventos apenas e necessario percorrer a lista e adicionar ao schedule

                    for (Event eventSchedule : eventsNotificationLocal) {
                        if (eventSchedule.getChecked() == false) {

                            DefaultScheduleEvent event = new DefaultScheduleEvent(
                                    eventSchedule.getScheduleTaskID().getTaskIdentifier(),
                                    eventSchedule.getStartDate(),
                                    eventSchedule.getPostPonedDate(),
                                    false);
                            event.setData(eventSchedule.getEventID());
                            event.setStyleClass(CustomStyleClass.instance().getStyleClass());
                            event.setDescription(eventSchedule.getScheduleTaskID().getTaskDescription());
                            eventModel.addEvent(event); // adiciona ao schedule
                        }

                    }
                }
            }
        }

    }

    public void taskStopCondition() {
        if (selected != null) {
            TemporalElement temporalElement = getTemporalElementFacade().
                    findByScheduleTaskID(selected);

            if (temporalElement != null) {
                if (temporalElement.isPeriodicity()) {
                    StopConditionSet selectedStopCondition = getStopConditionSetFacade().
                            findByPeriodicityID(temporalElement.getPeriodicityID());

                    if (!selectedStopCondition.getAsked()) {
                        this.selectedStopConditionSet = selectedStopCondition;

                        this.selectedStopConditionSet.setAsked(Boolean.TRUE);
                        this.selectedStopConditionSet.setCanAsk(Boolean.TRUE);
                        getStopConditionSetFacade().edit(this.selectedStopConditionSet);
                    }

                }
            }
        }
    }

    public void showOutcome() {
        List<Outcome> outcomes = getOutcomeFacade().findAll();

        for (Outcome outcome : outcomes) {
            if (outcome.getCanAsk() == true) {
                selectedOutcome = outcome;
            }
        }
    }

    public Outcome getSelectedOutcome() {
        if (selectedOutcome == null) {
            selectedOutcome = new Outcome();
        }

        return selectedOutcome;
    }

    public void refresh() {
        System.out.println("====================REFRESH MODEL========================");

        boolean refresh = false;

        try {
            refresh = (Boolean) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("refreshModel");
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        if (refresh) {
            System.out.println("================REFRESH COMPLETED===========================");

            FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().put("refreshModel", false);
            init();

        }
    }

    public ConditionFacade getConditionFacade() {
        return ejbConditionFacade;
    }

    public OutcomeFacade getOutcomeFacade() {
        return ejbOutcomeFacade;
    }

    public List<Condition> getConditions() {
        if (selectedOutcome != null) {
            conditions = getConditionFacade().findByConditionSetID(selectedOutcome.getConditionSetID());
        }
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void setSelectedOutcome(Outcome selectedOutcome) {
        this.selectedOutcome = selectedOutcome;
    }

    public ScheduleTask getSelected() {
        return selected;
    }

    public void setSelected(ScheduleTask selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    public StopConditionSetFacade getStopConditionSetFacade() {
        return ejbStopConditionSetFacade;
    }

    public StopConditionSet getSelectedStopConditionSet() {
        return selectedStopConditionSet;
    }

    public Question getQuestion() {
        if (question == null) {
            question = new Question();
        }
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    protected void initializeEmbeddableKey() {
    }

    private ScheduleTaskFacade getFacade() {
        return ejbFacade;
    }

    public List<ScheduleTask> getTasks() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    private TemporalElementFacade getTemporalElementFacade() {
        return ejbTemporalElementFacade;
    }

    public DataSelected getSelectedDate() {
        if (selectedDate == null) {
            selectedDate = new DataSelected();
        }

        return selectedDate;
    }

    public Event getSelectedEvent() {
        if (selectedEvent == null) {
            selectedEvent = new Event();
        }

        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public NotificationFacade getNotificationFacade() {
        return ejbNotificationFacade;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public void setProcessedTask(ProcessedTask processedTask) {
        this.processedTask = processedTask;
    }

    public EventFacade getEventFacade() {
        return ejbEventFacade;
    }

    public List<CalendarType> getCalendarTypes() {
        return Arrays.asList(CalendarType.values());
    }

    public CalendarType getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(CalendarType calendarType) {
        this.calendarType = calendarType;
    }

    private static List<DefaultScheduleEvent> clone(List<DefaultScheduleEvent> listToClone) {
        List<DefaultScheduleEvent> aux = new ArrayList<>();
        for (DefaultScheduleEvent event : listToClone) {
            DefaultScheduleEvent e = new DefaultScheduleEvent(
                    event.getTitle(), event.getStartDate(),
                    event.getEndDate(), event.getData());
            e.setStyleClass(event.getStyleClass());
            e.setAllDay(event.isAllDay());

            aux.add(e);
        }
        return aux;

    }

    private static int getRepetitionNumber(List<DefaultScheduleEvent> events, Event eventID, DefaultScheduleEvent eventSchedule) {
        int repetitionNumber = 0;

        for (DefaultScheduleEvent event : events) {
            if (event.getData() == eventID.getScheduleTaskID().getScheduleTaskID()) {
                repetitionNumber++;
                if (event.getStartDate() == eventID.getStartDate() && event.getEndDate() == eventID.getEndDate()) {
                    return repetitionNumber;
                }
            }
        }

        return repetitionNumber;
    }

    private List<DefaultScheduleEvent> processGuidelineTemporalPattern(ScheduleTask task, TemporalElement temporalElement) {
        List<DefaultScheduleEvent> eventList = new ArrayList<>();

        if (temporalElement != null) {
            if (temporalElement.isDuration()) {
                eventList = processGuidelineDuration(null, null, task, temporalElement.getDurationID());
            }

            if (temporalElement.isPeriodicity()) {
                eventList = processGuidelinePeriodicity(task, temporalElement.getPeriodicityID());
            }

            if (temporalElement.isWaitingTime()) {
                eventList = processGuidelineWaitingTime(eventList, task, temporalElement.getWaitingTimeID());
            }
        } else {
            DefaultScheduleEvent defaultScheduleEvent = createEvent(task, CustomStyleClass.instance().getStyleClass());

            defaultScheduleEvent.setStartDate(task.getStartDate());

            Calendar endDate = Calendar.getInstance();
            endDate.set(12, 59);
            endDate.set(11, 23);

            defaultScheduleEvent.setEndDate(endDate.getTime());
            eventList.add(defaultScheduleEvent);
        }

        return eventList;
    }

    public DefaultScheduleEvent createEvent(ScheduleTask task, String styleClass) {
        DefaultScheduleEvent event = new DefaultScheduleEvent();

        event.setTitle(task.getTaskIdentifier());
        event.setData(task.getScheduleTaskID());

        event.setEditable(false); // nao permitir que o utilizador edite

        //alteracao do tipo especifico de css para cada tipo de evento
        event.setStyleClass(styleClass);

        return event;
    }

    private List<DefaultScheduleEvent> processGuidelineDuration(Date fromStartDate,
            Date toEndDate,
            ScheduleTask task, Duration duration) {
//  Notificar o utilizador quando se alcanÃ§ar os tempos mÃ­nimo e mÃ¡ximo de duraÃ§Ã£o 
        List<DefaultScheduleEvent> eventList = new ArrayList<>();

        Calendar date = Calendar.getInstance();
        if (fromStartDate == null) {
            date.setTime(task.getStartDate());
        } else {
            date.setTime(fromStartDate);
        }

        if (duration.asExactValue()) {
            DefaultScheduleEvent event = null;

            event = createEvent(task, CustomStyleClass.instance().getStyleClass());

            Date endDate = null;

            if (toEndDate == null) {
                endDate = com.compguide.web.TemporalPattern.TemporalPattern.endDateFromStartDate(
                        date.getTime(),
                        duration.getTemporalUnitID().getValue(),
                        duration.getDurationValue());
            } else {
                endDate = toEndDate;
            }

            event.setStartDate(date.getTime());
            event.setEndDate(endDate);
            event.setAllDay(com.compguide.web.TemporalPattern.TemporalPattern.isAllDay(
                    endDate.getTime() - date.getTimeInMillis()));

            eventList.add(event);

        }

        if (duration.isInterval()) {

            String styleClass = CustomStyleClass.instance().getStyleClass();

            DefaultScheduleEvent eventMin = createEvent(task,
                    styleClass);
            DefaultScheduleEvent eventMax = createEvent(task,
                    styleClass);

            Date endDateMin = com.compguide.web.TemporalPattern.TemporalPattern.endDateFromStartDate(
                    date.getTime(),
                    duration.getTemporalUnitID().getValue(),
                    duration.getMinDurationValue());

            eventMin.setStartDate(date.getTime());
            eventMin.setEndDate(endDateMin);
            eventMin.setAllDay(com.compguide.web.TemporalPattern.TemporalPattern.isAllDay(
                    endDateMin.getTime() - date.getTime().getTime()));
            eventList.add(eventMin);

            Date endDateMax = com.compguide.web.TemporalPattern.TemporalPattern.endDateFromStartDate(
                    date.getTime(),
                    duration.getTemporalUnitID().getValue(),
                    duration.getMaxDurationValue());

            eventMax.setAllDay(com.compguide.web.TemporalPattern.TemporalPattern.isAllDay(
                    duration.getTemporalUnitID().getValue(),
                    duration.getMaxDurationValue()));

            eventMax.setStartDate(date.getTime());
            eventMax.setEndDate(endDateMax);
            eventMax.setAllDay(com.compguide.web.TemporalPattern.TemporalPattern.isAllDay(
                    endDateMax.getTime() - date.getTime().getTime()));
            eventList.add(eventMax);

        }

        return eventList;
    }

    private List<DefaultScheduleEvent> processGuidelinePeriodicity(ScheduleTask task, Periodicity periodicity) {
//  Notificar quantas vezes a tarefa deve repetir-se e quantas vezes foi repetida (quantas repetiÃ§Ãµes faltam). 

        List<DefaultScheduleEvent> eventList = new ArrayList<>();

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(task.getStartDate());

        if (periodicity.isRepetition() // periodicidade da tarefa tem um numero de repeticoes
                && periodicity.isExecutionTimeValid()) {

            Date newStartDate = startDate.getTime(); // dentro de cada ciclo existem iteracoes
            // em cada iteracao e alterada a data de comeco correspondendo a data de finalizacao da ultima iteracao a ser executada

            for (int i = 0; i < periodicity.getRepetitionValue(); i++) { // percorre todas as iteracoes repetitionValue = numero de iteracoes
                Date endDate = new Date(newStartDate.getTime()
                        + periodicity.getPartExecutionTime()); // data de finalizacao da tarefa e o somatorio da data de inicio com a duracao de cada iteracao

                // calcular a duracao de cada iteracao 
                if (periodicity.isCyclePart()) {

                    if (periodicity.getCyclePartDefinitionID().asDuration()) {
                        eventList.addAll(
                                processGuidelineDuration(newStartDate, //adiciona o evento a partir da data de inicio da iteracao ate a data de finalizacao
                                        endDate,
                                        task,
                                        periodicity.getCyclePartDefinitionID().getDurationID())
                        );
                    }
                }

                if (!periodicity.isCyclePart()) { // nao temos a duracao de cada parte de cada repeticao e preciso calcular
                    DefaultScheduleEvent event = createEvent(task,
                            CustomStyleClass.instance().getStyleClass());

                    event.setStartDate(newStartDate);
                    event.setEndDate(endDate);
                    event.setAllDay(com.compguide.web.TemporalPattern.TemporalPattern.isAllDay(
                            endDate.getTime() - newStartDate.getTime()));

                    eventList.add(event);
                }

                newStartDate = (Date) endDate.clone();
            }
        }

        if (periodicity.isDuration() // o evento periodico da tarefa ocorre durante um determindo tempo 
                && periodicity.isExecutionTimeValid()) {

            if (periodicity.isCyclePart()) {
                eventList.addAll(
                        processGuidelineCyclePart(null,
                                task,
                                periodicity)
                );
            }
            if (!periodicity.isCyclePart()) { // duracao sem parte de cada iteracao

                Date newStartDate = startDate.getTime(); // data de inicio da tarefa

                // 1 mes / 1 semana = 2629743830L/ 604800000L = 4
                int iterations = ((Long) (periodicity.getExecutionTotalTime() // numero de iteracoes corresponde ao tempo total de 
                        / periodicity.getPartExecutionTime())).intValue(); // execucao da tarefa a dividir pelo tempo de execucao de cada iteracao do ciclo

                for (int i = 0; i < iterations; i++) {
                    Date endDate = new Date(newStartDate.getTime() + com.compguide.web.TemporalPattern.TemporalPattern.temporalUnitToMilliseconds(
                            periodicity.getTemporalUnitID().getValue(),
                            periodicity.getPeriodicityValue()));

                    eventList.addAll(
                            processGuidelineDuration(
                                    newStartDate,
                                    endDate,
                                    task,
                                    periodicity.getDurationID())
                    );

                    newStartDate = (Date) endDate.clone();
                }
            }
        }
        return eventList;
    }

    public void taskExecutionSuccess() {
        System.out.println("ENTREI");
        String summary = "Outcome";
        String message = "The execution of the task " + selectedOutcome.getScheduleTaskID().getTaskIdentifier()
                + " wich started at " + selectedOutcome.getScheduleTaskID().getStartDate().toString() + " and finished at"
                + selectedOutcome.getScheduleTaskID().getCompleted().toString() + " was successfully executed";
        displayMessage(FacesMessage.SEVERITY_INFO, summary, message);
        outcomeAsked();
    }

    private void outcomeAsked() {
        List<Condition> list = getConditionFacade().findByConditionSetID(selectedOutcome.getConditionSetID());

        for (Condition condition : list) {
            if (condition.getCanAsk() == true) {
                condition.setAsked(true);
                getConditionFacade().edit(condition);
                selectedOutcome.setCanAsk(false);
                getOutcomeFacade().edit(selectedOutcome);
                break;
            }
        }
    }

    public void taskExecutionFail() {
        System.out.println("ENTREI");
        String summary = "Outcome";
        String message = "The execution of the task " + selectedOutcome.getScheduleTaskID().getTaskIdentifier()
                + " wich started at " + selectedOutcome.getScheduleTaskID().getStartDate().toString() + " and finished at "
                + selectedOutcome.getScheduleTaskID().getCompletedDate().toString() + " failed";
        displayMessage(FacesMessage.SEVERITY_ERROR, summary, message);

        Notification notification = new Notification(false, message);
        getNotificationFacade().create(notification);

        outcomeAsked();

    }

    private List<DefaultScheduleEvent> processGuidelineCyclePart(Date fromStartDate, ScheduleTask task, Periodicity periodicity) {
        List<DefaultScheduleEvent> events = new ArrayList<>();

        Calendar startDate = Calendar.getInstance();

        if (fromStartDate == null) {
            startDate.setTime(task.getStartDate());
        } else {
            startDate.setTime(fromStartDate);
        }

        int iterations = ((Long) (periodicity.getExecutionTotalTime() // numero de iteracoes corresponde ao tempo total de 
                / periodicity.getPartExecutionTime())).intValue(); // execucao da tarefa a dividir pelo tempo de execucao de cada iteracao do ciclo

        Date newStartDate = startDate.getTime();

        for (int i = 0; i < iterations; i++) {

            if (periodicity.isCyclePart()) { // a periodicidade tem iteracoes
                Date newEndDate = com.compguide.web.TemporalPattern.TemporalPattern.endDateFromStartDate(
                        newStartDate,
                        periodicity.getTemporalUnitID().getValue(),
                        periodicity.getPeriodicityValue());

                if (periodicity.getCyclePartDefinitionID().asDuration()) {
                    events.addAll(
                            processGuidelineDuration(newStartDate,
                                    newEndDate,
                                    task,
                                    periodicity.getCyclePartDefinitionID().getDurationID())
                    );

                }

                if (periodicity.getCyclePartDefinitionID().asPeriodicity()) {
                    events.addAll(
                            processGuidelineCyclePartPeriodicity(newStartDate,
                                    newEndDate,
                                    task,
                                    periodicity.getCyclePartDefinitionID().getCyclePartPeriodicityID())
                    );
                }
                newStartDate = (Date) newEndDate.clone();
            }

        }

        return events;

    }

    private List<DefaultScheduleEvent> processGuidelineCyclePartPeriodicity(Date fromStartDate, Date toEndDate, ScheduleTask task, CyclePartPeriodicity cyclePartPeriodicity) {
        List<DefaultScheduleEvent> eventList = new ArrayList<>();
        Calendar startDate = Calendar.getInstance();

        if (fromStartDate == null) {
            startDate.setTime(task.getStartDate());
        } else {
            startDate.setTime(fromStartDate);
        }

        if (cyclePartPeriodicity.asDuration()) {

            int numIterations = ((Long) (cyclePartPeriodicity.getDurationID().getExecutionTotalTime()
                    / cyclePartPeriodicity.getPartExecutionTime())).intValue();

            Date newStartDate = startDate.getTime();
            Date endDate = null;

            for (int i = 0; i < numIterations; i++) {
                endDate = new Date(newStartDate.getTime() + com.compguide.web.TemporalPattern.TemporalPattern.temporalUnitToMilliseconds(
                        cyclePartPeriodicity.getTemporalUnitID().getValue(),
                        cyclePartPeriodicity.getPeriodicityValue()));

                eventList.addAll(
                        processGuidelineDuration(newStartDate,
                                endDate,
                                task,
                                cyclePartPeriodicity.getDurationID())
                );

                newStartDate = (Date) endDate.clone();
            }

        }

        if (cyclePartPeriodicity.asRepetition()) {

            Date newStartDate = startDate.getTime();

            for (int i = 0; i < cyclePartPeriodicity.getRepetitionValue(); i++) {
                DefaultScheduleEvent event = createEvent(task,
                        CustomStyleClass.instance().getStyleClass());

                Date endDate = new Date(newStartDate.getTime() + com.compguide.web.TemporalPattern.TemporalPattern.temporalUnitToMilliseconds(
                        cyclePartPeriodicity.getTemporalUnitID().getValue(),
                        cyclePartPeriodicity.getPeriodicityValue()));

                event.setStartDate(newStartDate);
                event.setEndDate(endDate);
                event.setAllDay(com.compguide.web.TemporalPattern.TemporalPattern.isAllDay(
                        endDate.getTime() - newStartDate.getTime()));

                eventList.add(event);

                newStartDate = (Date) endDate.clone();

            }

        }

        return eventList;
    }

    private List<DefaultScheduleEvent> processGuidelineWaitingTime(List<DefaultScheduleEvent> events, ScheduleTask task, WaitingTime waitingTime) {

        for (DefaultScheduleEvent event : events) {

            Date startDate = event.getStartDate();
            Date endDate = event.getEndDate();

            if (waitingTime.asExactValue()) {
                DefaultScheduleEvent aux = event;
                event.setStartDate(com.compguide.web.TemporalPattern.TemporalPattern.endDateFromStartDate(
                        startDate,
                        waitingTime.getTemporalUnitID().getValue(),
                        waitingTime.getExactWaitingTime()));
                event.setEndDate(com.compguide.web.TemporalPattern.TemporalPattern.endDateFromStartDate(
                        endDate,
                        waitingTime.getTemporalUnitID().getValue(),
                        waitingTime.getExactWaitingTime()));

                events.set(events.indexOf(aux), event);
            }

            if (waitingTime.asInterval()) {

            }
        }
        return events;
    }

    /**
     * Evento para quando o utlizador clica em um enveto ja existente
     *
     * @param selectEvent
     */
    public void showTask(SelectEvent selectEvent) {

        ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();
        List<Event> events = getEventFacade().findAll();

        for (Event eventSchedule : events) {
            if (Objects.equals(eventSchedule.getEventID(), (Integer) event.getData())) {
                selected = eventSchedule.getScheduleTaskID();
                selectedEvent = eventSchedule;
                getSelectedDate().setDate(selectedEvent.getEndDate());
                processedTask = callServiceLastTask(selected.getGuideExecID());
                FacesContext.getCurrentInstance().
                        getExternalContext().getSessionMap().put("guideexec", selected.getGuideExecID());
                break;
            }
        }

    }

    public Outcome checkOutcome(ScheduleTask scheduleTask, ClinicalTask clinicalTask) {
        Outcome outcome = new Outcome();

        outcome.setCanAsk(false);
        outcome.setScheduleTaskID(scheduleTask);

        ConditionSet conditionSet = new ConditionSet();

        return null;

    }

    public void checkTask() {
        List<ScheduleEvent> eventList = eventModel.getEvents();

        System.out.println("========================CHECK TASK==============================");

        for (ScheduleEvent event : eventList) {
            if (Objects.equals(selectedEvent.getEventID(), event.getData())) {

                verifyTemporalConstraints(selected, selectedEvent);

                if (selectedEvent.getScheduleTaskID().getCurrentNumberOfRepetitions() == 0) {
                    verifyTaskConditions(processedTask, selectedEvent.getScheduleTaskID());
                }

                incrementTaskRepetitionValue();

                if (Objects.equals(selectedEvent.getNumberOfRepetitions(), selected.getCurrentNumberOfRepetitions())) {
                    selected = selectedEvent.getScheduleTaskID();

                    selected.setCompletedDate(new Date());
                    selected.setCompleted(Boolean.TRUE);
                }

                selectedEvent.setChecked(true);

                Event ev = Event.clone(selectedEvent);
                ScheduleTask t = ScheduleTask.clone(selectedEvent.getScheduleTaskID());

                getFacade().edit(t);
                ev.setScheduleTaskID(t);
                getEventFacade().edit(ev);

                selectedEvent = ev;

                if (Objects.equals(selectedEvent.getNumberOfRepetitions(), selected.getCurrentNumberOfRepetitions())) {

                    if (canRequestNextTask()) {
                        //pedir nova task
                        // esperemos que o valor de completed da task Ã¡ tenha sido alterado XD
                        List<ScheduleTask> scheduleTasks = requestNextTask(selectedEvent, processedTask);

                        // alterar a parent task das novas tasks
                        for (ScheduleTask scheduleTask : scheduleTasks) {
                            scheduleTask.setParentTaskID(selectedEvent.getScheduleTaskID());
                            getFacade().edit(scheduleTask);
                        }
                    }
                }

                deleteEventNotification(selectedEvent, getNotificationFacade().findByEventID(selectedEvent));

                eventModel.deleteEvent(event);
                break;
            }
        }

        displayMessage(FacesMessage.SEVERITY_INFO, "Task",
                "The Task " + selectedEvent.getScheduleTaskID().getTaskIdentifier()
                + ", wich was started at " + selectedEvent.getScheduleTaskID().getStartDate().toString()
                + ", was successfully checked");

        update();

        init();
        System.out.println("=====================================================");

    }

    public void nextTask() {
        //eliminar os eventos da task
        TemporalElement temporalElement = getTemporalElementFacade().findByPeriodicityID(selectedStopConditionSet.getPeriodicityID());
        selected = temporalElement.getScheduleTaskID();

        selected.setCompleted(Boolean.TRUE);

        getFacade().edit(selected);

        selectedStopConditionSet.setAsked(Boolean.TRUE);
        selectedStopConditionSet.setCanAsk(Boolean.FALSE);

        getStopConditionSetFacade().edit(selectedStopConditionSet);

        requestNextTask(selectedEvent, processedTask);
    }

    public void continueExecution() {
        selectedStopConditionSet.setAsked(Boolean.TRUE);
        selectedStopConditionSet.setCanAsk(Boolean.TRUE);

        getStopConditionSetFacade().edit(selectedStopConditionSet);
    }

    public void displayMessage(FacesMessage.Severity severity, String summary, String details) {
        FacesMessage msg = new FacesMessage(severity, summary, details);

        FacesContext.getCurrentInstance().addMessage("message", msg);
    }

    public void incrementTaskRepetitionValue() {
        int value = selectedEvent.getScheduleTaskID().getCurrentNumberOfRepetitions();
        value++;

        selectedEvent.getScheduleTaskID().setCurrentNumberOfRepetitions(value);
    }

    public void deleteEventNotification(Event event, List<Notification> notifications) {
        if (!notifications.isEmpty()) {
            for (Notification notification : notifications) {
                if (Objects.equals(notification.getEventID().getEventID(), event.getEventID())) {
                    notification.setEventID(event);
                    getNotificationFacade().remove(notification);
                    notifications.remove(notification);
                    break;
                }
            }
        }
    }

    public List<ScheduleTask> requestNextTask(Event event, ProcessedTask processedTask) {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setGuideexec(
                event.getScheduleTaskID().
                getGuideExecID().getIdguideexec().
                toString()
        );
        
        processedTask.setController(
                com.compguide.web.Execution.Entities.TaskController
                        .fromJson(event.getScheduleTaskID().getNextTask())
        );
        
        taskRequest.setTaskQuadruple(processedTask.
                getController().getNextTask().
                get(taskNumber(event, processedTask)));

        if (containsClass(processedTask, "Question")) {
            addQuestionToRequest(processedTask, taskRequest, event);
        }

        this.processedTask = callServiceNextTask(processedTask, taskRequest);
        List<ScheduleTask> scheduleTasks = storeProcessedTask(event.getScheduleTaskID().getGuideExecID(), this.processedTask);

        // decidir se adiciono as Task na BD
        // storeProcessedTask(event.getScheduleTaskID().getIdguideexec(), processedTask);
        // fetchTemporalElementFromProcessedTask(); so posso fazer isto depois de as Tasks estarem na BD
        // provavelmente adicionar as Tasks em /last fazer pedido de /Next seguide /Last
        return scheduleTasks;
    }

    public List<ScheduleTask> storeProcessedTask(GuideExec guideExec, ProcessedTask processedTask) {
        List<ScheduleTask> tasks = Utils.processedTaskToScheduleTaskList(guideExec, processedTask);
        List<ScheduleTask> scheduleTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            selected = new ScheduleTask(tasks.get(i).getTaskType(),
                    tasks.get(i).getTaskFormat(), tasks.get(i).getTaskDescription(),
                    tasks.get(i).getTaskIdentifier(), null, tasks.get(i).getNextTask(),
                    false, 0, null, new Date(), guideExec);

            persist(PersistAction.CREATE, null);

            ((TemporalElementAdapter) temporalElementAdapter).passObject(selected);

            TemporalElement element = (TemporalElement) ((TemporalElementAdapter) temporalElementAdapter.
                    fetchTemporalPatternFromClinicaltask(processedTask.getTasks().get(i))).getObject();

            ((OutcomeAdapter) outcomeAdapter).passObject(selected);
            Outcome outcome = (Outcome) ((OutcomeAdapter) outcomeAdapter.
                    fetchTemporalPatternFromClinicaltask(processedTask.getTasks().get(i))).getObject();

            if (!scheduleTasks.contains(selected)) {
                scheduleTasks.add(selected);
            }

            if (element != null && !element.isEmpty()) {
                element.setScheduleTaskID(selected);

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

    public void fetchTemporalElementFromProcessedTask(GuideExec guideExecID, ProcessedTask processedTask) {
        List<ScheduleTask> tasks = getFacade().findByGuideExecID(guideExecID);
        TemporalElement temporalElement = null;

        for (ClinicalTask clinicalTask : processedTask.getTasks()) {

            temporalElement = (TemporalElement) adapterFactory.
                    getAdapter("TemporalElement").fetchTemporalPatternFromClinicaltask(clinicalTask);

            if (temporalElement != null) {
                for (ScheduleTask task : tasks) {

                    if (task.getTaskFormat().equals(clinicalTask.getTaskFormat())
                            && task.getTaskType().equals(clinicalTask.getTaskType())
                            && clinicalTask.getId().equals(task.getTaskIdentifier())) {

                        temporalElement.setScheduleTaskID(task);
                        TemporalElementComposite elementComposite = new TemporalElementComposite(temporalElement);

                        // verifica se existe todas as classes (Periodicity, WaitingTime e Duration)e caso nao exista cria
                        // retorna ja o obeto da BD
                        temporalElement = elementComposite.check();

                    }
                }
            }

        }

    }

    public ProcessedTask callServiceLastTask(GuideExec guideExecID) {
        Header header = (Header) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("header");

        ProcessedTask procTask = new ProcessedTask();
        procTask = ServiceRequest.requestGetLastTask(header, guideExecID.getIdguideexec().toString());
        return procTask;
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

    private boolean canRequestNextTask() {
        /*
         * preferenceAlternativeTask
         * verificar se fez check a alguma das Tasks recebidas em ProcessedTask
         */
        if (Objects.equals(selectedEvent.getScheduleTaskID().getTaskFormat(), "preferenceAlternativeTask")
                && selectedEvent.getScheduleTaskID().getCompleted() == true) {
            return true;
        }

        /*
         * alternativeTask
         * verificar se fez check a alguma das Tasks recebidas em ProcessedTask
         */
        if (Objects.equals(selectedEvent.getScheduleTaskID().getTaskFormat(), "alternativeTask")
                && selectedEvent.getScheduleTaskID().getCompleted() == true) {
            return true;
        }

        /*
         * nextTask
         * verificar se fez check a alguma das Tasks recebidas em ProcessedTask
         */
        if (Objects.equals(selectedEvent.getScheduleTaskID().getTaskFormat(), "nextTask")
                && selectedEvent.getScheduleTaskID().getCompleted() == true) {
            return true;
        }
        /*
         * parallelTask
         * verificar se fez check de todas as recebidas em ProcessedTask
         * So pode fazer next Task apos todas as outras terem sido check
         */
        if (Objects.equals(selectedEvent.getScheduleTaskID().getTaskFormat(), "parallelTask")) {
            List<ScheduleTask> currentTasks = getFacade().
                    findByGuideExecID(selectedEvent.getScheduleTaskID().getGuideExecID());
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

    private void verifyTaskConditions(ProcessedTask processedTask, ScheduleTask taskID) {

        List<ScheduleTask> currentTasks = getFacade().
                findByGuideExecID(selectedEvent.getScheduleTaskID().getGuideExecID());
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

                        getFacade().remove(currentTask);
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

    public GuideExecFacade getGuideExecFacade() {
        return ejbGuideExecFacade;
    }

    public ProcessedTask getProcessedTask() {
        processedTask = (ProcessedTask) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("processedTask");

        if (processedTask == null) {
            processedTask = new ProcessedTask();
        }
        return processedTask;
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

    public ScheduleTask prepareCreate() {
        selected = new ScheduleTask();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("resources/Bundle").getString("ScheduleTaskCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("resources/Bundle").getString("ScheduleTaskUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("resources/Bundle").getString("ScheduleTaskDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ScheduleTask> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().create(selected);
                }
                if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(selected);
                }
                if (persistAction == PersistAction.DELETE) {
                    getFacade().remove(selected);
                }
                if (successMessage != null) {
                    JsfUtil.addSuccessMessage(successMessage);
                }
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ScheduleTask getScheduleTask(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ScheduleTask> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ScheduleTask> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    private void verifyTemporalConstraints(ScheduleTask selected, Event selectedEvent) {
        TemporalElement temporalElement = getTemporalElementFacade().findByScheduleTaskID(selected);

        if (temporalElement != null) {
            if (!temporalElement.isEmpty()) {
                if (temporalElement.isDuration()
                        && temporalElement.getDurationID().isInterval()) {
                    List<Event> eventList = getEventFacade().findByScheduleTaskID(selected);

                    for (Event event : eventList) {
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
                    List<Event> eventList = getEventFacade().findByScheduleTaskID(selected);

                    boolean taskFinished = Boolean.FALSE;

                    if (Objects.equals(selected.getCurrentNumberOfRepetitions(), selectedEvent.getNumberOfRepetitions())) {
                        taskFinished = Boolean.TRUE;
                    }

                    verifyStopConditionConstraint(taskFinished, temporalElement.getPeriodicityID());

                    for (Event event : eventList) {
                        if (!Objects.equals(event, selectedEvent)
                                && Objects.equals(event.getStartDate(), selectedEvent.getStartDate())) {
                            deleteEventNotification(event, getNotificationFacade().findByEventID(event));
                            getEventFacade().remove(event);
                        }
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

    private void addToList(List<ScheduleTask> items, List<ScheduleTask> list) {
        for (ScheduleTask item : list) {
            if (!items.contains(item)) {
                items.add(item);
            }
        }
    }

    @FacesConverter(forClass = ScheduleTask.class)
    public static class ScheduleTaskControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ScheduleTaskController controller = (ScheduleTaskController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "scheduleTaskController");
            return controller.getScheduleTask(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ScheduleTask) {
                ScheduleTask o = (ScheduleTask) object;
                return getStringKey(o.getScheduleTaskID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ScheduleTask.class.getName()});
                return null;
            }
        }

    }

}
