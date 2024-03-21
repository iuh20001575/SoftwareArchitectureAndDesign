package vn.iuh.iuh.fit;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.iuh.iuh.fit.models.Product;
import vn.iuh.iuh.fit.models.ProductQuantity;
import vn.iuh.iuh.fit.repositories.ProductRepository;

import java.util.Random;

@SpringBootApplication
public class ActiveMqLabMvnApplication {
    private final ProductRepository productRepository;

    public ActiveMqLabMvnApplication(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ActiveMqLabMvnApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            Faker faker = new Faker();

            Book book = faker.book();
            Random random = new Random();

            for (int i = 0; i < 20; i++) {
                Product product = new Product(book.title(), book.author(), null, "https://picsum.photos/300/300.jpg", faker.number().randomDouble(2, 50000, 100000));
                ProductQuantity productQuantity = new ProductQuantity(product, random.nextFloat() * 10000);

                product.setQuantity(productQuantity);

                productRepository.save(product);
            }
        };
    }
}
