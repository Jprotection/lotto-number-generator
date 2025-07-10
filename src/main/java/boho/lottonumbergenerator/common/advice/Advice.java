package boho.lottonumbergenerator.common.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import boho.lottonumbergenerator.config.security.UsernameDuplicateException;

@ControllerAdvice
public class Advice {

	@ExceptionHandler(UsernameDuplicateException.class)
	public String handleJsonProcessingException(UsernameDuplicateException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "redirect:/signup";
	}

	@ExceptionHandler(RuntimeException.class)
	public String handleJsonProcessingException(RuntimeException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error/500";
	}
}
