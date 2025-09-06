package com.stayonline.application.usecase;

import java.util.List;

import com.stayonline.domain.model.Purchase;
import com.stayonline.domain.ports.PurchaseRepository;

import org.springframework.stereotype.Service;

@Service
public class ListUserPurchasesUseCase {
    private final PurchaseRepository repo;

    public ListUserPurchasesUseCase(PurchaseRepository repo) {
        this.repo = repo;
    }

    public List<Purchase> execute(String userId) {
        return repo.findByUserId(userId);
    }
}
