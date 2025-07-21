package boho.lottonumbergenerator.common.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class Advice {

	@ExceptionHandler(Exception.class)
	public String handleRuntimeException(Exception e, Model model) {
		log.error("error occurred!", e);
		model.addAttribute("message", "죄송합니다 유효하지 않은 요청입니다.");
		return "error/500";
	}
}
