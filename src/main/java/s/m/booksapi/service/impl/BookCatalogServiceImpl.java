package s.m.booksapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import s.m.booksapi.dao.BookInventoryRepository;
import s.m.booksapi.entities.BookOrderDetail;
import s.m.booksapi.dto.BookQtyDTO;
import s.m.booksapi.entities.BookInventory;
import s.m.booksapi.exceptions.BookAppException;
import s.m.booksapi.exceptions.ErrorCode;
import s.m.booksapi.service.BookCatalogService;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
public class BookCatalogServiceImpl implements BookCatalogService {

    private final BookInventoryRepository bookInventoryRepository;

    public BookCatalogServiceImpl(BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BookOrderDetail addBookToInventory(BookOrderDetail bookItem) {

        BookInventory savedBookInventoryItem = bookInventoryRepository.save(bookItem.getBookInventory());
        log.info("saved book catalog entry with id {} ISBN {}, qty {}",
                bookItem.getBookInventory().getId(),
                bookItem.getBookInventory().getBook().getISBN(),
                bookItem.getBookInventory().getQuantity());
        return BookOrderDetail.fromBookInventory(savedBookInventoryItem);
    }

    @Override
    public BookOrderDetail getBookFromInventory(String ISBN) {
        return BookOrderDetail.fromBookInventory(bookInventoryRepository.findBookInventoryByISBN(ISBN));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateBookInventory(BookOrderDetail bookItem) {
        BookInventory previousBookInventory = bookInventoryRepository.findBookInventoryByISBN(bookItem.getISBN());
        log.info("updated book with ISBN {}", bookItem.getBook().getISBN());

        BookInventory updatedBookInventory = new BookInventory();
        updatedBookInventory.setBook(bookItem.getBook());
        updatedBookInventory.setQuantity(bookItem.getQuantity());
        updatedBookInventory.setId(previousBookInventory.getId());
        updatedBookInventory = bookInventoryRepository.save(updatedBookInventory);

        log.info("updated book catalog entry with ISBN {}, qty {}",
                updatedBookInventory.getBook().getISBN(),
                updatedBookInventory.getQuantity());

    }

    @Override
    public void dropBookFromInventory(String ISBN) {
        BookInventory bookEntryToDrop = bookInventoryRepository.findBookInventoryByISBN(ISBN);
        bookInventoryRepository.delete(bookEntryToDrop);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<BookOrderDetail> removeBooksFromInventory(Set<BookQtyDTO> bookQtyDTOSet) {
        Set<BookOrderDetail> removedBooks = new LinkedHashSet<>();

        for(BookQtyDTO bookQtyDTO : bookQtyDTOSet){

            BookInventory bookInventory = bookInventoryRepository.findBookInventoryByISBN(bookQtyDTO.getISBN());
            int qtyToRemove = bookQtyDTO.getQty();
            int qtyLeft = bookInventory.getQuantity();
            if(qtyToRemove<=qtyLeft) {
                bookInventory.setQuantity(qtyLeft-qtyToRemove);
            }
            else {
                /* throw exception */
                throw new BookAppException(ErrorCode.INSUFFICIENT_QUANTITY);
            }
            BookOrderDetail bookOrderDetail = new BookOrderDetail();
            bookOrderDetail.setQuantity(bookQtyDTO.getQty());
            BeanUtils.copyProperties(bookInventory.getBook(), bookOrderDetail);
            removedBooks.add(bookOrderDetail);
            this.bookInventoryRepository.save(bookInventory);
        }
        return removedBooks;
    }

    @Override
    public Set<BookOrderDetail> getBooks() {
        Iterator<BookInventory> iterator = this.bookInventoryRepository.findAll().iterator();
        Set<BookOrderDetail> bookOrderDetails = new LinkedHashSet<>();
        while (iterator.hasNext()){
            BookInventory bookInventory = iterator.next();
            bookOrderDetails.add(BookOrderDetail.fromBookInventory(bookInventory));
        }
        return bookOrderDetails;
    }
}
