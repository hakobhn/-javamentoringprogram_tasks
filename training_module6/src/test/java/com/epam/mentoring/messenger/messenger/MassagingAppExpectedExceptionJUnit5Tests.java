package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "command.line.runner.enabled=false",
        "application.runner.enabled=false" })
public class MassagingAppExpectedExceptionJUnit5Tests {

    @Autowired
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
    void testThrowException_notAllDataProvided() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
        writer.write("lastName=Hakobyan");
        writer.newLine();
        writer.flush();
        writer.close();

        Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);
        assertTrue(inputs.size() > 0);

        Throwable exception = assertThrows(InvalidDataProvidedException.class,
                () -> templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));
        assertEquals("Not all data for placeholders provided", exception.getMessage());

        assertEquals(0, outFile.length());
    }

    @Test
    @EnabledIf(
            expression = "${tests.enabled}",
            loadContext = true)
    void givenEnabledIfExpression_WhenTrue_ThenTestExecuted() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
        writer.write("lastName=Hakobyan");
        writer.newLine();
        writer.flush();
        writer.close();

        Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);
        assertTrue(inputs.size() > 0);

        Throwable exception = assertThrows(InvalidDataProvidedException.class,
                () -> templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));
        assertEquals("Not all data for placeholders provided", exception.getMessage());

        assertEquals(0, outFile.length());
    }

    @Test
    @EnabledIf(
            expression = "#{systemProperties['os.name'].toLowerCase().contains('mac')}",
            reason = "Enabled on Mac OS"
    )
    void givenEnabledIfExpression_WhenMacOS_ThenTestExecuted() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
        writer.write("lastName=Hakobyan");
        writer.newLine();
        writer.flush();
        writer.close();

        Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);
        assertTrue(inputs.size() > 0);

        Throwable exception = assertThrows(InvalidDataProvidedException.class,
                () -> templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));
        assertEquals("Not all data for placeholders provided", exception.getMessage());

        assertEquals(0, outFile.length());
    }

    @Test
    @EnabledIf(
            expression = "#{systemProperties['os.name'].toLowerCase().contains('win')}",
            reason = "Enabled on Windows OS"
    )
    void givenEnabledIfExpression_WhenWindowsOS_ThenTestExecuted() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
        writer.write("lastName=Hakobyan");
        writer.newLine();
        writer.flush();
        writer.close();

        Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);
        assertTrue(inputs.size() > 0);

        Throwable exception = assertThrows(InvalidDataProvidedException.class,
                () -> templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));
        assertEquals("Not all data for placeholders provided", exception.getMessage());

        assertEquals(0, outFile.length());
    }

    @Test
    @EnabledIf(
            expression = "#{systemProperties['os.name'].toLowerCase().contains('linux')}",
            reason = "Enabled on Linux OS"
    )
    void givenEnabledIfExpression_WhenLinuxOS_ThenTestExecuted() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
        writer.write("lastName=Hakobyan");
        writer.newLine();
        writer.flush();
        writer.close();

        Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);
        assertTrue(inputs.size() > 0);

        Throwable exception = assertThrows(InvalidDataProvidedException.class,
                () -> templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));
        assertEquals("Not all data for placeholders provided", exception.getMessage());

        assertEquals(0, outFile.length());
    }

}
