package pl.spjava.gabinet.web.visit;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.ScheduleDTO;
import pl.spjava.gabinet.ejb.endpoints.ScheduleEndpoint;
import pl.spjava.gabinet.ejb.endpoints.VisitEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;

@Named
@ViewScoped
public class ViewScheduleDetailsBean implements Serializable {

    @EJB
    private ScheduleEndpoint scheduleEndpoint;

    @EJB
    private VisitEndpoint visitEndpoint;

    @Inject
    private VisitController visitController;

    private ScheduleDTO scheduleDTO;

    @PostConstruct
    public void init(){
        visitController.loadScheduleFromManagerStateToView();
        scheduleDTO = visitController.getViewScheduleDTO();
    }

    public ScheduleDTO getScheduleDTO() {
        return scheduleDTO;
    }

    public void setScheduleDTO(ScheduleDTO scheduleDTO) {
        this.scheduleDTO = scheduleDTO;
    }

    public String createNewVisit() throws AppBaseException {
       return visitController.createNewVisit();
    }
}
