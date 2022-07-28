package com.epam.mentoring.messenger.messenger;

import com.epam.mentoring.messenger.messenger.config.CommandLineRunnerTaskExecutor;
import com.epam.mentoring.messenger.messenger.config.DataLoader;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataPairException;
import com.epam.mentoring.messenger.messenger.exception.InvalidDataProvidedException;
import com.epam.mentoring.messenger.messenger.service.TemplateGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessengerAppCommandLineInputTests {

	@Autowired
	private TemplateGenerator templateGenerator;

	@Autowired
	private DataLoader dataLoader;

	@Autowired
	private CommandLineRunner executor;

	@BeforeEach
	public void setUp() throws IOException {
		System.out.println("setup...");
	}


	@Test
	void testWithFiles() throws Exception {
		executor.run();
	}

}
