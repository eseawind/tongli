package cn.com.softvan.dao.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
				// 序列化
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				byte[] bytes = baos.toByteArray();
				oos.close();
				return bytes;
		} catch (IOException e) {
			System.out.println("序列化失败!");
//			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return new byte[0];
	}

	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/***
	 * test 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
//		ConfigObject2Bean obj=new ConfigObject2Bean();
//		obj.setNode_name("1");
//		ConfigObject2Bean obj1=(ConfigObject2Bean) unserialize(serialize(obj));
//		System.out.println(obj1.getNode_name());
		
//		VehicleInfoRespBean bean = new VehicleInfoRespBean();
//		bean.setVehicle_id("00000");
//		List<String> l=new ArrayList<String>();
//		l.add("1");
//		l.add("2");
//		serialize(l);
//		Map<String, VehicleInfoRespBean> map = new HashMap<String, VehicleInfoRespBean>();
//		map.put("ttt", bean);
//		byte[] byte1 =serialize(map);
//		
//		Map<String, VehicleInfoRespBean> m=(Map<String, VehicleInfoRespBean>) unserialize(byte1);
//		System.out.println(m.get("ttt"));
	}
}