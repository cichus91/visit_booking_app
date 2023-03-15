package pl.spjava.gabinet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Account")
@SecondaryTable(name = "PersonalData")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "accountIdGen", initialValue = 100)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("ACCOUNT")
public abstract class Account extends AbstractEntity{

    public enum AccountType{ //todo poprawić
        ADMIN, RECEPTIONIST, PATIENT
    }

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountIdGen")
    private Long id;

    @NotNull
    @Column(updatable = false, unique = true, nullable = false, length = 32)
    @Size(min = 3, max = 32)
    @Pattern(regexp ="^[_a-zA-Z0-9-]*$")
    private String login;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private Boolean active = false;

    @NotNull
    @Size(min = 3, max = 25)
    @Column(nullable = false, table = "PersonalData", length = 25)
    private String firstName;

    @NotNull
    @Column(nullable = false, table = "PersonalData", length = 25)
    private String secondName;

    @NotNull
    @Email
    @Column(nullable = false, unique = true, table = "PersonalData")
    private String email;

    @NotNull
    @Size(min = 9, max = 12)
    @Column(nullable = false, length = 12)
    @Pattern(regexp = "\\d*")
    private String phoneNumber;

    @Column(name = "type")
    private String type;


    public Account() {}

    public Account(String login, String email, String password, Boolean active, String firstName, String secondName, String phoneNumber) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.active = active;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Object getBusinessKey() {
        return login;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
}
