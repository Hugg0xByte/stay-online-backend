package com.stayonline.adapter.inbound.rest;

import java.util.List;

import com.stayonline.adapter.inbound.rest.dto.CreatePurchaseRequest;
import com.stayonline.adapter.inbound.rest.dto.PurchaseResponse;
import com.stayonline.application.usecase.CreatePurchaseUseCase;
import com.stayonline.application.usecase.ListUserPurchasesUseCase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PurchaseController {
    private final CreatePurchaseUseCase createPurchase;
    private final ListUserPurchasesUseCase listUserPurchases;

    public PurchaseController(CreatePurchaseUseCase createPurchase, ListUserPurchasesUseCase listUserPurchases) {
        this.createPurchase = createPurchase;
        this.listUserPurchases = listUserPurchases;
    }

    @PostMapping("/purchases")
    public PurchaseResponse create(@RequestBody CreatePurchaseRequest req) {
        var result = createPurchase.execute(req.userId(), req.packageId());
        return PurchaseResponse.from(result.getPurchase(), result.getUnsignedXdr());
    }

    @GetMapping("/users/{userId}/purchases")
    public List<PurchaseResponse> byUser(@PathVariable String userId) {
        return listUserPurchases.execute(userId).stream().map(PurchaseResponse::from).toList();
    }
}