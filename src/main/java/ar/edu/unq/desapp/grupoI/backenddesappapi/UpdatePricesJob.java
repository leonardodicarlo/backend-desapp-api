package ar.edu.unq.desapp.grupoI.backenddesappapi;

import ar.edu.unq.desapp.grupoI.backenddesappapi.services.CriptoCurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdatePricesJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePricesJob.class);

    private final CriptoCurrencyService criptoCurrencyService;

    @Autowired
    public UpdatePricesJob(CriptoCurrencyService criptoCurrencyService) {
        this.criptoCurrencyService = criptoCurrencyService;
    }

    @Scheduled(cron = "*/60 * * * * *")
    public void reportCurrentTime() {
        LOGGER.info("Actualizacion de precios iniciada:");
        try {
            criptoCurrencyService.updatePrices();
        }
        catch (Exception error){

            LOGGER.error("Falló conexión API BINANCE");
        }


    }

    @Scheduled(cron = "@daily")
    public void updateExchangeRates() {
        try {
            criptoCurrencyService.getExchangeRates();
        }
        catch (Exception error){
            LOGGER.error("Attempt to request exchange rates failed");
        }


    }

}