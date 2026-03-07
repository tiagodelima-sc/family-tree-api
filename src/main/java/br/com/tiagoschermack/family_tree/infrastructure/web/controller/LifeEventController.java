package br.com.tiagoschermack.family_tree.infrastructure.web.controller;

import br.com.tiagoschermack.family_tree.applicationservice.LifeEventService;
import br.com.tiagoschermack.family_tree.dto.request.CreateLifeEventRequest;
import br.com.tiagoschermack.family_tree.dto.request.UpdateLifeEventRequest;
import br.com.tiagoschermack.family_tree.dto.response.LifeEventResponse;
import br.com.tiagoschermack.family_tree.shared.security.UserAuthenticated;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(PATH_TREES + "/{treeId}")
@SuppressWarnings("unused")
public class LifeEventController {

    private final LifeEventService lifeEventService;

    public LifeEventController(LifeEventService lifeEventService) {
        this.lifeEventService = lifeEventService;
    }

    @PostMapping(PATH_EVENTS)
    public ResponseEntity<LifeEventResponse> createEvent(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId, @RequestBody @Valid CreateLifeEventRequest eventRequest) {
        return ResponseEntity.status(CREATED).body(lifeEventService.createEvent(treeId, userAuthenticated.getUser().getId(), eventRequest));
    }

    @GetMapping(PATH_EVENTS + "/{eventId}")
    public ResponseEntity<LifeEventResponse> getEvent(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId, @PathVariable String eventId) {
        return ResponseEntity.ok(lifeEventService.getEvent(treeId, eventId, userAuthenticated.getUser().getId()));
    }

    @GetMapping(PATH_PERSONS + "/{personId}" + PATH_EVENTS)
    public ResponseEntity<List<LifeEventResponse>> getPersonEvents(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId, @PathVariable String personId) {
        return ResponseEntity.ok(lifeEventService.getPersonEvents(treeId, personId, userAuthenticated.getUser().getId()));
    }

    @PutMapping(PATH_EVENTS + "/{eventId}")
    public ResponseEntity<LifeEventResponse> updateEvent(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId, @PathVariable String eventId, @RequestBody @Valid UpdateLifeEventRequest lifeRequest) {
        return ResponseEntity.ok(lifeEventService.updateEvent(treeId, eventId, userAuthenticated.getUser().getId(), lifeRequest));
    }

    @DeleteMapping(PATH_EVENTS + "/{eventId}")
    public ResponseEntity<Void> deleteEvent(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @PathVariable String treeId, @PathVariable String eventId) {
        lifeEventService.deleteEvent(treeId, eventId, userAuthenticated.getUser().getId());

        return ResponseEntity.noContent().build();
    }

}
