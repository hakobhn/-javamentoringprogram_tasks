package com.epam.mentoring.messenger.messenger.config;

import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public EmailTemplate emailTemplate() throws IOException {
        File file = ResourceUtils.getFile("classpath:email_template.html");
        String content = new String(Files.readAllBytes(file.toPath()));
        return new EmailTemplate(content);
    }

    @Bean
    public Map<String, String> inputs() {
        return new HashMap<>();
    }
}
