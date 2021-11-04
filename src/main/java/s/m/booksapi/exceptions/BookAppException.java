package s.m.booksapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import s.m.booksapi.dto.ResponseDTO;

@Getter
public class BookAppException extends RuntimeException {

    private final String errorMessage;
    private final HttpStatus httpStatus;
    private final Throwable throwable;
    private ErrorCode errorCode;

    public BookAppException(ErrorCode errorCode, Throwable throwable){
        super(throwable);
        this.throwable = throwable;
        this.errorMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getStatus();
    }

    public BookAppException(ErrorCode errorCode){
        super(new RuntimeException(errorCode.getMessage()));
        this.throwable = super.getCause();
        this.errorMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getStatus();
        this.errorCode = errorCode;
    }

    public ResponseDTO<?> getResponse(){
        return new ResponseDTO<>(errorMessage);
    }

}
