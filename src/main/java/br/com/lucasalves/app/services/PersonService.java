package br.com.lucasalves.app.services;

import br.com.lucasalves.app.exceptions.ResourceNotFoundException;
import br.com.lucasalves.app.exceptions.handler.CustomizedResponseEntityExceptionHandler;
import br.com.lucasalves.app.data.vo.PersonVO;
import br.com.lucasalves.app.repositories.PersonRespository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    private final PersonRespository repository;

    public PersonService(PersonRespository repository) {
        this.repository = repository;
    }

    public List<PersonVO> findAll() {
        logger.info("Finding all people!");

        return this.repository.findAll();
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");

        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person!");

        return this.repository.save(person);
    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating one person!");

        var entity = this.repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return this.repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        var entity = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        this.repository.delete(entity);
    }


}