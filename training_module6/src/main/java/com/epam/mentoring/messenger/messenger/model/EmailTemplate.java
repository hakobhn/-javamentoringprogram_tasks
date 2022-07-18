package com.epam.mentoring.messenger.messenger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailTemplate {

    private List<String> values = new ArrayList<>();
    private String content;

    public EmailTemplate(String content) {
        this.content = content;
        formFromContent();
    }

    private void formFromContent() {
        Matcher m = Pattern.compile("#\\{(.*?)}").matcher(content);
        while (m.find()) {
            System.out.println(m.group(1));
            values.add(m.group(1));
        }
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
