package me.bluedavy.mcqueen.rpc.netty.client;

/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import java.net.InetSocketAddress;

import me.bluedavy.mcqueen.rpc.RequestWrapper;
import me.bluedavy.mcqueen.rpc.ResponseWrapper;
import me.bluedavy.mcqueen.rpc.client.AbstractClient;
import me.bluedavy.mcqueen.rpc.client.Client;
import me.bluedavy.mcqueen.rpc.client.ClientFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;


/**
 * Netty Client
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class NettyClient extends AbstractClient {

	private static final Log LOGGER = LogFactory.getLog(NettyClient.class);

	private ChannelFuture cf;

	private String key;

	private int connectTimeout;

	public NettyClient(ChannelFuture cf, String key, int connectTimeout) {
		this.cf = cf;
		this.key = key;
		this.connectTimeout = connectTimeout;
	}

	public void sendRequest(final RequestWrapper wrapper, final int timeout)
			throws Exception {
		final long beginTime = System.currentTimeMillis();
		final Client self = this;
		ChannelFuture writeFuture = cf.getChannel().write(wrapper);
		// use listener to avoid wait for write & thread context switch
		writeFuture.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					return;
				}
				String errorMsg = "";
				// write timeout
				if (System.currentTimeMillis() - beginTime >= timeout) {
					errorMsg = "write to send buffer consume too long time("
							+ (System.currentTimeMillis() - beginTime)
							+ "),request id is:" + wrapper.getId();
				}
				if (future.isCancelled()) {
					errorMsg = "Send request to " + cf.getChannel().toString()
							+ " cancelled by user,request id is:"
							+ wrapper.getId();
				}
				if (!future.isSuccess()) {
					if (cf.getChannel().isConnected()) {
						// maybe some exception,so close the channel
						cf.getChannel().close();
					} 
					else {
						NettyClientFactory.getInstance().removeClient(key, self);
					}
					errorMsg = "Send request to " + cf.getChannel().toString() + " error" + future.getCause();
				}
				LOGGER.error(errorMsg);
				ResponseWrapper response = new ResponseWrapper(wrapper.getId(), wrapper.getCodecType(), wrapper.getProtocolType());
				response.setException(new Exception(errorMsg));
				self.putResponse(response);
			}
		});
	}

	public String getServerIP() {
		return ((InetSocketAddress) cf.getChannel().getRemoteAddress())
				.getHostName();
	}

	public int getServerPort() {
		return ((InetSocketAddress) cf.getChannel().getRemoteAddress())
				.getPort();
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public long getSendingBytesSize() {
		// TODO: implement it
		return 0;
	}

	public ClientFactory getClientFactory() {
		return NettyClientFactory.getInstance();
	}

}
