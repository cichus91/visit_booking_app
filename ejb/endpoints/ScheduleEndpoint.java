package pl.spjava.gabinet.ejb.endpoints;

import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.*;
import jakarta.interceptor.Interceptors;
import pl.spjava.gabinet.dto.ScheduleDTO;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.ejb.managers.ScheduleManager;
import pl.spjava.gabinet.ejb.managers.VisitManager;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.exception.VisitException;
import pl.spjava.gabinet.model.Schedule;
import pl.spjava.gabinet.model.Visit;
import pl.spjava.gabinet.utils.EntityDTOConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Receptionist"})
public class ScheduleEndpoint {

    @EJB
    private ScheduleManager scheduleManager;

    @EJB
    private VisitManager visitManager;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    @Resource
    private SessionContext sessionContext;

    private ScheduleDTO scheduleDTOState;

    public void createNewSchedule(ScheduleDTO scheduleDTO) throws AppBaseException {

        Schedule schedule = new Schedule();
        schedule.setStartDate(scheduleDTO.getStartDate());
        schedule.setEndDate(scheduleDTO.getEndDate());

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do{
            try{
                scheduleManager.createNewSchedule(schedule);
                rollbackTX = scheduleManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter == 0);

        if (rollbackTX && retryTXCounter == 0){
            throw VisitException.createVisitExceptionWithThRetryRollBack();
        }
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        List<Schedule> scheduleList = scheduleManager.getAllSchedules();
        for (Schedule schedule:
             scheduleList) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setStartDate(schedule.getStartDate());
            scheduleDTO.setEndDate(schedule.getEndDate());
            scheduleDTO.setCreatedBy(EntityDTOConverter.accountEntityToDTO(schedule.getCreatedBy()));
            scheduleDTOList.add(scheduleDTO);
        }
        return scheduleDTOList;
    }

    public void loadScheduleIntoManagerState(ScheduleDTO scheduleDTO) throws AppBaseException {
        scheduleManager.loadScheduleIntoState(scheduleDTO.getId());
    }

    public ScheduleDTO loadScheduleFromManagerState() {
            Schedule schedule = scheduleManager.getScheduleState();
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setStartDate(schedule.getStartDate());
            scheduleDTO.setEndDate(schedule.getEndDate());
            scheduleDTO.setCreatedBy(EntityDTOConverter.accountEntityToDTO(schedule.getCreatedBy()));
            List<Visit> visitList = schedule.getVisits();
            List<VisitDTO> visitDTOList = new ArrayList<>();
            if (!visitList.isEmpty()) {
                for (Visit visit :
                        visitList) {
                    visitDTOList.add(visitToVisitDTO(visit));//todo przepisac wszystkie konwersje do konwertera
                }
            }
            scheduleDTO.setVisitDTOS(visitDTOList);
            return scheduleDTO;
    }

    public ScheduleDTO getScheduleDTOState() {
        return scheduleDTOState;
    }

    public void setScheduleDTOState(ScheduleDTO scheduleDTOState) {
        this.scheduleDTOState = scheduleDTOState;
    }

    public void createNewVisitInSchedule(VisitDTO visitDTO, ScheduleDTO scheduleDTO) throws AppBaseException {
        Visit visit = new Visit();
        visit.setVisitDate(scheduleDTO.getStartDate());
        visit.getVisitDate().setHours(visitDTO.getVisitDate().getHours());
        visit.getVisitDate().setMinutes(visitDTO.getVisitDate().getMinutes());
        visit.setDuration(visitDTO.getDuration());
        scheduleManager.createNewVisitInSchedule(visit);
    }
    //Metody narzedziowe

    private static VisitDTO visitToVisitDTO(Visit visit){
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setVisitDate(visit.getVisitDate());
        visitDTO.setDuration(visit.getDuration());
        visitDTO.setId(visit.getId());
        visitDTO.setCreatedBy(EntityDTOConverter.accountEntityToDTO(visit.getCreatedBy()));
        if(visit.getReservedBy() != null){
            visitDTO.setReservedBy(EntityDTOConverter.patientEntityToDTO(visit.getReservedBy()));
        }
        return visitDTO;
    }

    public void initiateSchedule(ScheduleDTO scheduleDTO) throws AppBaseException {
        scheduleManager.initiateSchedule(scheduleDTO.getId());
    }
}
