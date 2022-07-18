package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import com.epam.mentoring.messenger.messenger.service.TemplateGeneratorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(args={"--input.file=inputFile.txt", "--output.file=outputFile.txt"})
class MessengerApplicationWithArgsTests {

	@Value("${input.file}")
	private String inputFile;

	@Value("${output.file}")
	private String outputFile;

	@Autowired
	TemplateGenerator templateGenerator;

	@Test
	void testWithFiles() throws IOException {
		File inpFile = ResourceUtils.getFile("classpath:"+inputFile);
		File outFile = ResourceUtils.getFile("classpath:"+outputFile);

		BufferedWriter writer = new BufferedWriter(new FileWriter(inpFile));
		writer.write("firstName=Hakob");
		writer.write("lastName=Hakobyan");
		writer.flush();
		writer.close();

		assertEquals("Some value: Hakob", templateGenerator.generate());

		assertEquals("inputFile.txt", inputFile);
		assertEquals("outputFile.txt", outputFile);

		assertTrue(outFile.exists());
	}

}
