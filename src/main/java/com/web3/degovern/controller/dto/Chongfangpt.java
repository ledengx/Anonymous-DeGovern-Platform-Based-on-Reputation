package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Chongfangpt {
    BigInteger Bob_balance0;
    BigInteger LastTimestamp0;
    BigInteger Bob_balance1;
    BigInteger LastTimestamp1;
    BigInteger Bob_balance2;
    String []log={" "
            ,"==========重放攻击防护========="
            ,"[重放攻击防护]查看账户余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额",
"[重放攻击防护]发送交易<发送方 接收方 交易金额 nounce>0x239fe5de1acb07e87a871d4c474bc3b05370431d向0x97d495fb16f6945d3bbab472f9d1448430786ecc发送转账交易，转账金额为100",
"[重放攻击防护]查看账户余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额",
"[重放攻击防护]查看交易时间戳<查看对象>0x239fe5de1acb07e87a871d4c474bc3b05370431d交易时间戳，timeStamp为1694063143216",
"[重放攻击防护]启动重放攻击防护，，，，，，，，，，，，，success!",
           " [重放攻击防护]再次发送交易<发送方 接收方 交易金额 nounce 时间戳>0x239fe5de1acb07e87a871d4c474bc3b05370431d向0x97d495fb16f6945d3bbab472f9d1448430786ecc发送转账交易，转账金额为100，时间戳为1693469701227，，，，，，，，，success!",
"            [重放攻击防护]查看账户余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为300，，，，，，，，，，，，，，Protect Success!"
};
}
