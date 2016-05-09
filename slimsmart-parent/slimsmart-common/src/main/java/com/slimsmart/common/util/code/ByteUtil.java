package com.shq.common.util.code;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字节工具类
 * 
 * @author zhutianwei
 * 
 */
public class ByteUtil {
	
	protected final static Logger logger = LoggerFactory.getLogger(ByteUtil.class);
	
	public static Object byte2object(byte[] bytes) {
		Object obj = null;
		try {
			// bytearray to object
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);
			obj = oi.readObject();
			bi.close();
			oi.close();
		} catch (Exception e) {
			logger.error("byte2object error:",e);
		}
		return obj;
	}
	public static byte[] object2byte(Object obj) {
		byte[] bytes = null;
		try {
			// object to bytearray
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		} catch (Exception e) {
			logger.error("object2byte error:",e);
		}
		return bytes;
	}
}
