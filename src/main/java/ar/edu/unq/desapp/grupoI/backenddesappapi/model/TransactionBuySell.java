package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Setter
@Entity
public class TransactionBuySell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_seller")
    private User userSeller;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_buyer")
    private User userBuyer;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cripto")
    private CriptoCurrency criptoCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "sell_price")
    private Double sellPrice;

    @Column(name = "quantity")
    private Double quantity;

}
