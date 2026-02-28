package br.com.tiagoschermack.family_tree.dto.response;

import br.com.tiagoschermack.family_tree.domain.entity.Person;
import br.com.tiagoschermack.family_tree.domain.enums.Gender;

public record PersonSummaryResponse(
        String id,
        String firstName,
        String lastName,
        Gender gender
) {
    public static PersonSummaryResponse of(Person person) {
        return new PersonSummaryResponse(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender()
        );
    }
}
