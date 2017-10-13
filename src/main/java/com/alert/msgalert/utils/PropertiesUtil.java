package com.alert.msgalert.utils;

import java.io.*;
import java.util.Properties;

/**
 * 
 * 配置文件工具类
 * 
 * @author data
 *
 */
public class PropertiesUtil {

    private static String default_properties = "mail.properties";
    private static Properties prop;
    static {
        prop = new Properties();
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(getPath() + default_properties));
//            InputStream is = new BufferedInputStream(new FileInputStream("/Users/data/Desktop/mail.properties"));
            BufferedReader bf = new BufferedReader(new InputStreamReader(is,"UTF-8"));//解决读取properties文件中产生中文乱码的问题
            prop.load(bf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = prop.getProperty(key);
        if (value == null)
            return defaultValue;
        return value;
    }

    public static boolean getBooleanProperty(String name, boolean defaultValue) {
        String value = prop.getProperty(name);
        if (value == null)
            return defaultValue;
        return (new Boolean(value)).booleanValue();
    }

    public static int getIntProperty(String name) {
        return getIntProperty(name, 0);
    }

    public static int getIntProperty(String name, int defaultValue) {
        String value = prop.getProperty(name);
        if (value == null)
            return defaultValue;
        return (new Integer(value)).intValue();
    }

    public static String getPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    /**
     * 读取指定properties中的值
     * @param properties  文件名
     * @param name  要读取的属性
     * @return
     */
    private String readProper(String properties, String name) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(properties);
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p.getProperty(name);
    }

    public static void main(String[] args) {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        String name = PropertiesUtil.getProperty("name");
        String str = propertiesUtil.readProper("mail.properties","str");
        Boolean bo = propertiesUtil.getBooleanProperty("boolean", false);

        System.out.println("name=="+name+","+"str=="+str+", boolean == " +bo);
    }
}
