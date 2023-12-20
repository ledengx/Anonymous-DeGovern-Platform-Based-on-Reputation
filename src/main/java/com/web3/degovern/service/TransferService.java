package com.web3.degovern.service;

import com.web3.degovern.conf.ContractConfig;
import com.web3.degovern.contracts.Transfer;
import com.web3.degovern.controller.dto.*;
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
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@Data
public class TransferService {

    @Autowired
    private Client client;

    @Autowired
    private ContractConfig contractConfig;

    AssembleTransactionProcessor txProcessor;

    private Transfer transfer;

    @PostConstruct
    //项目部署时运行一次
    public void init() throws Exception {
        this.txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        //判断在智能合约配置类里，该智能合约是否已经有地址
        if(contractConfig.getTransferAddress() == null ||
                contractConfig.getTransferAddress().isEmpty()){
            //如果没有的话，将其部署到链上
            transfer = Transfer.deploy(client,client.getCryptoSuite().getCryptoKeyPair());
            System.out.println("address = " + transfer.getContractAddress());
            //将该合约的地址填写到智能合约配置类中
            contractConfig.setHelloWorldAddress(transfer.getContractAddress());
        }else{
            //如果已有地址，则加载
            transfer = Transfer.load(
                    contractConfig.getTransferAddress(),
                    client,
                    client.getCryptoSuite().getCryptoKeyPair()
            );
        }
        ;
    }

    public String ttransfer(String sender,String receiver,BigInteger amount)throws Exception{
        TransactionReceipt set=transfer.transfer(sender,receiver,amount);
        return set.getGasUsed();
    }


    public String getAddress()throws Exception{
        return transfer.getContractAddress();
    }

    public BigInteger getBalance(String id)throws Exception{
        return transfer.getBalance(id);
    }

    public String setBalance(String id,BigInteger amount)throws Exception{
        TransactionReceipt set=transfer.setBalance(id,amount);
        return set.getGasUsed();
    }

    public String setBalanceC(String id,BigInteger amount)throws Exception{
        TransactionReceipt set=transfer.setBalanceC(id,amount);
        return set.getGasUsed();
    }

    public BigInteger getBalanceC(String id)throws Exception{
        return transfer.getBalanceC(id);
    }

    public String setGas(String id,BigInteger amount)throws Exception{
        TransactionReceipt set=transfer.setGas(id,amount);
        return set.getGasUsed();
    }

    public BigInteger getGas(String id)throws Exception{
        return transfer.getGas(id);
    }

    public String shopping(String sender)throws Exception{

        TransactionReceipt shopping = transfer.shopping(sender);
        return transfer.getShoppingOutput(shopping).getValue1();
    }

    public BigInteger double_attack(String sender,BigInteger data)throws Exception{

        TransactionReceipt double_attack=transfer.double_attrack(sender,data);
        return transfer.getDouble_attrackOutput(double_attack).getValue1();
    }

    public BigInteger double_protect(String account,BigInteger time)throws Exception{
        TransactionReceipt double_protect=transfer.double_protect(account,time);
        return transfer.getDouble_protectOutput(double_protect).getValue1();
    }

    public BigInteger withdraw(String sender,String attracker,BigInteger now){
        TransactionReceipt withdraw=transfer.withdraw(sender,attracker,now);
        return transfer.getWithdrawOutput(withdraw).getValue1();

    }

    public String time_protect(String sender,String attacker,Boolean result)throws  Exception{
        TransactionReceipt time_protect=transfer.time_protect(sender,attacker,result);
        return time_protect.getGasUsed();
    }

    public String reentrancy_attrack(String sender,String victim,BigInteger amount)throws  Exception{
        TransactionReceipt reentrancy_attrack=transfer.reentrancy_attrack(sender,victim,amount);
        return reentrancy_attrack.getGasUsed();
    }

    public String reentrancy_protect(String sender,String victim,BigInteger amount)throws  Exception{
        TransactionReceipt reentrancy_protect=transfer.reentrancy_protect(sender,victim,amount);
        return reentrancy_protect.getGasUsed();
    }

    public String getPresident()throws Exception{
        return transfer.getPresident();
    }

    public  Boolean compareStr(String str1,String str2)throws  Exception{
        TransactionReceipt compareStr= transfer.compareStr(str1,str2);
        return transfer.getCompareStrOutput(compareStr).getValue1();
    }

