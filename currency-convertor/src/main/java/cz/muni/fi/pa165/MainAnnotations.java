package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency CZK = Currency.getInstance("CZK");

    public static void main(String[] args) {
        //Not working
        /*ApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        CurrencyConvertor convertor = applicationContext.getBean(CurrencyConvertor.class);

        BigDecimal result = convertor.convert(EUR, CZK, BigDecimal.ONE);
        System.out.println(result);*/
    }

}
