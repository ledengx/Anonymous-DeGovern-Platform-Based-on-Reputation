package com.web3.degovern.conf;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ContractConfig {
    private String HelloWorldAddress = "0xc696a74dcbe7f2ed688f56f49c3ca91354907bc0";
    private String DeGovernAddress = "0xf98f4b0fae7ca94f95af1b950e48cee2200a265c";
    private String dappStorageStructure = "";
    private String dapp = "";
    private String Upgrade = "";
    private String ReplayAddress="0x7f95c23f0a43da9945e0e1d67b26ea8842a4fe9e";
    private String Replay_protectAddress="0xc3fcf5a6aef810f06327cf06e71cca1be5cca2bc";
    private String TransferAddress="0x0a2e2f632debbeb85e28cf8b18ea189251cbb9ac";
    private String Data_SharingAddress="";
}
