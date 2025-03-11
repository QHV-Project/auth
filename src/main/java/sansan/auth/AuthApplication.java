package sansan.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@EntityScan(basePackages = "sansan.auth.Entity")
@ComponentScan(basePackages = {"sansan.utility.lib", "sansan.auth"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void checkBeans() {
        System.out.println("===== LIST OF BEANS =====");
        Arrays.stream(context.getBeanDefinitionNames()).sorted().forEach(System.out::println);
    }
}
