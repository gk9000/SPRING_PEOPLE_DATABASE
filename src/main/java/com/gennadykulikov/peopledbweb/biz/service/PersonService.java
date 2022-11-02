package com.gennadykulikov.peopledbweb.biz.service;

import com.gennadykulikov.peopledbweb.biz.model.Person;
import com.gennadykulikov.peopledbweb.data.FileStorageRepository;
import com.gennadykulikov.peopledbweb.data.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipInputStream;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final FileStorageRepository storageRepository;

    public PersonService(PersonRepository personRepository, FileStorageRepository storageRepository) {
        this.personRepository = personRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public Person save(Person person, InputStream photoStream) {
        Person savedPerson = personRepository.save(person);
        storageRepository.save(person.getPhotoFile(), photoStream);
        return savedPerson;
    }

    public Optional<Person> findById(Long aLong) {
        return personRepository.findById(aLong);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public void deleteAllById(Iterable<Long> ids) {

        Iterable<Person> peopleToDelete = personRepository.findAllById(ids);
        Stream<Person> peopleStream = StreamSupport.stream(peopleToDelete.spliterator(), false);
        Set<String> filenames = peopleStream.map(Person::getPhotoFile).collect(Collectors.toSet());

        personRepository.deleteAllById(ids);
        storageRepository.deleteAllByName(filenames);
    }


    public void importCSV(InputStream csvFileStream) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(csvFileStream); // change zipped bytes stream into bytes stream
            zipInputStream.getNextEntry(); // point to the only existing file withing zip package
            InputStreamReader inputStreamReader = new InputStreamReader(zipInputStream); // change bytes stream into characters stream
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //change characters stream into stream of lines of text
            bufferedReader.lines()
                    .skip(1)
                    .limit(20)
                    .map(Person::parse)
                    .forEach(personRepository::save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
