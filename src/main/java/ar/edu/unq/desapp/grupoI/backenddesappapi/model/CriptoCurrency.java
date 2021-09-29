package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import javax.persistence.*;

@Entity
@Table(name = "cripto_currency")
public class CriptoCurrency {

    public CriptoCurrency() {
        super();
    }

    public CriptoCurrency(String name, String symbol, Double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String symbol;

    @Column
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
