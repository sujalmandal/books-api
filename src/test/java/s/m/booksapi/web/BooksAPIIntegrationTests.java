package s.m.booksapi.web;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import s.m.booksapi.dto.ResponseDTO;
import s.m.booksapi.entities.BookOrderDetail;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static s.m.booksapi.web.util.TestDataUtil.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksAPIIntegrationTests {

    private final String API_URI_TEMPLATE = "http://localhost:%s/books-api/%s";
    private final String ROOT ="/";
    private final String BY_ISBN ="/%s";

    @LocalServerPort
    private int port;

    @Autowired
    private BooksShopController booksShopController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void contextLoads() {
        assertNotNull(booksShopController);
        assertNotNull(restTemplate);
        log.info("web tests started with port number {}", port);
    }

    @Test
    @Order(2)
    public void testSaveBooks() {
        String saveBookURI = String.format(API_URI_TEMPLATE, port,ROOT);
        ResponseDTO<?> resp1 = this.restTemplate.postForObject(saveBookURI,getTestbook_1(),ResponseDTO.class);
        log.info("response : {}", resp1);
        assertNotNull(resp1);
        ResponseDTO<?> resp2 = this.restTemplate.postForObject(saveBookURI,getTestBook_2(),ResponseDTO.class);
        log.info("response : {}", resp2);
        assertNotNull(resp2);
    }

    @Test
    @Order(3)
    public void testGetAllBooks() {
        String getAllBooksURI = String.format(API_URI_TEMPLATE, port, ROOT);
        ResponseDTO<?> resp = this.restTemplate.getForObject(getAllBooksURI, ResponseDTO.class);
        log.info("response : {}", resp);
        assertNotNull(resp);
        assertNotNull(resp.getBody());
        Collection<BookOrderDetail> books = (Collection<BookOrderDetail>) resp.getBody();
        assertTrue(books.size()==2);
    }

    @Test
    @Order(3)
    public void testGetBookByISBN() {
        String getBookByISBNURI = String.format(API_URI_TEMPLATE, port,
                String.format(BY_ISBN, TEST_ISBN_1));
        ResponseDTO<?> resp = this.restTemplate.getForObject(getBookByISBNURI, ResponseDTO.class);
        log.info("response : {}", resp);
        assertNotNull(resp);
        assertNotNull(resp.getBody());
    }

    @Test
    @Order(4)
    public void testUpdateBook() {
        String getBookByISBNURI = String.format(API_URI_TEMPLATE, port,
                String.format(BY_ISBN, TEST_ISBN_1));

        ResponseDTO<?> savedBookResponse =
                this.restTemplate.getForObject(getBookByISBNURI, ResponseDTO.class);

        String savedBookId = (String) savedBookResponse.getBodyAsMap().get("id");

        BookOrderDetail bookToUpdate = getTestbook_1();
        bookToUpdate.setName(bookToUpdate.getName()+" UPDATED");
        /* required for update */
        bookToUpdate.setId(savedBookId);
        String putURI = String.format(API_URI_TEMPLATE, port, ROOT);
        /* update */
        restTemplate.put(putURI, bookToUpdate);
        ResponseDTO<?> updatedBookResponse =
                this.restTemplate.getForObject(getBookByISBNURI, ResponseDTO.class);
        String updatedName = (String) updatedBookResponse.getBodyAsMap().get("name");
        assertTrue(updatedName.contains("UPDATED"));
    }

    @Test
    @Order(5)
    public void testDeleteBook() {
        String getDropBookURI = String.format(API_URI_TEMPLATE, port,
                String.format(BY_ISBN, TEST_ISBN_1));
        this.restTemplate.delete(getDropBookURI);

        String getBookByISBNURI = String.format(API_URI_TEMPLATE, port,
                String.format(BY_ISBN, TEST_ISBN_1));

        ResponseDTO searchResponse = restTemplate.getForObject(getBookByISBNURI, ResponseDTO.class);
        assertTrue(searchResponse.getBodyAsString().contains("ISBN was not found"));
    }

    @Test
    @Order(6)
    public void testFindNonExistentISBN() {
        String getBookByISBNURI = String.format(API_URI_TEMPLATE, port,
                String.format(BY_ISBN, TEST_ISBN_INVALID));
        ResponseDTO<?> resp = this.restTemplate.getForObject(getBookByISBNURI, ResponseDTO.class);
        log.info("response : {}", resp);
        assertTrue(resp.getBodyAsString().contains("ISBN was not found"));
    }

}
