package pl.spjava.gabinet.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Date;

public class VisitDTO {

    private static final LocalTime OPEN_HOUR = LocalTime.ofSecondOfDay(28800L); //8*60*60 - przeliczenie godziny 8:00 na sekundy

    private static final LocalTime CLOSE_HOUR = LocalTime.ofSecondOfDay(64800L);//JW tyle, że dla godziny 18

    private static final Long DEFAULT_VISIT_DURATION = 3300l; //3300 sekund - tyle trwa jedna wizyta

    public static LocalTime getOPEN_HOUR() {
        return OPEN_HOUR;
    }

    public static LocalTime getCLOSE_HOUR() {
        return CLOSE_HOUR;
    }

    public static Long getDEFAULT_VISIT_DURATION() {
        return DEFAULT_VISIT_DURATION;
    }


    private @NotNull Long duration;

    private Date visitDate;

    private Long id;

    private Boolean reserved;

    private AccountDTO reservedBy;

    private AccountDTO createdBy;

    public VisitDTO() {
    }

    public VisitDTO(@NotNull Long duration, Date visitDate, Long id, Boolean reserved) {
        this.duration = duration;
        this.visitDate = visitDate;
        this.id = id;
        this.reserved = reserved;
    }

    public VisitDTO(Long duration, Date visitDate) {
        this.duration = duration;
        this.visitDate = visitDate;
    }

    public @NotNull Long getDuration() {
        return duration;
    }

    public void setDuration(@NotNull Long duration) {
        this.duration = duration;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountDTO getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(AccountDTO reservedBy) {
        this.reservedBy = reservedBy;
    }

    public AccountDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AccountDTO createdBy) {
        this.createdBy = createdBy;
    }
}
