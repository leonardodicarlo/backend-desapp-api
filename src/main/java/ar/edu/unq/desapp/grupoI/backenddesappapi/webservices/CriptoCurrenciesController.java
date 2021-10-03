package ar.edu.unq.desapp.grupoI.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrency;
import ar.edu.unq.desapp.grupoI.backenddesappapi.services.CriptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/criptocurrencies")
public class CriptoCurrenciesController {

    @Autowired
    private CriptoCurrencyService service;

    @GetMapping("/price")
    public ResponseEntity<?> getAll() throws Exception {

        service.getCotizaciones();
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws Exception {

        return ResponseEntity.ok(service.findById(id));
    }



}
