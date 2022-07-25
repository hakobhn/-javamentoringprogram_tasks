package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.model.EmailTemplate;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
				"command.line.runner.enabled=false",
				"application.runner.enabled=false" })
class MessengerApplicationDynamicTests {

	@MockBean
	private EmailTemplate emailTemplate;
	@Autowired
	private TemplateGenerator templateGenerator;

	@Autowired
	private DataLoader dataLoader;

	@BeforeEach
	public void setUp() throws IOException {
		System.out.println("Initiating tests...");
	}

	@TestFactory
	Collection<DynamicTest> dynamicTestsFromCollection() {

		return Arrays.asList(
				dynamicTest("1st dynamic test", () -> {
					when(emailTemplate.getContent()).thenReturn("Some value: #{firstName} #{lastName}");
					assertEquals("Some value: Adam Smith", templateGenerator.generate(
							new HashMap<String, String>() {{
								put("firstName", "Adam");
								put("lastName", "Smith");
							}}
					));
				})
		);
	}
}
