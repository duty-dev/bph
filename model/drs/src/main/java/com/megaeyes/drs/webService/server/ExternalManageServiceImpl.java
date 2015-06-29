package com.megaeyes.drs.webService.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.WebService;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.megaeyes.drs.bean.CjdbBean;
import com.megaeyes.drs.bean.CjryBean;
import com.megaeyes.drs.bean.Jjdb110Bean;
import com.megaeyes.drs.bean.XlzBean;
import com.megaeyes.drs.domain.PatrolGroup;
import com.megaeyes.drs.domain.PoliceUser;
import com.tianyi.bph.domain.alarm.CJOrder;
import com.tianyi.bph.domain.alarm.Jjdb110;
import com.tianyi.bph.service.alarm.AlarmDispatchService;

@Component
@WebService(serviceName = "ExternalManageService", endpointInterface = "com.megaeyes.drs.webService.server.ExternalManageService")
@javax.xml.ws.soap.MTOM(enabled = true, threshold = 1024 * 1024)
public class ExternalManageServiceImpl implements ExternalManageService {

	final static private int size = 1024 * 1024;

	private Log logger = LogFactory.getLog(ExternalManageServiceImpl.class);

	@Autowired
	private AlarmDispatchService dispatchService;

	@Override
	public boolean pushCurJjdb110(Jjdb110Bean jjdb110Bean) {
		try {
			if (jjdb110Bean != null) {
				if (StringUtils.hasLength(jjdb110Bean.getJjdbh())) {
					logger.info("接收到警情jjdbh:" + jjdb110Bean.getJjdbh());
					Jjdb110 jjdb110 = null;
					jjdb110 = new Jjdb110();
					processJjdb110(jjdb110Bean, jjdb110);
					dispatchService.saveOrUpdateJjdb110(jjdb110);
				} else {
					logger.info("无效警情，接警单编号不存在");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean pushCurCjdb(CjdbBean cjdbBean) {
		CJOrder cjOrder = new CJOrder();
		try {
			logger.info("========pushCurCjdb");
			setCjdb(cjdbBean, cjOrder);
			dispatchService.saveOrUpdateCjOrder(cjOrder);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean pushCurState(String jjdbh, String cjdbh, Integer zt) {
		return true;
	}

	@Override
	public boolean pushCurCjry(CjryBean cjrybean) {
		return true;
	}

	@Override
	public boolean pushCurXlz(XlzBean xlzbean) {
		return true;
	}

	@Override
	public boolean delCjdb(CjdbBean cjdbBean) {
		CJOrder cjorder = new CJOrder();
		try {
			setCjdb(cjdbBean, cjorder);

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public PatrolGroup getPatrolGroupByRegionId(String regionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PoliceUser> findUsersByOrganCode(String platformId,
			String organCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatrolGroup> findPatrolGroupByOrganCode(String platformId,
			String organCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void upload(DataHandler dataHandler) {
		InputStream in = null;
		OutputStream out = null;
		try {
			File file = new File("D:\\ad.txt");
			in = dataHandler.getInputStream();
			out = new FileOutputStream(file);
			byte[] buf = new byte[size];
			int read;
			while ((read = in.read(buf)) != -1) {
				out.write(buf, 0, read);
				out.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static File getFileDepository() {
		return new File(System.getProperty("java.io.tmpdir"));
	}

	@Override
	public DataHandler downLoad(String fileName) throws FileNotFoundException {
		// if (fileName == null || fileName.isEmpty()) {
		// throw new FileNotFoundException("file name is null");
		// }
		File dir = getFileDepository();
		File downLoadFile = new File("D:\\QQ图片20150104105643.jpg");
		if (!downLoadFile.exists()) {
			throw new FileNotFoundException("file name is null");
		}
		return new DataHandler(new FileDataSource(downLoadFile));
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.io.tmpdir"));
	}

	private void processJjdb110(Jjdb110Bean jjdb110Bean, Jjdb110 jjdb110)
			throws ParseException {
		jjdb110.setJjdbh(jjdb110Bean.getJjdbh());
		jjdb110.setXzqhbh(jjdb110Bean.getXzqhbh());
		jjdb110.setJjdwbh(jjdb110Bean.getJjdwbh());
		jjdb110.setGljjdbh(jjdb110Bean.getGljjdbh());
		jjdb110.setLhlxbh(jjdb110Bean.getLhlxbh());
		jjdb110.setBjfsbh(jjdb110Bean.getBjfsbh());
		jjdb110.setTfhm(jjdb110Bean.getTfhm());
		jjdb110.setJjybh(jjdb110Bean.getJjybh());
		jjdb110.setJjyxm(jjdb110Bean.getJjyxm());
		if (StringUtils.hasText(jjdb110Bean.getBjsj())) {
			jjdb110.setBjsj(DateUtils.parseDate(jjdb110Bean.getBjsj(),
					new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmss" }));
		}
		if (StringUtils.hasText(jjdb110Bean.getJjsj())) {
			Date jjsj = DateUtils.parseDate(jjdb110Bean.getJjsj(),
					new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmss" });
			jjdb110.setJjsj(jjsj);
		}
		jjdb110.setBjdh(jjdb110Bean.getBjdh());
		jjdb110.setBjdhyhxm(jjdb110Bean.getBjdhyhxm());
		jjdb110.setBjdhyhdz(jjdb110Bean.getBjdhyhdz());
		jjdb110.setBjrxm(jjdb110Bean.getBjrxm());
		jjdb110.setBjrxb(jjdb110Bean.getBjrxb());
		jjdb110.setLxdh(jjdb110Bean.getLxdh());
		jjdb110.setSfdz(jjdb110Bean.getSfdz());
		jjdb110.setBjnr(jjdb110Bean.getBjnr());
		jjdb110.setGxdwbh(jjdb110Bean.getGxdwbh());
		jjdb110.setBjlb(jjdb110Bean.getBjlb());
		jjdb110.setBjlx(jjdb110Bean.getBjlx());
		jjdb110.setBjxl(jjdb110Bean.getBjxl());
		jjdb110.setLdgbh(jjdb110Bean.getLdgbh());
		jjdb110.setYwwxwz(jjdb110Bean.getYwwxwz());
		jjdb110.setYwbzxl(jjdb110Bean.getYwbzxl());
		jjdb110.setYwbkry(jjdb110Bean.getYwbkry());
		jjdb110.setSfsw(jjdb110Bean.getSfsw());
		jjdb110.setSfswybj(jjdb110Bean.getSfswybj());
		double x, y;
		if (StringUtils.hasText(jjdb110Bean.getSddwxzb())
				&& StringUtils.hasText(jjdb110Bean.getSddwyzb())) {
			x = Double.valueOf(jjdb110Bean.getSddwxzb());
			y = Double.valueOf(jjdb110Bean.getSddwyzb());
			if ((x < 104.54 || x > 102.55) && (y < 31.26 || x > 30.03)) {
				jjdb110.setSddwxzb(x);
				jjdb110.setSddwyzb(y);
				jjdb110.setMark(true);
				jjdb110.setGpsConfig("x:" + jjdb110Bean.getSddwxzb() + ", y:"
						+ jjdb110Bean.getSddwyzb());
			}

		}
		if (StringUtils.hasText(jjdb110Bean.getZddwxzb())
				&& StringUtils.hasText(jjdb110Bean.getZddwyzb())) {
			x = Double.valueOf(jjdb110Bean.getZddwxzb());
			y = Double.valueOf(jjdb110Bean.getZddwyzb());
			if ((x < 104.54 || x > 102.55) && (y < 31.26 || x > 30.03)) {
				jjdb110.setZddwxzb(x);
				jjdb110.setZddwyzb(y);
				jjdb110.setMark(true);
				jjdb110.setGpsConfig("x:" + jjdb110Bean.getZddwxzb() + ", y:"
						+ jjdb110Bean.getZddwyzb());
			}
		}
		jjdb110.setBcjjnr(jjdb110Bean.getBcjjnr());
		jjdb110.setFkyq(jjdb110Bean.getFkyq());
		jjdb110.setAjzt(jjdb110Bean.getAjzt() == null ? -1 : jjdb110Bean
				.getAjzt());
		jjdb110.setYwxajbs(jjdb110Bean.getYwxajbs() == null ? -1 : jjdb110Bean
				.getYwxajbs());
		jjdb110.setZfajbs(jjdb110Bean.getZfajbs() == null ? -1 : jjdb110Bean
				.getZfajbs());
		jjdb110.setZagj(jjdb110Bean.getZagj());
		jjdb110.setTransmitunit(jjdb110Bean.getTransmitunit());
		if (jjdb110Bean.getCaseLevel() != null) {
			jjdb110.setCaseLevel(jjdb110Bean.getCaseLevel());
		}
	}

	private void setCjdb(CjdbBean cjdbBean, CJOrder cjdb) throws ParseException {
		cjdb.setCjdbh(cjdbBean.getCjdbh());
		cjdb.setJjdbh(cjdbBean.getJjdbh());
		cjdb.setCjdwbh(cjdbBean.getCjdwbh());
		cjdb.setCjybh(cjdbBean.getCjybh());
		cjdb.setCjyxm(cjdbBean.getCjyxm());

		if (StringUtils.hasText(cjdbBean.getCzsj())) {
			Date czsj = DateUtils.parseDate(cjdbBean.getCzsj(), new String[] {
					"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS",
					"yyyyMMddHHmmss" });
			cjdb.setCzsj(czsj);
		}

		if (StringUtils.hasText(cjdbBean.getDdxcsj())) {
			Date ddxcsj = DateUtils.parseDate(cjdbBean.getDdxcsj(),
					new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmss" });
			cjdb.setDdxcsj(ddxcsj);
		}
		if (StringUtils.hasText(cjdbBean.getClwbsj())) {
			Date clwbsj = DateUtils.parseDate(cjdbBean.getClwbsj(),
					new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmss" });
			cjdb.setClwbsj(clwbsj);
		}
		if (StringUtils.hasText(cjdbBean.getScfksj())) {
			Date scfksj = DateUtils.parseDate(cjdbBean.getScfksj(),
					new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd HH:mm:ss.SSS", "yyyyMMddHHmmss" });
			cjdb.setScfksj(scfksj);
		}
		cjdb.setFknr(cjdbBean.getFknr());
		cjdb.setCjqk(cjdbBean.getCjqk());
		if (StringUtils.hasText(cjdbBean.getCjsj())) {
			Date cjsj = DateUtils.parseDate(cjdbBean.getCjsj(), new String[] {
					"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS",
					"yyyyMMddHHmmss" });
			cjdb.setCzsj(cjsj);
		}
	}

}
