package pl.cleankod.exchange.core.gateway

import pl.cleankod.exchange.core.domain.Money
import pl.cleankod.exchange.provider.CurrencyConversionNbpService
import pl.cleankod.exchange.provider.nbp.ExchangeRatesNbpClient
import pl.cleankod.exchange.provider.nbp.model.Rate
import pl.cleankod.exchange.provider.nbp.model.RateWrapper
import spock.lang.Specification

class CurrencyConversionServiceTest extends Specification {

    /**
     * A test to confirm TODO-1 impl works correctly.
     * TODO: remove after API level integration test is defined for this, to confront with project conventions.
     */
    void "testCurrencyConversion expect scale 2 and round mode HALF_EVEN" (){
        def eur = Currency.getInstance("EUR")
        def czk = Currency.getInstance("CZK")
        def client = Mock(ExchangeRatesNbpClient)
        client.fetch(_, _) >> mockRate("1000")
        CurrencyConversionService service = new CurrencyConversionNbpService(client)

        when:
        def resultRoundDown = service.convert(new Money(new BigDecimal(1665), eur), czk)

        then:
        new BigDecimal("1.66") == resultRoundDown.amount()
        2 == resultRoundDown.amount().scale()

        when:
        def resultRoundUp = service.convert(new Money(new BigDecimal(1675), eur), czk)

        then:
        new BigDecimal("1.68") == resultRoundUp.amount()
        2 == resultRoundUp.amount().scale()

    }

    static RateWrapper mockRate(String rate) {
        return new RateWrapper("A", "CZK", "203", List.of(new Rate("1", "today", new BigDecimal(rate))));
    }
}
