package com.example.cj.tool;

import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AesUtilTest {

    @Test
    void encrypt() throws Exception {
        Key secretKey = AesUtil.getSecretKey();
        String content = "hello";
        String encryptContent = AesUtil.encrypt(content, secretKey);
        String secretKeyString = AesUtil.convertKeyString(secretKey);
        Key secretKeySpec = AesUtil.convertSecretKey(secretKeyString);
        String decryptContent = AesUtil.decrypt(encryptContent, secretKeySpec);
        assertEquals(content, decryptContent);
    }

}