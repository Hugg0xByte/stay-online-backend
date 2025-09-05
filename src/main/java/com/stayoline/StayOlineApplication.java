package com.stayoline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.stayoline.domain.ports.PackageRepository;
import com.stayoline.domain.ports.PurchaseRepository;
import com.stayoline.infrastructure.repository.InMemoryPackageRepository;
import com.stayoline.infrastructure.repository.InMemoryPurchaseRepository;

@SpringBootApplication
public class StayOlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(StayOlineApplication.class, args);
    }
    @Bean public PackageRepository packageRepository() { return new InMemoryPackageRepository().seedDefaults(); }
    @Bean public PurchaseRepository purchaseRepository() { return new InMemoryPurchaseRepository(); }
}
