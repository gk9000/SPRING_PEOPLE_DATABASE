package com.gennadykulikov.peopledbweb.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocalDateFormatter implements Formatter <LocalDate> {
//    private final DateTimeFormatter formatterForParsing = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatterForParsing = DateTimeFormatter.ISO_LOCAL_DATE;
//    private final DateTimeFormatter formatterForPrinting = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter formatterForPrinting = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, formatterForParsing);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return formatterForPrinting.format(object);
    }
}
