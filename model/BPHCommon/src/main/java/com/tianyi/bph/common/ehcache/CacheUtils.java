package com.tianyi.bph.common.ehcache;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author chen
 * @since 2014-04-20
 * @Description
 * @TODO
 */
public class CacheUtils {
	public  static final String USER_BASE_DATA="USER_BASE_DATA";
	public  static final String ROLE_BASE_DATA="ROLE_BASE_DATA";
	public 	static final String ORGAN_DATASOURCE="ORGAN_DATASOURCE";
	
	public static Ehcache getCache(CacheManager cacheManager, String cacheName) {
		if (cacheManager == null || StringUtils.isBlank(cacheName)) {
			return null;
		}
		return cacheManager.getEhcache(cacheName);
	}

	public static Element getElement(CacheManager cacheManager,
			String cacheName, String key) {
		if (cacheManager == null || StringUtils.isBlank(cacheName)
				|| StringUtils.isBlank(key)) {
			return null;
		}
		Ehcache ehcache = getCache(cacheManager, cacheName);
		return ehcache == null ? null : ehcache.get(key);
	}

	public static Object getObjectValue(CacheManager cacheManager,
			String cacheName, String key) {
		if (cacheManager == null || StringUtils.isBlank(cacheName)
				|| StringUtils.isBlank(key)) {
			return null;
		}
		Element element = getElement(cacheManager, cacheName, key);
		return element == null ? null : element.getObjectValue();
	}

	public static String getStringValue(CacheManager cacheManager,
			String cacheName, String key) {
		if (cacheManager == null || StringUtils.isBlank(cacheName)
				|| StringUtils.isBlank(key)) {
			return null;
		}
		Element element = getElement(cacheManager, cacheName, key);
		return element == null ? null : String.valueOf(element.getObjectValue());
	}

	public static void updateValue(CacheManager cacheManager, String cacheName,
			String key, Object value) {
		if (cacheManager == null || StringUtils.isBlank(cacheName)
				|| StringUtils.isBlank(key)) {
			return;
		}
		Ehcache cache = getCache(cacheManager, cacheName);
		if (cache == null) {
			return;
		}
		if (value != null)
			cache.put(new Element(key, value));
		else
			invalidateValue(cacheManager, cacheName, key);
	}

	public static void updateValue(CacheManager cacheManager, String cacheName,
			String key, Object value, int timeToLive) {
		if (cacheManager == null || StringUtils.isBlank(cacheName)
				|| StringUtils.isBlank(key)) {
			return;
		}
		Ehcache cache = getCache(cacheManager, cacheName);
		if (value != null) {
			Element element = new Element(key, value);
			element.setTimeToLive(timeToLive);
			element.setTimeToIdle(timeToLive / 2);
			cache.put(element);
		} else {
			invalidateValue(cacheManager, cacheName, key);
		}
	}

	public static void invalidateValue(CacheManager cacheManager,
			String cacheName, String key) {
		if (cacheManager == null || StringUtils.isBlank(cacheName)
				|| StringUtils.isBlank(key)) {
			return;
		}
		Ehcache cache = getCache(cacheManager, cacheName);
		if (cache != null)
			cache.remove(key);
	}
	
	/**
	 * 根据数组返回指定的字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String getStringFromObjects(Object[] obj) {
		StringBuffer str = new StringBuffer("");
		if (obj == null) {
			return str.toString();
		}
		for (Object o : obj) {
			str.append(o);
			str.append("/");
		}
		return str.toString();
	}

	/**
	 * 根据List返回指定的字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String getStringFromList(List<Object> obj) {
		StringBuffer str = new StringBuffer("");
		if (obj == null) {
			return str.toString();
		}
		for (Object o : obj) {
			str.append(o);
			str.append("/");
		}
		return str.toString();
	}

	/**
	 * 得到动态数据的Key
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getKey(Object... obj) {
		int length = obj.length;
		StringBuffer str = new StringBuffer("");
		if (length == 0) {
			return str.toString();
		}
		for (int i = 0; i < length; i++) {
			if (obj[i] instanceof Object[]) {
				str.append(getStringFromObjects((Object[]) obj[i]));
			} else if (obj[i] instanceof List) {
				str.append(getStringFromList((List<Object>) obj[i]));
			} else {
				str.append(obj[i]);
			}
		}
		return str.toString();
	}
}
