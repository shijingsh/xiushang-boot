package com.xiushang.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.xiushang.framework.utils.UserHolder;

public class CurrentUserConverter extends ClassicConverter {

	@Override
	public String convert(ILoggingEvent event) {
		return UserHolder.getLoginUserTenantId()+":"+ UserHolder.getLoginName();
	}

}
