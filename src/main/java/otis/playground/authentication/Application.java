package otis.playground.authentication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Boot Playground - Authentication Service",
        version = "1.0",
        description = "Implementation Auth with JWT",
        contact = @Contact(url = "https://www.instagram.com/linggaaskaedo", name = "Lingga Edo", email = "lemp.otis@gmail.com"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"))
)
@EntityScan("otis.playground.authentication.model.entity")
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
