package software.amazonaws.example;

import java.util.HashSet;

import org.joda.time.DateTime;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazonaws.example.product.entity.Product;
import software.amazonaws.example.product.entity.Products;

@RegisterForReflection(targets = {
		APIGatewayProxyRequestEvent.class,
		HashSet.class, 
		APIGatewayProxyRequestEvent.ProxyRequestContext.class, 
		APIGatewayProxyRequestEvent.RequestIdentity.class,
        DateTime.class,
        Product.class,
        Products.class,
})
public class ReflectionConfig {

}
