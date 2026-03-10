package com.shivanshu.Assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class CorsConfig {
    //CORS- means Cross Origin Resource sharing, if I will call backend api i.e, on port 8080 from
    //frontend then it will block my request due ti security policies, so I need to tell backend to
    //allow request from my frontend which is on port 4200(see allowed origins and methods)
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        //WebMvc->interface provided by Spring MVC
        //addCordMapping is a method already contained by this interface and we are implementing
        //the interface directly inside this above method
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //registry->object provided by spring which allow us to register Cors rules like which URL, which Origin, which method etc etc
                registry.addMapping("/**")  //apply cors rules to these endpoints, **->means all
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*"); //allow all headers like Authorization, accept etc
            }
        };
    }
}
//note : /** we used this which means all controllers will automatically allow angular requests, and we won't be needing @CrossOrigin(origins="4200 link") on each Controller class