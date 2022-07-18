package com.epam.mentoring.messenger.messenger.service;

import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;

import java.util.Map;

public class TemplateGeneratorImpl implements TemplateGenerator {

    private final EmailTemplate emailTemplate;

    public TemplateGeneratorImpl(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Override
    public String generate(Map<String, String> data) {
        if (!data.keySet().containsAll(emailTemplate.getValues())) {
            throw new InvalidDataProvidedException("Not all data for placeholders provided");
        }
        return "Some value: Hakob";
    }
}
