package com.glc.managment.common.error;

import com.glc.managment.common.util.MessageSourceHolder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mberhe on 4/13/19.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MemberNotFoundException extends AuthenticationException{
    public MemberNotFoundException(String principal) {
        super(MessageSourceHolder.messageSource.getMessage("error.member.not.found.by.principal", new String[]{principal},
                String.format("Member not found with principal: %s", principal), LocaleContextHolder.getLocale()));
    }
}
