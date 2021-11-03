package s.m.booksapi.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import s.m.booksapi.entities.BookOrder;

@Repository
public interface CheckoutRepository extends CrudRepository<BookOrder,String> {
}