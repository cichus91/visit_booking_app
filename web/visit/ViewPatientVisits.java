package pl.spjava.gabinet.web.visit;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.endpoints.VisitEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.web.account.AccountController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ViewPatientVisits implements Serializable {

    @EJB
    private VisitEndpoint visitEndpoint;

    @Inject
    private AccountController accountController;

    private VisitDTO visitDTO;

    private List<VisitDTO> viewPatientVisits = new ArrayList<>();

    public ViewPatientVisits() {
    }

    public VisitDTO getVisitDTO() {
        return visitDTO;
    }

    public void setVisitDTO(VisitDTO visitDTO) {
        this.visitDTO = visitDTO;
    }

    public List<VisitDTO> getViewPatientVisits() {
        return viewPatientVisits;
    }

    public void setViewPatientVisits(List<VisitDTO> viewPatientVisits) {
        this.viewPatientVisits = viewPatientVisits;
    }

    @PostConstruct
    public void init(){
        viewPatientVisits = accountController.getViewPatientVisits();
    }

    public String cancelVisitReservation(VisitDTO visitDTO) throws AppBaseException {
        visitEndpoint.cancelVisitReservation(visitDTO);
        return "index";
    }

    public String viewVisitDetails(VisitDTO visitDTO){
        visitEndpoint.loadVisitIntoManagerState(visitDTO);
        return "viewschedule";
    }
}
