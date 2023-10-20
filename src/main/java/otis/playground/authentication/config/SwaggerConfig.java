package otis.playground.authentication.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otis.playground.authentication.controller.AuthController;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("*")
                .displayName("All")
                .pathsToMatch("/api/**")
                .packagesToScan("otis.playground.authentication.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("1")
                .displayName("Authentication")
                .pathsToMatch("/api/auth/**")
                .packagesToScan(AuthController.class.getPackageName())
                .build();
    }

    @Bean
    public GroupedOpenApi testApi() {
        return GroupedOpenApi.builder()
                .group("2")
                .displayName("Test")
                .pathsToMatch("/api/test/**")
                .packagesToScan(AuthController.class.getPackageName())
                .build();
    }
}
