package s.m.booksapi.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s.m.booksapi.entities.BookOrderDetail;
import s.m.booksapi.dto.CheckoutDTO;
import s.m.booksapi.dto.ResponseDTO;
import s.m.booksapi.entities.BookOrder;
import s.m.booksapi.service.BookCatalogService;
import s.m.booksapi.service.CheckoutService;
import s.m.booksapi.service.impl.ValidationServiceImpl;

import java.util.Set;

@RequestMapping("books-api")
@RestController
@Slf4j
public class BooksShopController {

    private final BookCatalogService bookCatalogService;
    private final CheckoutService checkoutService;
    private final ValidationServiceImpl validationService;

    @Autowired
    public BooksShopController(
            BookCatalogService bookCatalogService, CheckoutService checkoutService,
            ValidationServiceImpl validationService) {
        this.bookCatalogService = bookCatalogService;
        this.checkoutService = checkoutService;
        this.validationService = validationService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getBooks(){
        ResponseDTO<Set<BookOrderDetail>> response = new ResponseDTO<>(bookCatalogService.getBooks());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ISBN}")
    public  ResponseEntity<ResponseDTO<?>> getBook(@PathVariable("ISBN") String ISBN){
        validationService.validateISBN(ISBN);
        ResponseDTO<BookOrderDetail> response = new ResponseDTO<>(bookCatalogService.getBookFromInventory(ISBN));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<?>> saveBook(@RequestBody BookOrderDetail book){
        sanitizeSaveBookRequest(book);
        validationService.validateSaveBook(book);
        ResponseDTO<BookOrderDetail> response = new ResponseDTO<>(bookCatalogService.addBookToInventory(book));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<?>> updateBook(@RequestBody BookOrderDetail book){
        validationService.validateUpdateBook(book);
        bookCatalogService.updateBookInventory(book);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{ISBN}")
    public ResponseEntity<ResponseDTO<?>> dropBook(@PathVariable("ISBN") String ISBN){
        validationService.validateISBN(ISBN);
        bookCatalogService.dropBookFromInventory(ISBN);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<ResponseDTO<?>> checkout(@RequestBody CheckoutDTO checkoutDTO){
        validationService.validateCheckout(checkoutDTO);
        ResponseDTO<BookOrder> response = new ResponseDTO<>(checkoutService.checkout(checkoutDTO));
        return ResponseEntity.ok(response);
    }

    private void sanitizeSaveBookRequest(BookOrderDetail book) {
        book.setId(null);
    }

}
