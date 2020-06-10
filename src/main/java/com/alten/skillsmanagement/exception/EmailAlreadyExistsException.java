package com.alten.skillsmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExistsException extends RuntimeException {
    private String emailValue;

    public EmailAlreadyExistsException(String emailValue) {
        super(String.format("%s already taken.", emailValue));
        this.emailValue = emailValue;
    }

    public String getEmailValue() {
        return emailValue;
    }
}
