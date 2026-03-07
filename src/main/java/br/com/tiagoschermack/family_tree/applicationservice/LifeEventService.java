package br.com.tiagoschermack.family_tree.applicationservice;

import br.com.tiagoschermack.family_tree.domain.entity.LifeEvent;
import br.com.tiagoschermack.family_tree.domain.entity.Person;
import br.com.tiagoschermack.family_tree.domain.enums.ParticipationRole;
import br.com.tiagoschermack.family_tree.domain.relationship.EventParticipation;
import br.com.tiagoschermack.family_tree.domain.repository.FamilyTreeRepository;
import br.com.tiagoschermack.family_tree.domain.repository.LifeEventRepository;
import br.com.tiagoschermack.family_tree.domain.repository.PersonRepository;
import br.com.tiagoschermack.family_tree.dto.request.CreateLifeEventRequest;
import br.com.tiagoschermack.family_tree.dto.request.EventParticipantRequest;
import br.com.tiagoschermack.family_tree.dto.request.UpdateLifeEventRequest;
import br.com.tiagoschermack.family_tree.dto.response.EventParticipationResponse;
import br.com.tiagoschermack.family_tree.dto.response.LifeEventResponse;
import br.com.tiagoschermack.family_tree.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifeEventService {

    private final LifeEventRepository lifeEventRepository;
    private final PersonRepository personRepository;
    private final FamilyTreeRepository familyTreeRepository;

    public LifeEventService(LifeEventRepository lifeEventRepository, PersonRepository personRepository, FamilyTreeRepository familyTreeRepository) {
        this.lifeEventRepository  = lifeEventRepository;
        this.personRepository     = personRepository;
        this.familyTreeRepository = familyTreeRepository;
    }

    public LifeEventResponse createEvent(String treeId, String userId, CreateLifeEventRequest eventRequest) {
        findTreeByIdAndUserId(treeId, userId);

        LifeEvent eventSaved = lifeEventRepository.save(new LifeEvent(eventRequest.type(), eventRequest.date(), eventRequest.place(), eventRequest.description()));

        for (EventParticipantRequest participantRequest : eventRequest.participants()) {
            Person person = findPersonByIdAndTreeId(participantRequest.personId(), treeId);
            person.addLifeEvent(new EventParticipation(participantRequest.role(), eventSaved));

            personRepository.save(person);
        }

        return mapLifeEventResponse(eventSaved, treeId);
    }

    public LifeEventResponse getEvent(String treeId, String eventId, String userId) {
        findTreeByIdAndUserId(treeId, userId);

        return mapLifeEventResponse(findEventById(eventId), treeId);
    }

    public List<LifeEventResponse> getPersonEvents(String treeId, String personId, String userId) {
        findTreeByIdAndUserId(treeId, userId);

        return lifeEventRepository.findAllByPersonId(personId)
                .stream()
                .map   (event -> mapLifeEventResponse(event, treeId))
                .toList();
    }

    public LifeEventResponse updateEvent(String treeId, String eventId, String userId, UpdateLifeEventRequest eventRequest) {
        findTreeByIdAndUserId(treeId, userId);

        LifeEvent existingEvent = findEventById(eventId);

        if (eventRequest.type() != null)
            existingEvent.setType(eventRequest.type());

        if (eventRequest.date() != null)
            existingEvent.setDate(eventRequest.date());

        if (eventRequest.place() != null)
            existingEvent.setPlace(eventRequest.place());

        if (eventRequest.description() != null)
            existingEvent.setDescription(eventRequest.description());

        LifeEvent eventSaved = lifeEventRepository.save(existingEvent);
        return mapLifeEventResponse(eventSaved, treeId);
    }

    public void deleteEvent(String treeId, String eventId, String userId) {
        findTreeByIdAndUserId(treeId, userId);

        LifeEvent event = findEventById(eventId);
        event.setDeleted(true);

        lifeEventRepository.save(event);
    }

    private LifeEventResponse mapLifeEventResponse(LifeEvent event, String treeId) {
        return LifeEventResponse.of(event, mapEventParticipationResponses(event.getId(), treeId));
    }

    private List<EventParticipationResponse> mapEventParticipationResponses(String eventId, String treeId) {
        return personRepository.findAllByEventIdAndTreeId(eventId, treeId)
                .stream()
                .map   (person -> mapEventParticipationResponse(person, eventId))
                .toList();
    }

    private EventParticipationResponse mapEventParticipationResponse(Person person, String eventId) {
        ParticipationRole role = person.getLifeEvents()
                .stream     ()
                .filter     (ep -> ep.getEvent().getId().equals(eventId))
                .map        (EventParticipation::getRole)
                .findFirst  ()
                .orElseThrow(() -> new NotFoundException("Participação no evento não encontrada"));

        return new EventParticipationResponse(
                person.getId       (),
                person.getFirstName(),
                person.getLastName (),
                role
        );
    }

    private void findTreeByIdAndUserId(String treeId, String userId) {
        familyTreeRepository.findByTreeIdAndUserId(treeId, userId)
                .orElseThrow(() -> new NotFoundException("Árvore genealógica não encontrada"));
    }

    private Person findPersonByIdAndTreeId(String personId, String treeId) {
        return personRepository.findByIdAndTreeId(personId, treeId)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
    }

    private LifeEvent findEventById(String eventId) {
        return lifeEventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Evento não encontrado"));
    }
}
