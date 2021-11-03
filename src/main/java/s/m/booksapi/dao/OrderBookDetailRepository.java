package s.m.booksapi.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import s.m.booksapi.entities.BookOrderDetail;

@Repository
public interface OrderBookDetailRepository extends CrudRepository<BookOrderDetail,String> {
}