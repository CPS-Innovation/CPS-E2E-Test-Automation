package com.cps.fct.e2e.utils.common;


import java.util.Base64;

public class SecurePassCode {

    public static String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decode(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes);
    }


    public static void main(String[] args) {
        String original = "passwordCode";
        String encoded = SecurePassCode.encode(original);
        System.out.println("Encoded: " + encoded);
    }

}



