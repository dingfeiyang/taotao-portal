package com.taotao.portal.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;

public class LoginIntercepter implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//返回ModelAndView之后
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// Handler执行之后，返回ModelAndView之前

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		//Handler执行之前处理
		//通过Cookie获取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//根据token获取tbuser对象
		TbUser user = userService.getUserByToken(token);
		//如果有该对象就放行，否则拦截
		if(user!=null){
			request.setAttribute("user", user);
			return true;
		}else{
			response.sendRedirect(userService.SSO_BASE_URL+userService.SSO_LOGIN_URL+"?redirect="+request.getRequestURL().toString());
			return false;
		}
		
	}

}
