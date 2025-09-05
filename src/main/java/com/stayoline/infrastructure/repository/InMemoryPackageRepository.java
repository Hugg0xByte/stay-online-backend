package com.stayoline.infrastructure.repository;

import java.time.Duration;
import java.util.*;
import com.stayoline.domain.model.AccessPackage;
import com.stayoline.domain.ports.PackageRepository;

public class InMemoryPackageRepository implements PackageRepository {
    private final Map<String, AccessPackage> byId = new LinkedHashMap<>();
    @Override public List<AccessPackage> findAll() { return new ArrayList<>(byId.values()); }
    @Override public Optional<AccessPackage> findById(String id) { return Optional.ofNullable(byId.get(id)); }
    public InMemoryPackageRepository seedDefaults() {
        put(new AccessPackage("p30", "30 min", 5_000_000L, Duration.ofMinutes(30)));
        put(new AccessPackage("p60", "1 hora", 10_000_000L, Duration.ofHours(1)));
        put(new AccessPackage("p180", "3 horas", 20_000_000L, Duration.ofHours(3)));
        return this;
    }
    public void put(AccessPackage p) { byId.put(p.getId(), p); }
}
