package zy.code.project_highconcurrencymall.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import zy.code.project_highconcurrencymall.domain.MiaoshaUser;
import zy.code.project_highconcurrencymall.service.MiaoshaUserService;


@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	MiaoshaUserService userService;
	
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz== MiaoshaUser.class;
	}

	/**
	 * 处理请求中的参数
	 */
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {


		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

		//获取当前request域的请求参数
		String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
		//调用内部方法，获取name对应的cookie值
		String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE_NAME_TOKEN);

		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		//获取参数中的token或者cookie中保存的token
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;

		//根据token获取user对象并返回
		return userService.getByToken(response, token);
	}


	/**
	 * 根据name获取当前request域中的cookie值
	 * @param request
	 * @param cookieName
	 * @return
	 */
	private String getCookieValue(HttpServletRequest request, String cookieName) {
		//获取当前request域中的cookie
		Cookie[]  cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
