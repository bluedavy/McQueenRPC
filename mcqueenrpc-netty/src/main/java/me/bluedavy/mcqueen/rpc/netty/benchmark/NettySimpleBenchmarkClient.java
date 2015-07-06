package me.bluedavy.mcqueen.rpc.netty.benchmark;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import me.bluedavy.mcqueen.rpc.benchmark.AbstractSimpleProcessorBenchmarkClient;
import me.bluedavy.mcqueen.rpc.client.ClientFactory;
import me.bluedavy.mcqueen.rpc.netty.client.NettyClientFactory;

/**
 * Netty Direct Call RPC Benchmark Client
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class NettySimpleBenchmarkClient extends AbstractSimpleProcessorBenchmarkClient {

	public static void main(String[] args) throws Exception{
		new NettySimpleBenchmarkClient().run(args);
	}
	
	public ClientFactory getClientFactory() {
		return NettyClientFactory.getInstance();
	}

}
