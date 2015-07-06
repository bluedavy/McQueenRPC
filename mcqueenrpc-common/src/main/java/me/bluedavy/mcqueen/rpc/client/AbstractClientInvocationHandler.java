package me.bluedavy.mcqueen.rpc.client;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * Abstract Client Invocation Handler,help for client proxy 
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public abstract class AbstractClientInvocationHandler implements InvocationHandler {

	private List<InetSocketAddress> servers;
	
	private int clientNums;
	
	private int connectTimeout;
	
	private String targetInstanceName;
	
	private int codecType;
	
	private int protocolType;
	
	// per method timeout,some special method use methodName.toLowerCase to set timeout,other use *
	private Map<String, Integer> methodTimeouts;
	
	public AbstractClientInvocationHandler(List<InetSocketAddress> servers,int clientNums,int connectTimeout,
										   String targetInstanceName,Map<String, Integer> methodTimeouts,
										   int codecType,int protocolType){
		this.servers = Collections.unmodifiableList(servers);
		this.clientNums = clientNums;
		this.connectTimeout = connectTimeout;
		this.methodTimeouts = methodTimeouts;
		this.targetInstanceName = targetInstanceName;
		this.codecType = codecType;
		this.protocolType = protocolType;
	}
	
	public void updateServers(List<InetSocketAddress> servers){
		this.servers = Collections.unmodifiableList(servers);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		InetSocketAddress server = null;
		if(servers.size() == 1){
			server = servers.get(0);
		}
		else{
			// random is not thread-safe,so...
			Random random = new Random();
			server = servers.get(random.nextInt(servers.size()));
		}
		Client client = getClientFactory().get(server.getAddress().getHostAddress(), server.getPort(), connectTimeout, clientNums);
		String methodName = method.getName();
		String[] argTypes = createParamSignature(method.getParameterTypes());
		int timeout = 0;
		if(methodTimeouts.containsKey(methodName.toLowerCase())){
			timeout = methodTimeouts.get(methodName);
		}
		else{
			timeout = methodTimeouts.get("*");
		}
		return client.invokeSync(targetInstanceName, methodName, argTypes, args, timeout, codecType, protocolType);
	}
	
	private String[] createParamSignature(Class<?>[] argTypes) {
        if (argTypes == null || argTypes.length == 0) {
            return new String[] {};
        }
        String[] paramSig = new String[argTypes.length];
        for (int x = 0; x < argTypes.length; x++) {
            paramSig[x] = argTypes[x].getName();
        }
        return paramSig;
    }
	
	public abstract ClientFactory getClientFactory();

}
