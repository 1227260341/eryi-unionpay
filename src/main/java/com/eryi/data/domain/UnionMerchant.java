package com.eryi.data.domain;

import java.io.Serializable;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 银联商户
 * </p>
 *
 * @author zhouzhenjang123
 * @since 2018-11-19
 */
public class UnionMerchant {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 应用id（暂时）
     */
    private Integer appId;
    /**
     * 商户号
     */
    private String number;
    /**
     * 创建时间
     */
    private Date makeTime;
    /**
     * Y 有效， N 无效
     */
    private String validFlag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Date makeTime) {
        this.makeTime = makeTime;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    @Override
    public String toString() {
        return "UnionMerchant{" +
        "id=" + id +
        ", appId=" + appId +
        ", number=" + number +
        ", makeTime=" + makeTime +
        ", validFlag=" + validFlag +
        "}";
    }
}
