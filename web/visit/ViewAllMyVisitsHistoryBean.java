package pl.spjava.gabinet.web.visit;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.web.account.AccountController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ViewAllMyVisitsHistoryBean implements Serializable {


    @Inject
    private AccountController accountController;

    private VisitDTO visitDTO;

    private List<VisitDTO> viewMyVisitsHistory = new ArrayList<>();

    public VisitDTO getVisitDTO() {
        return visitDTO;
    }

    public void setVisitDTO(VisitDTO visitDTO) {
        this.visitDTO = visitDTO;
    }


    @PostConstruct
    public void init() {
        viewMyVisitsHistory = accountController.loadMyVisitHistoryFromState();
    }

    public List<VisitDTO> getViewMyVisitsHistory() {
        return viewMyVisitsHistory;
    }

    public void setViewMyVisitsHistory(List<VisitDTO> viewMyVisitsHistory) {
        this.viewMyVisitsHistory = viewMyVisitsHistory;
    }
}