    public String becomePresident(String sender,BigInteger price)throws Exception{
        TransactionReceipt becomePresident=transfer.becomePresident(sender,price);
        return becomePresident.getGasUsed();
    }

    public String reject_attack()throws Exception{
        TransactionReceipt reject_attack=transfer.reject_attrack();
        return reject_attack.getGasUsed();
    }

    public String becomePresident1(String sender,BigInteger price)throws Exception{
        TransactionReceipt becomePresident1=transfer.becomePresident1(sender,price);
        return becomePresident1.getGasUsed();
    }

    public String reject_protect()throws Exception{
        TransactionReceipt reject_protect=transfer.reject_protect();
        return reject_protect.getGasUsed();
    }

    public String order1(String sender,String receiver,BigInteger value,BigInteger Gas)throws Exception{
        TransactionReceipt order1=transfer.order1(sender,receiver,value,Gas);
        return order1.getGasUsed();

    }

    public String order(String sender,String receiver)throws Exception{
        TransactionReceipt order=transfer.order(sender,receiver);
        return order.getGasUsed();
    }

    public String order_attrack(String receiver)throws Exception{
        TransactionReceipt order_attrack=transfer.order_attrack(receiver);
        return order_attrack.getGasUsed();
    }

    public String order_protect(String receiver)throws Exception{
        TransactionReceipt order_protect=transfer.order_protect(receiver);
        return order_protect.getGasUsed();
    }
    public String tranfersMM(String sender,String receiver,BigInteger amount,BigInteger num)throws Exception{
        TransactionReceipt transferMM=transfer.transferMM(sender,receiver,amount,num);
        return transferMM.getGasUsed();

    }

    public Shuanghuaak zshuanghuagongji(String A,String B,String C,BigInteger balance,BigInteger amount) throws Exception {
        BigInteger zero=BigInteger.ZERO;
        setBalance(A,balance);
        shopping(A);
        double_attack(A,amount);
        Shuanghuaak shuanghuaak=new Shuanghuaak();
        shuanghuaak.setAlice_balance(getBalance(A));
        shuanghuaak.setBob_balance(getBalance(B));
        shuanghuaak.setCim_balance(getBalance(C));
//        ShuanghuagongjiDTO shuanghuagongjiDTOA = new ShuanghuagongjiDTO();
//        ShuanghuagongjiDTO shuanghuagongjiDTOB = new ShuanghuagongjiDTO();
//        ShuanghuagongjiDTO shuanghuagongjiDTOC = new ShuanghuagongjiDTO();
//        List<ShuanghuagongjiDTO> res  = new ArrayList<>();
//        shuanghuagongjiDTOA.setName(A);
//        shuanghuagongjiDTOA.setBalance(getBalance(A));
//        res.add(shuanghuagongjiDTOA);
//        shuanghuagongjiDTOB.setName(B);
//        shuanghuagongjiDTOB.setBalance(getBalance(B));
//        res.add(shuanghuagongjiDTOB);
//        shuanghuagongjiDTOC.setName(C);
//        shuanghuagongjiDTOC.setBalance(getBalance(C));
//        res.add(shuanghuagongjiDTOC);
        return shuanghuaak;
    }

    public Shuanghuapt shuanghuapt(String A,String B,String C,BigInteger timelock) throws Exception {
        Shuanghuapt shuanghuapt=new Shuanghuapt();
        BigInteger zero=BigInteger.ZERO;
        BigInteger balanceA = new BigInteger("8000");
        setBalance(A,balanceA);
        shuanghuapt.setAlice_initial_balance(getBalance(A));
        setBalance(B,zero);
        setBalance(C,zero);
        double_protect(B,timelock);
        shuanghuapt.setAlice_final_balance(getBalance(A));
        shuanghuapt.setBob_final_balance(getBalance(B));
        shuanghuapt.setCim_final_balance(getBalance(C));
        return shuanghuapt;
    }

    public Timestampat timestampat(String A,String B,BigInteger amount)throws Exception{
        BigInteger zero=BigInteger.ZERO;
        BigInteger balanceA = new BigInteger("8000");
        setBalance(A,balanceA);
        Timestampat timestampat = new Timestampat();
        timestampat.setAlice_initial_balance(getBalance(A));
        setBalance(B,zero);
        timestampat.setBob_initial_balance(getBalance(B));
        withdraw(A,B,amount);
        timestampat.setAlice_final_balance(getBalance(A));
        timestampat.setBob_final_balance(getBalance(B));
        return timestampat;
    }

