package com.epam.mentoring.messenger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {
				"command.line.runner.enabled=false",
				"application.runner.enabled=false" })
class MessengerApplicationTests {

	@Test
	void contextLoads(ApplicationContext context) {
		assertNotNull(context);
	}

}
