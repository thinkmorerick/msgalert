package com.alert.msgalert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

/**
 * 自定义sink
 */
public class MySinks1 extends AbstractSink implements Configurable {
	private static final Logger logger = LoggerFactory.getLogger(MySinks1.class);
	private static final String PROP_KEY_ROOTPATH = "fileName";
	private String fileName;
	
	private List<String> pnList = new ArrayList<String>();
	private Integer totalCount;
	

	public void configure(Context context) {
		fileName = context.getString(PROP_KEY_ROOTPATH);
	}

	public Status process() throws EventDeliveryException {
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
			// 取值
			String body = new String(event.getBody());
			// logger.info("event.getBody()-----" + body);
			String[] Pnss = body.split(" ");
			// 定时器
			CacheMap<String, Integer> counter = CacheMap.getDefault();

			if (Pnss[0].length()>4 && Pnss[0].substring(0, 4).equals("time")) {

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

						// 阀值控制
						if (pn != null && counter.get(pn) > 100) {
							count = counter.get(pn);
							if (count % 10 == 0) {
//								Thread.sleep(3000);
								MailUtil.sendMail(pn, count, totalCount);
								TimeUnit.SECONDS.sleep(30);
							}
						}

					}
				}

				// sink内容
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String res = "time:" + sdf.format(d) + " pn:" + pn + " count:" + counter.get(pn) + " totalCount:"
						+ totalCount + "\r\n" + "body" + body + "\r\n";

				File file = new File(fileName);
				FileOutputStream fos = null;
				Pns = null;
				Pnss = null;
				try {
					fos = new FileOutputStream(file, true);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					fos.write(res.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			txn.commit();
			status = Status.READY;
		} catch (Throwable th) {
			txn.rollback();
			status = Status.BACKOFF; 
			if (th instanceof Error) {
				throw (Error) th;
			} else {
				throw new EventDeliveryException(th);
			}
		} finally {
			txn.close();
		}
		return status;
	}
	

}
