package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
		"command.line.runner.enabled=false",
		"application.runner.enabled=false" })
@RunWith(PowerMockRunner.class)
@PrepareForTest({FileReader.class, BufferedReader.class})
class MessengerApplicationWithMockFilesTests {

	@Autowired
	private TemplateGenerator templateGenerator;

	@MockBean
	private DataLoader dataLoader;

	private File inpFile;
	private File outFile = new File("output.txt");

	@BeforeEach
	public void setUp() throws Exception {

		inpFile = Mockito.mock(File.class);

		FileReader fileReader = Mockito.mock(FileReader.class);
		BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);

		new BufferedReader(fileReader);

		try {
			PowerMockito.whenNew(FileReader.class).withArguments(inpFile).thenReturn(fileReader);
			PowerMockito.whenNew(BufferedReader.class).withAnyArguments().thenReturn(bufferedReader);
			PowerMockito.when(bufferedReader.readLine())
					.thenReturn("firstName=Hakob").thenReturn(null);
			when(dataLoader.loadDataFromFile(inpFile)).thenReturn(new HashMap<String, String>() {{
				put("firstName", "Hakob");
			}});
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testWithFiles() {
		assertEquals("Some value: Hakob",
				templateGenerator.generateIntoFile(dataLoader.loadDataFromFile(inpFile), outFile));

		assertTrue(outFile.length() > 0);
	}

}
