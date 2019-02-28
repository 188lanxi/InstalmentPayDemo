package com.lanxi.util;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * 生成签名工具类
 * Created by wuxiaobo on 2018/6/6 0006.
 */
public class SignUtil {

    /**
     * 生成签名
     *
     * @param map
     * @return
     */
    public static String toSign(TreeMap<String, String> map, String privateKey) throws Exception {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (!next.equals("sign")) {
                sb.append(next).
                        append("=").
                        append(map.get(next));
            }
        }
        String waitSign = sb.toString();
        String sign = Sha1WithRsaUtil.getSha1Sign(waitSign, privateKey);
        return sign;
    }

    /**
     * 验签
     *
     * @param map
     * @param publicKey
     * @return
     */
    public static boolean signVerify(TreeMap<String, String> map, String publicKey,String sign) throws Exception{
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (!next.equals("sign")) {
                sb.append(next).
                        append("=").
                        append(map.get(next));
            }
        }
        String waitSign = sb.toString();
        return Sha1WithRsaUtil.verifyWhenSha1Sign(waitSign,sign,publicKey);
    }
}
