package s.m.booksapi.service;

import org.apache.commons.lang3.StringUtils;
import s.m.booksapi.dao.BookInventoryRepository;
import s.m.booksapi.dao.CheckoutRepository;
import s.m.booksapi.dao.DiscountRepository;
import s.m.booksapi.dao.OrderBookDetailRepository;
import s.m.booksapi.dto.BookQtyDTO;
import s.m.booksapi.dto.CheckoutDTO;
import s.m.booksapi.entities.BookOrderDetail;
import s.m.booksapi.exceptions.BookAppException;
import s.m.booksapi.exceptions.ErrorCode;

import java.util.Objects;

public class ValidationService {

    final BookInventoryRepository bookInventoryRepository;
    final CheckoutRepository checkoutRepository;
    final DiscountRepository discountRepository;
    final OrderBookDetailRepository orderBookDetailRepository;

    public ValidationService(
            BookInventoryRepository bookInventoryRepository, CheckoutRepository checkoutRepository,
            DiscountRepository discountRepository, OrderBookDetailRepository orderBookDetailRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.checkoutRepository = checkoutRepository;
        this.discountRepository = discountRepository;
        this.orderBookDetailRepository = orderBookDetailRepository;
    }

    public void validateISBN(String isbn) {
        if(StringUtils.isEmpty(isbn)){
            throw new BookAppException(ErrorCode.REQUIRED_PARAM_EMPTY);
        }
        if(Objects.isNull(bookInventoryRepository.findBookInventoryByISBN(isbn))){
            throw new BookAppException(ErrorCode.NO_SUCH_BOOK_FOUND);
        }
    }

    public void validateSaveBook(BookOrderDetail book) {
        if(!(book.getQuantity()>0)){
            throw new BookAppException(ErrorCode.INVALID_QTY);
        }
        if(!(book.getPrice()>0)){
            throw new BookAppException(ErrorCode.INVALID_PRICE);
        }
        if(StringUtils.isEmpty(book.getAuthor()) || Objects.isNull(book.getType())
            || StringUtils.isEmpty(book.getISBN()) || StringUtils.isEmpty(book.getName())){
            throw new BookAppException(ErrorCode.REQUIRED_PARAM_EMPTY);
        }
        if(Objects.nonNull(bookInventoryRepository.findBookInventoryByISBN(book.getISBN()))){
            throw new BookAppException(ErrorCode.ISBN_ALREADY_PRESENT);
        }
    }

    public void validateUpdateBook(BookOrderDetail book) {
        this.validateSaveBook(book);
        this.validateISBN(book.getISBN());
    }

    public void validateCheckout(CheckoutDTO checkoutDTO) {
        checkoutDTO.getBooks().stream().map(BookQtyDTO::getISBN).forEach(this::validateISBN);
        checkoutDTO.getBooks().stream().map(BookQtyDTO::getQty).forEach(qty->{
            if(Objects.isNull(qty) || !(qty>0)){
                throw new BookAppException(ErrorCode.INVALID_QTY);
            }
        });
        /* some non-empty coupon code passed */
        if(StringUtils.isNotEmpty(checkoutDTO.getCouponCode())){
            /* coupon code must be present in the system */
            if(Objects.isNull(discountRepository.findDiscountByCouponCode(checkoutDTO.getCouponCode()))){
                throw new BookAppException(ErrorCode.NO_SUCH_DISCOUNT_FOUND);
            }
        }
    }
}
