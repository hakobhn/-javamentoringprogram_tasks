package com.epam.mentoring.messenger.service;

import java.io.File;
import java.util.Map;

public interface TemplateGenerator {

    String generate(Map<String, String> inputs);

    String generateIntoFile(Map<String, String> inputs, File outFile);

}
