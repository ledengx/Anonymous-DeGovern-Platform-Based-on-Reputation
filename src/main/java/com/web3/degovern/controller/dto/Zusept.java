package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Zusept {
    BigInteger Alice_final_balance;
    BigInteger Alice_ini_balance;
    String Error;
    String []log={" "
            ,"=========中继阻塞攻击防护========"
            ,"[中继阻塞攻击防护]进行中继阻塞攻击防护<发送方 接收方 交易金额 交易次数>0x239fe5de1acb07e87a871d4c474bc3b05370431d向自己账户发送交易，每次转账金额为10，转账次数为16次，操作成功！",
            "[中继阻塞攻击防护]进行中继阻塞攻击防护<发送方 接收方 交易金额 交易次数>0x239fe5de1acb07e87a871d4c474bc3b05370431d向自己账户发送交易，每次转账金额为10，转账次数为16次，操作失败！，，，，，，，，，，，，，，，，，，，，，Protect Success!"
};

}
