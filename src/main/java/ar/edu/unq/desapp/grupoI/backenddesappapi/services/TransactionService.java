package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.State;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySell;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySellDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        transactionBuySell.setState(State.Open);
        if(buySellDTO.getInitialType() == VENTA){
            transactionBuySell.setUserSeller(buySellDTO.getUserSeller());
        } else {
            transactionBuySell.setUserBuyer(buySellDTO.getUserBuyer());
        }
        transactionBuySell.setQuantity(buySellDTO.getQuantity());

        return this.transactionRepository.save(transactionBuySell);
    }

    public Iterable<TransactionBuySell> findOffers(String userId) {

        return transactionRepository.findOffers(Integer.valueOf(userId));
    }

    public Iterable<TransactionBuySell> findMyTransactions(String userId) {

        return transactionRepository.findMyTransactions(Integer.valueOf(userId));
    }

    public TransactionBuySell updateTransaction(TransactionBuySellDTO transactionBuySellDTO) {
        TransactionBuySell response = null;
        try {
            Optional<TransactionBuySell> transactionBuySell = transactionRepository.findById(transactionBuySellDTO.getId());
            if (transactionBuySell.isPresent()){
                TransactionBuySell transaction = transactionBuySell.get();
                transaction.setState(transaction.getState().nextState());
                transaction.setUserBuyer(transactionBuySellDTO.getUserBuyer());
                transaction.setUserSeller(transactionBuySellDTO.getUserSeller());
                response = transactionRepository.save(transaction);
            }
            else {
                throw new Exception("no encontre esa transaccion");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public ResponseEntity<?> getTransactionById(Integer id) throws Exception {
        Optional<TransactionBuySell> transactionBuySell = transactionRepository.findById(id);

        try{
        if (transactionBuySell.isPresent()) {
            TransactionBuySell transaction = transactionBuySell.get();
            TransactionBuySellDTO transactionBuySellDTO = new TransactionBuySellDTO();
            transactionBuySellDTO.setInitialType(transaction.getInitialType());
            transactionBuySellDTO.setSellPrice(transaction.getSellPrice());
            transactionBuySellDTO.setId(transaction.getId());
            transactionBuySellDTO.setQuantity(transaction.getQuantity());
            transactionBuySellDTO.setState(transaction.getState());
            transactionBuySellDTO.setUserBuyer(transaction.getUserBuyer());
            transactionBuySellDTO.setUserSeller(transaction.getUserSeller());
            transactionBuySellDTO.setCriptoCurrency(transaction.getCriptoCurrency());

            return ResponseEntity.ok().body(transactionBuySellDTO);
        } else {
            throw new Exception("No se encontro la transaccion");
        }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
