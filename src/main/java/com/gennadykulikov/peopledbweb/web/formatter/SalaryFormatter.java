package com.gennadykulikov.peopledbweb.web.formatter;

import com.fasterxml.jackson.core.io.BigDecimalParser;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

@Component
public class SalaryFormatter implements Formatter<BigDecimal> {

    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        return null;
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
        return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("ru-RU")).format(object);

    }
}
