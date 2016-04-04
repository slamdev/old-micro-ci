package com.github.slamdev.starter.business.samplemodule.entity;

import static java.util.Objects.hash;

public class Sample {

    private long id;

    private String name;

    public Sample(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sample() {
        // For serialization
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sample sample = (Sample) o;
        return id == sample.id;
    }

    @Override
    public int hashCode() {
        return hash(id);
    }
}
