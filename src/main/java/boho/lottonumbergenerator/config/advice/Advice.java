package boho.lottonumbergenerator.config.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advice {

	@ExceptionHandler(RuntimeException.class)
	public String handleJsonProcessingException(RuntimeException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error/500";
	}
}
