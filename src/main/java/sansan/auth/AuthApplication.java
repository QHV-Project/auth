package sansan.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "sansan.auth.Entity")
//@EnableFeignClients(basePackages = "sansan.utility.lib.Service")
//@ComponentScan(basePackages = {"sansan.utility.lib", "sansan.auth"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
