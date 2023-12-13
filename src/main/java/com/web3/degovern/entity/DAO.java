package com.web3.degovern.entity;


import lombok.Data;

import java.math.BigInteger;

@Data
public class DAO {
    private BigInteger ID;
    private String name;
    private String content;
    private String DAOContract;
    private BigInteger reputationBaseline;//治理声誉门槛
}
