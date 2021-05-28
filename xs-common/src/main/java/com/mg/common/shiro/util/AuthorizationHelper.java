package com.mg.common.shiro.util;

import com.mg.common.entity.UserEntity;
import com.mg.framework.utils.UserHolder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthorizationHelper {

	private static AuthorizationHelper INSTANCE = new AuthorizationHelper();

	private CacheManager cacheManager;

	private static Logger log = LoggerFactory.getLogger(AuthorizationHelper.class);

	public static String SHIRO_CACHE_NAME = "AuthorizationHelper";

	public AuthorizationHelper(){}

	public AuthorizationHelper(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public static AuthorizationHelper getInstance() {
		if(log.isDebugEnabled()){
			log.debug("AuthorizationHelper getInstance called");
		}
		return INSTANCE;
	}

	/**
	 * 清除权限信息
	 * 
	 * @param loginUserName
	 *            登陆的用户名
	 * @param companyName
	 *            登陆的公司实例名称
	 */
	public void clearAuthorizationInfo(String loginUserName, String companyName) {

		// 例如:"10866:绍兴银行"
		String cacheKey = toCacheKey(loginUserName, companyName);

		if (log.isDebugEnabled()) {
			log.debug("clear the " + cacheKey + " authorizationInfo");
		}

		Cache<Object, Object> cache = cacheManager.getCache(SHIRO_CACHE_NAME);

		cache.remove(cacheKey);
	}

	/**
	 * 清除权限信息
	 */
	public void clearAuthorizationInfo() {

		UserEntity userEntity = UserHolder.getLoginUser();

		String companyName = userEntity.getCompanyName();

		String loginUserName = userEntity.getLoginName();

		clearAuthorizationInfo(loginUserName, companyName);
	}

	/**
	 * 刷新用户权限信息
	 */
	public void refreshAuthorizationInfo() {

		// 清除权限信息
		clearAuthorizationInfo();

		// 随便做一个普通鉴权,shiro会去自动加载权限,并缓存
		SecurityUtils.getSubject().isPermitted("");
	}

	private static String toCacheKey(String loginUserName, String instanceName) {
		return loginUserName + ":" + instanceName;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}