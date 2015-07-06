package me.bluedavy.mcqueen.rpc.benchmark;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

/**
 * Kryo Utils
 * 
 * @author <a href="mailto:jlusdy@gmail.com">jlusdy</a>
 */
public class KryoUtils {
	@SuppressWarnings("rawtypes")
	private static final List<Class> classList = new ArrayList<Class>();
	@SuppressWarnings("rawtypes")
	private static final List<Serializer> serializerList = new ArrayList<Serializer>();
	private static final List<Integer> idList = new ArrayList<Integer>();
	private static final ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			int size = idList.size();
			for (int i = 0; i < size; i++) {
				kryo.register(classList.get(i), serializerList.get(i), idList.get(i));
			}
			kryo.setRegistrationRequired(true);
			kryo.setReferences(false);
			return kryo;
		}
	};

	/**
	 * 
	 */
	private KryoUtils() {
	}

	/**
	 * @param className
	 * @param serializer
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public static synchronized void registerClass(Class className, Serializer serializer, int id) {
		classList.add(className);
		serializerList.add(serializer);
		idList.add(id);
	}

	/**
	 * @return
	 */
	public static Kryo getKryo() {
		return kryos.get();
	}
}
