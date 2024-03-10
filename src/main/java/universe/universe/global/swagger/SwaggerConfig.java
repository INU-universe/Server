package universe.universe.global.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
// http://localhost:8080/swagger-ui/index.html#/
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi() {

        return new OpenAPI().components(new Components())
                .info(getInfo());

    }

    private Info getInfo() {
        return new Info()
                .version("1.0.0")
                .description("UNI REST API DOC")
                .title("UNI Server");
    }
}
