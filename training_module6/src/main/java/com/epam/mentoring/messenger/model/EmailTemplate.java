package com.epam.mentoring.messenger.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailTemplate {

    Logger log = LoggerFactory.getLogger(EmailTemplate.class);

    private Set<String> placeholders = new TreeSet<>();
    private String content;

    public EmailTemplate(String content) {
        this.content = content;
        formFromContent();
    }

    private void formFromContent() {
        Matcher m = Pattern.compile("#\\{(.*?)}").matcher(content);
        while (m.find()) {
            String placeholder = m.group(1);
            log.debug("Placeholder found: {}", placeholder);
            placeholders.add(placeholder);
        }
    }

    public Set<String> getPlaceholders() {
        return placeholders;
    }


    public String getContent() {
        return content;
    }

}
