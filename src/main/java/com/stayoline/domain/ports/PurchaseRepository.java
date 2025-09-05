package com.stayoline.domain.ports;

import java.util.List;
import com.stayoline.domain.model.Purchase;

public interface PurchaseRepository {
    Purchase save(Purchase p);
    List<Purchase> findByUserId(String userId);
}
