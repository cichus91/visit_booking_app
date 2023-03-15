package pl.spjava.gabinet.web.visit;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.web.visit.VisitController;

import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.endpoints.VisitEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class AllVisitsListBean implements Serializable {

    @EJB
    private VisitEndpoint visitEndpoint;

    @Inject
    private VisitController visitController;

    private VisitDTO visitDTO;

    private List<VisitDTO> visitDTOList = new ArrayList<>();

    @PostConstruct
    public void init(){
        visitDTOList = visitEndpoint.getAllVisits();
//        Long dateCounter = 0L;
//        do {
//            Date date = Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC));
//            date.setTime(VisitDTO.getOPEN_HOUR().getSecond() + VisitDTO.getDEFAULT_VISIT_DURATION());
//            VisitDTO visitDTO = new VisitDTO(VisitDTO.getDEFAULT_VISIT_DURATION(), date);
//            visitDTOList.add(visitDTO);
//            dateCounter++;
//        } while //(visitDTO.getVisitDate().getTime() <= VisitDTO.getCLOSE_HOUR().getSecond());
//        (dateCounter <= 8L);
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
        visitController.deleteVisit(visitDTO);
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
