package pl.spjava.gabinet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;

public class AccountDTO {

    private Long id;

    @NotNull(message = "{constraints.NotNull.message}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp ="^[_a-zA-Z0-9-]*$")
    private String login;

    @NotNull(message = "{constraints.NotNull.message}")
    @Email(message = "{constraint.string.incorrectemail}")
    private String email;

    private boolean active;

    @NotNull(message = "{constraints.NotNull.message}")
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    private String password;

    @NotNull(message = "{constraints.NotNull.message}")
    @Size(min = 3, max = 25, message = "{constraint.string.length.notinrange}")
    private String firstName;

    @NotNull(message = "{constraints.NotNull.message}")
    @Size(min = 3, max = 25, message = "{constraint.string.length.notinrange}")
    private String secondName;

    private AddressDTO addressDTO;

    @NotNull(message = "{constraints.NotNull.message}")
    private Date dateOfBirth;

    @NotNull(message = "{constraints.NotNull.message}")
    @Size(min = 9, max = 12, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "\\d*", message = "{constraint.string.incorrectchar}")
    private String phoneNumber;

    private String type;

    public AccountDTO() {
    }

    public AccountDTO(String login, String email, boolean active, String password,
                      String firstName, String secondName, AddressDTO addressDTO, Date dateOfBirth, String phoneNumber) {
        this.login = login;
        this.email = email;
        this.active = active;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.addressDTO = addressDTO;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public AccountDTO(Long id, String login, String email, boolean active, String password,
                      String firstName, String secondName, String phoneNumber, String type) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.active = active;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotNull @Size(min = 9, max = 12) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull @Size(min = 9, max = 12) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return firstName + " " + secondName+ " "+ phoneNumber;
    }
}
