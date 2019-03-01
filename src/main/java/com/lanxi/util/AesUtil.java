package com.lanxi.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by wuxiaobo on 2019/2/22 0022.
 */
public class AesUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法
    private static final Logger log = LoggerFactory.getLogger(AesUtil.class);

    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @param password 密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return Base64.encodeBase64String(result);//通过Base64转码返回
        } catch (Exception ex) {
            log.info("AES加密发生异常",ex);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));

            return new String(result, "utf-8");
        } catch (Exception ex) {
            log.info("AES解密发生异常",ex);
        }
        return null;
    }

    /**
     * 随机生成秘钥
     */
    public static void getKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            //要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String s = byteToHexString(b);
            System.out.println(s);
            System.out.println("十六进制密钥长度为" + s.length());
            System.out.println("二进制密钥的长度为" + s.length() * 4);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
        }
    }

    /**
     * 使用指定的字符串生成秘钥
     */
    public static String getKeyByPass(String password) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            // kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            //SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以生成的秘钥就一样。
            kg.init(128, new SecureRandom(password.getBytes()));
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String s = byteToHexString(b);
            System.out.println(s);
            System.out.println("十六进制密钥长度为" + s.length());
            System.out.println("二进制密钥的长度为" + s.length() * 4);
            return s;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
            return null;
        }
    }

    /**
     * byte数组转化为16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);

            //AES 要求密钥长度为 128
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(password.getBytes());
            kg.init(128,secureRandom);
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            log.info("AES生成秘钥发生异常",ex);
        }

        return null;
    }

    public static void main(String[] args) {
//        String keyByPass = getKeyByPass("测试秘钥key");
//        System.out.println("生成秘钥:"+keyByPass);
        String keyByPass="7a8056db366e0846d03cb9c939701b4b";
//        String content="测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!测试测试这是测试!";
//        System.out.println("加密内容长度:"+content.length());
//        String encrypt = encrypt("300", keyByPass);
//        System.out.println("加密后:"+encrypt);
        //                                 LldffhRhXlPdJalQKK/4ug==
        String decrypt = decrypt("9+SFss8gd+TN12S3ZCT+Ig==", keyByPass);
        System.out.println("解密后:"+decrypt);

    }
}
