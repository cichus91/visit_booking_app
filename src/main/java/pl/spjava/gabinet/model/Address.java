package pl.spjava.gabinet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

@Entity
@Table(name = "Address")
@SequenceGenerator(name = "addressIdGen", initialValue = 100)
public class Address extends AbstractEntity implements Serializable {


    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressIdGen")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String street;

    @NotNull
    @Column(nullable = false)
    @Pattern(regexp = "\\d+.?")
    private String houseNumber;

    @Column (nullable = true)
    private String flatNumber;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{2}-\\d{3}")
    private String zipcode;

    @Column(nullable = false)
    private String place;

    public Address() {
    }

    public Address(String street, String houseNumber, String flatNumber, String zipcode, String place) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
        this.zipcode = zipcode;
        this.place = place;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String number) {
        this.houseNumber = number;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return id;
    }
}
