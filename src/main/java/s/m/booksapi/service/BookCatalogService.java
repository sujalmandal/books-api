package s.m.booksapi.service;

import s.m.booksapi.entities.BookOrderDetail;
import s.m.booksapi.dto.BookQtyDTO;

import java.util.Set;

/* Inventory operations on books */
public interface BookCatalogService {
    /* add a book as well as the qty of the book */
    BookOrderDetail addBookToInventory(BookOrderDetail bookItem);
    /* should decrement inventory by one */
    BookOrderDetail getBookFromInventory(String ISBN);
    /* update inventory */
    BookOrderDetail updateBookInventory(BookOrderDetail bookItem);
    /* remove a book from the inventory */
    Set<BookOrderDetail> removeBooksFromInventory(Set<BookQtyDTO> bookQtyDTOSet);
    /* get all books */
    Set<BookOrderDetail> getBooks();
}
