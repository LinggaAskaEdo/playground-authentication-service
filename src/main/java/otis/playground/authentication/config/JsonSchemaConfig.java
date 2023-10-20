package otis.playground.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.JanLoebel.jsonschemavalidation.advice.JsonValidationRequestBodyControllerAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonSchemaConfig {
  @Bean
  public JsonValidationRequestBodyControllerAdvice JsonValidationRequestBodyControllerAdvice(ObjectMapper objectMapper) {
    // We construct a custom jsonSchemaProvider to configure the module.
    return new JsonValidationRequestBodyControllerAdvice(objectMapper, new CustomJsonSchemaProvider());
  }
}
