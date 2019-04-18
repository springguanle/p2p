package com.bjpowernode.p2p.pay.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhangguanle
 * @create 2019-01-23 16:23
 */
public class PayConfig {

    @Value("${alipayGatewayUrl}")
    private String alipayGatewayUrl;

    public String getAlipayGatewayUrl() {
        return alipayGatewayUrl;
    }

    public void setAlipayGatewayUrl(String alipayGatewayUrl) {
        this.alipayGatewayUrl = alipayGatewayUrl;
    }

    public String getAlipayAppid() {
        return alipayAppid;
    }

    public void setAlipayAppid(String alipayAppid) {
        this.alipayAppid = alipayAppid;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAlipayFormat() {
        return alipayFormat;
    }

    public void setAlipayFormat(String alipayFormat) {
        this.alipayFormat = alipayFormat;
    }

    public String getAplipayCharset() {
        return aplipayCharset;
    }

    public void setAplipayCharset(String aplipayCharset) {
        this.aplipayCharset = aplipayCharset;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getAlipaySignType() {
        return alipaySignType;
    }

    public void setAlipaySignType(String alipaySignType) {
        this.alipaySignType = alipaySignType;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Value("${alipayAppid}")
    private String alipayAppid;
    @Value("${appPrivateKey}")
    private String appPrivateKey;
    @Value("${alipayFormat}")
    private String alipayFormat;
    @Value("${aplipayCharset}")
    private String aplipayCharset;
    @Value("${alipayPublicKey}")
    private String alipayPublicKey;
    @Value("${alipaySignType}")
    private String alipaySignType;
    @Value("${returnUrl}")
    private String returnUrl;
}
