/**
 * @author fantedan@tieserv.com
 *
 * @date  2015-1-13 上午10:33:50
 */
package com.tianyi.bph.exception;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = -5567312352199262748L;

	/** XML解析错误 */
	static final public int ERROR_XML_PARSER = 1001;

	/** IO操作异常 */
	static final public int ERROR_IO = 1002;

	/** 信息格式错误 */
	static final public int ERROR_FIELD_FORMAT = 1003;

	/** 未知错误 */
	static final public int ERROR_UNKNOWN = 1004;

	private int errorCode;
	private String message;

	public RestException(int errorCode) {
		this.errorCode = errorCode;
		this.message = super.getMessage();
	}

	public RestException(int errorCode, String msg) {
		this.errorCode = errorCode;
		this.message = msg;
	}

	public RestException(String msg) {
		super(msg);
		this.message = msg;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
