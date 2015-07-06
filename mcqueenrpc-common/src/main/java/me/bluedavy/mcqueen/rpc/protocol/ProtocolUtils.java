/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
package me.bluedavy.mcqueen.rpc.protocol;

import me.bluedavy.mcqueen.rpc.ProtocolFactory;
import me.bluedavy.mcqueen.rpc.RequestWrapper;
import me.bluedavy.mcqueen.rpc.ResponseWrapper;

/**
 * Protocol Header
 * 	VERSION(1B): Protocol Version
 *  TYPE(1B):    Protocol Type,so u can custom your protocol
 *  CUSTOM PROTOCOL (such as RPCProtocol)
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class ProtocolUtils {

	public static final int HEADER_LEN = 2;
	
	public static final byte CURRENT_VERSION = (byte)1;
	
	public static ByteBufferWrapper encode(Object message,ByteBufferWrapper bytebufferWrapper) throws Exception {
		Integer type = 0;
		if(message instanceof RequestWrapper){
			type = ((RequestWrapper)message).getProtocolType();
		}
		else if(message instanceof ResponseWrapper){
			type = ((ResponseWrapper)message).getProtocolType();
		}
		return ProtocolFactory.getProtocol(type).encode(message, bytebufferWrapper);
	}

	public static Object decode(ByteBufferWrapper wrapper, Object errorObject) throws Exception {
		final int originPos = wrapper.readerIndex();
		if(wrapper.readableBytes() < 2){
			wrapper.setReaderIndex(originPos);
        	return errorObject;
        }
		int version = wrapper.readByte();
		if(version == 1){
			int type = wrapper.readByte();
			Protocol protocol = ProtocolFactory.getProtocol(type);
			if(protocol == null){
				throw new Exception("Unsupport protocol type: "+type);
			}
			return ProtocolFactory.getProtocol(type).decode(wrapper, errorObject, new int[]{originPos});
		}
		else{
			throw new Exception("Unsupport protocol version: "+ version);
		}
	}

}
