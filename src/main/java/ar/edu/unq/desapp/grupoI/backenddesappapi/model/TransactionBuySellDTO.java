package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionBuySellDTO {

    private Integer id;
    private User userSeller;
    private User userBuyer;
    private CriptoCurrency criptoCurrency;
    private State state;
    private Double sellPrice;
    private Double quantity;

}
