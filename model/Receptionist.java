package pl.spjava.gabinet.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ReceptionistData")
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("RECEPTIONIST")
public class Receptionist extends Account implements Serializable {

    public Receptionist() {
    }

    @JoinColumn
    @OneToMany(mappedBy = "createdBy")
    private List<Schedule> createdSchedules;

    @JoinColumn
    @OneToMany(mappedBy = "createdBy")
    private List<Visit> createdVisits;

    public List<Schedule> getCreatedSchedules() {
        return createdSchedules;
    }

    public void setCreatedSchedules(List<Schedule> createdSchedules) {
        this.createdSchedules = createdSchedules;
    }

    public List<Visit> getCreatedVisits() {
        return createdVisits;
    }

    public void setCreatedVisits(List<Visit> createdVisits) {
        this.createdVisits = createdVisits;
    }
}
