package com.tms.easyrento.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/14/23 10:31 PM
 */
@Service
public class CustomMessageSource {

    private MessageSource messageSource;

    private static final String basename = "messages";

    CustomMessageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();

        bundleMessageSource.setBasename("messages");
        bundleMessageSource.setDefaultEncoding("UTF-8");
        bundleMessageSource.setCacheMillis(5);
        this.messageSource = bundleMessageSource;
    }

    public String get(String code) {
        return messageSource.getMessage(code, null, getCurrentLocale());
    }

    public String get(String code, Object... objects) {
        return messageSource.getMessage(code, objects, getCurrentLocale());
    }

    public Locale getCurrentLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.getDisplayLanguage().equalsIgnoreCase("np")) {
            locale = new Locale("np", "Nepal");
        }
        return locale;
    }
}
