package com.cpunisher.pedo.util;

import java.math.BigInteger;
import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CryptUtil {

    public String encryptMD5(String data) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(data.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public byte[] decrypt(String encryptedData, String iv, String sessionKey) {

        byte[] dataByte = Base64.decodeBase64(encryptedData);
        byte[] keyByte = Base64.decodeBase64(sessionKey);
        byte[] ivByte = Base64.decodeBase64(iv);

        Security.addProvider(new BouncyCastleProvider());

        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

        AlgorithmParameters parameters;
        byte[] resultByte = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

            parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

            resultByte = cipher.doFinal(dataByte);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultByte;
    }
}