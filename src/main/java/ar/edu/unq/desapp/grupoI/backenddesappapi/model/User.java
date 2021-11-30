package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
//@JsonIgnoreProperties({"password"})
@Getter
@Setter
public class User {

    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String address, String password, String cvumercadoPago, String walletAddress) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvumercadoPago = cvumercadoPago;
        this.walletAddress = walletAddress;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "El email es un campo obligatorio")
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "cvu_mercado_pago")
    private String cvumercadoPago;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "reputation")
    private float reputation = 0;

    @Column(name = "finished_transactions" , columnDefinition = "integer default 0")
    private Integer finishedTransactions = 0;

    @Column(name = "points")
    private Integer points = 0;

    public void cancelOperation() {
        this.points -= 20;
        this.calculateReputation();
    }

    private void calculateReputation() {
        this.reputation = (this.finishedTransactions != 0) ? (float) (this.points / this.finishedTransactions) : 0;
    }

    public void closeTransaction(Integer points){
        this.points += points;
        this.finishedTransactions++;
        this.calculateReputation();
    }
}


