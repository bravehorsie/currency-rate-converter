package pl.cleankod.exchange.provider.nbp;

import pl.cleankod.exchange.provider.nbp.model.RateWrapper;

public interface ExchangeRatesNbpClientService {

    RateWrapper fetch(String table, String currency);
}
