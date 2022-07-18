package com.epam.mentoring.messenger.messenger.service;

import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;

@Service
public class TemplateGeneratorImpl implements TemplateGenerator {

    @Value("${output.file}")
    private String outputFile;

    private final EmailTemplate emailTemplate;
    private final Map<String, String> inputs;

    public TemplateGeneratorImpl(Map<String, String> inputs, EmailTemplate emailTemplate) {
        this.inputs = inputs;
        this.emailTemplate = emailTemplate;
    }

    @Override
    public String generate() {
        if (!inputs.keySet().containsAll(emailTemplate.getValues())) {
            throw new InvalidDataProvidedException("Not all data for placeholders provided");
        }
        String result = emailTemplate.getContent();
        result = inputs.entrySet().stream()
                .map(e-> (Function<String,String>)s->s.replaceAll("#\\{"+e.getKey()+"}", e.getValue()))
                .reduce(Function.identity(), Function::andThen)
                .apply(result);

        try {
            if (outputFile != null) {
                ClassLoader classLoader = getClass().getClassLoader();
                File outFile = new File(classLoader.getResource(".").getFile() +File.separator + outputFile);
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                writer.write(result);
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
