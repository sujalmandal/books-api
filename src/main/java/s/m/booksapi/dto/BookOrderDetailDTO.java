package s.m.booksapi.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import s.m.booksapi.entities.Book;
import s.m.booksapi.entities.BookOrderDetail;

import java.util.Objects;

@Data
public class BookOrderDetailDTO {
    private String ISBN;
    private String name;
    private String description;
    private String author;
    private Book.Type type;
    private double price;
    private int quantity;

    public BookOrderDetail getBookOrderDetail(){
        BookOrderDetail orderDetail = new BookOrderDetail();
        BeanUtils.copyProperties(this, orderDetail);
        return orderDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookOrderDetailDTO that = (BookOrderDetailDTO) o;
        return Objects.equals(ISBN, that.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }
}
