package com.web3.degovern.conf;


import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.math.BigInteger;

@Configuration
@Slf4j
public class SdkBeanConfig {

    private BcosSDK bcosSDK;
    private Client client;

    @Autowired
    private ContractConfig contractConfig;

    @Bean
    public Client client() throws Exception{
        //指定配置xml文件的位置
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        //选择群组1
        client = bcosSDK.getClient(Integer.valueOf(1));
        //连接后返回当前区块高度
        BigInteger blockNumber = client.getBlockNumber().getBlockNumber();
        System.out.println("==========================");
        System.out.println(blockNumber);
        System.out.println("==========================");

        return client;
    }

}