    public Timestamppt timestamppt(String A,String B,String C)throws Exception{

        BigInteger zero=BigInteger.ZERO;
        BigInteger balanceA = new BigInteger("8000");
        setBalance(A,balanceA);
        setBalance(B,zero);
        Timestamppt timestamppt=new Timestamppt();
        timestamppt.setAlice_initial_balance(getBalance(A));
        timestamppt.setBob_initial_balance(getBalance(B));
        time_protect(A,A,true);
        timestamppt.setBob_final_balance(getBalance(B));
        return timestamppt;

    }

    public  Shunxuak shunxuak(String A,String B,String C)throws Exception{
        Shunxuak shunxuak=new Shunxuak();
        setBalance(A,new BigInteger("8000"));
        shunxuak.setAlice_initial_balance(getBalance(A));
        order_attrack(A);
        shunxuak.setBob_Gas(getGas(B));
        shunxuak.setCim_Gas(getGas(C));
        shunxuak.setCim_fianal_balance(getBalance(C));
        shunxuak.setBob_fianal_Balance(getBalance(B));
        shunxuak.setAlice_final_Balance(getBalance(A));

        //order1(B,C,value,gas);
        //setBalance(C,amount);
        return shunxuak;
    }

    public Shunxupt shunxupt(String A,String B,String C)throws Exception{
        setBalance(C,new BigInteger("10"));
        order_protect(A);
        Shunxupt shunxupt=new Shunxupt();
        shunxupt.setBob_final_balance(getBalance(B));
        return shunxupt;
    }


    public Chongruak chongruak(String A,String B,String C,BigInteger amount1,BigInteger amount2,BigInteger amount3,BigInteger value)throws Exception{
        setBalance(A,amount1);
        setBalance(B,amount2);
        setBalanceC(B,amount3);
        Chongruak chongruak=new Chongruak();
         chongruak.setBob_balanceC(getBalanceC(B));
         chongruak.setBob_balance(getBalance(B));
         chongruak.setAlice_balance(getBalance(A));
         reentrancy_attrack(B,A,value);
         chongruak.setBob_final_balanceC(getBalanceC(B));
         chongruak.setBob_final_balance(getBalance(B));
        chongruak.setAlice_final_balance(getBalance(A));
        return chongruak;
    }

    public Chongrupt chongrupt(String A,String B,String C)throws Exception{
        setBalance(A,new BigInteger("10"));
        setBalance(B,new BigInteger("10"));
        setBalanceC(B,new BigInteger("0"));
        reentrancy_protect(B,A,new BigInteger("1"));
        Chongrupt chongrupt=new Chongrupt();
        chongrupt.setBob_final_balanceC(getBalanceC(B));
        chongrupt.setBob_final_balance(getBalance(B));
        chongrupt.setAlice_final_balance(getBalance(A));
        return chongrupt;
    }

    public Jujueak jujueak(String A,String B,String C,BigInteger amount1,BigInteger amount2,BigInteger amount3)throws Exception{
        setBalance(A,amount1);
        becomePresident(B,amount2);
        reject_attack();
        becomePresident(C,amount3);
        Jujueak jujueak=new Jujueak();
        jujueak.setPresident(getPresident());
        return jujueak;
    }

    public Jujuept jujuept(String A,String B,String C)throws Exception{        reject_protect();
        reject_protect();
        becomePresident(C,new BigInteger("4"));
        Jujuept jujuept=new Jujuept();
        jujuept.setPresident(getPresident());
        return jujuept;
    }

    public Zuseak Zuseak(String A,String B,String C,BigInteger ini,BigInteger amount,BigInteger times)throws Exception{
        setBalance(A,ini);
        tranfersMM(A,A,amount,times);
        Zuseak zuseak=new Zuseak();
        zuseak.setError("transaction failed !");
        return zuseak;
    }

    public Zusept Zusept(String A,String B,String C,BigInteger amount,BigInteger times)throws Exception{
        setBalance(A,new BigInteger("10000"));
        Zusept zusept=new Zusept();
        zusept.setAlice_ini_balance(getBalance(A));
        tranfersMM(A,A,amount,times);
        tranfersMM(B,A,amount,times);
        zusept.setError("one transaction failed !");
        zusept.setAlice_final_balance(getBalance(A));
        return zusept;
    }



}
