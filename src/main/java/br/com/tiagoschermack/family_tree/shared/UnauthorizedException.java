package br.com.tiagoschermack.family_tree.shared;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message, FORBIDDEN);
    }
}
