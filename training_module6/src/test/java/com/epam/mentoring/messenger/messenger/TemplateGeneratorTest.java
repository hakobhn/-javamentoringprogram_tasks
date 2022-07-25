package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import com.epam.mentoring.messenger.messenger.service.TemplateGeneratorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "command.line.runner.enabled=false",
        "application.runner.enabled=false" })
public class TemplateGeneratorTest {

    @Test
    public void testSimpleTemplateGeneration() {
        Map<String, String> data = new HashMap<>();

        EmailTemplate emailTemplate = mock(EmailTemplate.class);
        when(emailTemplate.getContent()).thenReturn("Some value: Hakob");

        TemplateGenerator templateGenerator = new TemplateGeneratorImpl(emailTemplate);

        assertEquals("Some value: Hakob", templateGenerator.generate(data));
    }

    @Test
    public void generateSimpleTemplate() {
        Map<String, String> data = new HashMap<>();
        data.put("firstName", "Hakob");
        data.put("lastName", "Hakobyan");
        data.put("label", "Press");
        data.put("url", "https://epam.com");

        EmailTemplate emailTemplate = new EmailTemplate("Some value: #{firstName}");
        TemplateGenerator templateGenerator = new TemplateGeneratorImpl(emailTemplate);

        assertEquals("Some value: Hakob", templateGenerator.generate(data));
    }

    @Test
    public void processInvalidSimpleTemplate() {
        Map<String, String> data = new HashMap<>();
        data.put("lastName", "Hakobyan");
        data.put("label", "Press");
        data.put("url", "https://epam.com");

        EmailTemplate emailTemplate = new EmailTemplate("Some value: #{firstName}");
        TemplateGenerator templateGenerator = new TemplateGeneratorImpl(emailTemplate);

        assertThrows(InvalidDataProvidedException.class, () -> {
            templateGenerator.generate(data);
        });
    }

}
