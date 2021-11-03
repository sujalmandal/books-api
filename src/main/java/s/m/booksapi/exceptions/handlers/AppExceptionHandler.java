package s.m.booksapi.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import s.m.booksapi.dto.ResponseDTO;
import s.m.booksapi.exceptions.BookAppException;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(BookAppException.class)
    public ResponseEntity<ResponseDTO<?>> handleBookAppException(BookAppException ex) {
        log.error("BookAppException occurred", ex);
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<?>> handleGenericException(Exception ex) {
        log.error("Error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(ex.getMessage()));
    }

}
