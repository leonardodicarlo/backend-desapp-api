package ar.edu.unq.desapp.grupoI.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.TransactionBuySellDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.services.CriptoCurrencyService;
import ar.edu.unq.desapp.grupoI.backenddesappapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/transactions")
public class TransactionController {

    private CriptoCurrencyService currencyService;
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 CriptoCurrencyService currencyService) {

        this.transactionService = transactionService;
        this.currencyService = currencyService;
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sell(@RequestBody TransactionBuySellDTO transactionBuySellDTO) {


        return ResponseEntity.ok(transactionService.save(transactionBuySellDTO));
    }
    @PutMapping("/sell")
    public ResponseEntity<?> update(@RequestBody TransactionBuySellDTO transactionBuySellDTO) {


        return ResponseEntity.ok(transactionService.updateTransaction(transactionBuySellDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws Exception {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/offers")
    public ResponseEntity<?> getTransactions(@RequestHeader String userId) {

        return ResponseEntity.ok(transactionService.findOffers(userId));
    }
    @GetMapping("/mytransactions")
    public ResponseEntity<?> getMyTransactions(@RequestHeader String userId) {

        return ResponseEntity.ok(transactionService.findMyTransactions(userId));
    }


}
