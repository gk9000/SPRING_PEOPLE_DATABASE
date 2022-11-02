package com.gennadykulikov.peopledbweb.data;

import com.gennadykulikov.peopledbweb.biz.model.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//@Component
public class PersonDataLoader implements ApplicationRunner {
    private PersonRepository personRepository;

    public PersonDataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        if (personRepository.count()==0) {
//            List<Person> listOfPeople = List.of(
//                    new Person(null,"Vasya", "Pupkin", LocalDate.of(2000, 1,30), new BigDecimal("999.99"), "vasya@pupkin.com"),
//                    new Person(null,"Alex", "Smithson", LocalDate.of(1982, 2,25), new BigDecimal("637.12"), "alex@smithson.com"),
//                    new Person(null,"Zurab", "Minassali", LocalDate.of(1995, 5,9), new BigDecimal("1057.33"), "zurab@winemakers.com"),
//                    new Person(null,"Rajiv", "Kumar", LocalDate.of(1990, 8,1), new BigDecimal("758.00"), "rajiv@biryani.net"));
//            personRepository.saveAll(listOfPeople);
//        }
    }
}
