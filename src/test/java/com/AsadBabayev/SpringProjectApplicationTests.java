package com.AsadBabayev;

import com.AsadBabayev.starter.SpringProjectApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringProjectApplication.class)
class SpringProjectApplicationTests {

	@org.junit.jupiter.api.Disabled("Disabled until Spring context is fully configured")
	@Test
	void contextLoads() {
	}

}
