package me.bluedavy.mcqueen.rpc.netty.serialize;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import me.bluedavy.mcqueen.rpc.protocol.ProtocolUtils;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * decode byte[]
 * 	change to pipeline receive requests or responses,let's IO thread do less thing
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class NettyProtocolDecoder extends FrameDecoder {
	
	private ChannelBuffer cumulation;
	
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Object m = e.getMessage();
        if (!(m instanceof ChannelBuffer)) {
            ctx.sendUpstream(e);
            return;
        }

        ChannelBuffer input = (ChannelBuffer) m;
        if (!input.readable()) {
            return;
        }

        ChannelBuffer cumulation = cumulation(ctx);
        if (cumulation.readable()) {
            cumulation.discardReadBytes();
            cumulation.writeBytes(input);
            callDecode(ctx, e.getChannel(), cumulation, e.getRemoteAddress());
        } else {
            callDecode(ctx, e.getChannel(), input, e.getRemoteAddress());
            if (input.readable()) {
                cumulation.writeBytes(input);
            }
        }
	}
	
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer in) throws Exception {
		NettyByteBufferWrapper wrapper = new NettyByteBufferWrapper(in);
		return ProtocolUtils.decode(wrapper, null);
	}
	
	private void callDecode(
            ChannelHandlerContext context, Channel channel,
            ChannelBuffer cumulation, SocketAddress remoteAddress) throws Exception {
		// pipeline to receive requests or responses
		List<Object> results = new ArrayList<Object>();
        while (cumulation.readable()) {
            int oldReaderIndex = cumulation.readerIndex();
            Object frame = decode(context, channel, cumulation);
            if (frame == null) {
                if (oldReaderIndex == cumulation.readerIndex()) {
                    // Seems like more data is required.
                    // Let us wait for the next notification.
                    break;
                } else {
                    // Previous data has been discarded.
                    // Probably it is reading on.
                    continue;
                }
            } else if (oldReaderIndex == cumulation.readerIndex()) {
                throw new IllegalStateException(
                        "decode() method must read at least one byte " +
                        "if it returned a frame (caused by: " + getClass() + ")");
            }
            
            results.add(frame);
        }
        if(results.size() > 0)
        	fireMessageReceived(context, remoteAddress, results);
    }
	
	private void fireMessageReceived(ChannelHandlerContext context, SocketAddress remoteAddress, Object result) {
        Channels.fireMessageReceived(context, result, remoteAddress);
    }
	
	private ChannelBuffer cumulation(ChannelHandlerContext ctx) {
        ChannelBuffer c = cumulation;
        if (c == null) {
            c = ChannelBuffers.dynamicBuffer(
                    ctx.getChannel().getConfig().getBufferFactory());
            cumulation = c;
        }
        return c;
    }

}
