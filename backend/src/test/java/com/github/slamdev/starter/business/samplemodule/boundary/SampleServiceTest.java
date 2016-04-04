package com.github.slamdev.starter.business.samplemodule.boundary;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class SampleServiceTest {

    private SampleService service;

    @Before
    public void setUp() throws IOException {
        service = new SampleService();
    }

    @Test
    public void should_return_something() {
        assertNotNull(service.getSample());
    }
}
