package ar.edu.unq.desapp.grupoI.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.UserDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.CriptoCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/criptocurrencies")
public class CriptoCurrenciesController {

    @Autowired
    private CriptoCurrencyRepository repository;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RequestMapping(value = "/algo", method = RequestMethod.POST)
    public String get(@RequestBody UserDTO user, RestTemplate restTemplate) throws Exception {

        Map<String, Object> response = new HashMap<>();

            return restTemplate.getForObject(
                    "https://api1.binance.com/api/v3/ticker/price", String.class);
    }



}
