package com.epam.mentoring.messenger.messenger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@SpringBootTest(args={"--test=test"})
class MessengerApplicationTests {

	@SpyBean
	MessengerApplication application;

	@Test
	void contextLoads() {
		verify(application, times(1)).run(any());
	}

}
