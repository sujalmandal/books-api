package s.m.booksapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Book {
    private String ISBN;
    private String name;
    private String description;
    private String author;
    private Type type;
    private double price;

    /* copy constructor */
    public Book(Book book){
        BeanUtils.copyProperties(book, this);
    }

    public enum Type {
        COMICS,
        EDUCATIONAL,
        FICTIONAL,
        HISTORY,
        NOVELS,
        POETRY,
        ACADEMIC,
        SCIENCE,
        OTHERS
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN.equals(book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}
