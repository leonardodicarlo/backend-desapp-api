package ar.edu.unq.desapp.grupoi.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoi.backenddesappapi.services.CriptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/criptocurrencies")
public class CriptoCurrencyController {

    @Autowired
    private CriptoCurrencyService service;

    @GetMapping("/price")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/updateprices")
    public ResponseEntity<?> updatePrices() {
        service.getExchangeRates();
        service.updatePrices();
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }



}
