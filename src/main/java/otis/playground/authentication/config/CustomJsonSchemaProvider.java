package otis.playground.authentication.config;

import com.github.JanLoebel.jsonschemavalidation.provider.CacheableJsonSchemaProvider;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public class CustomJsonSchemaProvider extends CacheableJsonSchemaProvider {
    @Override
    public JsonSchema loadSchema(String url) {
        // Set base path of the schemas, so it's easier to annotate the e.g.: BookDto with the path.
        // Full path then will be: 'classpath:schema/book.json'
        final String fullPath =  "classpath:schema/" + url;
        return super.loadSchema(fullPath);
    }

    @Override
    protected JsonSchemaFactory getJsonSchemaFactory() {
        // In this example we use version 4 of the json schema specification, so we have to create a json
        // schema factory with this version. By default, version 7 is used.
        return JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
    }

    @Override
    public void handleValidationMessages(Collection<ValidationMessage> validationMessages) {
        // Here you could handle the validation messages yourself
        super.handleValidationMessages(validationMessages);
    }
}
