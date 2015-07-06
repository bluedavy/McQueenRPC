package me.bluedavy.mcqueen.rpc.netty.serialize;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import me.bluedavy.mcqueen.rpc.protocol.ProtocolUtils;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Encode Message
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class NettyProtocolEncoder extends OneToOneEncoder {
	
	protected Object encode(ChannelHandlerContext ctx, Channel channel,Object message) throws Exception {
		NettyByteBufferWrapper byteBufferWrapper = new NettyByteBufferWrapper();
		ProtocolUtils.encode(message, byteBufferWrapper);
		return byteBufferWrapper.getBuffer();
	}

}
