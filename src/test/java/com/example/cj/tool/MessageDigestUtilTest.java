package com.example.cj.tool;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

class MessageDigestUtilTest {

    @Test
    void hash() throws NoSuchAlgorithmException {
        System.out.println(MessageDigestUtil.hash("hello", MessageDigestUtil.SHA256_ALGORITHM));
    }
}