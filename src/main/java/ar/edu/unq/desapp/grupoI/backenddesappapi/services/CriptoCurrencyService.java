package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrency;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrencyDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.CriptoCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CriptoCurrencyService {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private CriptoCurrencyRepository currencyRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public Iterable<CriptoCurrency> getCotizaciones() {

        List<CriptoCurrency> criptoCurrenciesInDB = (ArrayList<CriptoCurrency>) currencyRepository.findAll();

        List<CriptoCurrency> criptosToUpdate = new ArrayList<>();


        if (criptoCurrenciesInDB.size() > 0) {

            ResponseEntity<CriptoCurrencyDTO[]> responseEntity =
                    restTemplate.getForEntity("https://api1.binance.com/api/v3/ticker/price", CriptoCurrencyDTO[].class);
            List<CriptoCurrencyDTO> criptoCurrenciesBinance = Arrays.asList(responseEntity.getBody());

            Map<String, CriptoCurrencyDTO> criptoCurrencyDTOBySymbolId = this.getCriptoCurrencyDTOBySymbolId(criptoCurrenciesBinance);

            criptoCurrenciesInDB.forEach(criptoCurrency -> {
                CriptoCurrencyDTO criptoCurrencyDTO = criptoCurrencyDTOBySymbolId.get(criptoCurrency.getSymbol());
                CriptoCurrency criptoCurrencyToUpdate = new CriptoCurrency();
                criptoCurrencyToUpdate.setId(criptoCurrency.getId());
                criptoCurrencyToUpdate.setSymbol(criptoCurrency.getSymbol());
                criptoCurrencyToUpdate.setName(criptoCurrency.getName());
                criptoCurrencyToUpdate.setPrice(criptoCurrencyDTO.getPrice());

                criptosToUpdate.add(criptoCurrencyToUpdate);
            });
        }
        return currencyRepository.saveAll(criptosToUpdate);
    }

    public Iterable<CriptoCurrency> findAll(){

        return currencyRepository.findAll();
    }

    private Map<String, CriptoCurrencyDTO> getCriptoCurrencyDTOBySymbolId(List<CriptoCurrencyDTO> criptoCurrenciesInDB) {
        return criptoCurrenciesInDB.stream()
                .collect(Collectors.toMap(CriptoCurrencyDTO::getSymbol, Function.identity()));
    }

    public Optional<CriptoCurrency> findById(Integer id) {
        return currencyRepository.findById(id);
    }
}
