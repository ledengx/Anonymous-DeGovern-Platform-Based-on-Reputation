package com.web3.degovern.service;


import com.web3.degovern.conf.ContractConfig;
import com.web3.degovern.contracts.HelloWorld;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@NoArgsConstructor
@Data
public class HelloWorldService {

    @Autowired
    private Client client;

    @Autowired
    private ContractConfig contractConfig;

    AssembleTransactionProcessor txProcessor;

    private HelloWorld helloWorld;

    @PostConstruct
    //项目部署时运行一次
    public void init() throws Exception {
        this.txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        //判断在智能合约配置类里，该智能合约是否已经有地址
        if(contractConfig.getHelloWorldAddress() == null ||
                contractConfig.getHelloWorldAddress().isEmpty()){
            //如果没有的话，将其部署到链上
            helloWorld = HelloWorld.deploy(client,client.getCryptoSuite().getCryptoKeyPair());
            System.out.println("address = " + helloWorld.getContractAddress());
            //将该合约的地址填写到智能合约配置类中
            contractConfig.setHelloWorldAddress(helloWorld.getContractAddress());
        }else{
            //如果已有地址，则加载
            helloWorld = HelloWorld.load(
                    contractConfig.getHelloWorldAddress(),
                    client,
                    client.getCryptoSuite().getCryptoKeyPair()
            );
        }
        ;
    }

    public String set(String n) throws Exception {
        TransactionReceipt set = helloWorld.set(n);
        return set.getBlockNumber();
    }

    public String get() throws Exception {
        String mess = helloWorld.get();
        return mess;
    }

}
