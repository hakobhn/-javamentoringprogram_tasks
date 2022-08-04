package com.epam.mentoring.messenger;

import com.epam.mentoring.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.service.DataLoader;
import com.epam.mentoring.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "command.line.runner.enabled=false",
        "application.runner.enabled=false" })
@ExtendWith(TestDurationReportExtension.class)
class MessagingAppDurationReportTest {

    @MockBean
    private EmailTemplate emailTemplate;
    @Autowired
    private TemplateGenerator templateGenerator;

    @Autowired
    private DataLoader dataLoader;

    private File inpFile = new File("input.txt");
    private File outFile = new File("output.txt");

    @BeforeEach
    public void setUp() throws IOException {
        if (inpFile.exists()) {
            inpFile.delete();
        }
        if (outFile.exists()) {
            outFile.delete();
        }
        inpFile.createNewFile();
    }

    @Test
    void testWithFileInputs() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
        writer.write("firstName=Hakob");
        writer.newLine();
        writer.flush();
        writer.close();

        Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);

        assertTrue(inputs.size() > 0);

        when(emailTemplate.getContent()).thenReturn("Some value: #{firstName}");

        assertEquals("Some value: " + inputs.get("firstName"),
                templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));

        assertTrue(outFile.length() > 0);
    }

    @Test
    void testWithMockInputs() throws IOException {
        when(emailTemplate.getContent()).thenReturn("Some value: #{firstName} #{lastName}");
        assertEquals("Some value: Adam Smith", templateGenerator.generate(
                new HashMap<String, String>() {{
                    put("firstName", "Adam");
                    put("lastName", "Smith");
                }}
        ));
    }

}
