package com.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.bean.MessageBean;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private Session session;
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtil.sessionFactory = sessionFactory;
	}

	/**
	 * ���淽���ķ�װ
	 * @param <T>
	 * @param obj
	 *            Ҫ����Ķ���
	 * @return ���ز��������MessageBean
	 */
	public <T> MessageBean saveObj(T obj) {
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(obj);
			session.getTransaction().commit();
			messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}

	/**
	 * ͨ��IDɾ������
	 *MessageBean
	 * @param <T>
	 * @param t_class Ҫɾ�������Class
	 * @param id �����ID
	 * @return ���ز��������MessageBean
	 */
	public static <T> MessageBean deleteObjById(Class<T> t_class, Serializable id) {
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			T t = (T) session.get(t_class, id);
			session.delete(t);
			messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}
	
	/**
	 * ͨ��ִ��hql���ɾ������
	 *MessageBean
	 * @param <T>
	 * @param hql ִ��ɾ������hql���
	 * @return ���ز��������MessageBean
	 */
	public static <T> MessageBean deleteObjByHql(String hql) {
		MessageBean messageBean = new MessageBean();
		Query query = null;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			query =  session.createQuery(hql);
			int count = query.executeUpdate();
			session.getTransaction().commit();
			if(count>0)
				messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}

	/**
	 * ͨ��ID�õ�Ҫ���ҵĶ���
	 *T
	 * @param <T>
	 * @param t_class Ҫ���Ҷ����Class
	 * @param id �����ID
	 * @return ���صõ��Ķ���
	 */
	public  <T>T getObjById(Class<T> t_class,Serializable id){
		Session session = sessionFactory.openSession();
		T t = null;
		try {
			t = (T) session.get(t_class, id);
			this.session = session;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}
	
	public void closeSession(){
		try {
			if(this.session!=null){
				this.session.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ͨ��hql��ʵ�ַ�ҳ�Ĳ���
	 *List<T> 
	 * @param <T>
	 * @param hql ���ҵ�hql���
	 * @param start ��ҳ�Ŀ�ʼ
	 * @param size ��ҳ�Ĵ�С
	 * @return ���صõ��ļ���
	 */
	public <T> List<T> getList(String hql,int start,int size) {
		Session session = sessionFactory.openSession();
		Query query = null;
		List<T> objList = null;
		try {
			query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(size);
			objList = query.list();
			this.session = session;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}

	/**
	 * ͨ��hql���õ�Ҫ���ҵļ���
	 *List<T>
	 * @param <T>
	 * @param hql ���ҵ�hql���
	 * @return ���صõ��ļ���
	 */
	public static <T> List<T> getList(String hql) {
		//sessionFactory = (SessionFactory)SpringUtil.getBean("sessionFactory", LocalSessionFactoryBean.class);
		Session session = sessionFactory.openSession();
		Query query = null;
		List<T> objList = null;
		try {
			query = session.createQuery(hql);
			objList = query.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}
	
	/**
	 * �����ҵõ��Ľ��ӳ���Map<String,Object> ����ʽ����
	 *List<Map<String,Object>> 
	 * @param hql ִ�в�ѯ����hql
	 * @return ���صõ��ļ���
	 */
	public static List<Map<String,Object>> getListForMap(String hql) {
		Session session = sessionFactory.openSession();
		Query query = null;
		List<Map<String,Object>> objList = null;
		try {
			query = session.createQuery(hql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			objList = query.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}
	
	/**
	 * ��Hibernate��ִ��sql���õ���ҳ��ѯ�Ľ��
	 *List<Map<String,Object>>
	 * @param sql Ҫ��ѯ��sql
	 * @param start sql��ҳ��ѯ�Ŀ�ʼ
	 * @param count sql��ҳ��ѯ�Ĵ�С
	 * @return ���صõ��ļ���
	 */
	public static List<Map<String,Object>> sqlQuery(String sql, int start, int count) {
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		List<Map<String,Object>> objList = null;
		try {
			query = session.createSQLQuery(sql);
			query.setFirstResult(start);
			query.setMaxResults(count);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			objList = query.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}
	
	/**
	 * ͨ��sql����ѯ
	 *List<Map<String,Object>>
	 * @param sql ִ�в�ѯ��sql���
	 * @return ���صõ��ļ���
	 */
	public static List<Map<String,Object>> sqlQuery(String sql) {
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		List<Map<String,Object>> objList = null;
		try {
			query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			objList = query.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}

	/**
	 * ִ��Hibernate�ĸ��²���
	 *MessageBean
	 * @param <T>
	 * @param hql ִ�и��²�����HQL���
	 * @param t Ҫ�������ݷ�װ�Ķ���
	 * @return ���ز��������MessageBean
	 */
	public static <T> MessageBean updateObj(String hql,T t){
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		Query query = null;
		try {
			session.beginTransaction();
			query = session.createQuery(hql);
			query.setProperties(t);
			query.executeUpdate();
			session.getTransaction().commit();
			messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}
	
	/**
	 * ����ĳ������
	 *boolean
	 * @param <T>
	 * @param t Ҫ���µĶ���
	 * @return ���ز��������MessageBean
	 */
	public static <T> MessageBean updateObj(T t){
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.update(t);
			session.getTransaction().commit();
			messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}
	
	/**
	 * ͨ��SQL���ִ�и��²���
	 *boolean
	 * @param <T>
	 * @param sql ִ�и��²�����SQL���
	 * @return ���ز��������MessageBean
	 */
	public static <T> MessageBean updateObj(String sql){
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		try {
			session.beginTransaction();
			query = session.createSQLQuery(sql);
			int rows = query.executeUpdate();
			session.getTransaction().commit();
			if(rows>0){
				messageBean.setMessageFlag(true);
			}else{
				messageBean.setMessageFlag(false);
			}
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}
	
	/**
	 * ͨ��ִ��HQL���õ����ݵ�������
	 *Long
	 * @param <T>
	 * @param hql Ҫִ�е�HQL���
	 * @return ���صõ���������
	 */
	public static <T> Long getCount(String hql){
		Session session = sessionFactory.openSession();
		Query query = null;
		Long count;
		try {
			query = session.createQuery(hql);
			count = (Long)query.uniqueResult();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return count;
	}
	
	/**
	 * ͨ��ִ��SQL���õ����ݵ�������
	 *Long
	 * @param <T>
	 * @param sql Ҫִ�е�SQL���
	 * @return ���صõ���������
	 */
	public static <T> Long getCountBySql(String sql){
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		Long count;
		try {
			query = session.createSQLQuery(sql);
			count = new BigDecimal(query.uniqueResult().toString()).longValue();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return count;
	}
	
	/**
	 * ͨ��ִ��hql���ɾ������
	 *UserShare
	 * @param <T>
	 * @param hql ִ��ɾ������hql���
	 * @return ���ز��������MessageBean
	 */
	public static int deleteUserShareBySql(String sql) {
	
		Query query = null;
		Session session = sessionFactory.openSession();
		int count;
		try {
			session.beginTransaction();
			query =  session.createSQLQuery(sql);
			count = query.executeUpdate();
			session.getTransaction().commit();
				
			session.close();
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		
		}
		return count;
	}
}
