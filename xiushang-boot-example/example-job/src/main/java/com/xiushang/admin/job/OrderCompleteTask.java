package com.xiushang.admin.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class OrderCompleteTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Scheduled(cron="0 0/1 * * * ?")
	public void task(){
		logger.debug("execute OrderCompleteTask....");
		try{
			//do something
		}catch(Exception ex){
			ex.printStackTrace();
		}

		logger.debug("OrderCompleteTask has done!");
	}
}
