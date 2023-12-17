package com.web3.degovern.utils.crypto;

import java.lang.System;
import java.security.MessageDigest;
import java.util.Formatter;

public class Crypto {

    private static final String MD5_ALGORITHM = "MD5";
    public static String encrypt(String data) throws Exception {
        // 获取MD5算法实例
        MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM);
        // 计算散列值
        byte[] digest = messageDigest.digest(data.getBytes());
        Formatter formatter = new Formatter();
        // 补齐前导0，并格式化
        for (byte b : digest) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static void main(String[] args) throws Exception {
        Integer num  = 0;
        String data = num.toString();
        String encryptedData = encrypt(data);
        System.out.println("加密后的数据：" + encryptedData);
    }


}
