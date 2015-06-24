package com.ifc;

import com.bean.UserBean;


public interface UserInterface {

	/**
	 * ����û��Ƿ����
	 * @param userName
	 * @return boolean
	 */
	boolean checkUser(String userName);

	/**
	 * ��������Ƿ���ȷ
	 * @param userName
	 * @param spwd
	 * @return boolean
	 */
	boolean checkPwd(String userName, String spwd);

	/**
	 * �����û����Ƿ����
	 * @param userName
	 * @return
	 */
	boolean checkExist(String userName);

	/**
	 * ����û�
	 * @param userBean
	 * @return
	 */
	boolean addUser(UserBean userBean);

	/**
	 * ����Id�����û�
	 * @param userName
	 * @return
	 */
	UserBean getUserById(String userName);

	/**
	 * �����û���Ϣ
	 * @param userBean
	 * @return
	 */
	boolean updateUser(UserBean userBean);

	
}
