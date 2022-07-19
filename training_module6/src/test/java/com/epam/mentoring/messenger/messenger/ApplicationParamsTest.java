package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.exception.InvalidAppParamsProvidedException;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import com.epam.mentoring.messenger.messenger.service.TemplateGeneratorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(args={"inputFile.txt", "outputFile.txt"})
public class ApplicationParamsTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    public void testRun() throws Exception {
        CommandLineRunner runner = ctx.getBean(CommandLineRunner.class);
        runner.run ( "--input.file=inputFile.txt", "--output.file=outputFile.txt");
    }

    @Test
    public void testFailRun() {
        CommandLineRunner runner = ctx.getBean(CommandLineRunner.class);
        assertThrows(InvalidAppParamsProvidedException.class, () -> {
            runner.run ( "inputFile.txt");
        });
    }

}
