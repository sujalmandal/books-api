package s.m.booksapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import s.m.booksapi.loader.InitialInventoryLoader;
import s.m.booksapi.loader.InitialDiscountsLoader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Objects;

@Slf4j
@SpringBootApplication
@EnableSwagger2
public class BooksAPIAppRunner implements CommandLineRunner {

	@Value("${load.data}")
	private String shouldLoadData;

	private final InitialInventoryLoader inventoryLoader;
	private final InitialDiscountsLoader discountsLoader;

	@Autowired
	public BooksAPIAppRunner(InitialInventoryLoader inventoryLoader, InitialDiscountsLoader discountsLoader) {
		this.inventoryLoader = inventoryLoader;
		this.discountsLoader = discountsLoader;
	}

	public static void main(String[] args) {
		SpringApplication.run(BooksAPIAppRunner.class, args);
	}

	@Override
	public void run(String... args) {
		if(Boolean.valueOf(shouldLoadData)){
			inventoryLoader.loadInventory();
			log.info("Property 'load.data' set to true. Loading default books.");
		} else{
			log.info("Property 'load.data' set to false. Not loading default book entries.");
		}
		discountsLoader.loadDiscounts();
	}
}
