package sansan.auth.Config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sansan.utility.lib.DTO.ExceptionDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleException(Exception e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}
