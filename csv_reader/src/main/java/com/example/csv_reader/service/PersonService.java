package com.example.csv_reader.service;

import com.example.csv_reader.config.CsvFieldMappingConfig;
import com.example.csv_reader.dto.PersonCsvRepresentation;
import com.example.csv_reader.entity.Address;
import com.example.csv_reader.entity.Person;
import com.example.csv_reader.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;



import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Integer uploadPerson(MultipartFile file) throws IOException {
        Set<Person> persons = parseCsv(file);
        personRepository.saveAll(persons);
        return persons.size();
    }

    private Set<Person> parseCsv(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<PersonCsvRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(PersonCsvRepresentation.class);

            CsvToBean<PersonCsvRepresentation> csvToBean = new CsvToBeanBuilder<PersonCsvRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse().stream()
                    .map(csvLine -> {
                        Address address = new Address();
                        address.setLane1(csvLine.getLane1());
                        address.setLane2(csvLine.getLane2());
                        address.setCity(csvLine.getCity());
                        address.setZipcode(csvLine.getZipcode());
                        address.setCountry(csvLine.getCountry());

                        return Person.builder()
                                .firstName(csvLine.getFirstName())
                                .lastName(csvLine.getLastName())
                                .mobileNo(csvLine.getMobileNo())
                                .email(csvLine.getEmail())
                                .gender(csvLine.getGender())
                                .ipAddress(csvLine.getIpAddress())
                                .address(address)
                                .build();
                    })
                    .collect(Collectors.toSet());
        }
    }



    public int loadFromClasspath(String filename) throws IOException {
        Resource resource = new ClassPathResource(filename);
        try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            Set<Person> persons = parseReader(reader);
            personRepository.saveAll(persons);
            return persons.size();
        }
    }

    public Set<Person> parseReader(Reader reader) {
        HeaderColumnNameMappingStrategy<PersonCsvRepresentation> strategy =
                new HeaderColumnNameMappingStrategy<>();
        strategy.setType(PersonCsvRepresentation.class);

        CsvToBean<PersonCsvRepresentation> csvToBean =
                new CsvToBeanBuilder<PersonCsvRepresentation>(reader)
                .withMappingStrategy(strategy)
                .withIgnoreEmptyLine(true)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse().stream()
                .map(csv -> {
                    Address address = new Address();
                    address.setLane1(csv.getLane1());
                    address.setLane2(csv.getLane2());
                    address.setCity(csv.getCity());
                    address.setZipcode(csv.getZipcode());
                    address.setCountry(csv.getCountry());

                    return Person.builder()
                            .firstName(csv.getFirstName())
                            .lastName(csv.getLastName())
                            .email(csv.getEmail())
                            .gender(csv.getGender())
                            .ipAddress(csv.getIpAddress())
                            .address(address)
                            .build();
                })
                .collect(Collectors.toSet());
    }



    public Integer uploadUsingMappingField(InputStream inputStream) throws IOException, CsvValidationException {
        CsvFieldMappingConfig config = loadFieldMappingConfig();

        Map<String, String> fieldMap = config.getMapping().stream()
                .collect(Collectors.toMap(
                        map -> map.getSourceField().toLowerCase(),
                        map -> map.getDestinationField().toLowerCase()
                ));

        List<Person> people = new ArrayList<>();

        try(Reader reader = new BufferedReader(new InputStreamReader(inputStream));
        CSVReader csvReader = new CSVReader(reader)) {

            String[] headers = csvReader.readNext();
            Map<String, Integer> headerIndexMap = new HashMap<>();
        for(int i = 0; i<headers.length; i++){
                headerIndexMap.put(headers[i].trim().toLowerCase(), i);
            }

        String[] row;
        while ((row = csvReader.readNext()) != null){
            Person person = new Person();

            for (Map.Entry<String, String> entry : fieldMap.entrySet()){
                String source = entry.getKey();
                String destination = entry.getValue();
                int index = headerIndexMap.getOrDefault(source, -1);

                if(index == -1)continue;

                String value = row[index];

                switch (destination){
                    case "firstname" -> person.setFirstName(value);
                    case "lastName" -> person.setLastName(value);
                    case "mobileNo" -> person.setMobileNo(value);
                }
            }
            people.add(person);
        }
        }
        personRepository.saveAll(people);
        return people.size();
    }

    private CsvFieldMappingConfig loadFieldMappingConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(
                new ClassPathResource("config/config.json").getInputStream(),
                CsvFieldMappingConfig.class
        );
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Integer id) {
        return personRepository.findById(id)
                .orElseThrow(null);
    }


    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Integer id, Person personDetails) {
        Person existing = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
        existing.setFirstName(personDetails.getFirstName());
        existing.setLastName(personDetails.getLastName());
        existing.setMobileNo(personDetails.getMobileNo());
        existing.setEmail(personDetails.getEmail());
        existing.setGender(personDetails.getGender());
        existing.setIpAddress(personDetails.getIpAddress());
        existing.setAddress(personDetails.getAddress());
        // add other field updates here
        return personRepository.save(existing);
    }

    public void deletePerson(Integer id) {
        Person existing = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
        personRepository.delete(existing);
    }


}
