package com.stayoline.adapter.inbound.rest;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.stayoline.application.usecase.CreatePurchaseUseCase;
import com.stayoline.application.usecase.ListUserPurchasesUseCase;
import com.stayoline.adapter.inbound.rest.dto.CreatePurchaseRequest;
import com.stayoline.adapter.inbound.rest.dto.PurchaseResponse;
import com.stayoline.domain.model.Purchase;

@RestController
@RequestMapping
public class PurchaseController {
    private final CreatePurchaseUseCase createPurchase;
    private final ListUserPurchasesUseCase listUserPurchases;
    public PurchaseController(CreatePurchaseUseCase createPurchase, ListUserPurchasesUseCase listUserPurchases) {
        this.createPurchase = createPurchase; this.listUserPurchases = listUserPurchases;
    }
    @PostMapping("/purchases")
    public PurchaseResponse create(@RequestBody CreatePurchaseRequest req) {
        Purchase p = createPurchase.execute(req.userId(), req.packageId());
        return PurchaseResponse.from(p);
    }
    @GetMapping("/users/{userId}/purchases")
    public List<PurchaseResponse> byUser(@PathVariable String userId) {
        return listUserPurchases.execute(userId).stream().map(PurchaseResponse::from).toList();
    }
}
