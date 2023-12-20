package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Timestamppt {
    BigInteger Alice_initial_balance;
    BigInteger Bob_initial_balance;
    BigInteger Bob_final_balance;
    String []log={" "
            ,"=========时间戳依赖攻击防护=========","[时间戳依赖攻击防护模块]查看余额<账户持有人>0x239fe5de1acb07e87a871d4c474bc3b05370431d账户余额为：8000",
            "[时间戳依赖攻击防护模块]查看余额<账户持有人> 0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：0",
           " [时间戳依赖攻击防护模块]进行时间戳依赖攻击防护<被攻击对象 攻击者 检测时间戳>对时间戳依赖进行防护后，Bob再次向0x239fe5de1acb07e87a871d4c474bc3b05370431d发起时间戳依赖攻击",
"[时间戳依赖攻击防护模块]查看余额<账户持有人> 0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：0 （防护成功!）"};
}
