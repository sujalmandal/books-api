package s.m.booksapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SERVER_ERROR("Something went wrong in the backend", HttpStatus.INTERNAL_SERVER_ERROR),
    INSUFFICIENT_QUANTITY("Some book(s) present in insufficient quantity", HttpStatus.BAD_REQUEST),
    NO_SUCH_BOOK_FOUND("Book(s) with the passed ISBN was not found in the system", HttpStatus.BAD_REQUEST),
    ISBN_ALREADY_PRESENT("Book with the passed ISBN is already present", HttpStatus.BAD_REQUEST),
    REQUIRED_PARAM_EMPTY("One or more parameters passed where empty but are required", HttpStatus.BAD_REQUEST),
    INVALID_QTY("Invalid quantity passed", HttpStatus.BAD_REQUEST),
    INVALID_PRICE("Invalid price passed", HttpStatus.BAD_REQUEST),
    NO_SUCH_DISCOUNT_FOUND("Invalid coupon code", HttpStatus.BAD_REQUEST);

    private final HttpStatus status;
    private final String message;

    ErrorCode(String msg, HttpStatus status) {
        this.message = msg;
        this.status = status;
    }

}
