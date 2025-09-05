package com.stayoline.adapter.inbound.rest;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stayoline.application.usecase.ListPackagesUseCase;
import com.stayoline.domain.model.AccessPackage;

@RestController
@RequestMapping("/packages")
public class PackageController {
    private final ListPackagesUseCase listPackages;
    public PackageController(ListPackagesUseCase listPackages) { this.listPackages = listPackages; }
    @GetMapping public List<AccessPackage> list() { return listPackages.execute(); }
}
