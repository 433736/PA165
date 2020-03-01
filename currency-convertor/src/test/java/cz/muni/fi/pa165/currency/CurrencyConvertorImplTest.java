package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private static Currency eurCurrency = Currency.getInstance("EUR");
    private static Currency czkCurrency = Currency.getInstance("CZK");

    @Mock
    public ExchangeRateTable exchangeRateTable;

    public CurrencyConvertor currencyConvertor;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(eurCurrency, czkCurrency))
                .thenReturn(new BigDecimal("10.0"));

        assertEquals(new BigDecimal("24.12"), currencyConvertor.convert(eurCurrency, czkCurrency, new BigDecimal("2.4125")));
        assertEquals(new BigDecimal("24.12"), currencyConvertor.convert(eurCurrency, czkCurrency, new BigDecimal("2.4115")));
        assertEquals(new BigDecimal("24.13"), currencyConvertor.convert(eurCurrency, czkCurrency, new BigDecimal("2.4126")));
        assertEquals(new BigDecimal("24.12"), currencyConvertor.convert(eurCurrency, czkCurrency, new BigDecimal("2.4124")));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(null, czkCurrency, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(eurCurrency, null, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(eurCurrency, czkCurrency, null);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(eurCurrency, czkCurrency))
                .thenReturn(null);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(eurCurrency, czkCurrency, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(eurCurrency, czkCurrency))
                .thenThrow(ExternalServiceFailureException.class);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(eurCurrency, czkCurrency, BigDecimal.ONE);
    }

}
