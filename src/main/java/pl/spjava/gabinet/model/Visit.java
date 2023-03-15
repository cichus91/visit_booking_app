package pl.spjava.gabinet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Visit")
@SequenceGenerator(name = "visitIdGen", initialValue = 10)
@NamedQueries(
        {@NamedQuery(name = "findAllVisitsByLogin", query = "SELECT v FROM Visit v where v.reservedBy.login=:login"),
        @NamedQuery(name = "findVisitByDate", query = "SELECT v FROM Visit v WHERE v.visitDate=:date"),
        @NamedQuery(name = "findAllUnbookedVisits", query = "SELECT v FROM Visit v WHERE v.reservedBy=null")}
)
public class Visit extends AbstractEntity implements Serializable {


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visitIdGen")
    private Long id;

    @Column(nullable = false)
    private @NotNull Long duration;

    @NotNull
    @Column(nullable = false)
    private Date visitDate;

    @Column(nullable = false)
    private Date visitEndDate;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.DETACH)
    private Receptionist createdBy;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.DETACH)
    private Patient reservedBy;

    @Column
    private Boolean confirmed = false;

    public Visit() {
    }

    public Visit(Long duration, Date visitDate, Receptionist createdBy) {
        this.duration = duration;
        this.visitDate = visitDate;
        this.createdBy = createdBy;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected Object getBusinessKey() {
        return id;
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

    public Date getVisitEndDate() {
        return visitEndDate;
    }

    public void setVisitEndDate(Date visitEndDate) {
        this.visitEndDate = visitEndDate;
    }

    public Receptionist getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Receptionist createdBy) {
        this.createdBy = createdBy;
    }

    public Patient getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(Patient reservedBy) {
        this.reservedBy = reservedBy;
    }

    public Boolean getReserved() {
        if(null != this.getReservedBy()){
            return true;
        } else{
            return false;
        }
    }//todo rozwazyc usuniecie pola ktorego to dotyczy, a getter ma sprawdzic czy ma ustawiona wartosc reserveedBy

}
