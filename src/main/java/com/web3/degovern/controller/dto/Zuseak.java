package com.web3.degovern.controller.dto;

import lombok.Data;

@Data
public class Zuseak {
    String error;
    String []log={" "
            ,"==========中继阻塞攻击========="
            ,"[中继阻塞攻击]设置初始金额<账户持有人 金额数量>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户初始金额已设置为：10000",
            "[中继阻塞攻击]发起中继阻塞攻击<发送方 接收方 交易金额 交易次数>0x239fe5de1acb07e87a871d4c474bc3b05370431d向自己账户发送交易，每次转账金额为10，共转账30000次，，，，，，，，，，，，，，，，，，，，Attack Success!"
};
}
