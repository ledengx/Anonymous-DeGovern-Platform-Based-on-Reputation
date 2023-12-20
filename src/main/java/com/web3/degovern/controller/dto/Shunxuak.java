package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Shunxuak {
    BigInteger Alice_initial_balance;
    BigInteger Bob_Gas;
    BigInteger Cim_Gas;
    BigInteger Cim_fianal_balance;
    BigInteger Bob_fianal_Balance;
    BigInteger Alice_final_Balance;
    String []log={" "
            ,"=========交易顺序依赖攻击========"
            ,"[交易顺序依赖攻击]设置初始金额<账户持有人 金额数量>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户初始金额已设置为：8000",
            "[交易顺序依赖攻击]查看余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：8000",
            "[交易顺序依赖攻击]发起交易顺序依赖攻击<转账账户>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74发起交易顺序依赖攻击，使自身转账交易先执行",
"[交易顺序依赖攻击]查看Gas值<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc的Gas值为：1",
"            [交易顺序依赖攻击]查看Gas值<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74的Gas值为：2",
         "   [交易顺序依赖攻击]查看余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：8999",
           " [交易顺序依赖攻击]查看余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：10",
            "[交易顺序依赖攻击]查看余额<账户持有人>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户余额为：11  （攻击成功！）"};

}
