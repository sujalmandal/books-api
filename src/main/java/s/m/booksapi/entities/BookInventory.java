package s.m.booksapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book_inventory")
public class BookInventory {
    @Id
    private String id;
    @Embedded
    private Book book;
    private int quantity;
}
