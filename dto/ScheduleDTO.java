package pl.spjava.gabinet.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDTO {

    private Long id;

    private Date startDate;

    private Date endDate;

    private List<VisitDTO> visitDTOS = new ArrayList<>();

    private AccountDTO createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<VisitDTO> getVisitDTOS() {
        return visitDTOS;
    }

    public void setVisitDTOS(List<VisitDTO> visitDTOS) {
        this.visitDTOS = visitDTOS;
    }

    public AccountDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AccountDTO createdBy) {
        this.createdBy = createdBy;
    }
}
