package br.com.tiagoschermack.family_tree.applicationservice;

import br.com.tiagoschermack.family_tree.domain.entity.FamilyTree;
import br.com.tiagoschermack.family_tree.domain.entity.Person;
import br.com.tiagoschermack.family_tree.domain.relationship.EventParticipation;
import br.com.tiagoschermack.family_tree.domain.repository.FamilyTreeRepository;
import br.com.tiagoschermack.family_tree.domain.repository.PersonRepository;
import br.com.tiagoschermack.family_tree.dto.request.CreatePersonRequest;
import br.com.tiagoschermack.family_tree.dto.request.UpdatePersonRequest;
import br.com.tiagoschermack.family_tree.dto.response.EventParticipationResponse;
import br.com.tiagoschermack.family_tree.dto.response.LifeEventResponse;
import br.com.tiagoschermack.family_tree.dto.response.PersonDetailResponse;
import br.com.tiagoschermack.family_tree.dto.response.PersonSummaryResponse;
import br.com.tiagoschermack.family_tree.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FamilyTreeRepository familyTreeRepository;

    public PersonService(PersonRepository personRepository, FamilyTreeRepository familyTreeRepository) {
        this.personRepository     = personRepository;
        this.familyTreeRepository = familyTreeRepository;
    }

    public PersonDetailResponse createPerson(String treeId, String userId, CreatePersonRequest personRequest) {
        Person personSaved = personRepository.save(new Person(personRequest.firstName(), personRequest.lastName(), personRequest.gender(), findTreeByIdAndUserId(treeId, userId)));

        return mapPersonDetailResponse(personSaved);
    }

    public PersonDetailResponse getPerson(String treeId, String personId) {
        return mapPersonDetailResponse(findPersonByIdAndTreeId(personId, treeId));
    }

    public List<PersonSummaryResponse> getPersons(String treeId, String userId) {
        return personRepository.findAllByTreeIdAndUserId(treeId, userId)
                .stream()
                .map   (PersonSummaryResponse::of)
                .toList();
    }

    public PersonDetailResponse updatePerson(String treeId, String personId, UpdatePersonRequest personRequest) {
        Person person = findPersonByIdAndTreeId(personId, treeId);

        if (personRequest.firstName() != null)
            person.setFirstName(personRequest.firstName());

        if (personRequest.lastName () != null)
            person.setLastName(personRequest.lastName());

        if (personRequest.gender() != null)
            person.setGender(personRequest.gender());

        Person personSaved = personRepository.save(person);
        return mapPersonDetailResponse(personSaved);
    }

    public void deletePerson(String treeId, String personId) {
        Person person = findPersonByIdAndTreeId(personId, treeId);
        person.setDeleted(true);

        personRepository.save(person);
    }

    private PersonDetailResponse mapPersonDetailResponse(Person person) {
        return PersonDetailResponse.of(
                person,
                mapActivePersons(person.getParents()),
                mapActivePersons(person.getChildren()),
                mapLifeEvents       (person)
        );
    }

    private List<PersonSummaryResponse> mapActivePersons(List<Person> persons) {
        return persons.stream()
                .filter(p -> !p.isDeleted())
                .map   (PersonSummaryResponse::of)
                .toList();
    }

    private List<LifeEventResponse> mapLifeEvents(Person person) {
        return person.getLifeEvents()
                .stream()
                .filter(ep -> !ep.getEvent().isDeleted())
                .map   (ep -> mapLifeEventResponse(person, ep))
                .toList();
    }

    private LifeEventResponse mapLifeEventResponse(Person person, EventParticipation participation) {
        List<EventParticipationResponse> participants = List.of(
                new EventParticipationResponse(
                        person.getId         (),
                        person.getFirstName  (),
                        person.getLastName   (),
                        participation.getRole()
                )
        );
        return LifeEventResponse.of(participation.getEvent(), participants);
    }

    private Person findPersonByIdAndTreeId(String personId, String treeId) {
        return personRepository.findByIdAndTreeId(personId, treeId)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
    }

    private FamilyTree findTreeByIdAndUserId(String treeId, String userId) {
        return familyTreeRepository.findByTreeIdAndUserId(treeId, userId)
                .orElseThrow(() -> new NotFoundException("Árvore genealógica não encontrada"));
    }
}
