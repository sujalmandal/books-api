package s.m.booksapi.web.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import s.m.booksapi.dao.BookInventoryRepository;
import s.m.booksapi.dao.CheckoutRepository;
import s.m.booksapi.dao.DiscountRepository;
import s.m.booksapi.dao.OrderBookDetailRepository;
import s.m.booksapi.dto.BookOrderDetailDTO;
import s.m.booksapi.dto.CheckoutDTO;
import s.m.booksapi.entities.BookInventory;
import s.m.booksapi.entities.BookOrderDetail;
import s.m.booksapi.exceptions.BookAppException;
import s.m.booksapi.exceptions.ErrorCode;
import s.m.booksapi.service.ValidationService;
import s.m.booksapi.service.impl.ValidationServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static s.m.booksapi.web.util.TestDataUtil.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ValidationServiceTest {
    @Mock
    private BookInventoryRepository bookInventoryRepository;
    @Mock
    private CheckoutRepository checkoutRepository;
    @Mock
    private DiscountRepository discountRepository;
    @Mock
    private OrderBookDetailRepository orderBookDetailRepository;

    private ValidationService validationService;

    @BeforeEach
    void setupValidationServiceWithMocks() {
        validationService = new ValidationServiceImpl(
                bookInventoryRepository,
                checkoutRepository,
                discountRepository,
                orderBookDetailRepository
        );
    }

    @Test
    public void validateISBN_WithNonExistentISBN(){

        when(bookInventoryRepository.findBookInventoryByISBN(TEST_ISBN_INVALID))
                .thenReturn(null);
        BookAppException ex = assertThrows(BookAppException.class,() -> {
            validationService.validateISBN(TEST_ISBN_INVALID);
        });
        assertEquals(ex.getErrorCode(),ErrorCode.NO_SUCH_BOOK_FOUND);
    }

    @Test
    public void validateSaveBook_WithExistingISBN(){

        when(bookInventoryRepository.findBookInventoryByISBN(getTestbook_1().getISBN()))
                .thenReturn(new BookInventory());
        BookAppException ex = assertThrows(BookAppException.class,() -> {
            validationService.validateSaveBook(getTestbook_1());
        });
        assertEquals(ex.getErrorCode(),ErrorCode.ISBN_ALREADY_PRESENT);
    }

    @Test
    public void validateUpdateBook_WithBadISBN(){

        when(bookInventoryRepository.findBookInventoryByISBN(TEST_ISBN_INVALID))
                .thenReturn(new BookInventory());
        BookAppException ex = assertThrows(BookAppException.class,() -> {
            BookOrderDetailDTO invalidIdBook = getTestbook_1();
            validationService.validateUpdateBook(invalidIdBook);
        });
        assertEquals(ex.getErrorCode(),ErrorCode.NO_SUCH_BOOK_FOUND);
    }

    @Test
    public void validateCheckout_InvalidCouponCode(){
        when(bookInventoryRepository.findBookInventoryByISBN(anyString()))
                .thenReturn(new BookInventory());
        when(discountRepository.findDiscountByCouponCode(TEST_INVALID_COUPON_CODE))
                .thenReturn(null);

        BookAppException ex = assertThrows(BookAppException.class,() -> {
            CheckoutDTO checkoutDTO = getTestCheckoutDTOWithTwoItems();
            checkoutDTO.setCouponCode(TEST_INVALID_COUPON_CODE);
            validationService.validateCheckout(checkoutDTO);
        });
        assertEquals(ex.getErrorCode(),ErrorCode.NO_SUCH_DISCOUNT_FOUND);
    }
}
