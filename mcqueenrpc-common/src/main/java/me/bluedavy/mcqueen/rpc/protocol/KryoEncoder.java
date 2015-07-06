package me.bluedavy.mcqueen.rpc.protocol;

import me.bluedavy.mcqueen.rpc.benchmark.KryoUtils;

import com.esotericsoftware.kryo.io.Output;

/**
 * Kryo Encoder
 * 
 * @author <a href="mailto:jlusdy@gmail.com">jlusdy</a>
 */
public class KryoEncoder implements Encoder {
	/**
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public byte[] encode(Object object) throws Exception {
		Output output = new Output(256);
		KryoUtils.getKryo().writeClassAndObject(output, object);
		return output.toBytes();
	}

}
