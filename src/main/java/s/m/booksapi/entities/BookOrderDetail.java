package s.m.booksapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "book_order_detail")
public class BookOrderDetail {
    @Id
    private String id;
    private String ISBN;
    private String name;
    private String description;
    private String author;
    private Book.Type type;
    private double price;
    private int quantity;

    public BookOrderDetail() {
        this.id = UUID.randomUUID().toString();
    }

    @JsonIgnore
    public Book getBook(){
        Book book = new Book();
        BeanUtils.copyProperties(this,book);
        return book;
    }

    @JsonIgnore
    public BookInventory getBookInventory(){
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBook(this.getBook());
        bookInventory.setQuantity(this.quantity);
        bookInventory.setId(StringUtils.isEmpty(this.id)?UUID.randomUUID().toString():this.id);
        return bookInventory;
    }

    public static BookOrderDetail fromBookInventory(BookInventory bookInventory){
        BookOrderDetail bookOrderDetail = new BookOrderDetail();
        BeanUtils.copyProperties(bookInventory.getBook(), bookOrderDetail);
        bookOrderDetail.setQuantity(bookInventory.getQuantity());
        return bookOrderDetail;
    }
}
