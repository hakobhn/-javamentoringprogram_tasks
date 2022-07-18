package com.epam.mentoring.messenger.messenger.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class TemplateGeneratorImpl implements TemplateGenerator {

    private String emailTemplate;

    public TemplateGeneratorImpl(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Override
    public String generate(Map<String, String> data) {
        return null;
    }
}
