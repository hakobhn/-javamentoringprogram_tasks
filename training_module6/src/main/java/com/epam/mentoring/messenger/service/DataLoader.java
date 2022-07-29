package com.epam.mentoring.messenger.service;

import com.epam.mentoring.messenger.exception.InvalidDataPairException;
import com.epam.mentoring.messenger.model.EmailTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class DataLoader {

    Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    EmailTemplate emailTemplate;

    private Map<String, String> inputs;

    public Map<String, String> loadDataFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You need to provide values for this params: \n"+
                emailTemplate.getPlaceholders().stream().collect(Collectors.joining("\n")));
        //  prompt for the user's values
        System.out.println("Enter values in format key=val or -1 for finish");

        inputs = new HashMap<>();
        // get their input as a String
        String line = scanner.nextLine();
        while (!line.equals("-1")) {
            processLine(line);
            line = scanner.nextLine();
        }
        scanner.close();
        return inputs;
    }

    public Map<String, String> loadDataFromFile(File inputFile) {
        if (!inputFile.exists()) {
            return new HashMap<>();
        }
        log.debug("Processing input file for retrieving data...");

        inputs = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = reader.readLine();
            while (line != null) {
                processLine(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputs;
    }

    private void processLine(String line) {
        log.debug("Inserted key value : {}", line);
        String[] parts = line.split("=");
        if (parts.length != 2) {
            log.error("Invalid input. \"{}\" is not in format placeholder=>value!", line);
            throw new InvalidDataPairException("Line: " + line + " is invalid");
        }
        if (parts[0].trim().equals("date")) {
            try {
                LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new InvalidDataPairException("Filed with label date, not contains valid date.", e);
            }
        }
        inputs.put(parts[0].trim(), parts[1].trim());
    }

}
