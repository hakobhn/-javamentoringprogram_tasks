package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
		"command.line.runner.enabled=false",
		"application.runner.enabled=false" })
class MessengerApplicationWithArgsTests {

	@Autowired
	private TemplateGenerator templateGenerator;

	@Autowired
	private DataLoader dataLoader;

	private File inpFile = new File("input.txt");
	private File outFile = new File("output.txt");

	@BeforeEach
	public void setUp() throws IOException {
		if (inpFile.exists()) {
			inpFile.delete();
		}
		if (outFile.exists()) {
			outFile.delete();
		}
		inpFile.createNewFile();
	}

	@AfterEach
	public void tearDown() throws IOException {
		if (inpFile.exists()) {
			inpFile.delete();
		}
		if (outFile.exists()) {
			outFile.delete();
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
				templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));

		assertTrue(outFile.length() > 0);
	}

	@Test
	void testWithValuesContainingSpecialParamsFiles() throws IOException {

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
				templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));

		assertTrue(outFile.length() > 0);
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
			templateGenerator.generateIntoFile(inputs, outFile);
		});

	}

}
