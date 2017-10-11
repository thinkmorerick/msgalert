package com.alert.msgalert.utils;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alert.msgalert.MySinks;

public class MyAppender extends DailyRollingFileAppender {
	private static final Logger logger = LoggerFactory.getLogger(MySinks.class);

	@Override  
    public boolean isAsSevereAsThreshold(Priority priority) {
		logger.info("<-----------------------isAsSevereAsThreshold()--------------------->");

          //只判断是否相等，而不判断优先级     
        return this.getThreshold().equals(priority);    
    }    

}
