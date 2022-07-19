package com.epam.mentoring.messenger.messenger.service;

import java.util.Map;

public interface TemplateGenerator {

    String generate(Map<String, String> inputs);

    String generate();

}
