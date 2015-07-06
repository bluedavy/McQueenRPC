/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
package me.bluedavy.mcqueen.rpc.client;

import java.util.List;

import me.bluedavy.mcqueen.rpc.ResponseWrapper;


/**
 * RPC Client Interface
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public interface Client {

	/**
	 * invoke sync via simple call
	 * 
	 * @param message
	 * @param timeout
	 * @param codecType serialize/deserialize type
	 * @param protocolType
	 * @return Object
	 * @throws Exception
	 */
	public Object invokeSync(Object message, int timeout, int codecType, int protocolType)
			throws Exception;

	/**
	 * invoke sync via rpc
	 * 
	 * @param targetInstanceName
	 *            server instance name
	 * @param methodName
	 *            server method name
	 * @param argTypes
	 *            server method arg types
	 * @param args
	 *            send to server request args
	 * @param timeout
	 *            rcp timeout
	 * @param codecType
	 *            serialize/deserialize type
	 * @param protocolType
	 * @return Object return response
	 * @throws Exception
	 *             if some exception,then will be throwed
	 */
	public Object invokeSync(String targetInstanceName, String methodName,
			String[] argTypes, Object[] args, int timeout, int codecType, int protocolType)
			throws Exception;

	/**
	 * receive response from server
	 */
	public void putResponse(ResponseWrapper response) throws Exception;
	
	/**
	 * receive responses from server
	 */
	public void putResponses(List<ResponseWrapper> responses) throws Exception;
	
	/**
	 * server address
	 * 
	 * @return String
	 */
	public String getServerIP();

	/**
	 * server port
	 * 
	 * @return int
	 */
	public int getServerPort();

	/**
	 * connect timeout
	 * 
	 * @return
	 */
	public int getConnectTimeout();
	
	/**
	 * get sending bytes size
	 * 
	 * @return long
	 */
	public long getSendingBytesSize();
	
	/**
	 * Get factory
	 */
	public ClientFactory getClientFactory();

}
