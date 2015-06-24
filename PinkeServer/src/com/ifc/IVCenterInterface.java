package com.ifc;

import java.util.List;

import com.bean.IVCenterBean;
import com.bean.ReviewBean;

public interface IVCenterInterface {
	
	/**
	 * ��ȡ־Ը�����ļ���
	 * @param limit 
	 * @param start 
	 * @return
	 */
	List<IVCenterBean> getList(int start, int limit);

	/**
	 * ����˵˵
	 * @param ivCenterBean
	 * @return
	 */
	boolean addIVCenter(IVCenterBean ivCenterBean);

	/**
	 * �������
	 * @param reviewBean
	 * @return
	 */
	boolean addReView(ReviewBean reviewBean);

	/**
	 * ��ȡ��key�µ�����
	 * @param reFKey 
	 * @return
	 */
	List<ReviewBean> getReviewList(int reFKey);

}
