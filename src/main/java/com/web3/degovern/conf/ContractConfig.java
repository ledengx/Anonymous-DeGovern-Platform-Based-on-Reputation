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
}
