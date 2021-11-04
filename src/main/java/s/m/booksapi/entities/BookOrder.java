package s.m.booksapi.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "book_order")
@ToString
public class BookOrder {
    @Id
    private String id;
    @ManyToMany
    @JoinTable(
            name = "book_order_mapping",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_detail_id", referencedColumnName = "id")
    )
    private Set<BookOrderDetail> books;
    @ManyToMany
    private Set<Discount> discounts;
    private Double totalPriceBeforeDiscount;
    private Double totalPriceAfterDiscount;
    private Status orderStatus;

    public enum Status{
        PENDING,
        COMPLETE
    }

    public BookOrder(){
        this.setId(UUID.randomUUID().toString());
        this.setOrderStatus(BookOrder.Status.PENDING);
    }

}
