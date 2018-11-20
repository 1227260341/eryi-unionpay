package com.eryi.data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eryi.data.domain.UnionMerchant;
import com.eryi.data.domain.UnionPay;
import com.eryi.data.domain.UnionProductDetail;
import com.eryi.data.mapper.UnionMerchantMapper;
import com.eryi.data.mapper.UnionPayMapper;
import com.eryi.data.mapper.UnionProductDetailMapper;
import com.eryi.data.util.DemoBase;
import com.eryi.data.util.PropertiesUtil;
import com.eryi.data.util.sdk.union.AcpService;
import com.eryi.data.util.sdk.union.LogUtil;
import com.eryi.data.util.sdk.union.SDKConfig;
import com.eryi.data.util.sdk.union.SDKConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/QRC/consumer")
public class QRCCousumerController {
	
	@Resource
	private UnionProductDetailMapper unionProductDetailMapper;
	@Resource 
	private UnionMerchantMapper unionMerchantMapper;
	@Resource 
	private UnionPayMapper unionPayMapper;
	

	/**
	 * reqReserved ANS1..1024  无 O-选填  商户自定义保留域，交易应答时会原样返回   url--base64
	 * 扫码消费（客户 - - 主扫）
	 * zj
	 * 2018年11月14日
	 */
	@RequestMapping("/getQRCode")
	public Object pay(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, Exception {
		String signCretPath = SDKConfig.getConfig().getSignCertPath();
		if (signCretPath == null || "".equals(signCretPath)) {
			SDKConfig.getConfig().loadPropertiesFromSrc1(); //从classpath加载acp_sdk.properties文件
		}
		
		Map resultMap = new HashMap<>();
		String appId = req.getParameter("appId");
		String txnAmt = req.getParameter("txnAmt");
		String orderId = req.getParameter("orderId");
		String reqReserved = req.getParameter("reqReserved");//商户自定义保留域，交易应答时会原样返回    路径进行base64加密
		
		if (PropertiesUtil.existEmptyOrNullParams(appId, txnAmt, orderId)) {
			resultMap.put("code", 405);
			resultMap.put("message", "请求参数错误！");
			return resultMap;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate = new Date();
		String txnTime = sdf.format(nowDate);
		reqReserved = new String(Base64.decodeBase64(reqReserved));
		
		//判断时间是都大于24小时 。是的话，则重新进行请求，否则的话，则直接获取二维码地址返回
		UnionProductDetail upd = unionProductDetailMapper.getByOrderId(orderId);
		if (upd != null) {
			String startTime = upd.getTxnTime();//订单发送时间
			Date startDate = sdf.parse(startTime);
			long difference = nowDate.getTime() - startDate.getTime();
			System.out.println("时间差 毫秒：" + difference);
			double result = difference * 1.0 / (1000 * 60 * 60);
			if (result < 24) {
				resultMap.put("code", 200);
				resultMap.put("data", upd.getQrCode());
				resultMap.put("message", "请求成功！");
				return resultMap;
			}
		}
		
		//获取商户号
		UnionMerchant um = unionMerchantMapper.getByAppId(Integer.parseInt(appId));
		if (um == null) {
			resultMap.put("code", 400);
			resultMap.put("message", "非法请求！");
			return resultMap;
		}
		
		String merId = um.getNumber();
		
		Map<String, String> contentData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);            //版本号 全渠道默认值
		contentData.put("encoding", DemoBase.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		contentData.put("txnType", "01");              		 	//交易类型 01:消费
		contentData.put("txnSubType", "07");           		 	//交易子类 07：申请消费二维码
		contentData.put("bizType", "000000");          		 	//填写000000
		contentData.put("channelType", "08");          		 	//渠道类型 08手机

		/***商户接入参数***/
		contentData.put("merId", merId);   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		contentData.put("accessType", "0");            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
		contentData.put("orderId", orderId);        	 	    //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("txnTime", txnTime);		 		    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("txnAmt", txnAmt);						//交易金额 单位为分，不能带小数点
		contentData.put("currencyCode", "156");                 //境内商户固定 156 人民币

		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		contentData.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		contentData.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
					
		//后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", DemoBase.backUrl);
		
		contentData.put("reqReserved", reqReserved);//商户自定义保留域，交易应答时会原样返回
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestAppUrl = SDKConfig.getConfig().getBackRequestUrl();								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,DemoBase.encoding);  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//成功,获取tn号
					//String tn = resmap.get("tn");
					//TODO
					
					String qrCode = rspData.get("qrCode");//二维码地址
					resultMap.put("code", 200);
					resultMap.put("data", qrCode);
					resultMap.put("message", "请求成功！");
					
//					UnionProductDetail upd = new UnionProductDetail();
					//upd.setAccessType(accessType);
//					UnionProductDetail unionProductDetail = (UnionProductDetail) contentData;
					contentData.put("qrCode", qrCode);
					unionProductDetailMapper.addForMap(contentData);
					
				}else{
					//其他应答码为失败请排查原因或做失败处理
					//TODO
					String qrCode = rspData.get("qrCode");//二维码地址
					String respMsg = rspData.get("respMsg");//二维码地址
					resultMap.put("code", respCode);
					resultMap.put("message", respMsg);
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
				resultMap.put("code", 500);
				resultMap.put("message", "验证签名失败！");
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			resultMap.put("code", 500);
			resultMap.put("message", "未获取到返回报文或返回http状态码非200！");
		}
		String reqMessage = DemoBase.genHtmlResult(reqData);
		String rspMessage = DemoBase.genHtmlResult(rspData);
//		resp.getWriter().write("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
		return resultMap;
	}
	
