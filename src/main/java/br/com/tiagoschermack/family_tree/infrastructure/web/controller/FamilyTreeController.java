package br.com.tiagoschermack.family_tree.infrastructure.web.controller;

import br.com.tiagoschermack.family_tree.applicationservice.FamilyTreeService;
import br.com.tiagoschermack.family_tree.dto.request.CreateFamilyTreeRequest;
import br.com.tiagoschermack.family_tree.dto.request.UpdateFamilyTreeRequest;
import br.com.tiagoschermack.family_tree.dto.response.FamilyTreeResponse;
import br.com.tiagoschermack.family_tree.shared.security.UserAuthenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_TREES;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "Árvores Genealógicas", description = "Gerenciamento de árvores genealógicas")
@RestController
@RequestMapping(PATH_TREES)
@SecurityRequirement(name = "Bearer Authentication")
@SuppressWarnings("unused")
public class FamilyTreeController {

    private final FamilyTreeService familyTreeService;

    public FamilyTreeController(FamilyTreeService familyTreeService) {
        this.familyTreeService = familyTreeService;
    }

    @Operation(
            summary     = "Criar árvore",
            description = "Registra uma nova árvore genealógica"
    )
    @PostMapping
    public ResponseEntity<FamilyTreeResponse> createTree(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @RequestBody @Valid CreateFamilyTreeRequest familyRequest) {
        return ResponseEntity.status(CREATED).body(familyTreeService.createTree(userAuthenticated.getUser().getId(), familyRequest));
    }

    @Operation(
            summary     = "Listar árvores",
            description = "Retorna todas as árvores genealógicas do usuário"
    )
    @GetMapping
    public ResponseEntity<List<FamilyTreeResponse>> getTrees(@AuthenticationPrincipal UserAuthenticated userAuthenticated) {
        return ResponseEntity.ok(familyTreeService.getTrees(userAuthenticated.getUser().getId()));
    }

    @Operation(
            summary     = "Listar árvore",
            description = "Retorna os dados de uma árvore genealógica específica"
    )
    @GetMapping("/{treeId}")
    public ResponseEntity<FamilyTreeResponse> getTree(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId) {
        return ResponseEntity.ok(familyTreeService.getTree(treeId, userAuthenticated.getUser().getId()));
    }

    @Operation(
            summary     = "Atualizar árvore",
            description = "Atualiza nome, descrição ou visibilidade da árvore genealógica"
    )
    @PutMapping("/{treeId}")
    public ResponseEntity<FamilyTreeResponse> updateTree(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId, @RequestBody @Valid UpdateFamilyTreeRequest familyRequest) {
        return ResponseEntity.ok(familyTreeService.updateTree(treeId, userAuthenticated.getUser().getId(), familyRequest));
    }

    @Operation(
            summary     = "Remover árvore",
            description = "Remove a árvore genealógica"
    )
    @DeleteMapping("/{treeId}")
    public ResponseEntity<Void> deleteTree(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId) {
        familyTreeService.deleteTree(treeId, userAuthenticated.getUser().getId());

        return ResponseEntity.noContent().build();
    }
}
