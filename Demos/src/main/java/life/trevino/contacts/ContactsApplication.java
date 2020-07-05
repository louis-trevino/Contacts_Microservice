package life.trevino.contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
/**
 * @author Louis Trevino
 *
 * Copyright (C) 2020. Louis Trevino.
 */
public class ContactsApplication {


    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("Contact Microservice").select()
                .apis(RequestHandlerSelectors.basePackage("life.trevino.contacts"))
                .paths(PathSelectors.any()).build().apiInfo(new ApiInfo("Contact Microservice",
                        "Contact Microservice", "1.0.0", null,
                        new Contact("Louis Trevino", "https://github.com/louis-trevino/go-uno/", "louis.trevino@gmail.com"),null, null));
    }



    public static void main(String[] args) {
        SpringApplication.run(ContactsApplication.class, args);
    }

}
