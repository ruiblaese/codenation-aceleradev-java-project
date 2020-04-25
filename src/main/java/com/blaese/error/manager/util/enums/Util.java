package com.blaese.error.manager.util.enums;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.RandomStringUtils;

public class Util {
    public static String generateToken(){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        StringBuilder hexString = new StringBuilder();
        byte[] data = md.digest(RandomStringUtils.randomAlphabetic(10).getBytes());
        for (byte datum : data) {
            hexString.append(Integer.toHexString((datum >> 4) & 0x0F));
            hexString.append(Integer.toHexString(datum & 0x0F));
        }
        return hexString.toString();
    }
}
