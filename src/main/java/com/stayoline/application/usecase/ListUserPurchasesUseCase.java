package com.stayoline.application.usecase;

import java.util.List;
import org.springframework.stereotype.Service;
import com.stayoline.domain.model.Purchase;
import com.stayoline.domain.ports.PurchaseRepository;

@Service
public class ListUserPurchasesUseCase {
    private final PurchaseRepository repo;
    public ListUserPurchasesUseCase(PurchaseRepository repo) { this.repo = repo; }
    public List<Purchase> execute(String userId) { return repo.findByUserId(userId); }
}
