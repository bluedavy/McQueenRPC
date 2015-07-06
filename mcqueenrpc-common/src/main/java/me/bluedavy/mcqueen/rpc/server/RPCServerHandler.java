package me.bluedavy.mcqueen.rpc.server;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import me.bluedavy.mcqueen.rpc.Codecs;
import me.bluedavy.mcqueen.rpc.RequestWrapper;
import me.bluedavy.mcqueen.rpc.ResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Reflection RPC Server Handler 
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class RPCServerHandler implements ServerHandler {

	private static final Log LOGGER = LogFactory.getLog(RPCServerHandler.class);
	
	// Server Processors     key: servicename    value: service instance
	private static Map<String, Object> processors = new HashMap<String, Object>();
	
	// Cached Server Methods  key: instanceName#methodname$argtype_argtype
	private static Map<String, Method> cacheMethods = new HashMap<String, Method>();
	
	public void registerProcessor(String instanceName,Object instance){
		processors.put(instanceName, instance);
		Class<?> instanceClass = instance.getClass();
		Method[] methods = instanceClass.getMethods();
		for (Method method : methods) {
			Class<?>[] argTypes = method.getParameterTypes();
			StringBuilder methodKeyBuilder = new StringBuilder();
			methodKeyBuilder.append(instanceName).append("#");
			methodKeyBuilder.append(method.getName()).append("$");
			for (Class<?> argClass : argTypes) {
				methodKeyBuilder.append(argClass.getName()).append("_");
			}
			cacheMethods.put(methodKeyBuilder.toString(), method);
		}
	}
	
	public ResponseWrapper handleRequest(final RequestWrapper request){
		ResponseWrapper responseWrapper = new ResponseWrapper(request.getId(),request.getCodecType(),request.getProtocolType());
		String targetInstanceName = new String(request.getTargetInstanceName());
		String methodName = new String(request.getMethodName());
		byte[][] argTypeBytes  = request.getArgTypes();
		String[] argTypes = new String[argTypeBytes.length];
		for(int i = 0; i <argTypeBytes.length; i++) {
		    argTypes[i] = new String(argTypeBytes[i]);
		}
		Object[] requestObjects = null;
		Method method = null;
		try{
			Object processor = processors.get(targetInstanceName);
			if(processor == null){
				throw new Exception("no "+targetInstanceName+" instance exists on the server");
			}
			if (argTypes != null && argTypes.length > 0) {
				StringBuilder methodKeyBuilder = new StringBuilder();
				methodKeyBuilder.append(targetInstanceName).append("#");
				methodKeyBuilder.append(methodName).append("$");
				Class<?>[] argTypeClasses = new Class<?>[argTypes.length];
				for (int i = 0; i < argTypes.length; i++) {
					methodKeyBuilder.append(argTypes[i]).append("_");
					argTypeClasses[i] = Class.forName(argTypes[i]);
				}
				requestObjects = new Object[argTypes.length];
				method = cacheMethods.get(methodKeyBuilder.toString());
				if(method == null){
					throw new Exception("no method: "+methodKeyBuilder.toString()+" find in "+targetInstanceName+" on the server");
				}
				Object[] tmprequestObjects = request
						.getRequestObjects();
				for (int i = 0; i < tmprequestObjects.length; i++) {
					try{
						requestObjects[i] = Codecs.getDecoder(request.getCodecType()).decode(argTypes[i],(byte[])tmprequestObjects[i]);
					}
					catch(Exception e){
						throw new Exception("decode request object args error",e);
					}
				}
			} 
			else {
				method = processor.getClass().getMethod(methodName,
						new Class<?>[] {});
				if(method == null){
					throw new Exception("no method: "+methodName+" find in "+targetInstanceName+" on the server");
				}
				requestObjects = new Object[] {};
			}
			responseWrapper.setResponse(method.invoke(processor, requestObjects));
		}
		catch(Exception e){
			LOGGER.error("server handle request error",e);
			responseWrapper.setException(e);
		}
		return responseWrapper;
	}
}
