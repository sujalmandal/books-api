package s.m.booksapi.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import s.m.booksapi.entities.Book;
import s.m.booksapi.entities.Discount;

import java.util.Set;

@Repository
public interface DiscountRepository extends CrudRepository<Discount,String> {
    Set<Discount> findDiscountsByBookTypeIn(Set<Book.Type> bookTypes);
    Discount findDiscountByCouponCode(String couponCode);
}
