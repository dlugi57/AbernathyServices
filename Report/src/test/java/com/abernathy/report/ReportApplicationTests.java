package com.abernathy.report;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class ReportApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void main() {
		mock(SpringApplication.class);
		ReportApplication.main(new String[]{"Hello", "World"});
	}

}
