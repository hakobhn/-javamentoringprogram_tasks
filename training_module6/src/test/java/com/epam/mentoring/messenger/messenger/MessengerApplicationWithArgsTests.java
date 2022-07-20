package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(args={"--input.file=inputFile.txt", "--output.file=outputFile.txt"})
class MessengerApplicationWithArgsTests {

	@Value("${input.file}")
	private String inputFile;

	@Value("${output.file}")
	private String outputFile;

	@Autowired
	private TemplateGenerator templateGenerator;

	@Autowired
	private DataLoader dataLoader;

	File inpFile = null;

	@BeforeEach
	public void setUp() throws IOException {
		inpFile = new File(inputFile);
		if (inpFile.exists()) {
			inpFile.delete();
		}
		inpFile.createNewFile();
	}

	@AfterEach
	public void tearDown() throws IOException {
		inpFile = new File(inputFile);
		if (inpFile.exists()) {
			inpFile.delete();
		}
	}

	@Test
	void testWithFiles() throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
		writer.write("firstName=Hakob");
		writer.newLine();
		writer.write("lastName=Hakobyan");
		writer.newLine();
		writer.write("label=Click");
		writer.newLine();
		writer.write("url=https://epam.com");
		writer.flush();
		writer.close();

		assertEquals("Some value: Hakob",
				templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), new File(outputFile)));

		assertEquals("inputFile.txt", inputFile);
		assertEquals("outputFile.txt", outputFile);

		assertTrue(new File(outputFile).exists());
	}

	@Test
	void testWithValuesContainingSpecialParmsFiles() throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
		writer.write("firstName=#{Hakob}");
		writer.newLine();
		writer.write("lastName=Hakobyan");
		writer.newLine();
		writer.write("label=Click");
		writer.newLine();
		writer.write("url=https://epam.com");
		writer.flush();
		writer.close();

		assertEquals("Some value: #{Hakob}",
				templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), new File(outputFile)));

		assertEquals("inputFile.txt", inputFile);
		assertEquals("outputFile.txt", outputFile);

		assertTrue(new File(outputFile).exists());
	}

	@Test
	void testFailWithDataWithFiles() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
		writer.write("lastName=Hakobyan");
		writer.newLine();
		writer.write("label=Click");
		writer.flush();
		writer.close();

		Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);

		assertThrows(InvalidDataProvidedException.class, () -> {
			templateGenerator.generateIntoFile(inputs, new File(outputFile));
		});

	}

}
