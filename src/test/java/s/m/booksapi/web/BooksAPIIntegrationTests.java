package s.m.booksapi.web;

import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import s.m.booksapi.dto.ResponseDTO;
import s.m.booksapi.entities.Book;
import s.m.booksapi.entities.BookOrderDetail;

import java.util.Collection;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksAPIIntegrationTests {

    private final String API_URI_TEMPLATE = "http://localhost:%s/books-api/%s";
    private final String GET_ALL_BOOKS="/";
    private final String SAVE_BOOK="/";

    @LocalServerPort
    private int port;

    @Autowired
    private BooksShopController booksShopController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        assertNotNull(booksShopController);
        assertNotNull(restTemplate);
        log.info("web tests started with port number {}", port);
    }

    @Test
    @Order(1)
    public void testSaveBooks() {
        BookOrderDetail book1 = new BookOrderDetail();
        book1.setType(Book.Type.EDUCATIONAL);
        book1.setPrice(100.00);
        book1.setAuthor("Sujal Mandal");
        book1.setISBN("9999999");
        book1.setName("Round the world in 10 years");
        book1.setDescription("A budding software engineer explores the world in a decade.");
        book1.setQuantity(3);

        BookOrderDetail book2 = new BookOrderDetail();
        book2.setType(Book.Type.COMICS);
        book2.setPrice(100.00);
        book2.setAuthor("Takashi Hikimoto");
        book2.setISBN("8903222");
        book2.setName("Doraemon");
        book2.setDescription("Robot cat from the future helps his sloppy buddy.");
        book2.setQuantity(3);

        String saveBookURI = String.format(API_URI_TEMPLATE, port,SAVE_BOOK);

        ResponseDTO<?> resp1 = this.restTemplate.postForObject(saveBookURI,book1,ResponseDTO.class);
        log.info("response : {}", resp1);

        ResponseDTO<?> resp2 = this.restTemplate.postForObject(saveBookURI,book2,ResponseDTO.class);
        log.info("response : {}", resp2);

    }

    @Test
    @Order(2)
    public void testGetAllBooks() {
        String getAllBooksURI = String.format(API_URI_TEMPLATE, port,GET_ALL_BOOKS);
        ResponseDTO<?> resp = this.restTemplate.getForObject(getAllBooksURI, ResponseDTO.class);
        log.info("response : {}", resp);
        assertNotNull(resp);
        assertNotNull(resp.getBody());
        Collection<BookOrderDetail> books = (Collection<BookOrderDetail>) resp.getBody();
        assertTrue(books.size()==2);
    }
}
