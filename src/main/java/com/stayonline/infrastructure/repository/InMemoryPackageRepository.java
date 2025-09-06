package com.stayonline.infrastructure.repository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import com.stayonline.domain.model.AccessPackage;
import com.stayonline.domain.ports.PackageRepository;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPackageRepository implements PackageRepository {

    private final List<AccessPackage> packages = List.of(
            new AccessPackage("1", "Pacote Básico", 50000000L, Duration.ofHours(1)), // 5 XLM em stroops
            new AccessPackage("2", "Pacote Premium", 200000000L, Duration.ofHours(24)), // 20 XLM em stroops
            new AccessPackage("3", "Pacote VIP", 500000000L, Duration.ofDays(7)) // 50 XLM em stroops
    );

    @Override
    public List<AccessPackage> findAll() {
        return packages;
    }

    @Override
    public Optional<AccessPackage> findById(String id) {
        return packages.stream()
                .filter(pkg -> pkg.getId().equals(id))
                .findFirst();
    }
}
