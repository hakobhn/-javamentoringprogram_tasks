package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "command.line.runner.enabled=false",
        "application.runner.enabled=false" })
public class MessagingTempDirectoryTests {

    @MockBean
    private EmailTemplate emailTemplate;
    @Autowired
    private TemplateGenerator templateGenerator;

    @Autowired
    private DataLoader dataLoader;

    File inpFile = null;
    File outFile = null;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) throws IOException {

        inpFile = tempDir.resolve("inputs.txt").toFile();
        outFile = tempDir.resolve("outputs.txt").toFile();

        if (inpFile.exists()) {
            inpFile.delete();
        }
        if (outFile.exists()) {
            outFile.delete();
        }
        inpFile.createNewFile();
        outFile.createNewFile();
    }

    @Test
    void givenTestMethodWithTempDirectory_whenWriteToFile_thenGenerationIsCorrect(@TempDir Path tempDir)
            throws IOException {

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

}
