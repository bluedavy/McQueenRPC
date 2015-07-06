package me.bluedavy.mcqueen.rpc;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RequestWrapper
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class RequestWrapper {
	
	private static AtomicInteger incId = new AtomicInteger(0);
	
	private byte[] targetInstanceName;
	
	private byte[] methodName;
	
	private byte[][] argTypes;

	private Object[] requestObjects = null;
	
	private Object message = null;
	
	private int timeout = 0;
	
	private int id = 0;
	
	private int protocolType;
	
	private int codecType = Codecs.HESSIAN_CODEC;
	
	private int messageLen;

	public RequestWrapper(Object message,int timeout,int codecType,int protocolType){
		this(message,timeout,incId.incrementAndGet(),codecType,protocolType);
	}
	
	public RequestWrapper(Object message,int timeout,int id,int codecType,int protocolType){
		this.message = message;
		this.id = id;
		this.timeout = timeout;
		this.codecType = codecType;
		this.protocolType = protocolType;
	}

	public RequestWrapper(byte[] targetInstanceName,byte[] methodName,byte[][] argTypes,
						  Object[] requestObjects,int timeout,int codecType,int protocolType){
		this(targetInstanceName,methodName,argTypes,requestObjects,timeout,incId.incrementAndGet(),codecType,protocolType);
	}

	public RequestWrapper(byte[] targetInstanceName,byte[] methodName,byte[][] argTypes,
						  Object[] requestObjects,int timeout,int id,int codecType,int protocolType){
		this.requestObjects = requestObjects;
		this.id = id;
		this.timeout = timeout;
		this.targetInstanceName = targetInstanceName;
		this.methodName = methodName;
		this.argTypes = argTypes;
		this.codecType = codecType;
		this.protocolType = protocolType;
	}

	public int getMessageLen() {
		return messageLen;
	}

	public void setMessageLen(int messageLen) {
		this.messageLen = messageLen;
	}
	
	public void setArgTypes(byte[][] argTypes) {
		this.argTypes = argTypes;
	}
	
	public int getProtocolType() {
		return protocolType;
	}

	public int getCodecType() {
		return codecType;
	}
	
	public Object getMessage() {
		return message;
	}
	
	public byte[] getTargetInstanceName() {
		return targetInstanceName;
	}

	public byte[] getMethodName() {
		return methodName;
	}

	public int getTimeout() {
		return timeout;
	}

	public Object[] getRequestObjects() {
		return requestObjects;
	}

	public int getId() {
		return id;
	}	
	
	public byte[][] getArgTypes() {
		return argTypes;
	}
	
}
