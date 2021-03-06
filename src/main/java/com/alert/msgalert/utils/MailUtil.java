package com.alert.msgalert.utils;

public class MailUtil {
	
	public static void sendMail(String pn, Integer count, Integer totalCount)  
    {  
//		System.setProperty("java.net.preferIPv4Stack","true");
        MailSenderInfo mailInfo = new MailSenderInfo();  
        
        mailInfo.setMailServerHost(PropertiesUtil.getProperty("ServerHost"));
        mailInfo.setMailServerPort(PropertiesUtil.getProperty("ServerPort"));
        mailInfo.setValidate(true);
        mailInfo.setUserName(PropertiesUtil.getProperty("UserName"));
        mailInfo.setPassword(PropertiesUtil.getProperty("Password"));//您的邮箱密码
        mailInfo.setFromAddress(PropertiesUtil.getProperty("FromAddress"));
        mailInfo.setToAddress(PropertiesUtil.getProperty("ToAddress"));
        
        
        
//        mailInfo.setCcAddress("");  
//        mailInfo.setBccAddress("");  
        mailInfo.setSubject(PropertiesUtil.getProperty("Subject"));
        mailInfo.setContent(PropertiesUtil.getProperty("Content" )+ "短信接口存在非法调用风险！"
        												+" 据统计：1小时内存在相同用户累计调用"+count.toString()+"次。");
        
//        mailInfo.setAttachFileNames(new String[]  
//        {  
//                "C:" + File.separator + "Users" + File.separator + "Desktop" + File.separator  
//                        + "新建文本文档 (1).txt",  
//                "C:" + File.separator + "Users" + File.separator + "Desktop" + File.separator  
//                        + "新建文本文档 (2).txt" });  
        SimpleMailSender.sendTextMail(mailInfo);  
    }  
	
	public static void main(String[] args) {
		sendMail("1111",11,11);
	}

}
