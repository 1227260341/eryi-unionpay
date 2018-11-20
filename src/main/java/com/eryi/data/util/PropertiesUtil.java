package com.eryi.data.util;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.beanutils.PropertyUtilsBean;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.poi.ss.formula.functions.T;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;


/**
 * 获取 .properties配置  及判断null "" .... 
 *name 及 properties文件名
 * @author zj
 * 
 * 2017年9月25日
 */
public class PropertiesUtil {
	
	static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
			"十亿", "百亿", "千亿", "万亿" };
	static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
	
	
	/**
	 * 获取配置文件的 value
	 * zj
	 * 2017年10月24日
	 */
	public static String getValue(String key, String name){
		if (name ==null || "".equals(name)) {
			name = "baseconfig";
		}
		Properties prop = new Properties();
		InputStream in = new PropertiesUtil().getClass().getResourceAsStream("/" + name + ".properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
	/**
	 * 验证 string类型 
	 * zj
	 * 2017年10月24日
	 * 
	 */
	public static boolean verifyString(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证string 判断 ‘null’ 的字符串
	 * zj
	 * 2017年11月29日
	 */
	public static boolean verifyStringNull(String str) {
		if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证list
	 * zj
	 * 2017年10月24日
	 */
	public static boolean verifyList(List list) {
		if (list == null || list.size() < 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证对象
	 * zj
	 * 2017年10月24日
	 */
	public static boolean verifyObject(Object obj) {
		if (obj == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证 string类型
	 * zj
	 * 2017年10月24日
	 */
	public static boolean isEmptyString(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证list
	 * zj
	 * 2017年10月24日
	 */
	public static boolean isEmptyList(List list) {
		if (list == null || list.size() < 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证对象
	 * zj
	 * 2017年10月24日
	 */
	public static boolean isEmptyObject(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmptyStrings(String[] str) {
		if (str == null || str.length < 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证多参数，是否是 null 或者 "",如果是 则 返回 true
	 * zj
	 * 2017年11月17日
	 */
	public static boolean existEmptyParams(String... params) {
		if (params == null || params.length < 1) {
			return true;
		}
		
		boolean isEmpty = false;
		for (int i = 0; i < params.length; i ++) {
			String param = params[i];
			if (isEmptyString(param)) {
				isEmpty = true;
				return isEmpty;
			}
		}
		return isEmpty;
	}
	
	/**
	 * 验证多参数，是否是 null 或 'null'字符串 或者 "",如果是 则 返回 true
	 * zj
	 * 2018年1月31日
	 */
	public static boolean existEmptyOrNullParams(String... params) {
		if (params == null || params.length < 1) {
			return true;
		}
		
		boolean isEmpty = false;
		for (int i = 0; i < params.length; i ++) {
			String param = params[i];
			if (!verifyStringNull(param)) {
				isEmpty = true;
				return isEmpty;
			}
		}
		return isEmpty;
	}
	
	/**
	 * 验证Object多参数，是否是 null 或 'null'字符串 或者 "",如果是 则 返回 true
	 * zj
	 * 2018年5月30日
	 */
	public static boolean existEmptyOrNullObject(Object... objects) {
		if (objects == null || objects.length < 1) {
			return true;
		}
		
		boolean isEmpty = false;
		for (int i = 0; i < objects.length; i ++) {
			Object param = objects[i];
			if (param == null || "".equals(param) || "null".equalsIgnoreCase(param.toString())) {
				isEmpty = true;
				return isEmpty;
			}
		}
		return isEmpty;
	}
	
	/**
	 * 获取请求的ip
	 * zj
	 * 2018年1月17日
	 */
	public static String getIpAddr(HttpServletRequest request) {
	     String ipAddress = null;
	     //ipAddress = this.getRequest().getRemoteAddr();
	     ipAddress = request.getHeader("x-forwarded-for");
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	      ipAddress = request.getHeader("Proxy-Client-IP");
	     }
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");
	     }
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
		     ipAddress = request.getRemoteAddr();
		     if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
		       //根据网卡取本机配置的IP
		       InetAddress inet=null;
			    try {
			     inet = InetAddress.getLocalHost();
			    } catch (UnknownHostException e) {
			     e.printStackTrace();
			    }
			    ipAddress= inet.getHostAddress();
		     }
	     }

	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
	         if(ipAddress.indexOf(",")>0){
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
	         }
	     }
	     return ipAddress; 
	}
	
	
	/**
	 * 判断两个double是否相等
	 * zj
	 * 2018年4月27日
	 */
	public static boolean equalsDouble(double a, double b) {
		
		if ((a - b > -0.000001) && (a-b) < 0.00001 ) {
			return true;
		}
		return false;
	}

	/**
	 * 四舍五入保留两位小数方法
	 * zj
	 * 2018年8月23日
	 */
	public static double doubleKeepTwo(double doubleValue) {
		
		BigDecimal bd = new BigDecimal(doubleValue);
		doubleValue = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return doubleValue;
	}
	
	/**
	 * 修改map 获取需要的字段
	 * zj
	 * 2018年9月30日
	 */
	public static double includeMap(double doubleValue) {
		
		BigDecimal bd = new BigDecimal(doubleValue);
		doubleValue = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return doubleValue;
	}
	
	
	/**
     * 实体对象转成Map
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * Map转成实体对象
     * @param map map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return obj;
    }

	/**
	 * 获取指定的字段数据返回
	 * zj
	 * 2018年10月8日
	 * 
	 */
	public static  Map getSpecifiedFields(Object obj, String... fields) {
		Map<String, Object> data = null;
		try {
			data = object2Map(obj);
			
			if (fields == null || fields.length < 1) {
				return null;
			}
			
			Iterator<Map.Entry<String, Object>> entrys = data.entrySet().iterator();
			while(entrys.hasNext()) {
				Map.Entry<String, Object> entry = entrys.next(); 
				boolean isNeedRemove = true;
				for (int i = 0; i < fields.length; i ++) {
					String param = fields[i];
					//System.out.println(param +"-------------------------------------------------------" + entry.getKey());
					if (param.equals(entry.getKey())) {
						isNeedRemove = false;
						//System.out.println("----------ddddddddddddddddd---------------------------------------------");
					}
				}
				if (isNeedRemove) {
					entrys.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 获取指定的字段数据返回List
	 * zj
	 * 2018年10月8日
	 * @param <E>
	 */
	public static  <E> List<Map<String, Object>> getSpecifiedFieldsList(List<E> objList, String... fields) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		try {
			for (Object obj : objList) {
				Map<String, Object> data = getSpecifiedFields(obj, fields);
				dataList.add(data);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	/**
	 * 根据浏览器不同获取不同编码格式的String
	 * zj
	 * 2018年10月15日
	 */
	public static  String getStringByBrowserType(HttpServletRequest request, String fileName) {
		
//      if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
//        finalFileName = URLEncoder.encode(fileName,"UTF8");
//    }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
//        finalFileName = new String(fileName.getBytes(), "ISO8859-1");
//    }else{
//        finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
//    }
		try {
			final String userAgent = request.getHeader("USER-AGENT");
			if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0 
					 || userAgent.indexOf("Safari") >= 0) {
				fileName= new String((fileName).getBytes(), "ISO8859-1");
			} else {
				fileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return fileName;
	}
	
	/**
	 * 下载文件（模板）
	 * zj
	 * 2018年10月15日
	 */
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) {
		try {
			InputStream is = new FileInputStream(new File(filePath));
			// 设置response参数，可以打开下载页面
			fileName = PropertiesUtil.getStringByBrowserType(request, fileName);
			
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
			    bis = new BufferedInputStream(is);
			    bos = new BufferedOutputStream(out);
			    byte[] buff = new byte[2048];
			    int bytesRead;
			    // Simple read/write loop.
			    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			    }
			} catch (final IOException e) {
			    throw e;
			} finally {
			    if (bis != null)
				bis.close();
			    if (bos != null)
				bos.close();
			    File tFile = new File(filePath);
			    tFile.deleteOnExit();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 将阿拉伯数字转换成中文大写数字
	 * zj
	 * 2018年11月7日
	 */
	public static String convertChineseNum(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		System.out.println("----" + len);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					//当前val[i]的下一个值val[i-1]为0则不输出零
					continue;
				} else {
					//只有当当前val[i]的下一个值val[i-1]不为0才输出零
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}
	
	public static String convertChineseNum2(String string) {
		String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";
        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';
            if (n == 2) {//说名是两位
            	if (i == 0) {
            		if (num == 1) {
            			result += s2[0];
            		} else {
            			result += s1[num] + s2[0];
            		}
            	} else {
            		if (num == 0) {
            			continue ;
            		}
            		result += s1[num];
            	}
            	
            } else if (n == 1) {
            	result = s1[num];
            } else {
	            if (i != n - 1 && num != 0) {
	                result += s1[num] + s2[n - 2 - i];
	            } else {
	                result += s1[num];
	            }
            }

        }

        System.out.println(result);
		return result;
	}
	
	
}