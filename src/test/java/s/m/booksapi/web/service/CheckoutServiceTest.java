package s.m.booksapi.web.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import s.m.booksapi.dao.CheckoutRepository;
import s.m.booksapi.dao.DiscountRepository;
import s.m.booksapi.dao.OrderBookDetailRepository;
import s.m.booksapi.entities.BookOrder;
import s.m.booksapi.helper.PriceCalcHelper;
import s.m.booksapi.service.BookCatalogService;
import s.m.booksapi.service.CheckoutService;
import s.m.booksapi.service.impl.CheckoutServiceImpl;


import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static s.m.booksapi.web.util.TestDataUtil.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CheckoutServiceTest {

    @Mock
    private CheckoutRepository checkoutRepository;
    @Mock
    private DiscountRepository discountRepository;
    @Mock
    private BookCatalogService bookCatalogService;
    @Mock
    private OrderBookDetailRepository orderBookDetailRepository;

    private CheckoutService checkoutService;

    @BeforeEach
    void setupValidationServiceWithMocks() {
        checkoutService = new CheckoutServiceImpl(
                checkoutRepository,
                discountRepository,
                bookCatalogService,
                orderBookDetailRepository
        );
    }

    @Test
    public void testSuccessfulCheckout(){
        /* wire up the services and repositories */
        when(checkoutRepository.save(any())).thenReturn(new BookOrder());
        when(bookCatalogService.removeBooksFromInventory(getTestCheckoutDTOWithTwoItems().getBooks()))
                .thenReturn(getTestBookOrderDetailSetForCheckoutDTO());
        when(orderBookDetailRepository.saveAll(any())).then(returnsFirstArg());
        when(discountRepository.findDiscountsByBookTypeIn(any())).
                thenReturn(getDiscountSet(
                        getTestFictionalTypeDiscount_5PercentOff())
                );
        when(discountRepository.findDiscountByCouponCode(any())).
                thenReturn(getTestOptionalDiscount_10PercentOff());
        when(checkoutRepository.save(any())).then(returnsFirstArg());

        BookOrder completedOrder = checkoutService.checkout(getTestCheckoutDTOWithTwoItems());
        log.info("checkout data : {}", completedOrder);

        assertEquals(completedOrder.getOrderStatus(), BookOrder.Status.COMPLETE);
        assertTrue(completedOrder.getBooks()
                .containsAll(getBookOrderDetailSet(getTestbook_1(),getTestBook_2())));

        Double expectedPriceBeforeDiscount =
                getTestbook_1().getPrice() * getTestbook_1().getQuantity() +
                        getTestBook_2().getPrice() * getTestBook_2().getQuantity();

        assertEquals(expectedPriceBeforeDiscount, completedOrder.getTotalPriceBeforeDiscount());

        Double expectedPriceAfterDiscounts =
                (getTestbook_1().getPrice() * getTestbook_1().getQuantity()
                        * ((100-getTestFictionalTypeDiscount_5PercentOff().getPercentageDiscount())/100))
                        +
                (getTestBook_2().getPrice() * getTestBook_2().getQuantity()
                        * ((100-getTestFictionalTypeDiscount_5PercentOff().getPercentageDiscount())/100));

        /* optional coupon code off */
        expectedPriceAfterDiscounts *= ((100-getTestOptionalDiscount_10PercentOff().getPercentageDiscount())/100);

        assertEquals(expectedPriceAfterDiscounts, completedOrder.getTotalPriceAfterDiscount());
    }



}
