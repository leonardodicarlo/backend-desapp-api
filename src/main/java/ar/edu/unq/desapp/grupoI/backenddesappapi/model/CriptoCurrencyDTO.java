package ar.edu.unq.desapp.grupoi.backenddesappapi.model;

public class CriptoCurrencyDTO {

    private String name;
    private String symbol;
    private Double price;

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Double getPrice() {
        return this.price;
    }
}
