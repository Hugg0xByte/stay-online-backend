package com.stayonline.domain.ports;

import java.util.List;

import com.stayonline.domain.model.Purchase;

public interface PurchaseRepository {
    Purchase save(Purchase p);

    List<Purchase> findByUserId(String userId);
}
