package me.bluedavy.mcqueen.rpc.protocol;
/**
 * nfs-rpc
 *   Apache License
 *   
 *   http://code.google.com/p/nfs-rpc (c) 2011
 */
import java.io.ByteArrayInputStream;

import com.caucho.hessian.io.Hessian2Input;
/**
 * Hessian Decoder,use Hessian2
 * 
 * @author <a href="mailto:bluedavy@gmail.com">bluedavy</a>
 */
public class HessianDecoder implements Decoder {
	
	public Object decode(String className,byte[] bytes) throws Exception {
		Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(bytes));
		// avoid child object to parent object problem
		Object resultObject = input.readObject();
		input.close();
		return resultObject;
	}

}
