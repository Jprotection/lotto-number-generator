package boho.lottonumbergenerator.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
public class Advice {

	@ExceptionHandler(JsonProcessingException.class)
	public String handleJsonProcessingException(JsonProcessingException e, Model model) {

		return "error";
	}
}
