package com.stayonline.adapter.inbound.rest.dto;

import java.time.Instant;

import com.stayonline.domain.model.Purchase;
import com.stayonline.domain.model.PurchaseStatus;

public record PurchaseResponse(
        String id,
        String userId,
        String packageId,
        Instant createdAt,
        PurchaseStatus status,
        String stellarTx) {
    public static PurchaseResponse from(Purchase p) {
        return new PurchaseResponse(p.getId(), p.getUserId(), p.getPackageId(), p.getCreatedAt(), p.getStatus(),
                p.getStellarTx());
    }
}
