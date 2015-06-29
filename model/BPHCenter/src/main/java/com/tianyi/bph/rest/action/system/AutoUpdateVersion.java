package com.tianyi.bph.rest.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.system.autoUpdate.UpdateConfig;
import com.tianyi.bph.domain.system.autoUpdate.VersionFile;
import com.tianyi.bph.service.system.AutoUpdateService;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/version")
public class AutoUpdateVersion {
	
	static final Logger log = LoggerFactory.getLogger(AutoUpdateVersion.class);
	
	@Autowired
	private AutoUpdateService service;

	@RequestMapping(value = "/autoUpdate.do")
	@ResponseBody
	public ReturnResult addArea(@RequestParam(value="version", required=true) String version) {
		version = version.replace(" ", "");
		List<UpdateConfig> severinfo = service.queryUpdateConfig(version);
		if(severinfo != null && severinfo.size() > 0){
			String currentVersion = severinfo.get(0).getCurrentVersion();
			
			currentVersion = currentVersion.replace(" ", "");
			
			if(!(version.compareTo(currentVersion) < 0)){
				severinfo = null;
			}
		}
		
		List<VersionFile> files = service.queryVersion(version);//全部查询出来
		List<VersionFile> remove = new ArrayList<VersionFile>();
		if(files != null && files.size() > 0){
			for(VersionFile v : files){
				String versionCode = v.getVersionCode().replace(" ", "");//数据库的版本
				if(version.compareTo(versionCode) > 0){
					remove.add(v);
				}
			}
			files.removeAll(remove);
		}
		
		if(severinfo != null){
			@SuppressWarnings("rawtypes")
			Map<String, List> map = new HashMap<String, List>();
			map.put("severinfo", severinfo);
			map.put("files", files);
			return ReturnResult.SUCCESS(map);
		}else {
			return ReturnResult.SUCCESS("");
		}
	}
	
	public static void main(String args[]){
		String version = "v 1.0.3";
		String versionCode = "v 1.0.2";
	 String	version1=version.replace(" ", "");
	 String	version2=versionCode.replace(" ", "");
		
		System.out.print(version1.compareTo(version2));
	}
}
