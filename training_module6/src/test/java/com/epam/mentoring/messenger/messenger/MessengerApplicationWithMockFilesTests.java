package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataPairException;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
		"command.line.runner.enabled=false",
		"application.runner.enabled=false" })
class MessengerApplicationWithMockFilesTests {

	@Autowired
	private TemplateGenerator templateGenerator;

	@Autowired
	private DataLoader dataLoader;

	private File inpFile;
	private File outFile = new File("output.txt");

	@BeforeEach
	public void setUp() throws Exception {

		inpFile = PowerMockito.mock(File.class);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(inpFile);
		PowerMockito.when(inpFile.exists()).thenReturn(true);

		FileReader fileReader = Mockito.mock(FileReader.class);

		BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
		PowerMockito.when(inpFile.exists()).thenReturn(true);
		PowerMockito.whenNew(FileReader.class).withArguments(inpFile).thenReturn(fileReader);
		try {
//			PowerMockito.whenNew(FileReader.class).withArguments(inpFile).thenReturn(fileReader);
			PowerMockito.whenNew(BufferedReader.class).withArguments(fileReader)
					.thenThrow(new RuntimeException("AA"));
//					.thenReturn(bufferedReader);
//			PowerMockito.when(bufferedReader.readLine()).thenReturn("firstName=Hakob");
//			Mockito.when(bufferedReader.readLine()).thenReturn("line1", "line2", "line3");

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		BufferedReader reader1 = new BufferedReader(fileReader);
//		String line = reader1.readLine();
//		while (line != null) {
//			System.out.println("Inserted key value : " + line);
//			String[] parts = line.split("=");
//			if (parts.length < 2) {
//				throw new InvalidDataPairException("Line: " + line + " is invalid");
//			}
//		}
		System.out.println("Finish...");
	}

	@Test
	void testWithFiles() {
//		assertEquals("Some value: Hakob",
//				templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));
//
//		assertTrue(outFile.length() > 0);
	}

}
