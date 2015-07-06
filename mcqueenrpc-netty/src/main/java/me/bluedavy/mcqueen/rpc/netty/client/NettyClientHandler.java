package me.bluedavy.mcqueen.rpc.netty.client;

import java.io.IOException;
import java.util.List;

import me.bluedavy.mcqueen.rpc.ResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;


public class NettyClientHandler extends SimpleChannelUpstreamHandler {
	
	private static final Log LOGGER = LogFactory.getLog(NettyClientHandler.class);
	
	private static final boolean isDebugEnabled = LOGGER.isDebugEnabled();
	
	private NettyClientFactory factory;
	
	private String key;
	
	private NettyClient client;
	
	public NettyClientHandler(NettyClientFactory factory,String key){
		this.factory = factory;
		this.key = key;
	}
	
	public void setClient(NettyClient client){
		this.client = client;
	}
	
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if(e.getMessage() instanceof List){
			@SuppressWarnings("unchecked")
			List<ResponseWrapper> responses = (List<ResponseWrapper>)e.getMessage();
			if(isDebugEnabled){
				// for performance trace
				LOGGER.debug("receive response list from server: "+ctx.getChannel().getRemoteAddress()+",list size is:"+responses.size());
			}
			client.putResponses(responses);
		}
		else if(e.getMessage() instanceof ResponseWrapper){
			ResponseWrapper response = (ResponseWrapper)e.getMessage();
			if(isDebugEnabled){
				// for performance trace
				LOGGER.debug("receive response list from server: "+ctx.getChannel().getRemoteAddress()+",request is:"+response.getRequestId());
			}
			client.putResponse(response);
		}
		else{
			LOGGER.error("receive message error,only support List || ResponseWrapper");
			throw new Exception("receive message error,only support List || ResponseWrapper");
		}
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		if(!(e.getCause() instanceof IOException)){
			// only log
			LOGGER.error("catch some exception not IOException",e.getCause());
		}
	}
	
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		LOGGER.warn("connection closed: "+ctx.getChannel().getRemoteAddress());
		factory.removeClient(key,client);
	}
	
}
