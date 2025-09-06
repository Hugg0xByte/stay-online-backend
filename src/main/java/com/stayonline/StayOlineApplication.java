package com.stayonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StayOlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(StayOlineApplication.class, args);
    }
    // @Bean public PackageRepository packageRepository() { return new
    // InMemoryPackageRepository().seedDefaults(); }
    // @Bean public PurchaseRepository purchaseRepository() { return new
    // InMemoryPurchaseRepository(); }
}
