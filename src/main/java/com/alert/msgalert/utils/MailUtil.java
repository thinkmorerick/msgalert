package com.alert.msgalert.utils;

public class MailUtil {
	
	/**
	 * 告警邮件
	 * 
	 * @param pn
	 * @param count
	 * @param totalCount
	 */
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
	
	/**
	 * 
	 * 数据回滚提示邮件
	 * 
	 * @param rollbackCount
	 */
	public static void sendRollbackMail(Integer rollbackCount)  
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
        mailInfo.setSubject("--- Rollback ---");
        mailInfo.setContent("发生回滚 "+rollbackCount+" 次。");
        
        SimpleMailSender.sendTextMail(mailInfo);  
    }  
	
}
