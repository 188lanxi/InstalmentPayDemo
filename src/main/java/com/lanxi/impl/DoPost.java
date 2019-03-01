package com.lanxi.impl;

import com.alibaba.fastjson.JSON;
import com.lanxi.util.HttpUtils;
import com.lanxi.util.PayCheckUtil;
import com.lanxi.util.ProperrtiesUtil;
import com.lanxi.util.TimeAssist;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiaobo on 2019/1/3 0003.
 */
public class DoPost {
    static final Logger log = Logger.getLogger(DoPost.class);

    /**
     * 调起支付
     *
     * @param orderNo
     * @param payPrice
     * @param frontCallbackUrl
     * @param orderTime
     * @param secondaryAccount
     * @param extendParam
     * @param custId
     * @param periods
     * @return
     */
    public static Map<String, String> invokePay(String orderNo,
                                                String payPrice,
                                                String frontCallbackUrl,
                                                String orderTime,
                                                String secondaryAccount,
                                                String extendParam,
                                                String custId,
                                                String periods) throws Exception {
        String path = ProperrtiesUtil.get("url") + ProperrtiesUtil.get("invokePayPath");
        Map<String, String> map = new HashMap<>();
        map.put("channelNo", ProperrtiesUtil.get("channelNo"));
        map.put("frontCallbackUrl", frontCallbackUrl);
        map.put("extendParam", extendParam); //扩展字段
        map.put("orderNo", orderNo);
        map.put("payPrice", payPrice);
        map.put("orderTime", orderTime);
        map.put("requestTime", TimeAssist.todayFull());
        map.put("custId", custId);
        if (periods != null && !periods.isEmpty())
            map.put("periods", periods);
        if (secondaryAccount != null && !secondaryAccount.isEmpty())
            map.put("secondaryAccount", secondaryAccount);
        log.info("调起支付参数:" + map);
        Map<String, String> encrypt = PayCheckUtil.encrypt(map);
        log.info("调起支付加密后参数:" + encrypt);
        String response = HttpUtils.sendPost(path, encrypt, "utf-8");
        log.info("支付结果未解密:" + response);
        Map<String, String> parse = (Map<String, String>) JSON.parse(response);
        //解密支付结果
        Map<String, String> decode = PayCheckUtil.decode(parse);
        log.info("支付结果:" + decode);
        return decode;
    }

    /**
     * 订单状态查询
     *
     * @param orderNo
     * @param secondaryAccount
     * @return
     */
    public static Map<String, String> orderQuery(String orderNo,
                                                 String secondaryAccount) throws Exception {
        String path = ProperrtiesUtil.get("url") + ProperrtiesUtil.get("orderQueryPath");
        Map<String, String> map = new HashMap<>();
        map.put("channelNo", ProperrtiesUtil.get("channelNo"));
        map.put("orderNo", orderNo);
        map.put("requestTime", TimeAssist.todayFull());
        if (secondaryAccount != null && !secondaryAccount.isEmpty())
            map.put("secondaryAccount", secondaryAccount);
        log.info("查询订单状态参数:" + map);
        Map<String, String> encrypt = PayCheckUtil.encrypt(map);
        String response = HttpUtils.sendPost(path, encrypt, "utf-8");
        Map<String, String> parse = (Map<String, String>) JSON.parse(response);
        //解密支付结果
        Map<String, String> decode = PayCheckUtil.decode(parse);
        log.info("支付结果:" + decode);
        return decode;
    }

