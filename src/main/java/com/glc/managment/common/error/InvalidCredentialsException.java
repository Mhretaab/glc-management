package com.glc.managment.common.error;

import com.glc.managment.common.util.MessageSourceHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends AuthenticationException {

    public InvalidCredentialsException(String credential) {
        super(MessageSourceHolder.messageSource.getMessage("security.invalid.credentials", new String[]{credential},
                String.format("Invalid Credentials for member: %s", credential), null));
    }

}