package com.epam.mentoring.messenger.messenger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
				"command.line.runner.enabled=false",
				"application.runner.enabled=false" })
class MessengerApplicationTests {

	@Test
	void contextLoads() {
	}

}
