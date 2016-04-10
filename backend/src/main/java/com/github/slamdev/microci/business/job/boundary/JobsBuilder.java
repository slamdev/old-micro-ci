package com.github.slamdev.microci.business.job.boundary;

import com.github.slamdev.microci.business.job.entity.Job;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class JobsBuilder {

    public List<Job> build(Resource descriptor) {
        requireNonNull(descriptor);
        try {
            Iterable<Object> it = new Yaml().loadAll(descriptor.getInputStream());
            return stream(it.spliterator(), false).map(this::convert).collect(toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Job convert(Object o) {
        Job job = new Job("", emptyList());
        ConfigurablePropertyAccessor accessor = PropertyAccessorFactory.forDirectFieldAccess(job);
//        accessor.setAutoGrowNestedPaths(true);
        accessor.setPropertyValues((Map<?, ?>) o);
        return job;
    }
}
