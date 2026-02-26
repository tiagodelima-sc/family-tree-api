package br.com.tiagoschermack.family_tree.dto.request;

import br.com.tiagoschermack.family_tree.domain.enums.Gender;

public record UpdatePersonRequest(
        String firstName,
        String lastName,
        Gender gender
) {}

