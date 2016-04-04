package com.github.slamdev.starter.business.samplemodule;

import com.github.slamdev.starter.Application;
import com.github.slamdev.starter.business.samplemodule.boundary.SampleService;
import com.github.slamdev.starter.business.samplemodule.entity.Sample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestPropertySource("/application-test.properties")
@ActiveProfiles("SamplePageTest")
public class SamplePageTest {

    private static final Sample STUB = new Sample(1L, "stub");

    @Autowired
    private WebDriver driver;

    @Autowired
    private String baseUrl;

    @Test
    public void should_show_something() {
        driver.get(baseUrl + "/api/sample");
        assertTrue(driver.getPageSource().contains("{\"id\":1,\"name\":\"stub\"}"));
    }

    @Configuration
    @Profile("SamplePageTest")
    public static class Config {

        @Bean
        @Primary
        SampleService service() {
            SampleService service = mock(SampleService.class);
            when(service.getSample()).thenReturn(STUB);
            return service;
        }
    }
}
