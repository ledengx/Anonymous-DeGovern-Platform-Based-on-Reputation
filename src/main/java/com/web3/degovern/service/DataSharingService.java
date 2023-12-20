package com.web3.degovern.service;

import com.web3.degovern.conf.ContractConfig;
import com.web3.degovern.contracts.Data_Sharing;
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
public class DataSharingService {

    @Autowired
    private Client client;

    @Autowired
    private ContractConfig contractConfig;

    AssembleTransactionProcessor txProcessor;

    private Data_Sharing dataSharing;

    @PostConstruct
    //项目部署时运行一次
    public void init() throws Exception {
        this.txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        //判断在智能合约配置类里，该智能合约是否已经有地址
        if(contractConfig.getData_SharingAddress() == null ||
                contractConfig.getData_SharingAddress().isEmpty()){
            //如果没有的话，将其部署到链上
            dataSharing = Data_Sharing.deploy(client,client.getCryptoSuite().getCryptoKeyPair());
            System.out.println("address = " + dataSharing.getContractAddress());
            //将该合约的地址填写到智能合约配置类中
            contractConfig.setData_SharingAddress(dataSharing.getContractAddress());
        }else{
            //如果已有地址，则加载
            dataSharing = Data_Sharing.load(
                    contractConfig.getData_SharingAddress(),
                    client,
                    client.getCryptoSuite().getCryptoKeyPair()
            );
        }
        ;
    }

    public String Route_Attack(String user,String port)throws Exception
    {
        TransactionReceipt set=dataSharing.Route_Attack(user,port);
        return set.getGasUsed();
    }

    public String init(String user)throws Exception{
        TransactionReceipt set=dataSharing.init(user);
        return set.getGasUsed();
    }

    public String Route_protect(String user,String port)throws Exception
    {
        TransactionReceipt set=dataSharing.Route_protect(user,port);
        return set.getGasUsed();
    }

    public Boolean setData(String user,String data)throws Exception
    {
        TransactionReceipt res=dataSharing.setData(user,data);
        Boolean ans = true;
        try{
            dataSharing.getSetDataOutput(res).getValue1();
        }catch (Exception e){
            ans = false;
        }
        return ans;
    }

    public String getData(String user)throws Exception{
        return dataSharing.getData(user);
    }

    public Boolean Publish_Data(String data)throws Exception{
        TransactionReceipt res=dataSharing.Publish_Data(data);
        return dataSharing.getPublish_DataOutput(res).getValue1();
    }



    public  Boolean Accesscontrol(String attributes)throws Exception{
        return dataSharing.Access_Control(attributes);
    }

    public String Getdata(String attributes)throws Exception{
        return dataSharing.Get_Data(attributes);
    }

    public Jiechiak jiechiak(String A,String B,String C,String Data,String port)throws Exception{
        init(A);
        setData(A,Data);
        getData(B);
        Route_Attack(A,port);
        setData(A,Data);
        Jiechiak jiechiak=new Jiechiak();
        jiechiak.setError("transaction failed !");
        return jiechiak;
    }


    public Lubang1_shuju lubang1Shuju(String Data,String sta1)throws Exception
    {
        Lubang1_shuju lubang1Shuju=new Lubang1_shuju();
        Publish_Data(Data);
        lubang1Shuju.setData_return(Getdata(sta1));
        return lubang1Shuju;
    }

    public Lubang2_shuju lubang2Shuju(String Data,String sta1)throws Exception
    {
        Lubang2_shuju lubang2Shuju=new Lubang2_shuju();
        Publish_Data(Data);
        lubang2Shuju.setData_return(Getdata(sta1));
        return lubang2Shuju;
    }



    public Lubang3_shuju lubang3Shuju(String Data,String sta1,String sta2)throws Exception
    {
        Lubang3_shuju lubang3Shuju=new Lubang3_shuju();
        Publish_Data(Data);
        lubang3Shuju.setData_return(Getdata(sta1));
        Getdata(sta2);
        return lubang3Shuju;
    }

    public Jiechipt jiechipt(String A,String B,String C,String Data,String port)throws Exception
    {
        Route_protect(A,port);
        setData(A,Data);
        getData(B);
        Jiechipt jiechipt=new Jiechipt();
        jiechipt.setResult(Data);
        return jiechipt;
    }


}
