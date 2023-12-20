package com.web3.degovern.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Shunxupt {
    BigInteger Bob_final_balance;
    String []log={" "
            ,"========交易顺序依赖攻击防护======="
            ,            "[交易顺序依赖攻击防护]设置初始金额<账户持有人 金额数量>0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74账户初始金额已设置为：10",
            "[交易顺序依赖攻击防护]进行交易顺序依赖攻击防护<转账账户>启动交易顺序依赖防护，增加时间参数，并让0x9a22d447fb66a3d8d6ffcdf83c379229a2dfad74再次发起交易顺序依赖攻击",
"[交易顺序依赖攻击防护]查看余额<账户持有人>0x97d495fb16f6945d3bbab472f9d1448430786ecc账户余额为：11  （防护成功！）"};
}
