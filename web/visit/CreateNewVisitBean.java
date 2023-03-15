package pl.spjava.gabinet.web.visit;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.ScheduleDTO;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.endpoints.VisitEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;

@Named
@RequestScoped
public class CreateNewVisitBean {

    @Inject
    private VisitController visitController;

    private VisitDTO visitDTO = new VisitDTO();

    private ScheduleDTO scheduleDTO = new ScheduleDTO();

    @PostConstruct
    public void init(){
        visitController.loadScheduleFromManagerStateToCreateVisit();
        scheduleDTO = visitController.getCreateNewVisitScheduleDTO();
        visitDTO.setVisitDate(scheduleDTO.getStartDate());
    }

    public VisitDTO getVisitDTO() {
        return visitDTO;
    }

    public void setVisitDTO(VisitDTO visitDTO) {
        this.visitDTO = visitDTO;
    }

    public ScheduleDTO getScheduleDTO() {
        return scheduleDTO;
    }

    public void setScheduleDTO(ScheduleDTO scheduleDTO) {
        this.scheduleDTO = scheduleDTO;
    }

    public String createNewVisit() throws AppBaseException {
        return visitController.confirmNewVisit(visitDTO);
    }
}
