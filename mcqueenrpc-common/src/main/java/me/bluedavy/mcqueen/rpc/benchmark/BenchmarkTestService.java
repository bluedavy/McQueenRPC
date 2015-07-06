/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
package me.bluedavy.mcqueen.rpc.benchmark;
/**
 * Just for Reflection RPC Benchmark
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public interface BenchmarkTestService {

	public Object execute(Object request);
	
	public Object executePB(PB.RequestObject request);
	
}
