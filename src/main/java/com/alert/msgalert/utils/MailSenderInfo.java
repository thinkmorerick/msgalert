package com.alert.msgalert.utils;

import java.util.Properties;  

/** 
 * @ClassName MailSenderInfo 
 * @Description 邮件发送信息 
 *  
 */  
public class MailSenderInfo  
{  
    /** 
     * @FieldName mailServerHost 发送邮件的服务器的IP 
     */  
    private String mailServerHost;  
  
    /** 
     * @FieldName mailServerPort 发送邮件的服务器的端口 
     */  
    private String mailServerPort = "25";  
  
    /** 
     * @FieldName fromAddress 邮件发送者的地址 
     */  
    private String fromAddress;  
  
    /** 
     * @FieldName toAddress 邮件接收者的地址 
     */  
    private String toAddress;  
  
    /** 
     * @FieldName ccAddress 抄送地址 
     */  
    private String ccAddress;  
  
    /** 
     * @FieldName scAddress 密送地址 
     */  
    private String bccAddress;  
  
    /** 
     * @FieldName userName 登陆邮件发送服务器的用户名 
     */  
    private String userName;  
  
    /** 
     * @FieldName password 登陆邮件发送服务器的密码 
     */  
    private String password;  
  
    /** 
     * @FieldName validate 是否需要身份验证 
     */  
    private boolean validate = false;  
  
    /** 
     * @FieldName subject 邮件主题 
     */  
    private String subject;  
  
    /** 
     * @FieldName content 邮件的文本内容 
     */  
    private String content;  
  
    /** 
     * @FieldName attachFileNames 邮件附件的文件名 
     */  
    private String[] attachFileNames;  
  
    /** 
     * 获得邮件会话属性 
     */  
    public Properties getProperties()  
    {  
        Properties p = new Properties();  
        p.put("mail.smtp.host", this.mailServerHost);  
        p.put("mail.smtp.port", this.mailServerPort);  
        p.put("mail.smtp.auth", validate ? "true" : "false");  
        return p;  
    }  
  
    public String getMailServerHost()  
    {  
        return mailServerHost;  
    }  
  
    public void setMailServerHost(String mailServerHost)  
    {  
        this.mailServerHost = mailServerHost;  
    }  
  
    public String getMailServerPort()  
    {  
        return mailServerPort;  
    }  
  
    public void setMailServerPort(String mailServerPort)  
    {  
        this.mailServerPort = mailServerPort;  
    }  
  
    public boolean isValidate()  
    {  
        return validate;  
    }  
  
    public void setValidate(boolean validate)  
    {  
        this.validate = validate;  
    }  
  
    public String[] getAttachFileNames()  
    {  
        return attachFileNames;  
    }  
  
    public void setAttachFileNames(String[] fileNames)  
    {  
        this.attachFileNames = fileNames;  
    }  
  
    public String getFromAddress()  
    {  
        return fromAddress;  
    }  
  
    public void setFromAddress(String fromAddress)  
    {  
        this.fromAddress = fromAddress;  
    }  
  
    public String getPassword()  
    {  
        return password;  
    }  
  
    public void setPassword(String password)  
    {  
        this.password = password;  
    }  
  
    public String getToAddress()  
    {  
        return toAddress;  
    }  
  
    public void setToAddress(String toAddress)  
    {  
        this.toAddress = toAddress;  
    }  
  
    public String getUserName()  
    {  
        return userName;  
    }  
  
    public void setUserName(String userName)  
    {  
        this.userName = userName;  
    }  
  
    public String getSubject()  
    {  
        return subject;  
    }  
  
    public void setSubject(String subject)  
    {  
        this.subject = subject;  
    }  
  
    public String getContent()  
    {  
        return content;  
    }  
  
    public void setContent(String textContent)  
    {  
        this.content = textContent;  
    }  
  
    public String getCcAddress()  
    {  
        return ccAddress;  
    }  
  
    public void setCcAddress(String ccAddress)  
    {  
        this.ccAddress = ccAddress;  
    }  
  
    public String getBccAddress()  
    {  
        return bccAddress;  
    }  
  
    public void setBccAddress(String bccAddress)  
    {  
        this.bccAddress = bccAddress;  
    }  
  
}  
