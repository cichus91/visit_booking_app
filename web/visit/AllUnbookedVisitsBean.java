package pl.spjava.gabinet.web.visit;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.endpoints.VisitEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class AllUnbookedVisitsBean implements Serializable {

    @EJB
    private VisitEndpoint visitEndpoint;

    @Inject
    private VisitController visitController;

    private VisitDTO visitDTO;

    private List<VisitDTO> visitDTOList = new ArrayList<>();

    @PostConstruct
    public void init(){
        visitDTOList = visitEndpoint.getAllUnbookedVisits();
    }

    public VisitDTO getVisitDTO() {
        return visitDTO;
    }

    public void setVisitDTO(VisitDTO visitDTO) {
        this.visitDTO = visitDTO;
    }

    public List<VisitDTO> getVisitDTOList() {
        return visitDTOList;
    }

    public void setVisitDTOList(List<VisitDTO> visitDTOList) {
        this.visitDTOList = visitDTOList;
    }

    public void deleteVisit(VisitDTO visitDTO) throws AppBaseException {
        visitEndpoint.deleteVisit(visitDTO);
        init();
    }

    public String bookVisit(VisitDTO visitDTO) throws AppBaseException{
        return visitController.bookVisit(visitDTO);
    }

    public String cancelVisitReservation(VisitDTO visitDTO) throws AppBaseException{
        visitEndpoint.cancelVisitReservation(visitDTO);
        return "index";
    }

}
