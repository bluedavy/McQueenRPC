package me.bluedavy.mcqueen.rpc.netty.client;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import me.bluedavy.mcqueen.rpc.netty.serialize.NettyProtocolDecoder;
import me.bluedavy.mcqueen.rpc.netty.serialize.NettyProtocolEncoder;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * Netty Factory
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class NettyClientPipelineFactory implements ChannelPipelineFactory {

	private SimpleChannelUpstreamHandler handler;
	
	public NettyClientPipelineFactory(SimpleChannelUpstreamHandler handler){
		this.handler = handler;
	}
	
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = new DefaultChannelPipeline();
		pipeline.addLast("decoder", new NettyProtocolDecoder());
		pipeline.addLast("encoder", new NettyProtocolEncoder());
		pipeline.addLast("handler", handler);
		return pipeline;
	}

}
