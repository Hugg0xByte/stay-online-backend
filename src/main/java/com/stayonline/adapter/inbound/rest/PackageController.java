package com.stayonline.adapter.inbound.rest;

import java.util.List;

import com.stayonline.application.usecase.ListPackagesUseCase;
import com.stayonline.domain.model.AccessPackage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/packages")
public class PackageController {
    private final ListPackagesUseCase listPackages;

    public PackageController(ListPackagesUseCase listPackages) {
        this.listPackages = listPackages;
    }

    @GetMapping
    public List<AccessPackage> list() {
        return listPackages.execute();
    }
}
