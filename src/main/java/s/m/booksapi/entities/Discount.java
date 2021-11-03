package s.m.booksapi.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "discounts")
public class Discount {
    @Id
    private String id;
    private Double percentageDiscount;
    private String couponCode;
    private Book.Type bookType;
    private Boolean isGenreWideDiscount = Boolean.FALSE;

    public Discount(){
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(id, discount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
