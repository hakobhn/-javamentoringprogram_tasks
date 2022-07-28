package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import com.epam.mentoring.messenger.messenger.service.TemplateGeneratorImpl;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(properties = {
        "command.line.runner.enabled=false",
        "application.runner.enabled=false" })
public class MassagingAppExpectedExceptionJUnit4Tests {

    @Autowired
    private EmailTemplate emailTemplate;
    @Mock
    private TemplateGenerator templateGenerator;

    @Mock
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testThrowExceptionWithRule_notAllDataProvided() throws IOException {

        Map<String, String> data = new HashMap<>();
        data.put("lastName", "Dummy");

        when(dataLoader.loadDataFromFile(inpFile)).thenReturn(data);
        when(templateGenerator.generate(data)).thenThrow(
                new InvalidDataProvidedException("Not all data for placeholders provided"));

        Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);

        thrown.expect(InvalidDataProvidedException.class);
        thrown.expectMessage("Not all data for placeholders provided");
        templateGenerator.generate(inputs);

        assertEquals(0, outFile.length());
    }

}
