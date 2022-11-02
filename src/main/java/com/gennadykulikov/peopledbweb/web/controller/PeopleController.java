package com.gennadykulikov.peopledbweb.web.controller;


import com.gennadykulikov.peopledbweb.biz.model.Person;
import com.gennadykulikov.peopledbweb.biz.service.PersonService;
import com.gennadykulikov.peopledbweb.data.FileStorageRepository;
import com.gennadykulikov.peopledbweb.data.PersonRepository;
import com.gennadykulikov.peopledbweb.exception.StorageException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
@Log4j2
public class PeopleController {
     @Autowired
     private PersonRepository personRepository;
     // Autowired annotation at the previous line makes Spring create arg constructor for PeopleController class
     // while creating and injecting personRepository object, so that there is no need to write this constructor explicitly
//     public PeopleController(PersonRepository personRepository) {
//          this.personRepository = personRepository;
//     }

    @Autowired
    private FileStorageRepository fileStorageRepository;

    @Autowired
    private PersonService personService;

     @ModelAttribute("people")
     public Page<Person> getPeople(@PageableDefault(size=3) Pageable page){
         return personService.findAll(page);
     }

     @ModelAttribute
     public Person getPerson() {
//         Person person = new Person();
//         person.setFirstName("John");
//         person.setLastName("Doe");
//         person.setDob(LocalDate.of(2000,01,01));
//         person.setSalary(new BigDecimal("1000.00"));
//         person.setEmail("john.doe@somecompany.com");
//         This allows to pre-populate fields in the form
         return new Person();
         // here annotation ModelAttribute will use the lowercased return type as name for the model
         // (this is "person" in the form in people.html file)
     }

     @GetMapping
     public String showPeoplePage(){
              return "people";
      }

      @GetMapping("/images/{resource}")
     public ResponseEntity<Resource> getResource(@PathVariable String resource){
         String dispo= """
                  attachment; filename="%s"
                 """;
              return ResponseEntity
                      .ok()
                      .header(HttpHeaders.CONTENT_DISPOSITION, String.format(dispo, resource))
                      .body(fileStorageRepository.findByName(resource));
     }

      @PostMapping
      public String savePerson(Model model, @Valid Person person, Errors errors, @RequestParam MultipartFile photoFile) throws IOException {
          log.info(person);
          log.info("Filename " + photoFile.getOriginalFilename());
          log.info("Size " + photoFile.getSize());
          log.info("Errors " + errors);
          if (!errors.hasErrors()) {
              try {
                  personService.save(person, photoFile.getInputStream());
                  return "redirect:people";
              } catch (StorageException e) {
                  model.addAttribute("errorMsg", "Photo files are currently not accepted");
                  return "people";
              }
          }
          return "people";
      }

      @PostMapping(params = "action=delete")
      public String deletePerson(@RequestParam Optional<List<Long>> selections){
          log.info(selections);
          if (selections.isPresent()) {
              personService.deleteAllById(selections.get());
          }
          return "redirect:people";
      }

    @PostMapping(params = "action=edit")
    public String editPerson(@RequestParam Optional<List<Long>> selections, Model model){
        log.info(selections);
        if (selections.isPresent()) {
            Optional<Person> person = personRepository.findById(selections.get().get(0));
            model.addAttribute("person", person);
        }
        return "people";
    }

    @PostMapping(params="action=import")
    public String importCSV(@RequestParam MultipartFile csvFile){
        log.info("File name :" + csvFile.getOriginalFilename());
        log.info("File sixe :" + csvFile.getSize());
        try {
            personService.importCSV(csvFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:people";
    }
}
