package com.web3.degovern.entity;


import lombok.Data;
import java.math.BigInteger;
import java.security.KeyPair;

@Data
public class User {
    private BigInteger ID;
    private String pk;//公钥、私钥、用户地址
    private String userName;
    private BigInteger reputation;
    private BigInteger trustEvent;
    private BigInteger distrustEvent;
    private BigInteger level;//暂定为整型
}
