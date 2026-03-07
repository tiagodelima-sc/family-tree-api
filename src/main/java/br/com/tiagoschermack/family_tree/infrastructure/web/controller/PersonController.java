package br.com.tiagoschermack.family_tree.infrastructure.web.controller;

import br.com.tiagoschermack.family_tree.applicationservice.PersonService;
import br.com.tiagoschermack.family_tree.dto.request.CreatePersonRequest;
import br.com.tiagoschermack.family_tree.dto.request.UpdatePersonRequest;
import br.com.tiagoschermack.family_tree.dto.response.PersonDetailResponse;
import br.com.tiagoschermack.family_tree.dto.response.PersonSummaryResponse;
import br.com.tiagoschermack.family_tree.shared.security.UserAuthenticated;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_PERSONS;
import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_TREES;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(PATH_TREES + "/{treeId}" + PATH_PERSONS)
@SuppressWarnings("unused")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDetailResponse> createPerson(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId, @RequestBody @Valid CreatePersonRequest personRequest) {
        return ResponseEntity.status(CREATED).body(personService.createPerson(treeId, userAuthenticated.getUser().getId(), personRequest));
    }

    @GetMapping
    public ResponseEntity<List<PersonSummaryResponse>> getPersons(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId) {
        return ResponseEntity.ok(personService.getPersons(treeId, userAuthenticated.getUser().getId()));
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonDetailResponse> getPerson(@PathVariable String treeId, @PathVariable String personId) {
        return ResponseEntity.ok(personService.getPerson(treeId, personId));
    }

    @PutMapping("/{personId}")
    public ResponseEntity<PersonDetailResponse> updatePerson(@PathVariable String treeId, @PathVariable String personId, @RequestBody @Valid UpdatePersonRequest personRequest) {
        return ResponseEntity.ok(personService.updatePerson(treeId, personId, personRequest));
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable String treeId, @PathVariable String personId) {
        personService.deletePerson(treeId, personId);

        return ResponseEntity.noContent().build();
    }
}
