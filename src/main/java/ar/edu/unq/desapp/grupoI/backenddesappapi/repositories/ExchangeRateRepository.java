package ar.edu.unq.desapp.grupoi.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoi.backenddesappapi.model.ExchangeRate;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Configuration
@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Integer> {

    Optional<ExchangeRate> findByDate(Date date);

    Optional<ExchangeRate> findFirstByOrderByDateDesc();

}
