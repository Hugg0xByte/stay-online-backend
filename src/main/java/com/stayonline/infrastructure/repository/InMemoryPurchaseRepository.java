package com.stayonline.infrastructure.repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.stayonline.domain.model.Purchase;
import com.stayonline.domain.ports.PurchaseRepository;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPurchaseRepository implements PurchaseRepository {

    private final ConcurrentHashMap<String, Purchase> purchases = new ConcurrentHashMap<>();

    @Override
    public Purchase save(Purchase purchase) {
        purchases.put(purchase.getId(), purchase);
        return purchase;
    }

    @Override
    public List<Purchase> findByUserId(String userId) {
        return purchases.values().stream()
                .filter(purchase -> purchase.getUserId().equals(userId))
                .toList();
    }
}
