package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataPairException;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
				"command.line.runner.enabled=false",
				"application.runner.enabled=false" })
class MessengerApplicationParametrizedTests {

	@MockBean
	private EmailTemplate emailTemplate;
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

	@ParameterizedTest
	@DisplayName("checks if the given firstnames would be populated with generator")
	@ValueSource(strings ={
			"firstName=Adam", "firstName=John"
	})
	void testGeneratingFilesWithFirstnameParametrizedInputs(String firstname) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
		writer.write(firstname);
		writer.newLine();
		writer.flush();
		writer.close();

		Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);

		assertTrue(inputs.size() > 0);

		when(emailTemplate.getContent()).thenReturn("Some value: #{firstName}");

		assertEquals("Some value: " + inputs.get("firstName"),
				templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));

		assertTrue(outFile.length() > 0);
	}

	@ParameterizedTest
	@DisplayName("checks if the given params would be populated with generator")
	@CsvSource({
			"firstName=Adam, lastName=Smith, label=Click, url=https://epam.com",
			"firstName=John, lastName=Doe, label=Click Me, url=https://google.com"
	})
	void testGeneratingFilesWithParametrizedInputs(String firstname, String lastName, String label, String url) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
		writer.write(firstname);
		writer.newLine();
		writer.write(lastName);
		writer.newLine();
		writer.write(label);
		writer.newLine();
		writer.write(url);
		writer.flush();
		writer.close();

		Map<String, String> inputs = dataLoader.loadDataFromFile(inpFile);

		assertTrue(inputs.size() > 0);

		when(emailTemplate.getContent()).thenReturn("Some value firstName: #{firstName}, lastName: #{lastName}, " +
				"label: #{label}, url: #{url}");

		assertEquals("Some value firstName: " + inputs.get("firstName") + ", " +
						"lastName: " + inputs.get("lastName")+ ", " +
						"label: " + inputs.get("label")+ ", " +
						"url: " + inputs.get("url"),
				templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));

		assertTrue(outFile.length() > 0);
	}

	@ParameterizedTest
	@DisplayName("checks if the given params would be populated with generator")
	@CsvSource({
			"firstNameAdam, lastName=Smith, label=Click, url=https://epam.com",
			"firstName=John, a, label=Click Me, url=https://google.com",
			"firstName=John, lastName=Doe, label.Click Me, url=https://google.com"
	})
	void testGenerationFailWithInvalidPairsParametrizedInputs(String firstname, String lastName, String label, String url) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
		writer.write(firstname);
		writer.newLine();
		writer.write(lastName);
		writer.newLine();
		writer.write(label);
		writer.newLine();
		writer.write(url);
		writer.flush();
		writer.close();

		assertThrows(InvalidDataPairException.class, () -> {
			dataLoader.loadDataFromFile(inpFile);
		});
	}
}
