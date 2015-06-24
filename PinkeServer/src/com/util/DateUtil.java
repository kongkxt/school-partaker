package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author С��
 *
 */
public class DateUtil {
	/**
	 * �õ�ʱ���
	 * @return �ַ���
	 */
	public static String getDate(){
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
		String strTime = simpleDate.format(date);
		return strTime;
	}
	/**
	 * �õ���ʽ��֮���ʱ���ַ���
	 * @return �ַ���
	 */
	public static String getFormatDate(){
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd");
		String strTime = simpleDate.format(date);
		return strTime;
	}
	/**
	 * �õ��������ʱ���ַ���
	 * @return �ַ���
	 */
	public static String getMoreFormatDate(){
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
		String strTime = simpleDate.format(date);
		return strTime;
	}
}
