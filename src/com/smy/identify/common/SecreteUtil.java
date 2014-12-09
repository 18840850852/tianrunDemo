package com.smy.identify.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecreteUtil {
	
	 /**将一个字符串进行md5加密 
     * @param str 
     * @Description:  32位小写MD5 
     * @return  32位小写MD5
     */  
    public static String parseStrToMd5L32(String str){  
        String reStr = "";  
        try {  
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
            byte[] bytes = md5.digest(str.getBytes());  
            StringBuffer stringBuffer = new StringBuffer();  
            for (byte b : bytes){  
                int bt = b&0xff;  
                if (bt < 16){  
                    stringBuffer.append(0);  
                }   
                stringBuffer.append(Integer.toHexString(bt));  
            }  
            reStr = stringBuffer.toString();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return reStr;  
    }  
}
