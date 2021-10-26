package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrency;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrencyDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.ExchangeRate;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.ExchangeRateDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.CriptoCurrencyRepository;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CriptoCurrencyService {

    @Value("${bcra.accessToken}")
    private String accessToken;
    @Value("${bcra.api}")
    private String bcraApi;
    @Value("${binance.api}")
    private String binanceApi;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private CriptoCurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private RestTemplate restTemplate;

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

    public void getExchangeRates() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        ResponseEntity<ExchangeRateDTO[]> responseEntity = restTemplate.
                exchange(bcraApi, HttpMethod.GET, new HttpEntity<>(headers),
                        ExchangeRateDTO[].class,
                        headers
                );

        List<ExchangeRateDTO> exchangeRatesDTO = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));

        Optional<ExchangeRateDTO> exchangeRateDTOOptional = exchangeRatesDTO.stream().max(Comparator.comparing(ExchangeRateDTO::getD));
        if (exchangeRateDTOOptional.isPresent()) {
            ExchangeRateDTO exchangeRateDTO = exchangeRateDTOOptional.get();

            if (this.findByExchangeRateDate(addHoursToJavaUtilDate(exchangeRateDTO.getD(), 3)).isEmpty()) {
                ExchangeRate exchangeRate = new ExchangeRate();

                exchangeRate.setDate(addHoursToJavaUtilDate(exchangeRateDTO.getD(), 3));
                exchangeRate.setValue(exchangeRateDTO.getV());

                exchangeRateRepository.save(exchangeRate);
            }
        }
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

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
}
