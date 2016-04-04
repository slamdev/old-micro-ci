package com.github.slamdev.starter.business.samplemodule.boundary;

import com.github.slamdev.starter.Application;
import com.github.slamdev.starter.business.samplemodule.entity.Sample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestPropertySource("/application-test.properties")
@ActiveProfiles("SampleControllerTest")
public class SampleControllerTest {

    @Autowired
    private SampleService service;

    @Autowired
    private String baseUrl;

    @Test
    public void should_return_some_result() throws URISyntaxException, IOException {
        Sample expected = new Sample(1L, "stub");
        when(service.getSample()).thenReturn(expected);
        Sample actual = new TestRestTemplate().getForObject(baseUrl + "/api/sample", Sample.class);
        assertEquals(expected, actual);
    }

    @Configuration
    @Profile("SampleControllerTest")
    public static class Config {

        @Bean
        @Primary
        SampleService service() {
            return mock(SampleService.class);
        }
    }
}
