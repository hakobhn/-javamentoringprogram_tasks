package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.exception.InvalidAppParamsProvidedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(args={"inputFile.txt", "outputFile.txt"})
class ApplicationParamsTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    void testRun() throws Exception {
        CommandLineRunner runner = ctx.getBean(CommandLineRunner.class);
        runner.run ( "inputFile.txt", "outputFile.txt");
        assertNotNull(ctx.getId());
    }

    @Test
    void testFailRun() throws Exception {
        CommandLineRunner runner = ctx.getBean(CommandLineRunner.class);
        assertThrows(InvalidAppParamsProvidedException.class, () -> {
            runner.run ( "inputFile.txt");
        });
    }

}
