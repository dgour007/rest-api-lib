/**
 * 
 */
package com.omantel.restapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author Dhiraj Gour
 * @date 24 Feb 2020
 *
 */
@Configuration
public class RestOpenApiConfig {

	@Value("${spring.application.name}")
	String applicationName;
	
	@Value("${springdoc.openapi.description}")
	String description;
	
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(applicationName).version("1.0")
                		.description(description));
    }
}
