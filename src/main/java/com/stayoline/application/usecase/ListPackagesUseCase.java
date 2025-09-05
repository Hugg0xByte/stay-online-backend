package com.stayoline.application.usecase;

import java.util.List;
import com.stayoline.domain.model.AccessPackage;
import com.stayoline.domain.ports.PackageRepository;
import org.springframework.stereotype.Service;

@Service
public class ListPackagesUseCase {
    private final PackageRepository repo;
    public ListPackagesUseCase(PackageRepository repo) { this.repo = repo; }
    public List<AccessPackage> execute() { return repo.findAll(); }
}