    /**
     * 订单冲正
     *
     * @param orderNo
     * @param correctPrice
     * @param secondaryAccount
     * @param newOrderNo
     * @return
     */
    public static Map<String, String> orderCorrect(String orderNo,
                                                   BigDecimal correctPrice,
                                                   String secondaryAccount,
                                                   String newOrderNo) throws Exception {
        String path = ProperrtiesUtil.get("url") + ProperrtiesUtil.get("orderCorrectPath");
        Map<String, String> map = new HashMap<>();
        map.put("channelNo", ProperrtiesUtil.get("channelNo"));
        map.put("requestTime", TimeAssist.todayFull());
        map.put("orderNo", orderNo);
        map.put("correctPrice", correctPrice.toPlainString());
        map.put("newOrderNo", newOrderNo);
        if (secondaryAccount != null && !secondaryAccount.isEmpty())
            map.put("secondaryAccount", secondaryAccount);
        log.info("冲正参数:" + map);
        Map<String, String> encrypt = PayCheckUtil.encrypt(map);
        String response = HttpUtils.sendPost(path, encrypt, "utf-8");
        Map<String, String> parse = (Map<String, String>) JSON.parse(response);
        //解密冲正结果
        Map<String, String> decode = PayCheckUtil.decode(parse);
        log.info("订单:" + orderNo + "冲正结果:" + decode);
        return decode;
    }

    /**
     * 订单签收
     *
     * @param orderNo
     * @param lodgmentDate
     * @param secondaryAccount
     * @return
     */
    public static Map<String, String> orderLodgment(String orderNo,
                                                    String lodgmentDate,
                                                    String secondaryAccount) throws Exception {
        String path = ProperrtiesUtil.get("url") + ProperrtiesUtil.get("orderLodgmentPath");
        Map<String, String> map = new HashMap<>();
        map.put("channelNo", ProperrtiesUtil.get("channelNo"));
        map.put("requestTime", TimeAssist.todayFull());
        map.put("orderNo", orderNo);
        map.put("lodgmentDate", lodgmentDate);
        if (secondaryAccount != null && !secondaryAccount.isEmpty())
            map.put("secondaryAccount", secondaryAccount);
        log.info("签收参数:" + map);
        Map<String, String> encrypt = PayCheckUtil.encrypt(map);
        String response = HttpUtils.sendPost(path, encrypt, "utf-8");
        Map<String, String> parse = (Map<String, String>) JSON.parse(response);
        //解密冲正结果
        Map<String, String> decode = PayCheckUtil.decode(parse);
        log.info("签收结果:" + decode);
        return decode;
    }

    /**
     * 订单退款
     *
     * @param orderNo
     * @param refundPrice
     * @param secondaryAccount
     * @param newOrderNo
     * @return
     * @throws Exception
     */
    public static Map<String, String> orderRefund(String orderNo,
                                                  BigDecimal refundPrice,
                                                  String secondaryAccount,
                                                  String newOrderNo) throws Exception {
        String path = ProperrtiesUtil.get("url") + ProperrtiesUtil.get("orderRefundPath");
        Map<String, String> map = new HashMap<>();
        map.put("channelNo", ProperrtiesUtil.get("channelNo"));
        map.put("requestTime", TimeAssist.todayFull());
        map.put("orderNo", orderNo);
        map.put("refundPrice", refundPrice.toPlainString());
        map.put("newOrderNo", newOrderNo);
        if (secondaryAccount != null && !secondaryAccount.isEmpty())
            map.put("secondaryAccount", secondaryAccount);
        log.info("退款参数:" + map);
        Map<String, String> encrypt = PayCheckUtil.encrypt(map);
        String response = HttpUtils.sendPost(path, encrypt, "utf-8");
        Map<String, String> parse = (Map<String, String>) JSON.parse(response);
        //解密冲正结果
        Map<String, String> decode = PayCheckUtil.decode(parse);
        log.info("退款结果:" + decode);
        return decode;
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> map = invokePay(TimeAssist.todayFull(), "1000", "http://www.baidu.com", TimeAssist.todayFull(), null, "",
                "123456", null);
        System.out.println(map);
//        Map<String, String> map = orderQuery("123456", null);
//        Map<String, String> map = orderCorrect("123456", new BigDecimal("100"), null, "c123456");
//        Map<String, String> map = orderLodgment("123456", "20190104091000", null);
//        Map<String, String> map = orderRefund("123456", new BigDecimal("100"), null, "t123456");
    }
}
