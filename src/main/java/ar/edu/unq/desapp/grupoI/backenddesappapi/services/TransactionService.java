package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.State;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySell;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySellDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        transactionBuySell.setCriptoCurrency(buySellDTO.getCriptoCurrency());
        transactionBuySell.setState(State.OPEN);
        transactionBuySell.setUserSeller(buySellDTO.getUserSeller());
        transactionBuySell.setQuantity(buySellDTO.getQuantity());

        return this.transactionRepository.save(transactionBuySell);
    }
}
