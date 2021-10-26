package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrency;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.CriptoCurrencyDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.CriptoCurrencyRepository;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.ExchangeRateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriptoCurrencyServiceTest {


    @Autowired
    private CriptoCurrencyService criptoCurrencyService;

    @Mock
    public RestTemplate restTemplate;

    @MockBean
    private CriptoCurrencyRepository currencyRepository;

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {

        List<CriptoCurrency> criptoCurrencies = new ArrayList<>();
        CriptoCurrency criptoCurrency = new CriptoCurrency();
        criptoCurrency.setPrice(1.1);
        criptoCurrency.setSymbol("ABC");
        criptoCurrencies.add(criptoCurrency);

        mockServer = MockRestServiceServer.createServer(restTemplate);

        Mockito.when(currencyRepository.findAll()).thenReturn(criptoCurrencies);
//        Mockito.when(restTemplate.getForEntity(ArgumentMatchers.eq("theUrl"),
//                        ArgumentMatchers.any(CriptoCurrencyDTO.class)))
//                .thenReturn(new ResponseEntity <>(someMap, HttpStatus.OK));

    }

    @Test
    public void givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_thenReturnsMockedObject() throws Exception {
        CriptoCurrencyDTO criptoCurrencyDTO = new CriptoCurrencyDTO();
        CriptoCurrencyDTO[] criptoCurrencyDTOS =  new CriptoCurrencyDTO[]{criptoCurrencyDTO};

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://api1.binance.com/api/v3/ticker/price")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(criptoCurrencyDTOS))
                );

        Iterable<CriptoCurrency> criptoCurrencies = criptoCurrencyService.updatePrices();
        mockServer.verify();
        Assert.assertEquals(criptoCurrencyDTOS.length, List.of(criptoCurrencies).size());
    }


//    private ResponseEntity<?> getTestResponse(){
//
//        String json = "[\n" +
//                "    {\n" +
//                "        \"symbol\": \"ETHBTC\",\n" +
//                "        \"price\": \"0.06161000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"symbol\": \"LTCBTC\",\n" +
//                "        \"price\": \"0.00311100\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"symbol\": \"BNBBTC\",\n" +
//                "        \"price\": \"0.00720000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"symbol\": \"NEOBTC\",\n" +
//                "        \"price\": \"0.00078400\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"symbol\": \"QTUMETH\",\n" +
//                "        \"price\": \"0.00371000\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"symbol\": \"EOSETH\",\n" +
//                "        \"price\": \"0.00128200\"\n" +
//                "    },";
//        return new ResponseEntity.BodyBuilder("body", json);
//
//    }
}