package pl.spjava.gabinet.dto;

import jakarta.validation.constraints.*;

public class AddressDTO {

    @NotNull(message = "{constraints.NotNull.message}")
    @Size(min = 3, max = 60, message = "{constraint.string.length.notinrange}")
    private String street;

    @NotNull(message = "{constraints.NotNull.message}")
    @Pattern(regexp = "\\d+.?", message = "{constraint.string.incorrectchar}")
    private String houseNumber;

    @Pattern(regexp = "\\d+.?", message = "{constraint.string.incorrectchar}")
    private String flatNumber;

    @NotNull(message = "{constraints.NotNull.message}")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "{constraint.string.incorrectchar}")
    private String zipcode;

    @NotNull(message = "{constraints.NotNull.message}")
    @Size(min=3, max =60)
    private String place;

    public AddressDTO() {
    }

    public AddressDTO(String street, String houseNumber, String flatNumber, String zipcode, String place) {
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

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
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

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    @Override
    public String toString() {
        if (this.flatNumber != null) {
            return street + " " + houseNumber + "/" + flatNumber + " " + zipcode+ " " + place;
        } else {
            return street + " " + houseNumber + ", " + zipcode + " " + place;
        }
    }
}