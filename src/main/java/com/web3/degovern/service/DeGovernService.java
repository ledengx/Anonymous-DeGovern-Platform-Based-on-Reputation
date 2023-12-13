package com.web3.degovern.service;

import com.web3.degovern.contracts.DeGovern;
import com.web3.degovern.conf.ContractConfig;
import com.web3.degovern.controller.dto.RegisterDTO;
import com.web3.degovern.entity.Proposal;
import com.web3.degovern.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;


@Data
@Service
@NoArgsConstructor
public class DeGovernService {

    private static BigInteger Num = BigInteger.valueOf(0);

    @Autowired
    private Client client;

    @Autowired
    private ContractConfig contractConfig;

    AssembleTransactionProcessor txProcessor;

    private DeGovern deGovern;


    @PostConstruct
    //项目部署时运行一次
    public void init() throws Exception {
        this.txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        //判断在智能合约配置类里，该智能合约是否已经有地址
        if(contractConfig.getDeGovernAddress() == null ||
                contractConfig.getDeGovernAddress().isEmpty()){
            //如果没有的话，将其部署到链上
            deGovern = DeGovern.deploy(client,client.getCryptoSuite().getCryptoKeyPair());
            System.out.println("address = " + deGovern.getContractAddress());
            //将该合约的地址填写到智能合约配置类中
            contractConfig.setDeGovernAddress(deGovern.getContractAddress());
        }else{
            //如果已有地址，则加载
            deGovern = DeGovern.load(
                    contractConfig.getDeGovernAddress(),
                    client,
                    client.getCryptoSuite().getCryptoKeyPair()
            );
        }
        ;
    }

    //公私钥生成方法
    private static CryptoKeyPair getKeyPair(){
        // 创建非国密类型的CryptoSuite
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        // 随机生成非国密公私钥对
        return cryptoSuite.createKeyPair();
    }

    //声誉计算公式
    private static BigInteger RepCompute(){

        return BigInteger.valueOf(1);
    }

    //创建用户
    public BigInteger createUser(RegisterDTO registerDTO){
        /*
        * 应该返回公钥私钥和ID
        * */
        CryptoKeyPair account = getKeyPair();
        DeGovern.Struct0 user = new DeGovern.Struct0(registerDTO.getId(), account.getAddress(),
                                                     registerDTO.getUserName(), Num, Num, Num,
                                                     String.valueOf(account.keyPair.getPublic()), Num);
        TransactionReceipt receipt = deGovern.createUser(user);
        return deGovern.getCreateUserOutput(receipt).getValue1();
    }

    //登录
    public Boolean login(BigInteger id, String pk){
        TransactionReceipt receipt = deGovern.login(id, pk);
        return deGovern.getLoginOutput(receipt).getValue1();
    }

    //创建社区
    public BigInteger createDAO(String DAOName, String content, String address, BigInteger baseline){
        TransactionReceipt dao = deGovern.createDAO(DAOName, content, address, baseline);
        return deGovern.getCreateDAOOutput(dao).getValue1();
    }

    //创建提案
    public Boolean createProposal(Proposal proposal){
        DeGovern.Struct2 proposalInfo = new DeGovern.Struct2(proposal.getID(), proposal.getProposalAddress(),
                                                             proposal.getIsContract(), proposal.getProposalName(),
                                                             proposal.getProposalContent(), proposal.getIsPass(),
                                                             proposal.getYes(), proposal.getNo(),
                                                             proposal.getVoters(), proposal.getStart(), proposal.getStop());
        TransactionReceipt receipt = deGovern.createProposal(proposalInfo, proposal.getDAOID(), proposal.getUserID());
        return deGovern.getCreateProposalOutput(receipt).getValue1();
    }

    //投票
    public Boolean vote(Boolean choice, BigInteger userID, BigInteger DAOid, BigInteger proposalID) {

        TransactionReceipt receipt = deGovern.vote(choice, userID, DAOid, proposalID);
        return deGovern.getVoteOutput(receipt).getValue1();
    }
}