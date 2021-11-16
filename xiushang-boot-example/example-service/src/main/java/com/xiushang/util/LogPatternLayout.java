package com.xiushang.util;

import ch.qos.logback.classic.PatternLayout;

public class LogPatternLayout extends PatternLayout {

	static {
		defaultConverterMap.put("currentUser", CurrentUserConverter.class.getName());
	}


}
