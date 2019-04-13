package com.glc.managment.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class MessageSourceHolder {

    public static MessageSource messageSource;

    public static synchronized MessageSource load() {

        if(null != messageSource)
            return messageSource;

        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setCacheSeconds(0);

        MessageSourceHolder.messageSource = messageSource;

        return messageSource;
    }

}