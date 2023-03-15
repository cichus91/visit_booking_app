package pl.spjava.gabinet.ejb.managers;

import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.interceptor.Interceptors;
import pl.spjava.gabinet.ejb.facades.*;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.exception.AccountException;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.exception.VisitException;
import pl.spjava.gabinet.model.Receptionist;
import pl.spjava.gabinet.model.Schedule;
import pl.spjava.gabinet.model.Visit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class ScheduleManager extends AbstractManager
implements SessionSynchronization{

    @EJB
    private VisitFacade visitFacade;

    @EJB
    private ScheduleFacade scheduleFacade;

    @EJB
    private ReceptionistFacade receptionistFacade;

    @EJB
    private PatientFacade patientFacade;

    @EJB
    private AccountFacade accountFacade;

    @Resource
    private SessionContext sessionContext;

    private Schedule scheduleState;

    public void createNewSchedule(Schedule schedule) throws AppBaseException {
        if (isScheduleDatePassed(schedule)) {
            throw VisitException.createVisitExceptionWithVisitTimePassException();
        }

        schedule.setCreatedBy((Receptionist) accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName()));
//        if(scheduleFacade.findByDate(schedule.getStartDate(), schedule.getEndDate()).equals(schedule)){
//            throw VisitException.createVisitExceptionWithVisitTimePassException(); //todo
//        }
        schedule.setDateUniqueKey(new Date(schedule.getStartDate().getYear(), schedule.getStartDate().getMonth(), schedule.getStartDate().getDay()));
        scheduleFacade.create(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleFacade.findAll();
    }

    public void loadScheduleIntoState(Long id) throws AppBaseException{
        scheduleState = scheduleFacade.find(id);
        if(scheduleState == null){
            throw AccountException.createExceptionWithNoObjectInEJBState(); //todo dopisac wyjatek
        }

    }

    public Schedule getScheduleState() {
        return scheduleState;
    }

    public void setScheduleState(Schedule scheduleState) {
        this.scheduleState = scheduleState;
    }

    public void createNewVisitInSchedule(Visit visit) throws AppBaseException {

        visit.setCreatedBy((Receptionist) accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName()));
        visit.setVisitEndDate(getEndOfVisit(visit));
        scheduleState.getVisits().add(visit);
        scheduleFacade.edit(scheduleState);
        scheduleState = null;

    }

    public void initiateSchedule(Long id) throws AppBaseException {
        scheduleState = scheduleFacade.find(id);
        Visit firstVisitOfTheDay = new Visit(60L, scheduleState.getStartDate(), (Receptionist) accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName()));
        firstVisitOfTheDay.setVisitEndDate(getEndOfVisit(firstVisitOfTheDay));
        scheduleState.getVisits().add(firstVisitOfTheDay);
        do{
            Visit visit = new Visit(60L, scheduleState.getVisits().get(scheduleState.getVisits().size() -1).getVisitEndDate(),
                    (Receptionist) accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName()));
            visit.setVisitEndDate(getEndOfVisit(visit));
            scheduleState.getVisits().add(visit);
        } while (scheduleState.getVisits().get(scheduleState.getVisits().size()-1).getVisitEndDate().before(scheduleState.getEndDate()));
        scheduleFacade.edit(scheduleState);
        scheduleState =null;
    }

    // metody narzedziowe

    private static Date getEndOfVisit(Visit visit){
        Date date = Date.from(visit.getVisitDate().toInstant().plusSeconds(visit.getDuration() * 60));
        return date;
    }

    private static boolean isScheduleDatePassed(Schedule scheduleToCheck){
        return LocalDateTime.now().isAfter(scheduleToCheck.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    private static boolean isScheduleDateProper(Schedule scheduleToCheck){
        return (scheduleToCheck.getStartDate().getDate() != scheduleToCheck.getEndDate().getDate());
    }
}
