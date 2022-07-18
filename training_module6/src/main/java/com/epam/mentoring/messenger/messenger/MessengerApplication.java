package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.exception.InvalidDataPairException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class MessengerApplication implements ApplicationRunner {

	@Autowired
	private Map<String, String> inputs;

	@Value("${input.file}")
	private String inputFile;

	@Value("${output.file}")
	private String outputFile;

	public static void main(String[] args) {

		SpringApplication.run(MessengerApplication.class, args);
	}

	@Override
	public void run( ApplicationArguments args ) {

		if (args.getSourceArgs().length == 0) {
			Scanner scanner = new Scanner(System.in);

			//  prompt for the user's name
			System.out.println("Enter values as key=val or -1 for end ");

			// get their input as a String
			String line = scanner.next();
			inputs = new HashMap<>();
			while (!line.equals("-1")) {
				System.out.println("line : " + line);
				String[] parts = line.split("=");
				if (parts.length < 2) {
					throw new InvalidDataPairException("Line: " + line + " is invalid");
				}
				inputs.put(parts[0].trim(), parts[1].trim());
				line = scanner.next();
			}
		} else {

			ClassLoader classLoader = getClass().getClassLoader();
			File inpFile = new File(classLoader.getResource(".").getFile() +File.separator + inputFile);

			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(inpFile));
				String line = reader.readLine();
				while (line != null) {
					System.out.println("line : " + line);
					String[] parts = line.split("=");
					if (parts.length < 2) {
						throw new InvalidDataPairException("Line: " + line + " is invalid");
					}
					inputs.put(parts[0].trim(), parts[1].trim());
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
