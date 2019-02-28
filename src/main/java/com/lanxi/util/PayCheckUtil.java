package com.lanxi.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiaobo on 2018/10/30 0030.
 */
public class PayCheckUtil {
     static final Logger log=Logger.getLogger(PayCheckUtil.class);

    /**
     * 参数解密并验证签名
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public static Map<String, String> decode(Map<String, String> paramMap) throws Exception {
        String thirdPublicKey=ProperrtiesUtil.get("thirdPublicKey");
        TreeMap<String, String> treeMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String value = entry.getValue();
            value=value.replaceAll(" ","+");
            //解密
            String decrypt = AesUtil.decrypt(value, ProperrtiesUtil.get("aesKey"));
            treeMap.put(entry.getKey(),decrypt);
        }
        String sign=treeMap.get("sign");

        if (!SignUtil.signVerify(treeMap,thirdPublicKey,sign)) {
            treeMap.put("respCode", "403");
            treeMap.put("respMsg", "签名错误!");
            return treeMap;
        }
        return treeMap;
    }

    /**
     * 加密
     *
     * @param Map
     * @return
     * @throws Exception
     */
    public static Map<String, String> encrypt(Map<String, String> Map) throws Exception {
        String lanxiPrivateKey=ProperrtiesUtil.get("lanxiPrivateKey");
        TreeMap<String, String> treeMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : Map.entrySet()) {
            treeMap.put(entry.getKey(),entry.getValue());
        }
        String sign = SignUtil.toSign(treeMap,lanxiPrivateKey);
        treeMap.put("sign", sign);
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            //加密
            String encrypt = AesUtil.encrypt(value, ProperrtiesUtil.get("aesKey"));
            treeMap.put(entry.getKey(),encrypt);
        }
        return treeMap;
    }
}
