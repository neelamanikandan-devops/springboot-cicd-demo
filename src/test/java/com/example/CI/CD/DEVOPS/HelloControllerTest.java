package com.example.CI.CD.DEVOPS;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloControllerTest {

    @Test
    void testApplicationLoads() {
        Assertions.assertThat(true);
    }
}