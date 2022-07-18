package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.exception.InvalidDataPairException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class MessengerApplication implements ApplicationRunner {

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
			Map<String, String> inputs = new HashMap<>();
			while (!line.equals("-1")) {
				System.out.println("line : " + line);
				String[] parts = line.split("=");
				if (parts.length < 2) {
					throw new InvalidDataPairException("Line: " + line + " is invalid");
				}
				inputs.put(parts[0].trim(), parts[1].trim());
				line = scanner.next();
			}

			System.out.println(inputs);
		} else {

			System.out.println(args.getSourceArgs());

		}
	}

}
