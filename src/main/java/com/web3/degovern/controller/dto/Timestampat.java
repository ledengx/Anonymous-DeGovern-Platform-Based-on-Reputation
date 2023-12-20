package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Timestampat {
    BigInteger Alice_initial_balance;
    BigInteger Bob_initial_balance;
    BigInteger Alice_final_balance;
    BigInteger Bob_final_balance;
    String []log={" "
            ,"==========时间戳依赖攻击=========","[时间戳依赖攻击模块]设置初始金额<账户持有人 金额数量>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户初始金额已设置为：8000",
            "[时间戳依赖攻击模块]设置初始金额<账户持有人 金额数量>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户初始金额已设置为：0",
           " [时间戳依赖攻击模块]查看余额<账户持有人> 0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：0",
            "[时间戳依赖攻击模块]发起时间戳依赖攻击<被攻击对象 攻击者 时间戳>0x97d495fb16f6945d3bbab472f9d1448430786eccb向0x239fe5de1acb07e87a871d4c474bc3b05370431d发动时间戳依赖攻击",
"[时间戳依赖攻击模块]设置初始金额<账户持有人 金额数量>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户初始金额已设置为：7900",
           " [时间戳依赖攻击模块]设置初始金额<账户持有人 金额数量>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户初始金额已设置为：100 （攻击成功！）"};
}
