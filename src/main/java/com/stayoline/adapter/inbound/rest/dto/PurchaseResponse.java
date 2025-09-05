package com.stayoline.adapter.inbound.rest.dto;

import java.time.Instant;
import com.stayoline.domain.model.Purchase;
import com.stayoline.domain.model.PurchaseStatus;

public record PurchaseResponse(
    String id,
    String userId,
    String packageId,
    Instant createdAt,
    PurchaseStatus status,
    String stellarTx
) {
    public static PurchaseResponse from(Purchase p) {
        return new PurchaseResponse(p.getId(), p.getUserId(), p.getPackageId(), p.getCreatedAt(), p.getStatus(), p.getStellarTx());
    }
}
