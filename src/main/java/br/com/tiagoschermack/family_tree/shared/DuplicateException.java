package br.com.tiagoschermack.family_tree.shared;

import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicateException extends ApiException {

    public DuplicateException(String message) {
        super(message, CONFLICT);
    }
}
