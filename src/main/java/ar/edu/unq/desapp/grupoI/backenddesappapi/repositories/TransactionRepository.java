package ar.edu.unq.desapp.grupoI.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySell;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Configuration
@Repository
public interface TransactionRepository extends CrudRepository<TransactionBuySell, Integer> {

    @Query(value = "SELECT t FROM transactions t WHERE t.userBuyer.id <> :userId OR t.userSeller.id <> :userId")
    Iterable<TransactionBuySell> findOffers(Integer userId);
}
