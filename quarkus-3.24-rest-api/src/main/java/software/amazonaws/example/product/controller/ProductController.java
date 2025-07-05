// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jboss.resteasy.reactive.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import software.amazonaws.example.product.dao.ProductDao;
import software.amazonaws.example.product.entity.Product;

@Path("/products")
public class ProductController {

	@Inject
	private ProductDao productDao;

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@PUT	
	@Path("/")
	@Consumes("application/json")
	public Response createProduct(@Valid @NotNull Product product) {
		logger.info("entered create product "+product);
		productDao.putProduct(product);
		return Response.status(Response.Status.CREATED).entity(product).build();
	}

	@GET
	@Path("/{id}")
	public Response getProductById(@RestPath String id) {
		logger.info("entered get product by id "+id);
		Optional<Product> optionalProduct = productDao.getProduct(id);
		if (optionalProduct.isPresent()) {
			logger.info(" product found : " + optionalProduct.get());
			return Response.ok(optionalProduct.get()).build();
		}
		else {
			logger.info(" product with id " + id + " not found ");
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}