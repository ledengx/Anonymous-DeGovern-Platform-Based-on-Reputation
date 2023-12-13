package com.web3.degovern.controller.dto;


import lombok.Data;

import java.math.BigInteger;

@Data
public class RegisterDTO {
    private BigInteger id;
    private String userName;
}
