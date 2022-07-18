package com.epam.mentoring.messenger.messenger.service;

import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;

import javax.naming.StringRefAddr;
import java.util.Map;
import java.util.function.Function;

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
        String result = emailTemplate.getContent();
        result = data.entrySet().stream()
                .map(e-> (Function<String,String>)s->s.replaceAll("#\\{"+e.getKey()+"}", e.getValue()))
                .reduce(Function.identity(), Function::andThen)
                .apply(result);

        return result;
    }
}
