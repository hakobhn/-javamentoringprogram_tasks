package com.epam.mentoring.messenger.messenger.config;

import com.epam.mentoring.messenger.messenger.exception.InvalidDataPairException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class DataLoader {

    private Map<String, String> inputs;

    public Map<String, String> loadDataFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You need to provide values for this params: \nfirstName, \nlastName," +
                "\nlabel, \nurl");
        //  prompt for the user's values
        System.out.println("Enter values in format key=val or -1 for finish");

        inputs = new HashMap<>();
        // get their input as a String
        String line = scanner.nextLine();
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
        return inputs;
    }

    public Map<String, String> loadDataFromFile(File inputFile) {
        if (!inputFile.exists()) {
            return new HashMap<>();
        }
        System.out.println("Processing input file for retrieving data...");

        inputs = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputs;
    }

}
