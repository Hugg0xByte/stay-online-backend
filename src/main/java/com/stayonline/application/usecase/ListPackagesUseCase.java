package com.stayonline.application.usecase;

import java.util.List;

import com.stayonline.domain.model.AccessPackage;
import com.stayonline.domain.ports.PackageRepository;

import org.springframework.stereotype.Service;

@Service
public class ListPackagesUseCase {
    private final PackageRepository repo;

    public ListPackagesUseCase(PackageRepository repo) {
        this.repo = repo;
    }

    public List<AccessPackage> execute() {
        return repo.findAll();
    }
}
