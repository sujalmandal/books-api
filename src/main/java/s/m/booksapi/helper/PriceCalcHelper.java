package s.m.booksapi.helper;

import s.m.booksapi.entities.BookOrderDetail;
import s.m.booksapi.entities.Book;
import s.m.booksapi.entities.BookOrder;
import s.m.booksapi.entities.Discount;

import java.util.*;
import java.util.stream.Collectors;

public class PriceCalcHelper {

    public static double getPriceBeforeDiscounts(BookOrder order){
        double price = 0.0;
        for(BookOrderDetail book : order.getBooks()){
                price += book.getPrice() * book.getQuantity();
        }
        return price;
    }

    /* first apply genre-wide discounts and then apply optional coupon code discount */
    public static double getPriceAfterDiscounts(BookOrder order){
        double price = 0.0;

        Map<Book.Type, List<Discount>> discountsByType = order.getDiscounts().stream()
                .filter(Discount::getIsGenreWideDiscount)
                .collect(Collectors.groupingBy(Discount::getBookType));

        for(BookOrderDetail book : order.getBooks()){
                double currentBookPrice = book.getPrice();
                Discount discountForGenre = discountsByType.get(book.getType()).get(0);
                /* calculate percentage discount */
                currentBookPrice = ((100-discountForGenre.getPercentageDiscount())/100 * currentBookPrice)
                        * book.getQuantity();
                price += currentBookPrice;
        }

        /* calculate final discount */
        Discount couponCodeDiscount = order.getDiscounts().stream()
                .filter(discount -> !discount.getIsGenreWideDiscount()).findFirst().orElse(null);
        if(Objects.nonNull(couponCodeDiscount)){
            price = (100 - couponCodeDiscount.getPercentageDiscount())/100 * price;
        }
        return price;
    }

}
