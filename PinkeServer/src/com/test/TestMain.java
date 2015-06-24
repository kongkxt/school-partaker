package com.test;


public class TestMain {
	/**
	 * 
	 * @param args
	
	public static void main(String[] args) {
		MockHttpServletRequest request = new MockHttpServletRequest("post",
		"/my/user");
		request.addParameter("username","111");
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		MyController controller = new MyController();
		UserInterface userIFCProxy = SpringUtil.getBean("userIFCProxy",
		UserInterface.class);
		//ע��ӿڴ���
		controller.setUserIFCProxy(userIFCProxy);
		
		/**
		 * ����û�
		UserBean userBean = new UserBean();
		userBean.setSuser("xxxx2");
		userBean.setSname("yyyy2");
		userBean.setSpwd("123456");

		DeptBean addDeptBean = new DeptBean();
		addDeptBean.setDeptId(8);
		addDeptBean.setDeptName("����8");
		userBean.setDeptBean(addDeptBean);
		//��Ҫ�����UserBeanע�뵽MyController��
		controller.setUserBean(userBean);
		ModelAndView modelAndView = controller.saveAddUser(request, response);
		ModelMap modelMap = modelAndView.getModelMap();
		MessageBean messageBean = (MessageBean)modelMap.get("messageBean");
		System.out.println("���ؽ������ֵ:" + messageBean.isMessageFlag() + "������Ϣ:" + messageBean.getReturnMessage());
		 */		
		
		/**
		 * �õ��û�����Ϣ
		ModelAndView modelAndView = controller.getUser(request, response);
		System.out.println(modelAndView.getViewName());
		ModelMap modelMap = modelAndView.getModelMap();
		List<UserBean> userList = (List<UserBean>)modelMap.get("userList");
		for (UserBean bean : userList) {
			DeptBean deptBean = bean.getDeptBean();
			String str = "�û�����" + bean.getSuser() + "   ������" + bean.getSname()
					+ "   ����:" + bean.getSpwd() + "  ����ID��" + deptBean.getDeptId() + "  ��������:" + deptBean.getDeptName();
			System.out.println(str);
		}
		*/
		
		/**
		 * ���ѧ���ɼ� 
		ScoreBeanId scoreBeanId = new ScoreBeanId(3,"����");
		ScoreBean scoreBean = new ScoreBean(scoreBeanId,"����",89.0f);
		//��Ҫ�����ScoreBeanע��MyController
		controller.setScoreBean(scoreBean);
		ModelAndView modelAndView = controller.saveAddScore(request, response);
		System.out.println(modelAndView.getViewName());
		ModelMap modelMap = modelAndView.getModelMap();
		MessageBean messageBean = (MessageBean)modelMap.get("messageBean");
		System.out.println("���ؽ������ֵ:" + messageBean.isMessageFlag() + "������Ϣ:" + messageBean.getReturnMessage());
		 */				
		
		/**
		 * ��ȡѧ���ɼ�
		ModelAndView modelAndView = controller.getScore(request,response);
		System.out.println(modelAndView.getViewName());
		ModelMap modelMap = modelAndView.getModelMap();
		List<ScoreBean> scoreList = (List<ScoreBean>)modelMap.get("scoreList");
		for (ScoreBean bean : scoreList) {
			ScoreBeanId beanId = bean.getId();
			String str = "ѧ����ţ�" + beanId.getSno() + "   ��Ŀ��" + beanId.getSubject()
					+ "   ����:" + bean.getSname() + "  ������" + bean.getScore();
			System.out.println(str);
		}

	}
*/
}
