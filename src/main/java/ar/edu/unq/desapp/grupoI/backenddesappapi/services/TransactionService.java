package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.State;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySell;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySellDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.TransactionRepository;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ar.edu.unq.desapp.grupoI.backenddesappapi.model.InitialType.VENTA;
import static ar.edu.unq.desapp.grupoI.backenddesappapi.model.State.Closed;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;

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
        transactionBuySell.setCreatedDate(LocalDateTime.now());

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
                if (transaction.getState() == Closed){
                    this.closeTransaction(transaction.getUserBuyer(), transaction.getUserSeller(), transaction);
                }
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

    private void closeTransaction(User userBuyer, User userSeller, TransactionBuySell transaction) {

        List<User> usersToUpdate = new ArrayList<>();
        Integer points = this.getPoints(transaction);

        userBuyer.closeTransaction(points);
        userSeller.closeTransaction(points);
        usersToUpdate.add(userBuyer);
        usersToUpdate.add(userSeller);
        userRepository.saveAll(usersToUpdate);

    }
    public TransactionBuySell cancelTransaction(Integer id, String userId) {
        TransactionBuySell response = null;
        try {
            Optional<TransactionBuySell> transactionBuySell = transactionRepository.findById(id);
            if (transactionBuySell.isPresent()){
                TransactionBuySell transaction = transactionBuySell.get();
                transaction.setState(transaction.getState().cancel());
                Integer userIdToUpdate = Integer.valueOf(userId);
                Optional<User> userToUpdate = this.userRepository.findById(userIdToUpdate);
                if (userToUpdate.isPresent()) {
                    User user = userToUpdate.get();
                    user.cancelOperation();
                    this.userRepository.save(user);
                }
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
    private int getPoints(TransactionBuySell t){
        LocalDateTime now = LocalDateTime.now();

        long minutesPassed = ChronoUnit.MINUTES.between(now, t.getCreatedDate());

        if (minutesPassed > 30){
            return 5;
        }

        return 10;
    }
}
