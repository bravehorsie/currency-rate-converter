package pl.cleankod.exchange.provider;

import pl.cleankod.exchange.core.domain.Money;
import pl.cleankod.exchange.core.gateway.CurrencyConversionService;
import pl.cleankod.exchange.provider.nbp.ExchangeRatesNbpClientService;
import pl.cleankod.exchange.provider.nbp.model.RateWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class CurrencyConversionNbpService implements CurrencyConversionService {
    private final ExchangeRatesNbpClientService exchangeRatesNbpClientService;

    public CurrencyConversionNbpService(ExchangeRatesNbpClientService exchangeRatesNbpClientService) {
        this.exchangeRatesNbpClientService = exchangeRatesNbpClientService;
    }

    @Override
    public Money convert(Money money, Currency targetCurrency) {
        RateWrapper rateWrapper = exchangeRatesNbpClientService.fetch("A", targetCurrency.getCurrencyCode());
        BigDecimal midRate = rateWrapper.rates().get(0).mid();
        //HALF_EVEN ala bankers rounding, not to lose money over time (hopefully not a code smell comment)
        BigDecimal calculatedRate = money.amount().divide(midRate, 2, RoundingMode.HALF_EVEN);
        return new Money(calculatedRate, targetCurrency);
    }
}
