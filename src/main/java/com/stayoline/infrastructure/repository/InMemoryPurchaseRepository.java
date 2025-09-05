package com.stayoline.infrastructure.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import com.stayoline.domain.model.Purchase;
import com.stayoline.domain.ports.PurchaseRepository;

public class InMemoryPurchaseRepository implements PurchaseRepository {
    private final Map<String, Purchase> byId = new ConcurrentHashMap<>();
    @Override public Purchase save(Purchase p) { byId.put(p.getId(), p); return p; }
    @Override public List<Purchase> findByUserId(String userId) {
        return byId.values().stream()
            .filter(p -> p.getUserId().equals(userId))
            .sorted(Comparator.comparing(Purchase::getCreatedAt).reversed())
            .collect(Collectors.toList());
    }
}
