package com.appc.util;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.appc.views.ResultView;
import java.io.IOException;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class JsonMessageConverter extends FastJsonHttpMessageConverter {
	private static ThreadLocal<ResultView> resultView = new ThreadLocal();

	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		super.writeInternal(obj, outputMessage);
		if (obj instanceof ResultView)
			resultView.set((ResultView) obj);
	}

	public static ResultView getResultView() {
		return ((ResultView) resultView.get());
	}

	public static void cleanResult() {
		resultView.remove();
	}
}