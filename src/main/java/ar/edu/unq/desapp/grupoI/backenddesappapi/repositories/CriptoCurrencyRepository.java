package ar.edu.unq.desapp.grupoi.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoi.backenddesappapi.model.CriptoCurrency;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Configuration
@Repository
public interface CriptoCurrencyRepository extends CrudRepository<CriptoCurrency, Integer> {



}
