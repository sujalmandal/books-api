package s.m.booksapi.web.util;

import s.m.booksapi.dto.BookOrderDetailDTO;
import s.m.booksapi.dto.BookQtyDTO;
import s.m.booksapi.dto.CheckoutDTO;
import s.m.booksapi.entities.Book;
import s.m.booksapi.entities.BookOrderDetail;
import s.m.booksapi.entities.Discount;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class TestDataUtil {

    public static final String TEST_ISBN_1 = "9999999";
    public static final String TEST_ISBN_2 = "8903222";
    public static final String TEST_ISBN_INVALID = "0000000";

    public static final String TEST_INVALID_COUPON_CODE = "BADCOUPON";
    public static final String TEST_COUPON_CODE = "10PERCENTOFF";

    public static BookOrderDetailDTO getTestBook_2() {
        BookOrderDetailDTO book = new BookOrderDetailDTO();
        book.setType(Book.Type.FICTIONAL);
        book.setPrice(100.00);
        book.setAuthor("Takashi Hikimoto");
        book.setISBN(TEST_ISBN_2);
        book.setName("Doraemon");
        book.setDescription("Robot cat from the future helps his sloppy buddy.");
        book.setQuantity(3);
        return book;
    }

    public static BookOrderDetailDTO getTestbook_1() {
        BookOrderDetailDTO book = new BookOrderDetailDTO();
        book.setType(Book.Type.FICTIONAL);
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
        checkoutDTO.setCouponCode(TEST_COUPON_CODE);
        return checkoutDTO;
    }

    public static Set<BookOrderDetail> getTestBookOrderDetailSetForCheckoutDTO(){
        Set<BookOrderDetail> removedBooks = new LinkedHashSet<>();
        removedBooks.add(getTestbook_1().getBookOrderDetail());
        removedBooks.add(getTestBook_2().getBookOrderDetail());
        return removedBooks;
    }

    public static Discount getTestFictionalTypeDiscount_5PercentOff(){
        Discount fictionDiscount = new Discount();
        fictionDiscount.setBookType(Book.Type.FICTIONAL);
        fictionDiscount.setIsGenreWideDiscount(Boolean.TRUE);
        fictionDiscount.setPercentageDiscount(5.00);
        return fictionDiscount;
    }

    public static Discount getTestOptionalDiscount_10PercentOff(){
        Discount tenPercentOff = new Discount();
        tenPercentOff.setCouponCode(TEST_COUPON_CODE);
        tenPercentOff.setPercentageDiscount(10.00);
        return tenPercentOff;
    }

    public static Set<Discount> getDiscountSet(Discount... discounts){
        return new LinkedHashSet<>(Arrays.asList(discounts));
    }

    public static Set<BookOrderDetail> getBookOrderDetailSet(BookOrderDetail... bookOrderDetails){
        return new LinkedHashSet<>(Arrays.asList(bookOrderDetails));
    }

}
