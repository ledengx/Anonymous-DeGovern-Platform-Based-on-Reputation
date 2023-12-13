package com.web3.degovern.entity;


import lombok.Data;
import java.math.BigInteger;


@Data
public class User {
    private BigInteger ID;
    private String userName;
    private String userAddress;
    private BigInteger reputation;
    private BigInteger level;//暂定为整型
}
