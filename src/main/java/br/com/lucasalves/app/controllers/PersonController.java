package br.com.lucasalves.app.controllers;

import br.com.lucasalves.app.data.vo.PersonVO;
import br.com.lucasalves.app.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<List<PersonVO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PersonVO> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PersonVO> create(@RequestBody PersonVO personVO) {
        return new ResponseEntity<>(service.create(personVO), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PersonVO> update(@RequestBody PersonVO personVO) {
        return new ResponseEntity<>(service.update(personVO), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
       service.delete(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
