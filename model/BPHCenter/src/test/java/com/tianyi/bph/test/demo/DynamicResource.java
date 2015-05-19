package com.tianyi.bph.test.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

public class DynamicResource implements Resource {
	static String filepath="D:\\applicationContext.xml";
	private DynamicBean dynamicBean;
	
	public DynamicResource(DynamicBean dynamicBean){
		this.dynamicBean = dynamicBean;
	}
	/* (non-Javadoc)
	 * @see org.springframework.core.io.InputStreamSource#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		System.out.println(dynamicBean.getXml());
		return new ByteArrayInputStream(dynamicBean.getXml().getBytes("UTF-8"));
	}
//其他实现方法省略
	@Override
	public long contentLength() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Resource createRelative(String arg0) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public File getFile() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getFilename() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public URI getURI() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public URL getURL() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isReadable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public long lastModified() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
}