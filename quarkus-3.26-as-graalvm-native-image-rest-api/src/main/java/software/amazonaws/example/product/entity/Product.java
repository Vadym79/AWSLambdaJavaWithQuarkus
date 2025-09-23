

package software.amazonaws.example.product.entity;

import java.math.BigDecimal;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record Product(String id, String name, BigDecimal price) {
}
  