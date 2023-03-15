package pl.spjava.gabinet.web.visit;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.ScheduleDTO;
import pl.spjava.gabinet.ejb.endpoints.ScheduleEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ViewAllSchedulesBean implements Serializable {

    @EJB
    private ScheduleEndpoint scheduleEndpoint;

    @Inject
    private VisitController visitController;

    private ScheduleDTO scheduleDTO;

    private List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

    @PostConstruct
    public void init(){
        scheduleDTOList = scheduleEndpoint.getAllSchedules();
    }

    public ScheduleDTO getScheduleDTO() {
        return scheduleDTO;
    }

    public void setScheduleDTO(ScheduleDTO scheduleDTO) {
        this.scheduleDTO = scheduleDTO;
    }

    public List<ScheduleDTO> getScheduleDTOList() {
        return scheduleDTOList;
    }

    public void setScheduleDTOList(List<ScheduleDTO> scheduleDTOList) {
        this.scheduleDTOList = scheduleDTOList;
    }

    public String viewScheduleDetails(ScheduleDTO scheduleDTO) throws AppBaseException {
        return visitController.loadScheduleIntoManagerState(scheduleDTO);
    }

    public void initiateSchedule(ScheduleDTO scheduleDTO) throws AppBaseException{
        visitController.initiateSchedule(scheduleDTO);
    }

    public boolean isScheduleInitiated(ScheduleDTO scheduleDTO){
       return !(scheduleDTO.getVisitDTOS().isEmpty());
    }
}
