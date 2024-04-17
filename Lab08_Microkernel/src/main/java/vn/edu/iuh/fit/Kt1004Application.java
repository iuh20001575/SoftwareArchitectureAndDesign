package vn.edu.iuh.fit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

@SpringBootApplication
public class Kt1004Application {
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            String path = new File("src/main/resources/application.properties").getAbsolutePath();
            properties.load(new FileReader(path));

            properties.forEach((key, value) -> {
                PluginManager.loadPlugin(value.toString());
            });

            PluginManager.PLUGINS.values().forEach(clazz -> {
                if (Language.class.isAssignableFrom(clazz.getClass())) {
                    try {
                        ((Language) clazz).readData();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SpringApplication.run(Kt1004Application.class, args);
    }
}
