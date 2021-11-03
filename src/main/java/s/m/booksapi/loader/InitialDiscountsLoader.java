package s.m.booksapi.loader;

import lombok.extern.slf4j.Slf4j;
import s.m.booksapi.entities.Book;
import s.m.booksapi.entities.Discount;
import s.m.booksapi.dao.DiscountRepository;

import java.util.UUID;

@Slf4j
public class InitialDiscountsLoader {

    private final DiscountRepository discountRepository;

    public InitialDiscountsLoader(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public void loadDiscounts(){
        log.info("loading discounts data..");
        Discount fictionDiscount = new Discount();
        fictionDiscount.setBookType(Book.Type.FICTIONAL);
        fictionDiscount.setIsGenreWideDiscount(Boolean.TRUE);
        fictionDiscount.setPercentageDiscount(2.99);

        Discount educationalDiscount = new Discount();
        educationalDiscount.setBookType(Book.Type.EDUCATIONAL);
        educationalDiscount.setIsGenreWideDiscount(Boolean.TRUE);
        educationalDiscount.setPercentageDiscount(14.99);

        Discount comicDiscount = new Discount();
        comicDiscount.setBookType(Book.Type.COMICS);
        comicDiscount.setIsGenreWideDiscount(Boolean.TRUE);
        comicDiscount.setPercentageDiscount(70.00);

        Discount summerHolidaysDiscount = new Discount();
        summerHolidaysDiscount.setCouponCode("SUMMEROF21");
        summerHolidaysDiscount.setPercentageDiscount(4.99);

        Discount insaneDiscount = new Discount();
        insaneDiscount.setCouponCode("INSANE75");
        insaneDiscount.setPercentageDiscount(75.00);

        this.discountRepository.save(fictionDiscount);
        this.discountRepository.save(educationalDiscount);
        this.discountRepository.save(comicDiscount);
        this.discountRepository.save(summerHolidaysDiscount);
        this.discountRepository.save(insaneDiscount);

        log.info("finished loading discounts data..");
    }
}
