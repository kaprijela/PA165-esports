package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validator;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableWebMvc
@Configuration
//@Import({EshopWithSampleDataConfiguration.class})
@ComponentScan(basePackages = {"controllers"})
public class RestSpringMvcConfig implements WebMvcConfigurer {

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    // see  http://stackoverflow.com/questions/25709672/how-to-change-hal-links-format-using-spring-hateoas
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer c) {
        c.defaultContentType(MediaTypes.HAL_JSON);
    }


    @Bean
    public ObjectMapper objectMapper() {
        //configuring mapper for HAL
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH));
        return objectMapper;
    }

    /**
     * Provides JSR-303 Validator.
     *
     * @return JSR-303 validator
     */
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}