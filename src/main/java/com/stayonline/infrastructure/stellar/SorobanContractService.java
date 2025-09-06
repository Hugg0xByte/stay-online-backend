package com.stayonline.infrastructure.stellar;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stellar.sdk.Address;
import org.stellar.sdk.Network;
import org.stellar.sdk.Server;
import org.stellar.sdk.SorobanServer;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.TransactionBuilder;
import org.stellar.sdk.TransactionBuilderAccount;
import org.stellar.sdk.operations.InvokeHostFunctionOperation;
import org.stellar.sdk.scval.Scv;
import org.stellar.sdk.xdr.HostFunction;
import org.stellar.sdk.xdr.HostFunctionType;
import org.stellar.sdk.xdr.InvokeContractArgs;
import org.stellar.sdk.xdr.SCSymbol;
import org.stellar.sdk.xdr.SCVal;
import org.stellar.sdk.xdr.SCValType;
import org.stellar.sdk.xdr.Uint32;
import org.stellar.sdk.xdr.XdrString;
import org.stellar.sdk.xdr.XdrUnsignedInteger;

/**
 * Gera XDR não assinado para o front assinar (equivalente ao CLI:
 * --build-only).
 */
@Service
public class SorobanContractService {

    @Autowired
    private final StellarConfig stellarConfig;
    private final Server horizon; // Horizon p/ contas e sequência
    private final SorobanServer soroban; // Soroban RPC
    private final Network network;

    public SorobanContractService(StellarConfig cfg) {
        this.stellarConfig = cfg;
        this.horizon = new Server(cfg.getHorizonUrl());
        this.soroban = new SorobanServer(cfg.getSorobanRpcUrl()); // ex.: http://localhost:8000/soroban/rpc
        this.network = new Network(cfg.getNetworkPassphrase());
    }

    /**
     * Monta a transação de invocação do contrato:
     * buy_order(owner: Address, package_id: u32)
     * Simula para obter footprint/resources, injeta os dados Soroban e
     * retorna o Envelope XDR em base64 **sem assinatura** (front assina).
     *
     * @param ownerAccountId G..., conta do usuário (fonte da tx e arg owner)
     * @param packageId      id do pacote (u32)
     * @return base64 do TransactionEnvelope (unsigned)
     */
    public String buildBuyOrderUnsignedXdr(String ownerAccountId, int packageId) throws Exception {
        // 0) Carrega a conta no formato certo (TransactionBuilderAccount) via Soroban
        // RPC
        TransactionBuilderAccount source = soroban.getAccount(ownerAccountId); // SorobanServer
        // se a conta não existir, o SDK lança AccountNotFoundException

        // 1) Argumentos do contrato
        SCVal ownerArg = new Address(ownerAccountId).toSCVal();
        SCVal pkgArg = Scv.toInt32(packageId);

        // 2) Operação de invocação (ajuste para usar seu helper se preferir)
        InvokeHostFunctionOperation op = InvokeHostFunctionOperation
                .invokeContractFunctionOperationBuilder(stellarConfig.getContractAddress(), "buy_order",
                        java.util.List.of(ownerArg, pkgArg))
                .build();

        // 3) Monta a transação (apenas UMA vez) — não assina
        Transaction tx = new TransactionBuilder(source, network)
                .addOperation(op)
                .setBaseFee(100) // base fee; resource fee virá do prepare
                .setTimeout(120)
                .build();

        // 4) Prepara (simula + injeta SorobanTransactionData + resource fee)
        tx = soroban.prepareTransaction(tx);

        // 5) Retorna XDR base64 não assinado (o front assina e envia)
        return tx.toEnvelopeXdrBase64();
    }

    // -------------------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------------------
    private InvokeHostFunctionOperation buildInvokeOperation(String fnName, List<SCVal> args) {
        Address contract = new Address(stellarConfig.getContractAddress());

        return InvokeHostFunctionOperation.builder()
                .hostFunction(HostFunction.builder()
                        .discriminant(HostFunctionType.HOST_FUNCTION_TYPE_INVOKE_CONTRACT)
                        .invokeContract(InvokeContractArgs.builder()
                                .contractAddress(contract.toSCAddress())
                                .functionName(sym(fnName))
                                .args(args.toArray(new SCVal[0]))
                                .build())
                        .build())
                .build();
    }

    // SCVal U32 (SDK sem builder() no Uint32)
    private static SCVal u32(int v) {
        Uint32 u = new Uint32();
        u.setUint32(new XdrUnsignedInteger(v));

        SCVal sc = new SCVal();
        sc.setDiscriminant(SCValType.SCV_U32);
        sc.setU32(u);
        return sc;
    }

    // SCSymbol (SDK sem builder(); usa XdrString + setSCSymbol)
    private static SCSymbol sym(String name) {
        byte[] bytes = name.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > 32) {
            throw new IllegalArgumentException("symbol too long (max 32 bytes): " + name);
        }
        SCSymbol s = new SCSymbol();
        s.setSCSymbol(new XdrString(bytes)); // se seu XdrString aceitar String, pode usar new XdrString(name)
        return s;
    }
}
