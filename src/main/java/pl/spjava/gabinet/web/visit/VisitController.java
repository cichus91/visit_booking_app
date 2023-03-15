package pl.spjava.gabinet.web.visit;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import pl.spjava.gabinet.dto.ScheduleDTO;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.endpoints.ScheduleEndpoint;
import pl.spjava.gabinet.ejb.endpoints.VisitEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.utils.ContextUtils;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
public class VisitController implements Serializable {

    @EJB
    private VisitEndpoint visitEndpoint;

    @EJB
    private ScheduleEndpoint scheduleEndpoint;


    private ScheduleDTO viewScheduleDTO;

    private ScheduleDTO createNewVisitScheduleDTO;

    public ScheduleDTO getViewScheduleDTO() {
        return viewScheduleDTO;
    }

    public void setViewScheduleDTO(ScheduleDTO viewScheduleDTO) {
        this.viewScheduleDTO = viewScheduleDTO;
    }

    public ScheduleDTO getCreateNewVisitScheduleDTO() {
        return createNewVisitScheduleDTO;
    }

    public void setCreateNewVisitScheduleDTO(ScheduleDTO createNewVisitScheduleDTO) {
        this.createNewVisitScheduleDTO = createNewVisitScheduleDTO;
    }

    public String loadScheduleIntoManagerState(ScheduleDTO scheduleDTO) throws AppBaseException {
        try{
            scheduleEndpoint.loadScheduleIntoManagerState(scheduleDTO);
            return "viewschedule";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        } catch (Exception exception){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", exception);
            return "error";
        }
    }

    public void loadScheduleFromManagerStateToView(){
        viewScheduleDTO = scheduleEndpoint.loadScheduleFromManagerState();
    }

    public void loadScheduleFromManagerStateToCreateVisit(){
        createNewVisitScheduleDTO = scheduleEndpoint.loadScheduleFromManagerState();
    }

    public String createNewVisit() throws AppBaseException {
//        scheduleEndpoint.loadScheduleIntoManagerState(scheduleDTO);
        return "createnewvisit";
    }

    public String confirmNewVisit(VisitDTO visitDTO) throws AppBaseException {
        try {
            scheduleEndpoint.createNewVisitInSchedule(visitDTO, createNewVisitScheduleDTO);
            return "success";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        } catch (Exception exception){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", exception);
            return "error";
        }
    }

    public void initiateSchedule(ScheduleDTO scheduleDTO) throws AppBaseException {
        scheduleEndpoint.initiateSchedule(scheduleDTO);
    }

    public String bookVisit(VisitDTO visitDTO) throws AppBaseException {
        try{
            visitEndpoint.bookVisit(visitDTO);
            return "success";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        } catch (Exception exception){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", exception);
            return "error";
        }
    }

    public String createNewSchedule(ScheduleDTO scheduleDTO) throws AppBaseException {
        try {
            if (scheduleDTO.getStartDate().getDate() != scheduleDTO.getEndDate().getDate()) {
                ContextUtils.emitInternationalizedMessage("newScheduleForm:scheduleEndDate", "key.wrong.date");
                return null;
            }
            scheduleEndpoint.createNewSchedule(scheduleDTO);
            return "allscheduleslist";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        } catch (Exception exception){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", exception);
            return "error";
        }
    }

    public String deleteVisit(VisitDTO visitDTO) throws AppBaseException {
        try{
            visitEndpoint.deleteVisit(visitDTO);
            return null;
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        } catch (Exception exception){
            Logger lg = Logger.getLogger(VisitController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", exception);
            return "error";
        }
    }
}
