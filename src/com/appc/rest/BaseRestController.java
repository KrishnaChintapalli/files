package com.appc.rest;

import com.appc.views.ResultView;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class BaseRestController {

	@Autowired
	public HttpServletRequest request;

	@Autowired
	public MessageSource messageSource;

	public Locale getLocale() {
		String lang = this.request.getParameter("lang");
		if ((StringUtils.isNotEmpty(lang)) && ((("en".equalsIgnoreCase(lang)) || ("zh".equalsIgnoreCase(lang))))) {
			return new Locale(lang);
		}

		String acceptLanguage = this.request.getHeader("Accept-Language");
		if ((StringUtils.isNotEmpty(acceptLanguage))
				&& ((("en".equalsIgnoreCase(acceptLanguage)) || ("zh".equalsIgnoreCase(acceptLanguage))))) {
			return new Locale(acceptLanguage);
		}

		return this.request.getLocale();
	}

	public String msg(String messageCode, Object[] objects) {
		return this.messageSource.getMessage(messageCode, objects, getLocale());
	}

	public String msg(String messageCode) {
		return this.messageSource.getMessage(messageCode, null, getLocale());
	}

	public ResultView success(String code, String message, Object data) {
		return new ResultView(200, code, message, data);
	}

	public ResultView success(String code, Object data) {
		return new ResultView(200, code, msg(code), data);
	}

	public ResultView success(String code, Object[] msgData, Object data) {
		return new ResultView(200, code, msg(code, msgData), data);
	}

	public ResultView success(String code, Object[] msgData) {
		return success(code, msgData, null);
	}

	public ResultView success(String code) {
		return success(code, null);
	}

	public ResultView success(Object data) {
		return new ResultView(200, null, "success", data);
	}

	public ResultView error(String code, String message, Object data) {
		return new ResultView(201, code, message, data);
	}

	public ResultView error(String code, Object data) {
		return new ResultView(201, code, msg(code), data);
	}

	public ResultView error(String code, Object[] msgData, Object data) {
		return new ResultView(201, code, msg(code, msgData), data);
	}

	public ResultView error(String code, Object[] msgData) {
		return error(code, msgData, null);
	}

	public ResultView error(String code) {
		return error(code, null);
	}
}