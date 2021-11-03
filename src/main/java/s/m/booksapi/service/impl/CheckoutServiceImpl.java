package s.m.booksapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import s.m.booksapi.dao.DiscountRepository;
import s.m.booksapi.dao.OrderBookDetailRepository;
import s.m.booksapi.entities.BookOrderDetail;
import s.m.booksapi.dto.CheckoutDTO;
import s.m.booksapi.entities.BookOrder;
import s.m.booksapi.dao.CheckoutRepository;
import s.m.booksapi.entities.Discount;
import s.m.booksapi.service.BookCatalogService;
import s.m.booksapi.service.CheckoutService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static s.m.booksapi.helper.PriceCalcHelper.getPriceAfterDiscounts;
import static s.m.booksapi.helper.PriceCalcHelper.getPriceBeforeDiscounts;

@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final DiscountRepository discountRepository;
    private final BookCatalogService bookCatalogService;
    private final OrderBookDetailRepository orderBookDetailRepository;


    public CheckoutServiceImpl(CheckoutRepository checkoutRepository,
                               DiscountRepository discountRepository, BookCatalogService bookCatalogService,
                               OrderBookDetailRepository orderBookDetailRepository) {
        this.checkoutRepository = checkoutRepository;
        this.discountRepository = discountRepository;
        this.bookCatalogService = bookCatalogService;
        this.orderBookDetailRepository = orderBookDetailRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BookOrder checkout(CheckoutDTO checkoutDTO) {
        BookOrder bookOrder = new BookOrder();
        checkoutRepository.save(bookOrder);
        log.info("created a {} order with id {}", bookOrder.getOrderStatus(), bookOrder.getId());
        Set<BookOrderDetail> booksToBuy = bookCatalogService.removeBooksFromInventory(checkoutDTO.getBooks());
        orderBookDetailRepository.saveAll(booksToBuy);
        fillOrderDetails(booksToBuy, checkoutDTO.getCouponCode(), bookOrder);
        bookOrder.setOrderStatus(BookOrder.Status.COMPLETE);
        checkoutRepository.save(bookOrder);
        log.info("updated {}", bookOrder.getId());
        return bookOrder;
    }

    public void fillOrderDetails(Set<BookOrderDetail> books, String couponCode, BookOrder order){
        order.setBooks(books);
        /* book type discounts first */
        Set<Discount> discounts = new LinkedHashSet<>(discountRepository.findDiscountsByBookTypeIn(
                order.getBooks().stream()
                        .map(BookOrderDetail::getType)
                        .collect(Collectors.toSet())
        ));
        /* optional coupon code */
        if(StringUtils.isNotEmpty(couponCode)){
            Discount optionalDiscountCode = discountRepository.findDiscountByCouponCode(couponCode);
            discounts.add(optionalDiscountCode);
        }
        order.setDiscounts(discounts);
        /* price before discounts */
        order.setTotalPriceBeforeDiscount(getPriceBeforeDiscounts(order));
        /* price after discount */
        order.setTotalPriceAfterDiscount(getPriceAfterDiscounts(order));
    }

}