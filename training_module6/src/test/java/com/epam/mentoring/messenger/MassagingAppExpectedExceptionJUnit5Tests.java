package com.epam.mentoring.messenger;

import com.epam.mentoring.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.service.DataLoader;
import com.epam.mentoring.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
class MassagingAppExpectedExceptionJUnit5Tests {

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
    @DisplayName("For mac os")
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

    @ParameterizedTest
    @DisplayName("checks if the given params would be populated with generator")
    @CsvSource({
            "firstName=Adam, lastName=Smith, label=Click, url=https://epam.com, date=2022/07/19",
            "firstName=John, lastName=Doe=dummy, label=Click Me, url=https://google.com, date=2022-03-03",
            "firstName=John, lastName=Doe, label=Click Me, url=https://google.com, date=abc"
    })
    void testGeneratingFailWithParametrizedInputs(String firstname, String lastName, String label, String url, String date) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
        writer.write(firstname);
        writer.newLine();
        writer.write(lastName);
        writer.newLine();
        writer.write(label);
        writer.newLine();
        writer.write(url);
        writer.newLine();
        writer.write(date);
        writer.flush();
        writer.close();

        assertThrows(Exception.class, () -> dataLoader.loadDataFromFile(inpFile));
    }

}
