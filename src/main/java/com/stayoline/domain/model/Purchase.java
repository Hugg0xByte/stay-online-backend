package com.stayoline.domain.model;

import java.time.Instant;

public class Purchase {
    private final String id;
    private final String userId;
    private final String packageId;
    private final Instant createdAt;
    private final PurchaseStatus status;
    private final String stellarTx;

    public Purchase(String id, String userId, String packageId, Instant createdAt, PurchaseStatus status,
            String stellarTx) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.createdAt = createdAt;
        this.status = status;
        this.stellarTx = stellarTx;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPackageId() {
        return packageId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public String getStellarTx() {
        return stellarTx;
    }
}
