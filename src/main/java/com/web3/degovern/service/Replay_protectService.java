package com.web3.degovern.service;

import com.web3.degovern.conf.ContractConfig;
import com.web3.degovern.contracts.Replay_protect;
import com.web3.degovern.controller.dto.Chongfang;
import com.web3.degovern.controller.dto.Chongfangpt;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@NoArgsConstructor
@Data
public class Replay_protectService {
    @Autowired
    private Client client;

    @Autowired
    private ContractConfig contractConfig;

    AssembleTransactionProcessor txProcessor;

    private Replay_protect replay_protect;

    @PostConstruct
    //项目部署时运行一次
    public void init() throws Exception {
        this.txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        //判断在智能合约配置类里，该智能合约是否已经有地址
        if(contractConfig.getReplay_protectAddress() == null ||
                contractConfig.getReplay_protectAddress().isEmpty()){
            //如果没有的话，将其部署到链上
            replay_protect = Replay_protect.deploy(client,client.getCryptoSuite().getCryptoKeyPair());
            System.out.println("address = " + replay_protect.getContractAddress());
            //将该合约的地址填写到智能合约配置类中
            contractConfig.setHelloWorldAddress(replay_protect.getContractAddress());
        }else{
            //如果已有地址，则加载
            replay_protect = replay_protect.load(
                    contractConfig.getReplay_protectAddress(),
                    client,
                    client.getCryptoSuite().getCryptoKeyPair()
            );
        }
        ;
    }
    public BigInteger pbalanceof(String n) throws Exception{
        BigInteger balace=replay_protect.balanceOf(n);
        return balace;
    }

    public BigInteger pgetnonce(String n) throws Exception{
        BigInteger nonce = replay_protect.getNonce(n);
        return nonce;
    }

    public BigInteger pgetLastTimestamp(String n) throws Exception{
        BigInteger timestamp = replay_protect.getLastTimestamp(n);
        return timestamp;
    }

    public String ptransfer(String sender,String receiver,BigInteger amount,BigInteger signature, BigInteger nonce,BigInteger timestamp)
    {

        TransactionReceipt ptransfer = replay_protect.transfer(sender, receiver, amount, signature, nonce,timestamp);
        return replay_protect.getTransferOutput(ptransfer).getValue1();
    }

    public Chongfangpt replaypt(String A, String B, String C)throws Exception{
        Chongfangpt chongfangpt=new Chongfangpt();
        chongfangpt.setBob_balance0(pbalanceof(B));
        BigInteger timestamp=pgetLastTimestamp(A);
        chongfangpt.setLastTimestamp0(timestamp);
        ptransfer(A,B,new BigInteger("100"),new BigInteger("123456"),new BigInteger("0"),timestamp);
        chongfangpt.setBob_balance1(pbalanceof(B));
        chongfangpt.setLastTimestamp1(timestamp);
        ptransfer(A,B,new BigInteger("100"),new BigInteger("123456"),new BigInteger("0"),timestamp);
        chongfangpt.setBob_balance2(pbalanceof(B));
        return chongfangpt;
    }
}