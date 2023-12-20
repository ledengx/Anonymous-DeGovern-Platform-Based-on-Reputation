package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Chongruak {
    BigInteger Bob_balanceC;
    BigInteger Bob_balance;
    BigInteger Alice_balance;
    BigInteger Bob_final_balanceC;
    BigInteger Bob_final_balance;
    BigInteger Alice_final_balance;
    String []log={" "
            ,"===========重入攻击=========="
            ,"[重入攻击]设置初始金额<账户持有人 金额数量>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户初始金额已设置为：10",
            "[重入攻击]设置初始金额<账户持有人 金额数量>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户初始金额已设置为：10",
            "[重入攻击]设置初始金额<账户持有人 金额数量>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74私人账户初始金额已设置为：0",
            "[重入攻击]查看余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：10",
            "[重入攻击]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户余额为：10",
            "[重入攻击]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74私人账户余额为：0",
            "[重入攻击]发起重入攻击<攻击者 被攻击者 单词转账金额>Bob发起重入攻击，多次获取Alice的账户金额。当0x239fe5de1acb07e87a871d4c474bc3b05370431d给Bob转账后，自动触发回退函数，使得0x239fe5de1acb07e87a871d4c474bc3b05370431d多次转账，直至为0。",
            "[重入攻击]查看余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：0",
            "[重入攻击]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户余额为：10",
           " [重入攻击]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74私人账户余额为：10 ，，，，，，，，，，，，，，，，，，，，，，，，，Attack Success！"
};
}
