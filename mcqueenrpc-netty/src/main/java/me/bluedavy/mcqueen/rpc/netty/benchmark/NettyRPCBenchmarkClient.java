package me.bluedavy.mcqueen.rpc.netty.benchmark;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import me.bluedavy.mcqueen.rpc.benchmark.AbstractRPCBenchmarkClient;
import me.bluedavy.mcqueen.rpc.benchmark.BenchmarkTestService;
import me.bluedavy.mcqueen.rpc.netty.client.NettyClientInvocationHandler;


/**
 * Netty RPC Benchmark Client
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class NettyRPCBenchmarkClient extends AbstractRPCBenchmarkClient {

	public static void main(String[] args) throws Exception{
		new NettyRPCBenchmarkClient().run(args);
	}

	public BenchmarkTestService getProxyInstance(
			List<InetSocketAddress> servers, int clientNums,
			int connectTimeout, String targetInstanceName,
			Map<String, Integer> methodTimeouts, int codectype,Integer protocolType) {
		return (BenchmarkTestService) Proxy.newProxyInstance(
				NettyRPCBenchmarkClient.class.getClassLoader(),
				new Class<?>[] { BenchmarkTestService.class },
				new NettyClientInvocationHandler(servers, clientNums,
						connectTimeout, targetInstanceName, methodTimeouts, codectype, protocolType));
	}

}
