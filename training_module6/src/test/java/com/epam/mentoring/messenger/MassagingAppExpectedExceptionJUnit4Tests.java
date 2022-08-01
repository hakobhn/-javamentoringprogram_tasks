package com.epam.mentoring.messenger;

import com.epam.mentoring.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.service.DataLoader;
import com.epam.mentoring.messenger.service.TemplateGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

    @Before
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
