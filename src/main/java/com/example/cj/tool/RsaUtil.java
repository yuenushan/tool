package com.example.cj.tool;

import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtil {

    private static final String KEY_ALGORITHM = "RSA";
    public static final String PRIVATE_KEY_FILE_NAME = "id_rsa";
    public static final String PUBLIC_KEY_FILE_NAME = "id_rsa.pub";

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024, new SecureRandom());
        return keyPairGen.generateKeyPair();
    }

    public static void generateKeyPairFile(String path) throws NoSuchAlgorithmException, IOException {
        KeyPair keyPair = generateKeyPair();
        String privateKey = convertKeyString(keyPair.getPrivate());
        String publicKey = convertKeyString(keyPair.getPublic());
        writeFile(privateKey, String.valueOf(Paths.get(path, PRIVATE_KEY_FILE_NAME)));
        writeFile(publicKey, String.valueOf(Paths.get(path, PUBLIC_KEY_FILE_NAME)));
    }

    private static void writeFile(String content, String path) throws IOException {
        try ( FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(content);
        }
    }

    public static String convertKeyString(Key secretKey) {
        return Base64Utils.encodeToString(secretKey.getEncoded());
    }

    public static PrivateKey convertPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey convertPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    public static String encrypt(PublicKey publicKey, String plainText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] enBytes = cipher.doFinal(plainText.getBytes());
        return Base64Utils.encodeToString(enBytes);
    }

    public static String decrypt(PrivateKey privateKey, String enStr) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] deBytes = cipher.doFinal(Base64Utils.decodeFromString(enStr));
        return new String(deBytes);
    }

}
