package vn.edu.iuh.fit;

import com.thedeanda.lorem.LoremIpsum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.ProductRepository;

import java.util.Random;

@SpringBootApplication
public class Lab07PipelineInvoiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab07PipelineInvoiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ProductRepository productRepository) {
        return args -> {
            LoremIpsum instance = LoremIpsum.getInstance();
            Random random = new Random();

            for (int i = 0; i < 20; i++) {
                Product product = new Product(
                        instance.getName(),
                        instance.getWords(10, 30),
                        random.nextInt(),
                        "https://source.unsplash.com/random/300×300",
                        random.nextDouble()
                );

                productRepository.save(product);
            }
        };
    }
}
