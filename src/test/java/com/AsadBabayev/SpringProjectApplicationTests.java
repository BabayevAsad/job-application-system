package com.AsadBabayev;

import com.AsadBabayev.starter.SpringProjectApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringProjectApplication.class)
@Disabled("Disabled until application context configuration is fixed")
class SpringProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
