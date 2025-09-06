package com.stayonline.application.usecase;

import java.time.Instant;
import java.util.UUID;

import com.stayonline.domain.model.Purchase;
import com.stayonline.domain.model.PurchaseStatus;
import com.stayonline.domain.ports.PackageRepository;
import com.stayonline.domain.ports.PurchaseRepository;

import org.springframework.stereotype.Service;

@Service
public class CreatePurchaseUseCase {
    private final PurchaseRepository purchases;
    private final PackageRepository packages;

    public CreatePurchaseUseCase(PurchaseRepository purchases, PackageRepository packages) {
        this.purchases = purchases;
        this.packages = packages;
    }

    public Purchase execute(String userId, String packageId) {
        packages.findById(packageId).orElseThrow(() -> new IllegalArgumentException("Package not found: " + packageId));
        String fakeTxHash = "tx_" + UUID.randomUUID();
        return purchases.save(new Purchase(UUID.randomUUID().toString(), userId, packageId, Instant.now(),
                PurchaseStatus.PENDING, fakeTxHash));
    }
}
