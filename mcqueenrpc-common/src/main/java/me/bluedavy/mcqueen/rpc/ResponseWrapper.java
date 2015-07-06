package me.bluedavy.mcqueen.rpc;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */

/**
 * ResponseWrapper
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class ResponseWrapper {

	private int requestId = 0;
	
	private Object response = null;
	
	private boolean isError = false;
	
	private Throwable exception = null;
	
	private int codecType = Codecs.HESSIAN_CODEC;
	
	private int protocolType;
	
	private int messageLen;
	
	private byte[] responseClassName;

	public ResponseWrapper(int requestId,int codecType,int protocolType){
		this.requestId = requestId;
		this.codecType = codecType;
		this.protocolType = protocolType;
	}
	
	public int getMessageLen() {
		return messageLen;
	}

	public void setMessageLen(int messageLen) {
		this.messageLen = messageLen;
	}

	public int getProtocolType() {
		return protocolType;
	}

	public int getCodecType() {
		return codecType;
	}

	public int getRequestId() {
		return requestId;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public boolean isError() {
		return isError;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
		isError = true;
	}
	
	public byte[] getResponseClassName() {
		return responseClassName;
	}

	public void setResponseClassName(byte[] responseClassName) {
		this.responseClassName = responseClassName;
	}
	
}
