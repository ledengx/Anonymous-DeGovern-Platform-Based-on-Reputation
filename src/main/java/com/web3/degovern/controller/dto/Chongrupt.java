package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Chongrupt {
    BigInteger Bob_final_balanceC;
    BigInteger Bob_final_balance;
    BigInteger Alice_final_balance;
    String []log={" "
            ,"==========重入攻击防护========="
            ,"[重入攻击防护]设置初始金额<账户持有人 金额数量>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户初始金额已设置为：10",
            "[重入攻击防护]设置初始金额<账户持有人 金额数量>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户初始金额已设置为：10",
            "[重入攻击防护]设置初始金额<账户持有人 金额数量>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74私人账户初始金额已设置为：0",
            "[重入攻击防护]进行重入攻击防护<攻击者 被攻击对象 单词转账金额>启动重入攻击防护，让转账逻辑变为金额优先减少，并让0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74再次发起重入攻击。",
            "[重入攻击防护]查看余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：10",
            "[重入攻击防护]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户余额为：10",
           " [重入攻击防护]查看余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：0 ，，，，，，，，，，，，，，，，，，，，，，，，，，Protect Success!"
};
}
