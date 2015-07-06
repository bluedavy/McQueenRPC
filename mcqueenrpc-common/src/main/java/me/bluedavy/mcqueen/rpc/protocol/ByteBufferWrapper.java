/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
package me.bluedavy.mcqueen.rpc.protocol;
/**
 * ByteBufferWrapper interface,help for intergrate different network framework
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public interface ByteBufferWrapper {

	public ByteBufferWrapper get(int capacity);
	
	public void writeByte(int index,byte data);
	
	public void writeByte(byte data);
	
	public byte readByte();
	
	public void writeInt(int data);
	
	public void writeBytes(byte[] data);
	
	public int readableBytes();
	
	public int readInt();
	
	public void readBytes(byte[] dst);
	
	public int readerIndex();
	
	public void setReaderIndex(int readerIndex);
	
}
