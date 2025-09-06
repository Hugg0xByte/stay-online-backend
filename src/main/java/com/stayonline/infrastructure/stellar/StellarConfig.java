package com.stayonline.infrastructure.stellar;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stellar")
public class StellarConfig {
    private String networkPassphrase;
    private String horizonUrl;
    private String sorobanRpcUrl;
    private String contractAddress;
    private String adminSecretKey;
    private String tokenAssetAddress;

    // getters/setters (Spring Boot faz o binding via setters)
    public String getNetworkPassphrase() {
        return networkPassphrase;
    }

    public void setNetworkPassphrase(String v) {
        this.networkPassphrase = v;
    }

    public String getHorizonUrl() {
        return horizonUrl;
    }

    public void setHorizonUrl(String v) {
        this.horizonUrl = v;
    }

    public String getSorobanRpcUrl() {
        return sorobanRpcUrl;
    }

    public void setSorobanRpcUrl(String v) {
        this.sorobanRpcUrl = v;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String v) {
        this.contractAddress = v;
    }

    public String getAdminSecretKey() {
        return adminSecretKey;
    }

    public void setAdminSecretKey(String v) {
        this.adminSecretKey = v;
    }

    public String getTokenAssetAddress() {
        return tokenAssetAddress;
    }

    public void setTokenAssetAddress(String v) {
        this.tokenAssetAddress = v;
    }
}
