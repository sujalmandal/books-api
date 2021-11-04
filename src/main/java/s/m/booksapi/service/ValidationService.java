package s.m.booksapi.service;

import s.m.booksapi.dto.CheckoutDTO;
import s.m.booksapi.entities.BookOrderDetail;

public interface ValidationService {
    void validateISBN(String isbn);
    void validateSaveBook(BookOrderDetail book);
    void validateUpdateBook(BookOrderDetail book);
    void validateCheckout(CheckoutDTO checkoutDTO);
}
