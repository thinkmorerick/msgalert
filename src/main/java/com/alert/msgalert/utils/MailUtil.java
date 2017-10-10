package com.alert.msgalert.utils;

public class MailUtil {
	
	public static void sendMail(String pn, Integer count, Integer totalCount)  
    {  
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
        mailInfo.setContent(PropertiesUtil.getProperty("Content" ));
        
        SimpleMailSender.sendTextMail(mailInfo);  
    }  
	
}
