package com.stayoline.domain.ports;

import java.util.List;
import java.util.Optional;
import com.stayoline.domain.model.AccessPackage;

public interface PackageRepository {
    List<AccessPackage> findAll();
    Optional<AccessPackage> findById(String id);
}
