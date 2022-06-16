package com.xiushang.framework.log;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Since RevisionListener is instantiated by a constructor in Envers,
 * you have to find another way to retrieve a handle to a Spring managed bean.
 * @author lance
 */
@Component
@Lazy(false)
public class ContextLookup implements ApplicationContextAware {
	private static ApplicationContext sApplicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		setContext(applicationContext);
	}

	public static void setContext(ApplicationContext aApplicationContext) {
		sApplicationContext = aApplicationContext;
	}

	protected static ApplicationContext getApplicationContext() {
		return sApplicationContext;
	}

	public static Object getBean(String aName) {
		if (sApplicationContext != null) {
			return sApplicationContext.getBean(aName);
		}
		return null;
	}

	public static <T> T getBean(Class<T> aClass) {
		if (sApplicationContext != null) {
			return sApplicationContext.getBean(aClass);
		}
		return null;
	}
}
