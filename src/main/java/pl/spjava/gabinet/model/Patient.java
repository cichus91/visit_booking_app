package pl.spjava.gabinet.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "PatientData")
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("PATIENT")
@NamedQueries({
        @NamedQuery(name = "Patient.findByLogin", query = "SELECT d FROM Patient d WHERE d.login= :login"),
        @NamedQuery(name = "Patient.findAllPatientVisitsByLogin", query = "SELECT p.visits FROM Patient p WHERE p.login= :login")
}
)
public class Patient extends Account implements Serializable {


    @JoinColumn(name = "address")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @Column(updatable = false)
    private Date dateOfBirth;


    @OneToMany(mappedBy = "reservedBy")
    private List<Visit> visits = new ArrayList<Visit>();

    public Patient(){
    }

    public Patient(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
