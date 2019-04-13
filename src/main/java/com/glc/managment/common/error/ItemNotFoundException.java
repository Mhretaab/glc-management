package com.glc.managment.common.error;

import com.glc.managment.common.util.MessageSourceHolder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends AbstractServiceException {

    public ItemNotFoundException(String itemId) {
        super(ErrorCodes.ITEM_NOT_FOUND, MessageSourceHolder.messageSource.getMessage("error.item.not.found.by.id",
                new String[]{itemId}, String.format("Item not found with id: %s", itemId), LocaleContextHolder.getLocale()));

    }

}