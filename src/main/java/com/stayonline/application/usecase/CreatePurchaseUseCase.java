package com.stayonline.application.usecase;

import java.time.Instant;
import java.util.UUID;

import com.stayonline.domain.model.Purchase;
import com.stayonline.domain.model.PurchaseStatus;
import com.stayonline.domain.ports.PackageRepository;
import com.stayonline.domain.ports.PurchaseRepository;
import com.stayonline.infrastructure.stellar.SorobanContractService;

import org.springframework.stereotype.Service;

@Service
public class CreatePurchaseUseCase {
    private final PurchaseRepository purchases;
    private final PackageRepository packages;
    private final SorobanContractService sorobanService;

    public CreatePurchaseUseCase(
            PurchaseRepository purchases,
            PackageRepository packages,
            SorobanContractService sorobanService) {
        this.purchases = purchases;
        this.packages = packages;
        this.sorobanService = sorobanService;
    }

    public CreatePurchaseResult execute(String userId, String packageId) {
        // Valida se o pacote existe
        var packageFound = packages.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Package not found: " + packageId));

        try {
            // Gera o XDR não assinado para o cliente assinar
            int packageIdInt = Integer.parseInt(packageId);
            String unsignedXdr = sorobanService.buildBuyOrderUnsignedXdr(userId, packageIdInt);

            // Cria a compra com status PENDING (aguardando assinatura)
            Purchase purchase = new Purchase(
                    UUID.randomUUID().toString(),
                    userId,
                    packageId,
                    Instant.now(),
                    PurchaseStatus.PENDING,
                    null // txHash será preenchido depois da assinatura
            );

            Purchase savedPurchase = purchases.save(purchase);

            return new CreatePurchaseResult(savedPurchase, unsignedXdr);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Package ID deve ser numérico: " + packageId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar compra: " + e.getMessage(), e);
        }
    }

    // Classe interna para retornar tanto a Purchase quanto o XDR
    public static class CreatePurchaseResult {
        private final Purchase purchase;
        private final String unsignedXdr;

        public CreatePurchaseResult(Purchase purchase, String unsignedXdr) {
            this.purchase = purchase;
            this.unsignedXdr = unsignedXdr;
        }

        public Purchase getPurchase() {
            return purchase;
        }

        public String getUnsignedXdr() {
            return unsignedXdr;
        }
    }
}