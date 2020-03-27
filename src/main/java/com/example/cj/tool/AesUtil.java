package com.example.cj.tool;

import org.springframework.util.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法
    private static final String CHARSET_NAME = "utf-8";

    public static String encrypt(String content, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
        byte[] byteContent = content.getBytes(CHARSET_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);// 加密
        return Base64Utils.encodeToString(result);//通过Base64转码返回
    }

    public static String decrypt(String content, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        //实例化
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));
        return new String(result, CHARSET_NAME);
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    public static Key getSecretKey() throws NoSuchAlgorithmException {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES 要求密钥长度为 128
        kg.init(128, new SecureRandom());
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    }

    public static String convertKeyString(Key secretKey) {
        return Base64Utils.encodeToString(secretKey.getEncoded());
    }

    public static Key convertSecretKey(String secretKeyString) {
        byte[] bytes = Base64Utils.decodeFromString(secretKeyString);
        return new SecretKeySpec(bytes, KEY_ALGORITHM);
    }
}
