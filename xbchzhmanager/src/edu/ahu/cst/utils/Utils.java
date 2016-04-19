package edu.ahu.cst.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Utils {

	public static String getTextFromStream(InputStream is){
		byte[] b = new byte[1024];
		int len = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			while((len = is.read(b)) != -1){
				baos.write(b,0,len);
			}
			String text = new String(baos.toByteArray());
			baos.close();
			return text;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
