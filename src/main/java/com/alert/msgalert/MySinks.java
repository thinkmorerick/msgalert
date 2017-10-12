package com.alert.msgalert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alert.msgalert.utils.CacheMap;
import com.alert.msgalert.utils.MailUtil;
import com.alert.msgalert.utils.PropertiesUtil;
import com.alert.msgalert.utils.RollingFileLogger;

/**
 * 自定义sink
 */
public class MySinks extends AbstractSink implements Configurable {
	private static final Logger logger = LoggerFactory.getLogger(MySinks.class);
	
	private List<String> pnList = new ArrayList<String>();
	private Integer totalCount;
	private Integer rollbackCount = 0;
		
	private static final String SINK_ID = "sink.id";  
    private static final String SINK_FILENAME = "sink.filename";  
    private static final String SINK_FILEPATTERN = "sink.filepattern";  
    private RollingFileLogger rollingFileLogger; 
    private static final String startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private static final String startTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

	public void configure(Context context) {
		logger.info("-----------------------configure()---------------------");
		String sinkId = context.getString(SINK_ID, "log");  
        String sinkFileName = context.getString(SINK_FILENAME); 
        String sinkFilePattern = context.getString(SINK_FILEPATTERN);
  
        logger.info("{} : {} ", SINK_ID, sinkId);  
        logger.info("{} : {} ", SINK_FILENAME, sinkFileName);  
        logger.info("{} : {} ", SINK_FILEPATTERN, sinkFilePattern);  
  
        rollingFileLogger = new RollingFileLogger(sinkId, sinkFileName, sinkFilePattern);  
	}

	public Status process() throws EventDeliveryException {
		logger.info("-----------------------process()---------------------");

		logger.info("startDate:---------------------------->"+startDate);
		logger.info("startTime:---------------------------->"+startTime);
		
		Status status = null; 
		// Start transaction
		Channel channel = getChannel();
		Transaction txn = channel.getTransaction();
		Event event = null;
		txn.begin();
		while (true) {
			event = channel.take();
			if (event != null) {
				break;
			}
		}
		try {
			logger.debug("Get event.");
			logger.info("event.getHeaders():--------------------->"+event.getHeaders().toString());
			// 取值
			String body = new String(event.getBody());
			logger.info("event.getBody():--------------------->"+body);
			String res ="";
			String[] Pnss = body.split(" ");
			// 定时器
			CacheMap<String, Integer> counter = CacheMap.getDefault();
			
			if ((Pnss[0].length())>4 && (Pnss[0].substring(0, 4)).equals("time") && 
					((Pnss[0].substring(5, 15)).compareTo(startDate))>=0 &&
					((Pnss[1].substring(0, 8).compareTo(startTime))>=0)){
				logger.info("bodydate:---------------------------->"+(Pnss[0].substring(5, 15)));
				logger.info("bodytime:---------------------------->"+(Pnss[1].substring(0, 8)));
				
				String s = Pnss[2].substring(8);
				s = s.substring(0, s.length() - 1);
				String[] Pns = s.split(",");
				for (String pn : Pns) {
					pnList.add(pn);
				}

				totalCount = pnList.size();
				String pn = "";
				Integer count = 0;
				for (int i = 0; i < Pns.length; i++) {
					pn = Pns[i];
					if (pn != null && pn != "") {
						if (counter.containsKey(pn)) {
							int oldValue = counter.get(pn);
							counter.put(pn, oldValue + 1);
						} else {
							counter.put(pn, 1);
						}
						count = counter.get(pn);
						event.setHeaders(new HashMap<String, String>(count,totalCount));
						// 阀值控制
						if (pn != null && count > Integer.parseInt(PropertiesUtil.getProperty("Threshold"))) {
							if (count % 10 == 0) {
								MailUtil.sendMail(pn, count, totalCount);
								TimeUnit.SECONDS.sleep(30);
							}
						}

					}
				}

				// sink内容
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				res = "time:" + sdf.format(d) + " pn:" + pn + " count:" + counter.get(pn) + " totalCount:"
						+ totalCount + "\r\n" + "body" + body + "\r\n";

				Pns = null;
				Pnss = null;
			}else {
				event = null;
				txn.commit();
				return Status.READY;
			}
			
			byte[] results = res.getBytes();
			if(results!=null && results.toString().trim()!="")
			handleEvent(res.getBytes());
			
			
			txn.commit();
			status = Status.READY;
			logger.info("event.getHeaders():----------2----------->"+event.getHeaders().toString());
		} catch (Throwable th) {
			txn.rollback();
			rollbackCount+=1;
			MailUtil.sendRollbackMail(rollbackCount);
			logger.info("=========================rollback()=========================");
			status = Status.BACKOFF; 
			if (th instanceof Error) {
				throw (Error) th;
			} else {
				throw new EventDeliveryException(th);
			}
		} finally {
			logger.info("-----------------------close()---------------------");
			txn.close();
		}
		return status;
	}
	
	public void handleEvent(byte[] msg)  {  
        try {  
            String msgStr = new String(msg, "utf-8");  
            rollingFileLogger.write(msgStr);  
            logger.info("-----------------------handleEvent()-------------try--------");
        } catch (Exception e) {  
            logger.error("Cookie inject error : ", e.getMessage(), e);  
        }  
    }  
	
}
