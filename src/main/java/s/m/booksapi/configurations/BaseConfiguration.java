package s.m.booksapi.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import s.m.booksapi.dao.*;
import s.m.booksapi.loader.InitialInventoryLoader;
import s.m.booksapi.loader.InitialDiscountsLoader;
import s.m.booksapi.service.*;
import s.m.booksapi.service.impl.BookCatalogServiceImpl;
import s.m.booksapi.service.impl.CheckoutServiceImpl;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Component
@Slf4j
public class BaseConfiguration implements InitializingBean, DisposableBean {

    private static final String WEB_CONTROLLER_PATH = "s.m.booksapi.web";

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("loading configurations..");
    }

    /* swagger configuration */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(WEB_CONTROLLER_PATH))
                .build();
    }

    /* data-loader configurations */
    @Bean
    public InitialInventoryLoader injectInitialCatalogLoader(
            BookInventoryRepository bookInventoryRepository){
        return new InitialInventoryLoader(
                bookInventoryRepository
        );
    }

    @Bean
    public InitialDiscountsLoader injectDiscountLoader(final DiscountRepository discountRepository){
        return new InitialDiscountsLoader(discountRepository);
    }

    /* service configurations */
    @Bean
    public BookCatalogService injectBookCatalogService(BookInventoryRepository bookInventoryRepository){
        return new BookCatalogServiceImpl(bookInventoryRepository);
    }

    @Bean
    public ValidationService injectValidationService(
            BookInventoryRepository bookInventoryRepository, CheckoutRepository checkoutRepository,
            DiscountRepository discountRepository, OrderBookDetailRepository orderBookDetailRepository){
        return new ValidationService(
                bookInventoryRepository,
                checkoutRepository,
                discountRepository,
                orderBookDetailRepository
        );
    }

    @Bean
    public CheckoutService injectCheckoutService(
            CheckoutRepository checkoutRepository, DiscountRepository discountRepository,
            BookCatalogService bookCatalogService, OrderBookDetailRepository orderBookDetailRepository){
        return new CheckoutServiceImpl(
                checkoutRepository,
                discountRepository,
                bookCatalogService,
                orderBookDetailRepository
        );
    }

    @Override
    public void destroy() throws Exception {
        log.info("disposing off configurations..");
    }

}
