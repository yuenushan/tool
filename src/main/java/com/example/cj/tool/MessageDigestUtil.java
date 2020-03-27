package com.example.cj.tool;

import org.springframework.util.Base64Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil {

    public static final String MD2_ALGORITHM = "MD2";
    public static final String MD5_ALGORITHM = "MD5";
    public static final String SHA1_ALGORITHM = "SHA-1";
    public static final String SHA256_ALGORITHM = "SHA-256";
    public static final String SHA_384_ALGORITHM = "SHA-384";
    public static final String SHA_512_ALGORITHM = "SHA-512";


    public static String hash(String input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance(algorithm);
        byte[] result = mDigest.digest(input.getBytes());
        return Base64Utils.encodeToString(result);
    }
}
