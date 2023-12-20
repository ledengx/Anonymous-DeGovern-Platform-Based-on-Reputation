package com.web3.degovern.service;

import com.web3.degovern.conf.ContractConfig;
import com.web3.degovern.contracts.Replay;
import com.web3.degovern.controller.dto.Chongfang;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

@Service
@NoArgsConstructor
@Data
public class ReplayService {

    @Autowired
    private Client client;

    @Autowired
    private ContractConfig contractConfig;

    AssembleTransactionProcessor txProcessor;

    private Replay replay;

    @PostConstruct
    //项目部署时运行一次
    public void init() throws Exception {
        this.txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        //判断在智能合约配置类里，该智能合约是否已经有地址
        if(contractConfig.getReplayAddress() == null ||
                contractConfig.getReplayAddress().isEmpty()){
            //如果没有的话，将其部署到链上
            replay = Replay.deploy(client,client.getCryptoSuite().getCryptoKeyPair());
            System.out.println("address = " + replay.getContractAddress());
            //将该合约的地址填写到智能合约配置类中
            contractConfig.setReplayAddress(replay.getContractAddress());
        }else{
            //如果已有地址，则加载
            replay = Replay.load(
                    contractConfig.getReplayAddress(),
                    client,
                    client.getCryptoSuite().getCryptoKeyPair()
            );
        }
        ;
    }


    public BigInteger balanceof(String n) throws Exception{
        BigInteger balace=replay.balanceOf(n);
        return balace;
    }

    public BigInteger getnonce(String n) throws Exception{
        BigInteger nonce = replay.getnonce(n);
        return nonce;
    }

    public String transfer(String sender,String receiver,BigInteger amount,BigInteger signature, BigInteger nonce)
    {

        TransactionReceipt transfer = replay.transfer(sender, receiver, amount, signature, nonce);
        return replay.getTransferOutput(transfer).getValue1();
    }

    public Chongfang replayak(String A,String B,String C,BigInteger amount1,BigInteger amount2,BigInteger amount3)throws Exception{
        Chongfang chongfang=new Chongfang();
        chongfang.setAlice_Balance0(balanceof(A));
        chongfang.setBob_Balance0(balanceof(B));
        chongfang.setAlice_nounce(getnonce(A));
        transfer(A,B,amount1,amount2,amount3);
        chongfang.setBob_Balance1(balanceof(B));
        transfer(A,B,amount1,amount2,amount3);
        chongfang.setBob_Balance2(balanceof(B));
        return chongfang;
    }
}
