package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class Shuanghuaak {

    private BigInteger Alice_balance;
    private BigInteger Bob_balance;
    private BigInteger Cim_balance;


    private String []log={" "
            ,"===========双花攻击=========="
            ,"[双花攻击模块]设置初始金额 <账户持有人 金额数量>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户初始金额已设置为：8000",
            "[双花攻击模块]查看余额<账户持有人> 0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：8000",
            "[双花攻击模块]进行购物消费 <消费人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额-100",
            "[双花攻击模块]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户余额为：0",
            "[双花攻击模块]发动双花攻击<攻击者 算力>0x239fe5de1acb07e87a871d4c474bc3b05370431d以51算力发动双花攻击",
            "[双花攻击模块]查看余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：7900",
            "[双花攻击模块]查看余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：0（商家）",
            "[双花攻击模块]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户余额为：100（攻击成功！）"};
}