	/**
	 * 支付成功--银联异步通知时的接收地址
	 * zj
	 * 2018年11月13日
	 */
	@RequestMapping("/backRcvResponse")
	public void backRcvResponse(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");
		
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);
		LogUtil.printRequestLog(reqParam);
		
		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(reqParam, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
			
			String orderId =reqParam.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
			String queryId =reqParam.get("queryId");//流水号
			String respCode = reqParam.get("respCode");
			String merId = reqParam.get("merId");
			String reqReserved = reqParam.get("reqReserved");// 商户保留域 原样返回
			String uri = req.getRequestURI();
			
			UnionPay up = unionPayMapper.getByQueryId(queryId);
			reqParam.put("status", "1");
			if (up == null) {
				unionPayMapper.addForMap(reqParam);
			} else {
				reqParam.put("id", up.getId() + "");
				unionPayMapper.updateForMap(reqParam);
			}
			
			System.out.println("request  uri =" + uri);
			String backUrl = new String(Base64.decodeBase64(reqReserved)) + "?" + uri;
			System.out.println("request backUrl =" + backUrl);
			ResponseEntity<String> response = new RestTemplate().exchange(backUrl, HttpMethod.GET,
					new HttpEntity<>(null, new HttpHeaders()), String.class);
			
			String result = response.getBody();
			System.out.println("result:" + result);
			if (result.equals("ok")) {//说名异步通知成功
				//TODO成功后的操作逻辑处理
				
			} else {
				//TODO 未通知成功操作逻辑处理
				
			}
			
			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
			
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
		//返回给银联服务器http 200  状态码
		resp.getWriter().print("ok");
	}
	
	/**
	 * 支付后--前端跳转页面（即按返回商户后）
	 * zj
	 * 2018年11月13日
	 */
	@RequestMapping("/frontRcvResponse")
	public void frontRcvResponse(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

		String encoding = req.getParameter(SDKConstants.param_encoding);
		LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
		String pageResult = "";
		if (DemoBase.encoding.equalsIgnoreCase(encoding)) {
			pageResult = "/utf8_result.jsp";
		} else {
			pageResult = "/gbk_result.jsp";
		}
		Map<String, String> respParam = getAllRequestParam(req);

		// 打印请求报文
		LogUtil.printRequestLog(respParam);

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet()
					.iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes(encoding), encoding);
				page.append("<tr><td width=\"30%\" align=\"right\">" + key
						+ "(" + key + ")</td><td>" + value + "</td></tr>");
				valideData.put(key, value);
			}
		}
		if (!AcpService.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
			LogUtil.writeLog("验证签名结果[失败].");
		} else {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
			LogUtil.writeLog("验证签名结果[成功].");
			System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
			
			String respCode = valideData.get("respCode");
			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
		}
		req.setAttribute("result", page.toString());
		req.getRequestDispatcher(pageResult).forward(req, resp);

		LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
	}
	
	/**
	 * 获取请求参数中所有的信息
	 * 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
	 * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}
	
	/**
	  * 获取请求参数中所有的信息。
	  * 非struts可以改用此方法获取，好处是可以过滤掉request.getParameter方法过滤不掉的url中的参数。
	  * struts可能对某些content-type会提前读取参数导致从inputstream读不到信息，所以可能用不了这个方法。理论应该可以调整struts配置使不影响，但请自己去研究。
	  * 调用本方法之前不能调用req.getParameter("key");这种方法，否则会导致request取不到输入流。
	  * @param request
	  * @return
	  */
	 public static Map<String, String> getAllRequestParamStream(
	   final HttpServletRequest request) {
		  Map<String, String> res = new HashMap<String, String>();
		  try {
		   String notifyStr = new String(IOUtils.toByteArray(request.getInputStream()),DemoBase.encoding);
		   LogUtil.writeLog("收到通知报文：" + notifyStr);
		   String[] kvs= notifyStr.split("&");
		   for(String kv : kvs){
		    String[] tmp = kv.split("=");
		    if(tmp.length >= 2){
		     String key = tmp[0];
		     String value = URLDecoder.decode(tmp[1],DemoBase.encoding);
		     res.put(key, value);
		    }
		   }
		  } catch (UnsupportedEncodingException e) {
		   LogUtil.writeLog("getAllRequestParamStream.UnsupportedEncodingException error: " + e.getClass() + ":" + e.getMessage());
		  } catch (IOException e) {
		   LogUtil.writeLog("getAllRequestParamStream.IOException error: " + e.getClass() + ":" + e.getMessage());
		  }
		  return res;
	 }
	
}
