package pl.spjava.gabinet.web.visit;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.ScheduleDTO;
import pl.spjava.gabinet.ejb.endpoints.ScheduleEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.model.Schedule;

import java.time.LocalDate;
import pl.spjava.gabinet.utils.ContextUtils;
import java.time.ZoneId;

@Named
@RequestScoped
public class CreateNewScheduleBean {

    @EJB
    private ScheduleEndpoint scheduleEndpoint;

    @Inject
    private VisitController visitController;

    private ScheduleDTO scheduleDTO = new ScheduleDTO();

    public ScheduleDTO getScheduleDTO() {
        return scheduleDTO;
    }

    public void setScheduleDTO(ScheduleDTO scheduleDTO) {
        this.scheduleDTO = scheduleDTO;
    }

    public String createNewSchedule() throws AppBaseException {
       return visitController.createNewSchedule(scheduleDTO);
    }

}
