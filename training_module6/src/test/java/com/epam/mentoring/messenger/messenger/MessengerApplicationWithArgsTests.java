package com.epam.mentoring.messenger.messenger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(args={"--input.file=inputFile.txt", "--output.file=outputFile.txt"})
class MessengerApplicationWithArgsTests {

	@Value("${input.file}")
	private String inputFile;

	@Value("${output.file}")
	private String outputFile;

	@Test
	void contextLoads() {
		assertEquals("inputFile.txt", inputFile);
		assertEquals("outputFile.txt", outputFile);
	}

}
