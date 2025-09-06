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
        String txHash,
        String unsignedXdr // Novo campo para o XDR
) {
    public static PurchaseResponse from(Purchase purchase) {
        return new PurchaseResponse(
                purchase.getId(),
                purchase.getUserId(),
                purchase.getPackageId(),
                purchase.getCreatedAt(),
                purchase.getStatus(),
                purchase.getStellarTx(),
                null // XDR será null para purchases existentes
        );
    }

    public static PurchaseResponse from(Purchase purchase, String unsignedXdr) {
        return new PurchaseResponse(
                purchase.getId(),
                purchase.getUserId(),
                purchase.getPackageId(),
                purchase.getCreatedAt(),
                purchase.getStatus(),
                purchase.getStellarTx(),
                unsignedXdr);
    }
}