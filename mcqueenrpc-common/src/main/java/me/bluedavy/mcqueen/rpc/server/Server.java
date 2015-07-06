package me.bluedavy.mcqueen.rpc.server;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import java.util.concurrent.ExecutorService;
/**
 * RPC Server Interface
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public interface Server {

	/**
	 * start server at listenPort,requests will be handled in businessThreadPool
	 */
	public void start(int listenPort,ExecutorService businessThreadPool) throws Exception;
	
	/**
	 * register business handler
	 */
	public void registerProcessor(int protocolType,String serviceName,Object serviceInstance);
	
	/**
	 * stop server
	 */
	public void stop() throws Exception;
	
}
