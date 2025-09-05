package com.stayoline.application.usecase;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.stayoline.domain.model.Purchase;
import com.stayoline.domain.model.PurchaseStatus;
import com.stayoline.domain.ports.PackageRepository;
import com.stayoline.domain.ports.PurchaseRepository;

@Service
public class CreatePurchaseUseCase {
    private final PurchaseRepository purchases;
    private final PackageRepository packages;
    public CreatePurchaseUseCase(PurchaseRepository purchases, PackageRepository packages) {
        this.purchases = purchases; this.packages = packages;
    }
    public Purchase execute(String userId, String packageId) {
        packages.findById(packageId).orElseThrow(() -> new IllegalArgumentException("Package not found: " + packageId));
        String fakeTxHash = "tx_" + UUID.randomUUID();
        return purchases.save(new Purchase(UUID.randomUUID().toString(), userId, packageId, Instant.now(), PurchaseStatus.PENDING, fakeTxHash));
    }
}
