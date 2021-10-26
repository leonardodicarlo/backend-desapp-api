package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {

    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String CVUMercadoPago;
    private String walletAddress;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
