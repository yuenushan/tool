package com.example.cj.tool;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RsaUtilTest {

    @Test
    void encrypt() throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        KeyPair keyPair = RsaUtil.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String content = "hello";
        String encryptContent = RsaUtil.encrypt(publicKey, "hello");
        String decryptContent = RsaUtil.decrypt(privateKey, encryptContent);
        assertEquals(content, decryptContent);
    }

    @Test
    public void testGenerateKeyPairFile() throws Exception {
        RsaUtil.generateKeyPairFile("/data");
    }
}