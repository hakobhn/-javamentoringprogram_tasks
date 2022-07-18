package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import com.epam.mentoring.messenger.messenger.service.TemplateGeneratorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TemplateGeneratorTest {

    @MockBean
    private EmailTemplate emailTemplate;

    @MockBean
    private TemplateGenerator templateGenerator;

    @Test
    public void generateSimpleTemplate() {
        Map<String, String> data = new HashMap<>();
        data.put("firstName", "Hakob");
        data.put("lastName", "Hakobyan");
        data.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        data.put("url", "https://epam.com");
        data.put("label", "Press");

        emailTemplate = new EmailTemplate("Some value: #{firstName}");

        assertEquals("Some value: Hakob", templateGenerator.generate(data));
    }

}
