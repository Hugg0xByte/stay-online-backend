package com.stayonline.domain.model;

import java.time.Duration;
import java.util.Objects;

public class AccessPackage {
    private final String id;
    private final String name;
    private final long priceInStroops;
    private final Duration duration;

    public AccessPackage(String id, String name, long priceInStroops, Duration duration) {
        this.id = id;
        this.name = name;
        this.priceInStroops = priceInStroops;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPriceInStroops() {
        return priceInStroops;
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AccessPackage ap && id.equals(ap.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
