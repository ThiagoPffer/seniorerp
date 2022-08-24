package thiago.piffer.seniorerp.exceptions.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import thiago.piffer.seniorerp.exceptions.DataIntegrityException;
import thiago.piffer.seniorerp.exceptions.ObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(value={ObjectNotFoundException.class, EmptyResultDataAccessException.class})
	public ResponseEntity<StandardError> objectNotFound(Exception e, HttpServletRequest request) {
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.NOT_FOUND.value(), 
				"Não encontrado", 
				e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(value = {DataIntegrityException.class, DataIntegrityViolationException.class})
	public ResponseEntity<StandardError> dataIntegrity(Exception e, HttpServletRequest request) {
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Integridade de dados", 
				e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError(
				System.currentTimeMillis(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Validação de dados",
				"Existem campos obrigatórios não preenchidos!",
				request.getRequestURI());

		for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			err.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
}