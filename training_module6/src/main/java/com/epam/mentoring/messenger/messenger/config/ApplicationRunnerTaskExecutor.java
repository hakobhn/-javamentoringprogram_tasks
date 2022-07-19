package com.epam.mentoring.messenger.messenger.config;

import com.epam.mentoring.messenger.messenger.exception.InvalidDataPairException;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@ConditionalOnProperty(
        prefix = "application.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class ApplicationRunnerTaskExecutor implements ApplicationRunner {

    @Autowired
    private Map<String, String> inputs;

    @Value("${input.file:}")
    private String inputFile;

    @Value("${output.file:}")
    private String outputFile;

    @Autowired
    private TemplateGenerator templateGenerator;


    @Override
    public void run( ApplicationArguments args ) {

        if (args.getSourceArgs().length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("You need to provide values for this params: \nfirstName, \nlastName," +
                    "\nlabel, \nurl");
            //  prompt for the user's values
            System.out.println("Enter values in format key=val or -1 for finish");

            // get their input as a String
            String line = scanner.nextLine();
            inputs = new HashMap<>();
            while (!line.equals("-1")) {
                System.out.println("Inserted key value : " + line);
                String[] parts = line.split("=");
                if (parts.length < 2) {
                    throw new InvalidDataPairException("Line: " + line + " is invalid");
                }
                inputs.put(parts[0].trim(), parts[1].trim());
                line = scanner.nextLine();
            }
            scanner.close();

            try {
                System.out.println(templateGenerator.generate(inputs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            if (inputFile.isEmpty()) {
                return;
            }

            ClassLoader classLoader = getClass().getClassLoader();
            File inpFile = new File(classLoader.getResource(".").getFile() +File.separator + inputFile);

            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(inpFile));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println("Inserted key value : " + line);
                    String[] parts = line.split("=");
                    if (parts.length < 2) {
                        throw new InvalidDataPairException("Line: " + line + " is invalid");
                    }
                    inputs.put(parts[0].trim(), parts[1].trim());
                    line = reader.readLine();
                }
                reader.close();

                try {
                    templateGenerator.generate(inputs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
