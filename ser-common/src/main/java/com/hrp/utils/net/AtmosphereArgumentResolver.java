package com.hrp.utils.net;

import javax.servlet.http.HttpServletRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * AtmosphereArgumentResolver
 */
public class AtmosphereArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * supportsParameter
	 */
	public boolean supportsParameter(final MethodParameter parameter) {
		return AtmosphereResource.class.isAssignableFrom(parameter.getParameterType());
	}

	/**
	 * resolveArgument
	 */
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
		return AtmosphereUtils.getAtmosphereResource(webRequest.getNativeRequest(HttpServletRequest.class));
	}
}
