package s.m.booksapi.loader;

import lombok.extern.slf4j.Slf4j;
import s.m.booksapi.entities.Book;
import s.m.booksapi.entities.BookInventory;
import s.m.booksapi.dao.BookInventoryRepository;

import java.util.UUID;

/* hard coded data loader for dev purposes*/
@Slf4j
public class InitialInventoryLoader {

    private final BookInventoryRepository bookInventoryRepository;

    public InitialInventoryLoader(BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }

    public void loadInventory(){
        log.info("loading initial inventory..");
        Book b1 = new Book();
        b1.setName("Gardner's Art Through the Ages");
        b1.setAuthor("Kleiner, Fred S.");
        b1.setISBN("0495915424");
        b1.setDescription("Gardner's Art Through the Ages: A Global History");
        b1.setType(Book.Type.EDUCATIONAL);
        b1.setPrice(299.99);
        BookInventory b1Inv = new BookInventory();
        b1Inv.setBook(b1);
        b1Inv.setQuantity(3);

        Book b2 = new Book();
        b2.setName("Ghost In The Shell Volume 2");
        b2.setAuthor("Shirow, Masamune");
        b2.setISBN("159307204X");
        b2.setDescription("Ghost In The Shell Volume 2 : Man-Machine Interface");
        b2.setType(Book.Type.COMICS);
        b2.setPrice(24.95);
        BookInventory b2Inv = new BookInventory();
        b2Inv.setBook(b2);
        b2Inv.setQuantity(2);


        Book b3 = new Book();
        b3.setName("The Metamorphosis");
        b3.setAuthor("Fraz Kafka");
        b3.setISBN("0393967972");
        b3.setDescription("The Metamorphosis (Norton Critical Edition)");
        b3.setType(Book.Type.FICTIONAL);
        b3.setPrice(3.95);
        BookInventory b3Inv = new BookInventory();
        b3Inv.setBook(b3);
        b3Inv.setQuantity(1);

        Book b4 = new Book();
        b4.setName("Journey Through Time");
        b4.setAuthor("Stephen Hawkins");
        b4.setISBN("9781788037587");
        b4.setDescription("The Metamorphosis (Norton Critical Edition)");
        b4.setType(Book.Type.SCIENCE);
        b4.setPrice(100);
        BookInventory b4Inv = new BookInventory();
        b4Inv.setBook(b4);
        b4Inv.setQuantity(2);

        this.bookInventoryRepository.save(b1Inv);
        this.bookInventoryRepository.save(b2Inv);
        this.bookInventoryRepository.save(b3Inv);
        this.bookInventoryRepository.save(b4Inv);

        log.info("saved books {}, {}, {}, {}", b1, b2, b3, b4);
    }

}
