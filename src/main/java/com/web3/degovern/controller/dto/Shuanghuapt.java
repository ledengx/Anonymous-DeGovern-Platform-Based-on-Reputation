package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Shuanghuapt {
    BigInteger Alice_initial_balance;
    BigInteger Alice_final_balance;
    BigInteger Bob_final_balance;
    BigInteger Cim_final_balance;
    String []log= {" "
            ,"==========双花攻击防护=========","[双花攻击防护模块]设置初始金额 <账户持有人 金额数量>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户初始金额已设置为：8000",
            "[双花攻击防护模块]查看余额<账户持有人> 0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：8000",
            "[双花攻击防护模块]进行双花攻击防护<防护对象 时间锁定值>0x97d495fb16f6945d3bbab472f9d1448430786ecc将time-lock的值设定为15进行防护，并进行正常购物消费",
"[双花攻击防护模块]查看余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：7900",
            "[双花攻击防护模块]查看余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：100（商家）",
           " [双花攻击防护模块]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户余额为：0（防护成功!）",
            "[双花攻击防护模块]再次发动双花攻击<攻击者 时间锁定值>时间锁定值错误，交易失败！"};

}
