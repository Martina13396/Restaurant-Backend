package com.example.restaurant.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Restaurant End Points",
                description = "All apis for Restaurant",
                contact = @Contact(
                        name = "Martina Nageh",
                        email = "Martina.Nageh16@gmail.com",
                        url = "https://www.linkedin.com/in/martina-nageh-614700262/"
                ),
                license = @License(
                        name = "restaurant licence",
                        url = "http://localhos:4200"
                ),
                version = "1"
        )
)
public class swaggerConfiguration {
}
