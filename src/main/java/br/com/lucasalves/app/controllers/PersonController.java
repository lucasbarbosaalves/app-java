package br.com.lucasalves.app.controllers;

import br.com.lucasalves.app.data.vo.PersonVO;
import br.com.lucasalves.app.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(
            summary = "Find all people recorded",
            description = "Find all people recorded in the database",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))),
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            }
    )
    public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam (value = "size", defaultValue = "12") Integer size,
            @RequestParam (value = "direction", defaultValue = "asc") String direction){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

        // sobrecarga com ordenação
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/findPersonsByName/{firstName}",produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(
            summary = "Find people recorded by name",
            description = "Find people recorded by name in the database",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))),
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            }
    )
    public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonsByName(
            @PathVariable(value = "firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam (value = "size", defaultValue = "12") Integer size,
            @RequestParam (value = "direction", defaultValue = "asc") String direction){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

        // sobrecarga com ordenação
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return new ResponseEntity<>(service.findPersonsByName(firstName,pageable), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(
            summary = "Find a person",
            description = "Find a person by id",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            }
    )
    public ResponseEntity<PersonVO> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/json", "application/xml", "application/x-yaml"}, produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(
            summary = "Add a new Person",
            description = "Add a new person by passing a JSON, XML or YAML object",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            }
    )
    public ResponseEntity<PersonVO> create(@RequestBody PersonVO personVO) {
        return new ResponseEntity<>(service.create(personVO), HttpStatus.CREATED);
    }

    @PutMapping(consumes = {"application/json", "application/xml", "application/x-yaml"}, produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(
            summary = "Update a person",
            description = "Update a Person by passing a JSON, XML or YAML object",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            }
    )
    public ResponseEntity<PersonVO> update(@RequestBody PersonVO personVO) {
        return new ResponseEntity<>(service.update(personVO), HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}",consumes = {"application/json", "application/xml", "application/x-yaml"}, produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(
            summary = "Disable a person",
            description = "Disable a Person by your id passing a JSON, XML or YAML object",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            }
    )
    public ResponseEntity<PersonVO> disablePerson(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(service.disablePerson(id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete a person",
            description = "Delete a Person by id",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
       service.delete(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
