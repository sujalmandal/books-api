package s.m.booksapi.service;


import s.m.booksapi.dto.CheckoutDTO;
import s.m.booksapi.entities.BookOrder;

public interface CheckoutService {
    BookOrder checkout(CheckoutDTO checkoutDTO);
}
