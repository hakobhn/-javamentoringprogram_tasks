package com.epam.mentoring.messenger.service;

import com.epam.mentoring.messenger.exception.InvalidDataPairException;
import com.epam.mentoring.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.model.EmailTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TemplateGeneratorImpl implements TemplateGenerator {

    private final EmailTemplate emailTemplate;

    public TemplateGeneratorImpl(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Override
    public String generate(Map<String, String> inputs) {
        if (!inputs.keySet().containsAll(emailTemplate.getPlaceholders())) {
            throw new InvalidDataProvidedException("Not all data for placeholders provided");
        }
        String result = emailTemplate.getContent();

        result = inputs.entrySet().stream()
                .map(e -> {
                    if (e.getKey().equals("date")) {
                        LocalDate.parse(e.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return e;
                })
                .map(e -> (Function<String, String>) s -> s.replaceAll("#\\{" + e.getKey() + "}", e.getValue()))
                .reduce(Function.identity(), Function::andThen)
                .apply(result);

        return result;
    }

    @Override
    public String generateIntoFile(Map<String, String> inputs, File outFile) {
        String result = generate(inputs);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            if (outFile.exists()) {
                outFile.delete();
            }
            writer.write(result);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
