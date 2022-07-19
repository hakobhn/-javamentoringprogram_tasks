package com.epam.mentoring.messenger.messenger.config;

import com.epam.mentoring.messenger.messenger.exception.InvalidAppParamsProvidedException;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

@ConditionalOnProperty(
        prefix = "application.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class CommandLineRunnerTaskExecutor implements CommandLineRunner {

    @Autowired
    private DataLoader dataLoader;

    @Autowired
    private TemplateGenerator templateGenerator;

//    @Value("${input.file:}")
//    private String inputFile;
//
//    @Value("${output.file:}")
//    private String outputFile;


    @Override
    public void run( String... args ) {

        if (args.length == 0) {
            Map<String, String> inputs = dataLoader.loadDataFromConsole();

            try {
                System.out.println(templateGenerator.generate(inputs));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (args.length == 2 && !args[0].isEmpty() && !args[1].isEmpty()) {
            String inputFile = args[0];
            String outputFile = args[1];
            Map<String, String> inputs = dataLoader.loadDataFromFile(new File(inputFile));

            try {
                templateGenerator.generateIntoFile(inputs, new File(outputFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new InvalidAppParamsProvidedException("Invalid args: " + Arrays.asList(args));
        }
    }
}
