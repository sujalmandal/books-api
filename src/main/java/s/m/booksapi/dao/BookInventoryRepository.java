package s.m.booksapi.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import s.m.booksapi.entities.BookInventory;

@Repository
public interface BookInventoryRepository extends CrudRepository<BookInventory,String> {
    @Query("from BookInventory where book.ISBN = :ISBN")
    BookInventory findBookInventoryByISBN(String ISBN);
}
