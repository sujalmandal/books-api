package s.m.booksapi.web.util;

import s.m.booksapi.dto.BookQtyDTO;
import s.m.booksapi.dto.CheckoutDTO;
import s.m.booksapi.entities.Book;
import s.m.booksapi.entities.BookOrderDetail;

import java.util.HashSet;
import java.util.Set;

public class TestDataUtil {

    public static final String TEST_ISBN_1 = "9999999";
    public static final String TEST_ISBN_2 = "8903222";
    public static final String TEST_ISBN_INVALID = "0000000";

    public static final String TEST_INVALID_COUPON_CODE = "BADCOUPON";

    public static BookOrderDetail getTestBook_2() {
        BookOrderDetail book = new BookOrderDetail();
        book.setId(null);
        book.setType(Book.Type.COMICS);
        book.setPrice(100.00);
        book.setAuthor("Takashi Hikimoto");
        book.setISBN(TEST_ISBN_2);
        book.setName("Doraemon");
        book.setDescription("Robot cat from the future helps his sloppy buddy.");
        book.setQuantity(3);
        return book;
    }

    public static BookOrderDetail getTestbook_1() {
        BookOrderDetail book = new BookOrderDetail();
        book.setId(null);
        book.setType(Book.Type.EDUCATIONAL);
        book.setPrice(100.00);
        book.setAuthor("Sujal Mandal");
        book.setISBN(TEST_ISBN_1);
        book.setName("Round the world in 10 years");
        book.setDescription("A budding software engineer explores the world in a decade.");
        book.setQuantity(3);
        return book;
    }

    public static CheckoutDTO getTestCheckoutDTOWithTwoItems(){
        CheckoutDTO checkoutDTO = new CheckoutDTO();
        Set<BookQtyDTO> bookQtyDTOSet = new HashSet<>();
        BookQtyDTO bookQtyDTO1 = new BookQtyDTO();
        BookQtyDTO bookQtyDTO2 = new BookQtyDTO();
        bookQtyDTO1.setISBN(getTestbook_1().getISBN());
        bookQtyDTOSet.add(bookQtyDTO1);
        bookQtyDTO2.setISBN(getTestBook_2().getISBN());
        bookQtyDTOSet.add(bookQtyDTO2);
        checkoutDTO.setBooks(bookQtyDTOSet);
        return checkoutDTO;
    }
}
