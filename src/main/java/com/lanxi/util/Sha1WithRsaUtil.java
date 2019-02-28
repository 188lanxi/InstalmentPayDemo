package com.lanxi.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by wuxiaobo on 2019/1/14 0014.
 */
public class Sha1WithRsaUtil {

    //生成密钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024); //可以理解为：加密后的密文长度，实际原文要小些 越大 加密解密越慢
        KeyPair keyPair = keyGen.generateKeyPair();
        return keyPair;
    }

    //用sha1生成内容摘要，再用RSA的私钥加密，进而生成数字签名
    public static String getSha1Sign(String content , String privateKey) throws Exception {
        PrivateKey key = getPrivateKey(privateKey);
        byte[] contentBytes = content.getBytes("utf-8");
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(key);
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return Base64.encodeBase64String(signs);
    }

    //对用md5和RSA私钥生成的数字签名进行验证
    public static boolean verifyWhenSha1Sign(String content, String sign, String publicKey) throws Exception {
        PublicKey key = getPublicKey(publicKey);
        byte[] contentBytes = content.getBytes("utf-8");
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(key);
        signature.update(contentBytes);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * String转公钥PublicKey
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * String转私钥PrivateKey
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static void main(String[] args)  throws Exception {
        String content = "这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!这是测试!";
        String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6Ok+2kTH4hlCD9aA9WZOctZC7cXWkvO8olpnznQkTdVeCi3ChhAASRWRPAWuS5tCSsO7HKMW9OJGw8JvGjbb/S4Zu8uyULo71cPDzCfKwwMdCVwscNCD1CV9vgQ6DV/MzAlgKF+mn/SvpjGTo3vfIo96800dfuw54SwCFG0kfNQIDAQAB";
        String privateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALo6T7aRMfiGUIP1oD1Zk5y1kLtxdaS87yiWmfOdCRN1V4KLcKGEABJFZE8Ba5Lm0JKw7scoxb04kbDwm8aNtv9Lhm7y7JQujvVw8PMJ8rDAx0JXCxw0IPUJX2+BDoNX8zMCWAoX6af9K+mMZOje98ij3rzTR1+7DnhLAIUbSR81AgMBAAECgYAUDBjur1yww0zBl9yDHjR8xzLoasNeU5RvSx8RkZMbjCM6g2ZBjOMxFFVDCAUbkEtdrzxVJjd7Mi7E3HH4guLzE2fk1mAIT555vH/lF7RPKBsrpJA5TfMLgSa/4x9qc2xnkG69M7hg6A7LVYcKioQ8gjwN/Xyv2ENLS32fm4zydQJBAO/KmCf6yvas06NASajPsj2OazSlGqIyaPir3TUGL5BKkC7W82gIWXRBuLjFqMSrwA2QEdjpBm1kZzo7hbI/x0cCQQDG0NcJeHS4WDsrJAfY5zxUVAcmnM2jVioVxbaWhHEkMbCe814U8+YfjNkcZhjSGGWuRakk3x3w1sBMykFx8VujAkBWEy49I6xZkb5CmqAWRb/gks/uzNlboabGpMK59ud57xoEhkpHbL/XmIPbZ7BmMJS3CaEucK9K80COybD7RCOfAkBGRXJ8CnNW5Pik2utbIwAT3k9YunuEi+P8JhHPSaij3lxLEkHirgJcjHAAtaV4PN9TpZOPUL1Ibt8xRRu0plZ5AkBRqIRfW7dxrYkMogsRZsAME8qlc6P0z2AsOZNIliQqjiVWtvfhcvTKi+Ts9uK2WO8RehkyZ23ejLgVFacpKQuu";
        System.out.println("content :"+content);
        System.out.println("长度 :"+content.length());
        String sha1Sign  = getSha1Sign(content,privateKey);
        System.out.println("sign with sha1 签名结果 :"+ sha1Sign);
        boolean sha1Verifty = verifyWhenSha1Sign(content,sha1Sign,publicKey);
        System.out.println("verify sign with sha1 验签结果 :"+ sha1Verifty);

    }
}
