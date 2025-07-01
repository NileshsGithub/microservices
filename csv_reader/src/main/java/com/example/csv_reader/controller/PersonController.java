    package com.example.csv_reader.controller;

    import com.example.csv_reader.entity.Person;
    import com.example.csv_reader.service.PersonService;
    import com.opencsv.exceptions.CsvValidationException;
    import jakarta.annotation.Resource;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.List;

    @RestController
    @RequestMapping("/api/person")
    public class PersonController {

        private final PersonService personService;

        public PersonController(PersonService service) {
            this.personService = service;
        }

        @PostMapping(value = "/upload", consumes = {"Multipart/form-data"})
        public ResponseEntity<Integer> upload(
                @RequestPart("file")MultipartFile file) throws IOException {
                return ResponseEntity.ok(personService.uploadPerson(file));
        }


        @GetMapping("/load")
        public ResponseEntity<String> loadFromClasspath() throws IOException {
            int count = personService.loadFromClasspath("csv/persons.csv");
            return ResponseEntity.ok("Loaded " + count + " persons");
        }

        @GetMapping("/load-mapped")
        public ResponseEntity<String> loadMappedFromClassPath() throws IOException, CsvValidationException {
            ClassPathResource resource = new ClassPathResource("csv/persons.csv");
            int count = personService.uploadUsingMappingField(resource.getInputStream());
            return ResponseEntity.ok("Loaded" + count + "persons using mapped fields");
        }


        @GetMapping
        public List<Person> getAllPersons(){
            return personService.getAllPersons();
        }

        @GetMapping("/{id}")
        public Person get(@PathVariable Integer id) {
            return personService.getPersonById(id);
        }


        @PostMapping
        public ResponseEntity<Person> createPerson(@RequestBody Person person) {
            Person saved = personService.createPerson(person);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Person> updatePerson(
                @PathVariable Integer id,
                @RequestBody Person personDetails) {
            Person updated = personService.updatePerson(id, personDetails);
            return ResponseEntity.ok(updated);
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
            personService.deletePerson(id);
            return ResponseEntity.noContent().build();
        }

    }
