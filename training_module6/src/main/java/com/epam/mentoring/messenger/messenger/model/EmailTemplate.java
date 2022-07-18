package com.epam.mentoring.messenger.messenger.model;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailTemplate {

    private Set<String> values = new HashSet<>();
    private String content;

    public EmailTemplate(String content) {
        this.content = content;
        formFromContent();
    }

    private void formFromContent() {
        Matcher m = Pattern.compile("#\\{(.*?)}").matcher(content);
        while (m.find()) {
            values.add(m.group(1));
        }
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
