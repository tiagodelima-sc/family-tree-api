package br.com.tiagoschermack.family_tree.infrastructure.web.controller;

import br.com.tiagoschermack.family_tree.applicationservice.PersonService;
import br.com.tiagoschermack.family_tree.dto.request.CreatePersonRequest;
import br.com.tiagoschermack.family_tree.dto.request.UpdatePersonRequest;
import br.com.tiagoschermack.family_tree.dto.response.PersonDetailResponse;
import br.com.tiagoschermack.family_tree.dto.response.PersonSummaryResponse;
import br.com.tiagoschermack.family_tree.shared.security.UserAuthenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_PERSONS;
import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_TREES;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "Pessoas", description = "Gerenciamento de pessoas dentro de uma árvore genealógica")
@RestController
@RequestMapping(PATH_TREES + "/{treeId}" + PATH_PERSONS)
@SecurityRequirement(name = "Bearer Authentication")
@SuppressWarnings("unused")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(
            summary     = "Criar pessoa",
            description = "Adiciona uma nova pessoa à árvore genealógica"
    )
    @PostMapping
    public ResponseEntity<PersonDetailResponse> createPerson(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId, @RequestBody @Valid CreatePersonRequest personRequest) {
        return ResponseEntity.status(CREATED).body(personService.createPerson(treeId, userAuthenticated.getUser().getId(), personRequest));
    }

    @Operation(
            summary     = "Listar pessoas",
            description = "Retorna todas as pessoas da árvore genealógica"
    )
    @GetMapping
    public ResponseEntity<List<PersonSummaryResponse>> getPersons(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId) {
        return ResponseEntity.ok(personService.getPersons(treeId, userAuthenticated.getUser().getId()));
    }

    @Operation(
            summary     = "Buscar pessoa",
            description = "Retorna os detalhes de uma pessoa, incluindo pais, filhos e os acontecimentos de vida"
    )
    @GetMapping("/{personId}")
    public ResponseEntity<PersonDetailResponse> getPerson(@PathVariable String treeId, @PathVariable String personId) {
        return ResponseEntity.ok(personService.getPerson(treeId, personId));
    }

    @Operation(
            summary     = "Atualizar pessoa",
            description = "Atualiza os dados de uma pessoa"
    )
    @PutMapping("/{personId}")
    public ResponseEntity<PersonDetailResponse> updatePerson(@PathVariable String treeId, @PathVariable String personId, @RequestBody @Valid UpdatePersonRequest personRequest) {
        return ResponseEntity.ok(personService.updatePerson(treeId, personId, personRequest));
    }

    @Operation(
            summary     = "Deletar pessoa",
            description = "Remove uma pessoa da árvore genealógica"
    )
    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable String treeId, @PathVariable String personId) {
        personService.deletePerson(treeId, personId);

        return ResponseEntity.noContent().build();
    }
}
