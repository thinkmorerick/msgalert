package com.alert.msgalert;



public class MailUtil {
	
	public static void main(String args[])  
    {  
        MailSenderInfo mailInfo = new MailSenderInfo();  
        mailInfo.setMailServerHost("smtp.163.com");  
        mailInfo.setMailServerPort("25");  
        mailInfo.setValidate(true);  
        mailInfo.setUserName("06406zhufeng@163.com");  
  
        // 邮箱密码  
        mailInfo.setPassword("9021351Zf!");  
        mailInfo.setFromAddress("06406zhufeng@163.com");  
        mailInfo.setToAddress("06406zhufeng@163.com,zhufeng@daokoudai.com");  
//        mailInfo.setCcAddress("9021351@qq.com,elevenzhu@126.com");  
//        mailInfo.setBccAddress("9021351@qq.com,1106368758@qq.com");  
        mailInfo.setSubject("邮件3：带附件");  
        mailInfo.setContent("邮件3：带附件");  
//        mailInfo.setAttachFileNames(new String[]  
//        {  
//                "C:" + File.separator + "Users" + File.separator + "Desktop" + File.separator  
//                        + "新建文本文档 (1).txt",  
//                "C:" + File.separator + "Users" + File.separator + "Desktop" + File.separator  
//                        + "新建文本文档 (2).txt" });  
        SimpleMailSender.sendTextMail(mailInfo);  
        System.out.println("Send Mail Over!");  
    }  

}
