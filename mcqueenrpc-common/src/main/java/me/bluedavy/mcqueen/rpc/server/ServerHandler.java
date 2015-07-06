package me.bluedavy.mcqueen.rpc.server;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import me.bluedavy.mcqueen.rpc.RequestWrapper;
import me.bluedavy.mcqueen.rpc.ResponseWrapper;
/**
 * Server Handler interface,when server receive message,it will handle 
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public interface ServerHandler {

	/**
	 * register business handler,provide for Server
	 */
	public void registerProcessor(String instanceName, Object instance);

	/**
	 * handle the request
	 */
	public ResponseWrapper handleRequest(final RequestWrapper request);

}