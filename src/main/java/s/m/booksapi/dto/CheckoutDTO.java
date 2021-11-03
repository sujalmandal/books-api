package s.m.booksapi.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CheckoutDTO {
    private Set<BookQtyDTO> books;
    private String couponCode;
}
