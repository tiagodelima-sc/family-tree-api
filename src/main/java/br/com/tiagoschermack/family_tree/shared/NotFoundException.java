package br.com.tiagoschermack.family_tree.shared;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends ApiException{

    public NotFoundException(String message) {
        super(message, NOT_FOUND);
    }
}
