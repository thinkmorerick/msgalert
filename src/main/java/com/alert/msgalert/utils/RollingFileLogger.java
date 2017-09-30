package com.alert.msgalert.utils;

import java.nio.charset.Charset;  
import java.util.concurrent.TimeUnit;  
  
import org.apache.logging.log4j.Level;  
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.apache.logging.log4j.core.Appender;  
import org.apache.logging.log4j.core.Layout;  
import org.apache.logging.log4j.core.LoggerContext;  
import org.apache.logging.log4j.core.appender.RollingFileAppender;  
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;  
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;  
import org.apache.logging.log4j.core.config.AppenderRef;  
import org.apache.logging.log4j.core.config.Configuration;  
import org.apache.logging.log4j.core.config.LoggerConfig;  
import org.apache.logging.log4j.core.layout.PatternLayout;  
  
/** 
 * RollingFileLogger ， 
 * 基于Log4j2的RollingFileAppender，通过运行时修改配置的方式， 
 * 在程序中实时指定要输出的目录和压缩规则
 */  
public class RollingFileLogger {  
  
    private static final Logger logger = LogManager.getLogger(RollingFileLogger.class);  
  
    private Logger fileWriter;  
  
    private String fileName;  
    private String filePattern;  
    private String appenderName;  
    private String loggerName;  
  
    /** 
     * 创建Logger 
     *  
     * @param id 
     *            logger唯一标识，相同的会覆盖 
     * @param fileName 
     *            log4j2 fileName ， 例如 ： logs/main.log 
     * @param filePattern 
     *            log4j2 的filePattern 
     *            ，例如：logs/$${date:yyyy-MM}/main-%d{yyyy-MM-dd_hh_mm_ss}.log.gz 
     */  
    public RollingFileLogger(String loggerId, String fileName, String filePattern) {  
        this.fileName = fileName;  
        this.filePattern = filePattern;  
  
        appenderName = loggerId + "_appender";  
        loggerName = loggerId + "_logger";  
  
        logger.info("fileName : " + fileName);  
        logger.info("filePattern : " + filePattern);  
        logger.info("appenderName : " + appenderName);  
        logger.info("loggerName : " + loggerName);  
  
        updateLoggerConfig();  
        fileWriter = LogManager.getLogger(loggerName);  
    }  
  
    /** 
     * 更新配置 
     */  
    private void updateLoggerConfig() {  
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);  
        final Configuration config = ctx.getConfiguration();  
  
        // add RollingFileAppender  
        TriggeringPolicy policy = TimeBasedTriggeringPolicy.createPolicy("1", "true");  
        Layout<?> layout = PatternLayout.createLayout("%m%n", null, config, null, Charset.forName("utf-8"), true, false, null, null);  
        Appender appender = RollingFileAppender.createAppender(fileName, filePattern, "true", appenderName, "true", "", "true", policy, null, layout, null, "true", "false", null, config);  
        appender.start();  
        config.addAppender(appender);  
  
        // add AsyncLogger  
        AppenderRef ref = AppenderRef.createAppenderRef(appenderName, null, null);  
        AppenderRef[] refs = new AppenderRef[] { ref };  
  
        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.INFO, loggerName, "true", refs, null, config, null);  
  
        loggerConfig.addAppender(appender, null, null);  
        config.addLogger(loggerName, loggerConfig);  
        ctx.updateLoggers();  
    }  
  
    public void write(String msg) {  
        fileWriter.info("{}", msg);  
    }  
  
//    public static void main(String[] args) throws Exception {  
//        RollingFileLogger writer = new RollingFileLogger("1", "test/test.data", "test/data/${date:yyyy-MM}/test-%d{yyyy-MM-dd_hh_mm_ss}.log.gz");  
//        RollingFileLogger writer2 = new RollingFileLogger("2", "hehe/hehe.data", "hehe/data/${date:yyyy-MM}/test-%d{yyyy-MM-dd_hh_mm_ss}.log.gz");  
//        for (int i = 0; true; i++) {  
//            writer.write("hh" + i);  
//            writer2.write("hehe" + i);  
//            TimeUnit.MICROSECONDS.sleep(100);  
//        }  
//    }  
}  
