package com.mg.framework.exception;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class ExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		ModelAndView mv = new ModelAndView();

		// 设置View
		mv.setView(new AbstractView() {
			@Override
			protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				String str = JSON.toJSONString(model, false);
				PrintWriter out = response.getWriter();
				out.print(str);
			}
		});

		ModelMap modelMap = mv.getModelMap();
		modelMap.clear();

		//错误调用栈
		String stackTrace = getStackTrace(ex);

		// 记录错误日志
		logger.error(stackTrace);

		// 设置数据
		modelMap.addAttribute("errorCode", "2015516102136");
		modelMap.addAttribute("errorText", htmlEncode(stackTrace));

		return mv;

	}

	public static String htmlEncode(String s) {
		if (s != null) {
			s = s.replaceAll("<", "&lt;");
			s = s.replaceAll(">", "&gt;");
		}
		return s;
	}

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

}
