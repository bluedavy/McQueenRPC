package me.bluedavy.mcqueen.rpc.benchmark;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * ResponseObject Serializer
 * 
 * @author <a href="mailto:jlusdy@gmail.com">jlusdy</a>
 */
public class ResponseObjectSerializer extends Serializer<ResponseObject> {
	/**
	 * @param kryo
	 * @param output
	 * @param resObject
	 */
	@Override
	public void write(Kryo kryo, Output output, ResponseObject resObject) {
		byte[] content = resObject.getBytes();
		output.write(content);
	}

	/**
	 * @param kryo
	 * @param input
	 * @param type
	 * @return
	 */
	public ResponseObject create(Kryo kryo, Input input, Class<ResponseObject> type) {
		return new ResponseObject(input.getBuffer().length - 1);
	}

  @Override
  public ResponseObject read(Kryo kryo, Input input,
      Class<ResponseObject> type) {
    return kryo.readObjectOrNull(input, type);
  }
}
