package com.epam.mentoring.messenger.messenger.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TemplateGenerator {

    String generate(Map<String, String> data);

}
