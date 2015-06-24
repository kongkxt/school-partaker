package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bean.UserBean;
import com.ifc.IVCenterInterface;
import com.ifc.UserInterface;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.util.Common;
import com.util.DateUtil;
import com.util.JsonUtil;

@Controller
@RequestMapping("/iv")
public class IVController {
	@Autowired
	private UserInterface userIFCProxy;
	
	@Autowired
	private IVCenterInterface ivCenterIFCProxy;
	PrintWriter out;
	@RequestMapping("/login")
	public void login(HttpServletRequest request,HttpServletResponse response){
		String userName = String.valueOf(request.getParameter("username"));
		String spwd = String.valueOf(Common.md5(request.getParameter("password")));
		UserBean returnBean = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		boolean flagUser = userIFCProxy.checkUser(userName);
		boolean flag = false;
		boolean codeFlag = false;
		if(!flagUser){
			returnMap.put("returnMsg","�û�������");
			returnMap.put("target","username");
		}else{
			flag = userIFCProxy.checkPwd(userName,spwd);
			if(flag){
				returnMap.put("returnMsg","��¼�ɹ�");
				codeFlag = true;
			}else{
				returnMap.put("returnMsg","�������");
				returnMap.put("target","password");
			}
		}
		
		if(codeFlag){
			//�ɹ�
			returnMap.put("returnCode",0);
			returnMap.put("sessionId",request.getSession().getId());
			returnBean = userIFCProxy.getUserById(userName);
			returnBean.setShareSet(null);
		}else{
			//ʧ��
			returnMap.put("returnCode",1);
		}
		returnMap.put("userInfo",returnBean);
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/regist")
	public void regist(HttpServletRequest request,HttpServletResponse response){
		String userName = request.getParameter("username");
		String spwd = Common.md5(request.getParameter("password"));
		String sname = request.getParameter("pname");
		String scard = request.getParameter("idCard");
		String sphone = request.getParameter("phone");
		String sarea = request.getParameter("area");
		String address = request.getParameter("address");
		String sex = request.getParameter("sex");
		String school = request.getParameter("school");
		String email = request.getParameter("email");
		
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		boolean existFlag = userIFCProxy.checkExist(userName);
		if(existFlag){
			UserBean userBean = new UserBean();
			userBean.setSuser(userName);
			userBean.setSpwd(spwd);
			userBean.setSarea(sarea);
			userBean.setScard(scard);
			userBean.setSname(sname);
			userBean.setSphone(sphone);
			userBean.setIvScore(0);
			userBean.setIvTimes(0);
			userBean.setIvTitle(0);
			userBean.setSaddress(address);
			userBean.setSsex(Integer.valueOf(sex));;
			userBean.setSschool(school);
			userBean.setSemail(email);;
			boolean addFlag = userIFCProxy.addUser(userBean);
			if(addFlag){
				returnMap.put("returnCode", 0);
				returnMap.put("returnMsg", "ע��ɹ�");
			}else{
				returnMap.put("returnCode", 1);
				returnMap.put("returnMsg", "ע��ʧ��");
			}
		}else{
			returnMap.put("returnCode", 1);
			returnMap.put("returnMsg", "���û����ѱ�ʹ��");
		}
		
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/alterInfo")
	public void alterInfo(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("phone");
		String name = request.getParameter("pname");
		String school  = request.getParameter("school");
		String email  = request.getParameter("email");
		String sex  = request.getParameter("sex");
		String suser = request.getParameter("suser");
		
		UserBean userBean = userIFCProxy.getUserById(suser);
	
		if(userBean != null){
			if(phone != null){
				userBean.setSphone(phone);
			}
			if(name != null){
				userBean.setSname(name);
			}
			if(school != null){
				userBean.setSschool(school);
			}
			if(sex != null){
				userBean.setSsex(Integer.valueOf(sex));
			}
			if(email != null){
				userBean.setSemail(email);
			}

		}
		boolean updateFlag = userIFCProxy.updateUser(userBean);
		UserBean returnBean = userIFCProxy.getUserById(suser);
		returnBean.setShareSet(null);
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		if(updateFlag){
			returnMap.put("returnCode",0);
			returnMap.put("returnMsg","���ĳɹ�");
			returnMap.put("userInfo",returnBean);
		}else{
			returnMap.put("returnCode",1);
			returnMap.put("returnMsg","����ʧ��");
		}
		
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/alterPwd")
	public void alterPwd(HttpServletRequest request,HttpServletResponse response){
		String oldPwd = Common.md5(request.getParameter("oldPwd"));
		String newPwd = Common.md5(request.getParameter("newPwd"));
		String suser = request.getParameter("suser");
		
		boolean comfirm=false;
		
		UserBean userBean = userIFCProxy.getUserById(suser);
		if(userBean != null){
			if(newPwd != null){
				if(oldPwd!=null&&userBean.getSpwd().equals(oldPwd)){
					userBean.setSpwd(newPwd);
					comfirm=true;
				}
			}
		}
	
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		
		if (comfirm) {
			boolean updateFlag = userIFCProxy.updateUser(userBean);
			if(updateFlag){
				returnMap.put("returnCode",0);
				returnMap.put("returnMsg","���ĳɹ�");
			}else{
				returnMap.put("returnCode",1);
				returnMap.put("returnMsg","����ʧ��");
			}
		}else {
			returnMap.put("returnCode",1);
			returnMap.put("returnMsg","�����벻��ȷ");
		}
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/downLoadImag")
	public void downLoadImag(HttpServletRequest request,HttpServletResponse response){
		String imageName = request.getParameter("image");
		String path = request.getRealPath("image") + "/" + imageName;
		try {
			File file = new File(path);
			InputStream inputStream = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
			response.addCookie(cookie);
			OutputStream outputStream = response.getOutputStream();
			int index = -1;
			byte[] b = new byte[10240];
			while((index = inputStream.read(b))!=-1){
				outputStream.write(b, 0, index);
			}
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/uploadImag")
	public void uploadImag(HttpServletRequest request,HttpServletResponse response){
		String suser = request.getParameter("suser");
		UserBean userBean = null;
		String imagePath = "";
		String imageName = "";
		String savePath = "";
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
			if(suser != null){
				userBean = userIFCProxy.getUserById(suser);
			}
			boolean isFirst = false;
			if(userBean != null){
				imagePath = userBean.getSimgPath();
				if(imagePath!=null){
					imageName = imagePath.substring(imagePath.indexOf("=")+1);
				}else{
					isFirst = true;
					imageName = DateUtil.getDate() + ".jpg";
					imagePath = "image="+imageName;
					userBean.setSimgPath(imagePath);
					this.userIFCProxy.updateUser(userBean);
				}
			}
			savePath = request.getRealPath("image") + "/" + imageName;
			File file = new File(savePath);
			if(file.exists()){
				file.delete();
			}
			outputStream = new FileOutputStream(file);
			int len = -1;
			byte[] b = new byte[10240];
			while((len=inputStream.read(b)) != -1){
				outputStream.write(b, 0, len);
			}
			Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("returnCode",0);
			returnMap.put("returnMsg","�ϴ��ɹ�");
			if(isFirst){
				returnMap.put("firstUpload",1);
			}else{
				returnMap.put("firstUpload",0);
			}
			returnMap.put("imagePath",imagePath);
			String json = JsonUtil.jsonUtil(returnMap);
			System.out.println(json);
			response.setContentType("application/json ; charset=utf-8");
			Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
			response.addCookie(cookie);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
