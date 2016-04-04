package com.github.slamdev.starter.business.samplemodule.boundary;

import com.github.slamdev.starter.business.samplemodule.entity.Sample;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @ApiOperation("Get sample")
    @RequestMapping(method = GET)
    public Sample get() {
        return sampleService.getSample();
    }
}
