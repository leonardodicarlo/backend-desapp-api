package ar.edu.unq.desapp.grupoI.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySell;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionBuySell, Integer> {
}
