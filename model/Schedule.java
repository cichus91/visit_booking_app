package pl.spjava.gabinet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Schedule")
@SequenceGenerator(name = "scheduleIdGen", initialValue = 10)
@NamedQueries({
        @NamedQuery(name = "Schedule.find.with.date", query = "SELECT s FROM Schedule s WHERE s.startDate= :startDate")
})
public class Schedule extends AbstractEntity{


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheduleIdGen")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    @Unique
    private Date startDate;

    @Column(nullable = false)
    @NotNull
    private Date endDate;

    @Unique
    @NotNull
    private Date dateUniqueKey;

    @JoinColumn
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Visit> visits = new ArrayList<Visit>();

    @JoinColumn
    @ManyToOne(cascade = CascadeType.DETACH)
    private Receptionist createdBy;

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

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public Receptionist getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Receptionist createdBy) {
        this.createdBy = createdBy;
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

    public Date getDateUniqueKey() {
        return dateUniqueKey;
    }

    public void setDateUniqueKey(Date dateUniqueKey) {
        this.dateUniqueKey = dateUniqueKey;
    }
}
