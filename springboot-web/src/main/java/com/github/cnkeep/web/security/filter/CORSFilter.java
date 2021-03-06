package com.github.cnkeep.web.security.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 同源策略过滤器
 */
@Component
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");

		//================================================================== httponly cookie
		//设置cookie
		response.addHeader("Set-Cookie", "uid=112; Path=/; HttpOnly");
		//设置多个cookie
		response.addHeader("Set-Cookie", "uid=112; Path=/; HttpOnly");
		response.addHeader("Set-Cookie", "timeout=30; Path=/test; HttpOnly");
		//设置https的cookie
		response.addHeader("Set-Cookie", "uid=112; Path=/; Secure; HttpOnly");

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}

	/**
	 * 也可以直接使用spring提供的CorsFilter bean完成同源策略的问题解决
	 * @return
	 */
	//@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

}