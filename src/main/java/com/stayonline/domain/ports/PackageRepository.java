package com.stayonline.domain.ports;

import java.util.List;
import java.util.Optional;

import com.stayonline.domain.model.AccessPackage;

public interface PackageRepository {
    List<AccessPackage> findAll();

    Optional<AccessPackage> findById(String id);
}
