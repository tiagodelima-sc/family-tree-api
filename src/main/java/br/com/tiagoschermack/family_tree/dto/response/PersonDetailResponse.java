package br.com.tiagoschermack.family_tree.dto.response;

import br.com.tiagoschermack.family_tree.domain.entity.Person;
import br.com.tiagoschermack.family_tree.domain.enums.Gender;

import java.util.List;

public record PersonDetailResponse(
        String id,
        String firstName,
        String lastName,
        Gender gender,
        List<PersonSummaryResponse> parents,
        List<PersonSummaryResponse> children,
        List<LifeEventResponse> events
) {
    public static PersonDetailResponse of(Person person, List<PersonSummaryResponse> parents, List<PersonSummaryResponse> children, List<LifeEventResponse> events) {
        return new PersonDetailResponse(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                parents,
                children,
                events
        );
    }
}
