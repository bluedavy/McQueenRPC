/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
package me.bluedavy.mcqueen.rpc.client;
/**
 * RPC ClientFactory Interface,help for get approviate nums client
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public interface ClientFactory {

	public static final long javaHeapSize = Runtime.getRuntime().maxMemory(); 
	
	/**
	 * when the size of sending bytes in queue reach percent * -Xmx,then do sth based on sendLimitPolicy 
	 * 	to avoid oom
	 * default is 50
	 * 
	 * for example:
	 * 	ClientFactory.sendLimitPercent = 50 -Xmx1g
	 *   if sending bytes size reaches 500m,when u call client.invokeSync then it'll throw NFSRPCRejectException
	 */
	public static int sendLimitPercent = 50;
	
	public static SendLimitPolicy sendLimitPolicy = SendLimitPolicy.REJECT;
	
	/**
	 * get client,default targetIP:targetPort --> one connection
	 * u can give custom the key by give customKey
	 */
	public Client get(final String targetIP, final int targetPort,
			final int connectTimeout, String... customKey) throws Exception;

	/**
	 * get client,create clientNums connections to targetIP:targetPort(or your custom key)
	 */
	public Client get(final String targetIP, final int targetPort,
			final int connectTimeout, final int clientNums, String... customKey)
			throws Exception;

	/**
	 * remove some error client
	 */
	public void removeClient(String key, Client client);
	
	/**
	 * check if exceed the send limit,if exceed then do sth based on SendLimitPolicy
	 */
	public void checkSendLimit() throws Exception;
	
	/**
	 * Enable Send limit,default is false;
	 */
	public void enableSendLimit();

}