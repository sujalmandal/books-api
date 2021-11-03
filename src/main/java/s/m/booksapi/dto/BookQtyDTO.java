package s.m.booksapi.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class BookQtyDTO {

        private String ISBN;
        private Integer qty=1;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                BookQtyDTO bookQtyDTO = (BookQtyDTO) o;
                return Objects.equals(ISBN, bookQtyDTO.ISBN);
        }

        @Override
        public int hashCode() {
                return Objects.hash(ISBN);
        }
}