package s.m.booksapi.service;

import s.m.booksapi.dto.BookOrderDetailDTO;
import s.m.booksapi.dto.CheckoutDTO;

public interface ValidationService {
    void validateISBN(String isbn);
    void validateSaveBook(BookOrderDetailDTO book);
    void validateUpdateBook(BookOrderDetailDTO book);
    void validateCheckout(CheckoutDTO checkoutDTO);
}
