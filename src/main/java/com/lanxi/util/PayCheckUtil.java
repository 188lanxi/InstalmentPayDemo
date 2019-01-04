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
        String thirdPrivateKey=ProperrtiesUtil.get("thirdPrivateKey");
        Base64 base64 = new Base64();
        TreeMap<String, String> treeMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String value = entry.getValue();
            value=value.replaceAll(" ","+");
            //解密
            byte[] decrypt = RSAHexKey.decrypt(thirdPrivateKey, base64.decode(value));
            treeMap.put(entry.getKey(), new String(decrypt));
        }
        String sign=treeMap.get("sign");
        String toSign = SignUtil.toSign(treeMap);
        if (!sign.equals(toSign)) {
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
        String lanxiPublicKey=ProperrtiesUtil.get("lanxiPublicKey");
        Base64 base64 = new Base64();
        TreeMap<String, String> treeMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : Map.entrySet()) {
            treeMap.put(entry.getKey(),entry.getValue());
        }
        String sign = SignUtil.toSign(treeMap);
        treeMap.put("sign", sign);
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            //加密
            byte[] decrypt = RSAHexKey.encrypt(lanxiPublicKey, value.getBytes());
            treeMap.put(entry.getKey(),new String(base64.encode(decrypt)));
        }
        return treeMap;
    }
}
