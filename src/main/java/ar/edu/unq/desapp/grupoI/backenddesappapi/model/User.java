package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String address, String password, String CVUMercadoPago, String walletAddress) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.CVUMercadoPago = CVUMercadoPago;
        this.walletAddress = walletAddress;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @NotBlank(message = "El email es un campo obligatorio")
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    private String address;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String CVUMercadoPago;

    @Column
    private String walletAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCVUMercadoPago() {
        return CVUMercadoPago;
    }

    public void setCVUMercadoPago(String CVUMercadoPago) {
        this.CVUMercadoPago = CVUMercadoPago;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}

