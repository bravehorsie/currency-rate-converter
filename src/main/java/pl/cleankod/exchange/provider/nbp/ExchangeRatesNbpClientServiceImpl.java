package pl.cleankod.exchange.provider.nbp;

import org.springframework.cache.annotation.Cacheable;
import pl.cleankod.exchange.provider.nbp.model.RateWrapper;

public class ExchangeRatesNbpClientServiceImpl implements ExchangeRatesNbpClientService {

    private final ExchangeRatesNbpClient exchangeRatesNbpClient;

    public ExchangeRatesNbpClientServiceImpl(ExchangeRatesNbpClient exchangeRatesNbpClient) {
        this.exchangeRatesNbpClient = exchangeRatesNbpClient;
    }

    @Override
    @Cacheable("currency")
    public RateWrapper fetch(String table, String currency) {
        return exchangeRatesNbpClient.fetch(table, currency);
    }
}
