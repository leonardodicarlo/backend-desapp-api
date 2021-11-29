package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.UpdatePricesJob;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrency;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrencyDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.ExchangeRate;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.ExchangeRateDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.CriptoCurrencyRepository;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.ExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CriptoCurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePricesJob.class);

    @Value("${bcra.accessToken}")
    private String accessToken;
    @Value("${bcra.api}")
    private String bcraApi;
    @Value("${binance.api}")
    private String binanceApi;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private CriptoCurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public Iterable<CriptoCurrency> updatePrices() {

        List<CriptoCurrency> criptoCurrenciesInDB = (ArrayList<CriptoCurrency>) currencyRepository.findAll();

        List<CriptoCurrency> criptosToUpdate = new ArrayList<>();


        if (criptoCurrenciesInDB.size() > 0) {

            Optional<ExchangeRate> exchangeRate = this.getExchangeRate();
            ResponseEntity<CriptoCurrencyDTO[]> responseEntity =
                    restTemplate.getForEntity(binanceApi, CriptoCurrencyDTO[].class);

            if (responseEntity.getBody() != null){

                List<CriptoCurrencyDTO> criptoCurrenciesBinance = Arrays.asList(responseEntity.getBody());

                Map<String, CriptoCurrencyDTO> criptoCurrencyDTOBySymbolId = this.getCriptoCurrencyDTOBySymbolId(criptoCurrenciesBinance);

                criptoCurrenciesInDB.forEach(criptoCurrency -> {
                    CriptoCurrencyDTO criptoCurrencyDTO = criptoCurrencyDTOBySymbolId.get(criptoCurrency.getSymbol());
                    CriptoCurrency criptoCurrencyToUpdate = new CriptoCurrency();
                    criptoCurrencyToUpdate.setId(criptoCurrency.getId());
                    criptoCurrencyToUpdate.setSymbol(criptoCurrency.getSymbol());
                    criptoCurrencyToUpdate.setName(criptoCurrency.getName());
                    criptoCurrencyToUpdate.setPrice(criptoCurrencyDTO.getPrice());
                    criptoCurrencyToUpdate.setPriceARS(criptoCurrencyDTO.getPrice()* exchangeRate.get().getValue());

                    criptosToUpdate.add(criptoCurrencyToUpdate);
                });
            }
        }
        return currencyRepository.saveAll(criptosToUpdate);
    }

    private Optional<ExchangeRate> getExchangeRate() {
        return exchangeRateRepository.findFirstByOrderByDateDesc();
    }


    @PostConstruct
    public void getExchangeRates() {

        LOGGER.info("Requesting Exchange rates from " + bcraApi);

        Date today = this.getDateWithoutTime();

        if (this.findByExchangeRateDate(today).isEmpty()) {

            ResponseEntity<ExchangeRateDTO[]> responseEntity = restTemplate.getForEntity(bcraApi, ExchangeRateDTO[].class);

            List<ExchangeRateDTO> exchangeRatesDTO = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));

            Optional<ExchangeRateDTO> officialExchangeRate = exchangeRatesDTO
                    .stream()
                    .filter(ExchangeRateDTO::isOfficialRate)
                    .findFirst();


            if (officialExchangeRate.isPresent()) {
                ExchangeRateDTO exchangeRateDTO = officialExchangeRate.get();
                exchangeRateDTO.setD(today);

                    ExchangeRate exchangeRate = new ExchangeRate();
                    exchangeRate.setDate(exchangeRateDTO.getD());
                    exchangeRate.setValue(exchangeRateDTO.getV());

                    exchangeRateRepository.save(exchangeRate);
            }
        }
    }

    @Cacheable(value = "criptosCache")
    public Iterable<CriptoCurrency> findAll() {

        return currencyRepository.findAll();
    }

    private Map<String, CriptoCurrencyDTO> getCriptoCurrencyDTOBySymbolId(List<CriptoCurrencyDTO> criptoCurrenciesInDB) {
        return criptoCurrenciesInDB.stream()
                .collect(Collectors.toMap(CriptoCurrencyDTO::getSymbol, Function.identity()));
    }

    public Optional<CriptoCurrency> findById(Integer id) {
        return currencyRepository.findById(id);
    }

    public Optional<ExchangeRate> findByExchangeRateDate(Date date) {
        return exchangeRateRepository.findByDate(date);
    }

    private Date getDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
