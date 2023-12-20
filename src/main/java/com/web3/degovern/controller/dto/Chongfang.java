package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Chongfang {
    BigInteger Alice_Balance0;
    BigInteger Bob_Balance0;
    BigInteger Alice_nounce;
    BigInteger Bob_Balance1;
    BigInteger Bob_Balance2;
    String []log={" "
            ,"===========重放攻击=========="
            ,"[重放攻击]查看账户余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：1000",
           " [重放攻击]查看账户余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：0",
            "[重放攻击]获取交易nounce值<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d的交易nounce值为：0",
            "[重放攻击]发送交易<发送方 接收方 交易金额 验证字符串>0x239fe5de1acb07e87a871d4c474bc3b05370431d向0x97d495fb16f6945d3bbab472f9d1448430786ecc发送交易，交易金额为100",
"[重放攻击]查看账户余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：100",
            "[重放攻击]发送交易<发送方 接收方 交易金额 验证字符串 nounce值>0x239fe5de1acb07e87a871d4c474bc3b05370431d向0x97d495fb16f6945d3bbab472f9d1448430786ecc发送交易，0x97d495fb16f6945d3bbab472f9d1448430786ecc使用nounce值重放此次交易，，，，，，，，，，，，，，success!",
           " [重放攻击]查看账户余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：200，，，，，，，，，，，，，，，，，，，，，，，Attack Success!"
};
}
