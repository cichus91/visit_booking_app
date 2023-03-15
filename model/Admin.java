package pl.spjava.gabinet.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "AdminData")
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("ADMIN")
public class Admin extends Account implements Serializable {

    public Admin() {
    }
}
