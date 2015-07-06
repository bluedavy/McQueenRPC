package me.bluedavy.mcqueen.rpc.benchmark;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * RequestObject Serializer
 * 
 * @author <a href="mailto:jlusdy@gmail.com">jlusdy</a>
 */
public class RequestObjectSerializer extends Serializer<RequestObject> {
	/**
	 * @param kryo
	 * @param output
	 * @param reqObject
	 */
	@Override
	public void write(Kryo kryo, Output output, RequestObject reqObject) {
		byte[] content = reqObject.getBytes();
		output.write(content);
	}

	/**
	 * @param kryo
	 * @param input
	 * @param type
	 * @return
	 */
	public RequestObject create(Kryo kryo, Input input, Class<RequestObject> type) {
		return new RequestObject(input.getBuffer().length - 1);
	}

  @Override
  public RequestObject read(Kryo kryo, Input input, Class<RequestObject> type) {
    return kryo.readObjectOrNull(input, type);
  }
}
