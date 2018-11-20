package com.eryi.data.domain;
public enum PayResultStatus {

	OK(200, "OK"),
	RESULT_0(0,"成功"),
	//RESULT_A6(A6,"有缺陷的成功"),
	RESULT_1(1,"交易失败。详情请咨询95516"),
	RESULT_2(2,"系统未开放或暂时关闭，请稍后再试"),
	RESULT_3(3,"交易通讯超时，请发起查询交易"),
	RESULT_5(5,"交易已受理，请稍后查询交易结果"),
	RESULT_6(6,"系统繁忙，请稍后再试"),
	RESULT_10(10,"报文格式错误"),
	RESULT_11(11,"验证签名失败"),
	RESULT_12(12,"重复交易"),
	RESULT_13(13,"报文交易要素缺失"),
	RESULT_14(14,"批量文件格式错误"),
	RESULT_30(30,"交易未通过，请尝试使用其他银联卡支付或联系95516"),
	RESULT_31(31,"商户状态不正确"),
	RESULT_32(32,"无此交易权限"),
	RESULT_33(33,"交易金额超限"),
	RESULT_34(34,"查无此交易"),
	RESULT_35(35,"原交易不存在或状态不正确"),
	RESULT_36(36,"与原交易信息不符"),
	RESULT_37(37,"已超过最大查询次数或操作过于频繁"),
	RESULT_38(38,"银联风险受限"),
	RESULT_39(39,"交易不在受理时间范围内"),
	RESULT_40(40,"绑定关系检查失败"),
	RESULT_41(41,"批量状态不正确，无法下载"),
	RESULT_42(42,"扣款成功但交易超过规定支付时间"),
	RESULT_43(43,"无此业务权限，详情请咨询95516"),
	RESULT_44(44,"输入号码错误或暂未开通此项业务，详情请咨询95516"),
	RESULT_45(45,"原交易已被成功退货或已被成功撤销"),
	RESULT_46(46,"交易已被成功冲正"),
	RESULT_60(60,"交易失败，详情请咨询您的发卡行"),
	RESULT_61(61,"输入的卡号无效，请确认后输入"),
	RESULT_62(62,"交易失败，发卡银行不支持该商户，请更换其他银行卡"),
	RESULT_63(63,"卡状态不正确"),
	RESULT_64(64,"卡上的余额不足"),
	RESULT_65(65,"输入的密码、有效期或CVN2有误，交易失败"),
	RESULT_66(66,"持卡人身份信息或手机号输入不正确，验证失败"),
	RESULT_67(67,"密码输入次数超限"),
	RESULT_68(68,"您的银行卡暂不支持该业务，请向您的银行或95516咨询"),
	RESULT_69(69,"您的输入超时，交易失败"),
	RESULT_70(70,"交易已跳转，等待持卡人输入"),
	RESULT_71(71,"动态口令或短信验证码校验失败"),
	RESULT_72(72,"您尚未在{}银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通或拨打{}"),
	RESULT_73(73,"支付卡已超过有效期"),
	RESULT_74(74,"扣款成功，销账未知"),
	RESULT_75(75,"扣款成功，销账失败"),
	RESULT_76(76,"需要验密开通"),
	RESULT_77(77,"银行卡未开通认证支付"),
	RESULT_78(78,"发卡行交易权限受限，详情请咨询您的发卡行"),
	RESULT_79(79,"此卡可用，但发卡行暂不支持短信验证"),
	RESULT_80(80,"交易失败，Token 已过期"),
	RESULT_81(81,"月累计交易笔数(金额)超限"),
	RESULT_82(82,"需要校验密码"),
	RESULT_83(83,"发卡行（渠道）处理中"),
	RESULT_85(85,"交易失败，营销规则不满足"),
	RESULT_86(86,"二维码状态错误"),
	RESULT_87(87,"支付次数超限"),
	RESULT_88(88,"无此二维码"),
	RESULT_89(89,"无此Token、TR状态无效或者Token状态无效"),
	RESULT_90(90,"账户余额不足"),
	RESULT_91(91,"认证失败"),
	RESULT_92(92,"营业执照过期"),
	RESULT_93(93,"营业执照吊销"),
	RESULT_94(94,"营业执照注销"),
	RESULT_95(95,"营业执照迁出"),
	RESULT_96(96,"营业执照撤销"),
	RESULT_98(98,"文件不存在"),
	RESULT_99(99,"通用错误");
	
	private int code;
	private String message;
	
	private PayResultStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}