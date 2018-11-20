package com.eryi.data.domain;

import java.io.Serializable;

import java.io.Serializable;

/**
 * <p>
 * 银联支付记录
 * </p>
 *
 * @author zhouzhenjang123
 * @since 2018-11-15
 */
public class UnionPay {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 消费交易的流水号，供后续查询用
     */
    private String queryId;
    /**
     * 商户号码
     */
    private String merId;
    /**
     * 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
     */
    private String orderId;
    /**
     * traceTime，格式为YYYYMMDDhhmmss
     */
    private String traceTime;
    /**
     * 0 失败， 1 成功
     */
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
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

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "UnionPay{" +
        "id=" + id +
        ", queryId=" + queryId +
        ", merId=" + merId +
        ", orderId=" + orderId +
        ", traceTime=" + traceTime +
        ", status=" + status +
        "}";
    }
}
