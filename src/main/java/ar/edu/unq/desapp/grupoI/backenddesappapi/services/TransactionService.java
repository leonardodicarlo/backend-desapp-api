package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.InitialType;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.State;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySell;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySellDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ar.edu.unq.desapp.grupoI.backenddesappapi.model.InitialType.VENTA;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository){

        this.transactionRepository = transactionRepository;

    }

    public TransactionBuySell save(TransactionBuySellDTO buySellDTO) {

        TransactionBuySell transactionBuySell = new TransactionBuySell();
        transactionBuySell.setSellPrice(buySellDTO.getSellPrice());
        transactionBuySell.setInitialType(buySellDTO.getInitialType());
        transactionBuySell.setCriptoCurrency(buySellDTO.getCriptoCurrency());
        transactionBuySell.setState(State.OPEN);
        if(buySellDTO.getInitialType() == VENTA){
            transactionBuySell.setUserSeller(buySellDTO.getUserSeller());
        } else {
            transactionBuySell.setUserBuyer(buySellDTO.getUserBuyer());
        }
        transactionBuySell.setQuantity(buySellDTO.getQuantity());

        return this.transactionRepository.save(transactionBuySell);
    }
}
