package br.com.tiagoschermack.family_tree.shared.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class DomainViolationException extends ApiException {

    public DomainViolationException(String message) {
        super(message, UNPROCESSABLE_ENTITY);
    }
}
