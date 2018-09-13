package com.spring.config;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.JSON;

import token.TokenUtil;

/**
 * 自定义拦截器
 *
 */
@Configuration
public class MyAdapter extends WebMvcConfigurerAdapter {

	public static Logger log = Logger.getLogger(MyAdapter.class);

	private boolean validateToken(String token) {
		return TokenUtil.verifyJwt(token);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				System.out.println("SpringBoot自定义拦截器检查token是否过期token:" + request.getParameter("token"));
				boolean bol = validateToken(request.getParameter("token"));
				if (!bol) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("datas", "validate token fail");
					response.setContentType("application/json");
					PrintWriter out = null;
					try {
						out = response.getWriter();
						out.print(JSON.toJSONString(map));
						out.flush();
						out.close();
					} catch (Exception e) {
						log.error("拦截器报错:" + e);
					}
					return bol;
				}
				return true;
			}

			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
				System.out.println("postHandle");
			}

			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) throws Exception {
				System.out.println("afterCompletion");
			}

		};
		registry.addInterceptor(handlerInterceptor).addPathPatterns("/**");
	}
}
