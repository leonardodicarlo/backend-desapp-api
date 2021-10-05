package ar.edu.unq.desapp.grupoI.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrency;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Configuration
@Repository
public interface CriptoCurrencyRepository extends CrudRepository<CriptoCurrency, Integer> {



}
