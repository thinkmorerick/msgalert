package com.alert.msgalert.utils;

import javax.mail.Authenticator;  
import javax.mail.PasswordAuthentication;  
  
/** 
 * @ClassName MyAuthenticator 
 * @Description 密码验证器 
 */  
public class MyAuthenticator extends Authenticator  
{  
    String userName = null;  
    String password = null;  
  
    public MyAuthenticator()  
    {  
    }  
  
    public MyAuthenticator(String username, String password)  
    {  
        this.userName = username;  
        this.password = password;  
    }  
  
    protected PasswordAuthentication getPasswordAuthentication()  
    {  
        return new PasswordAuthentication(userName, password);  
    }  
}  
