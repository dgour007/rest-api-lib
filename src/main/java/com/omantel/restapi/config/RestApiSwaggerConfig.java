/**
 * 
 */
package com.omantel.restapi.config;

/*import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;*/

/**
 * @author Dhiraj Gour
 * @date 23 Feb 2020
 *
 */
//@Configuration
//@EnableSwagger2
public class RestApiSwaggerConfig {
	
	/*@Value("${spring.application.name}")
	String applicationName;
	
	@Value("${applicatoin.description}")
	String description;
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("om.omantel"))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiDetails());
    }
    
    private ApiInfo apiDetails() {
    	return new ApiInfo(applicationName, description, "1.0", 
    			"For internal use only", 
    			new Contact("CRM+ Team", "Omantel", "crm@omantel.om") , 
    			"API License @ 2020", "Omantel.om", Collections.emptyList());
    }*/
}