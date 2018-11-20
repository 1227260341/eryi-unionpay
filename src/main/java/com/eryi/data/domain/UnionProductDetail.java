package com.eryi.data.domain;

import java.io.Serializable;

import java.io.Serializable;

/**
 * <p>
 * 银联产品详情表
 * </p>
 *
 * @author zhouzhenjang123
 * @since 2018-11-15
 */
public class UnionProductDetail {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 商户号码
     */
    private String merId;
    /**
     * 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
     */
    private String orderId;
    /**
     * 接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
     */
    private String accessType;
    /**
     * 订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
     */
    private String txnTime;
    /**
     * 交易金额 单位为分，不能带小数点
     */
    private String txnAmt;
    /**
     * 境内商户固定 156 人民币
     */
    private String currencyCode;
    /**
     * 签名方法
     */
    private String signMethod;
    /**
     * 交易类型 01:消费
     */
    private String txnType;
    /**
     * 交易子类 07：申请消费二维码
     */
    private String txnSubType;
    /**
     * 填写000000
     */
    private String bizType;
    /**
     * 渠道类型 08手机
     */
    private String channelType;
    
    private String qrCode;
    
    /**
     * 异步通知地址
     */
    private String backUrl;
   

	/**
     * 前端响应跳转地址
     */
    private String frontUrl;
    /**
     * 商户自定义保留域，交易应答时会原样返回（url--base64)
     */
    private String reqReserved;

    public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnSubType() {
        return txnSubType;
    }

    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    @Override
    public String toString() {
        return "UnionProductDetail{" +
        "id=" + id +
        ", merId=" + merId +
        ", orderId=" + orderId +
        ", accessType=" + accessType +
        ", txnTime=" + txnTime +
        ", txnAmt=" + txnAmt +
        ", currencyCode=" + currencyCode +
        ", signMethod=" + signMethod +
        ", txnType=" + txnType +
        ", txnSubType=" + txnSubType +
        ", bizType=" + bizType +
        ", channelType=" + channelType +
        ", backUrl=" + backUrl +
        ", frontUrl=" + frontUrl +
        ", reqReserved=" + reqReserved +
        "}";
    }
}
