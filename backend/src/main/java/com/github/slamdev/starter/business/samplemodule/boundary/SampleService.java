package com.github.slamdev.starter.business.samplemodule.boundary;

import com.github.slamdev.starter.business.samplemodule.entity.Sample;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    public Sample getSample() {
        return new Sample(1L, "sample");
    }
}
