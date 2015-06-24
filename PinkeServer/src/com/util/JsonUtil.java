package com.util;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	
	/**
	 * ���������͵�����ת��Ϊjson�ַ���
	 * @param returnObj ��Ҫת����������������
	 * @return �ַ���
	 */
	public static String jsonUtil(Object returnObj){
		StringWriter stringWriter = new StringWriter();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = null;
		try {
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					stringWriter);
			objectMapper.writeValue(jsonGenerator, returnObj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jsonStr = stringWriter.toString();
		return jsonStr;
	}
}
