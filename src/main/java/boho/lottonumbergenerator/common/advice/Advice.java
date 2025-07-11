package boho.lottonumbergenerator.common.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advice {

	@ExceptionHandler(Exception.class)
	public String handleRuntimeException(Exception e, Model model) {
		model.addAttribute("message", "죄송합니다 유효하지 않은 요청입니다.");
		e.printStackTrace();
		return "error/500";
	}
}
