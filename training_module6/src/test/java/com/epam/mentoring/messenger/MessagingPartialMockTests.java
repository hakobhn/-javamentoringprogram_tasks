package com.epam.mentoring.messenger;

import com.epam.mentoring.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "command.line.runner.enabled=false",
        "application.runner.enabled=false" })
class MessagingPartialMockTests {

    @SpyBean
    private TemplateGenerator templateGenerator;

    private File outFile = new File("output.txt");

    @BeforeEach
    public void setUp() throws IOException {
        if (outFile.exists()) {
            outFile.delete();
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (outFile.exists()) {
            outFile.delete();
        }
    }

    @Test
    void givenMessagingGeneratorMethodWithPartialMockedGenerator_thenGenerationIsCorrect() {

        Map<String, String> data = new HashMap<>();
        data.put("firstName", "Dummy");

        when(templateGenerator.generate(data)).thenReturn("Some value: Hakob");  // Mock implementation

        // Real implementation
        assertEquals("Some value: Hakob", templateGenerator.generateIntoFile(data, outFile));
    }

    @Test
    void givenMessagingGeneratorMethodWithFullMockedGenerator_thenGenerationIsInCorrect() {

        Map<String, String> data = new HashMap<>();
        data.put("firstName", "Dummy");

        TemplateGenerator templateGenerator = mock(TemplateGenerator.class);

        when(templateGenerator.generate(data)).thenReturn("Some value: Hakob");  // Mock implementation

        // Real implementation
        assertNotEquals("Some value: Hakob", templateGenerator.generateIntoFile(data, outFile));
    }

}
