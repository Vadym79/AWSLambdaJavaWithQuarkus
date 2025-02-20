// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.handler;

import org.crac.Core;
import org.crac.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazonaws.example.product.dao.DynamoProductDao;
import software.amazonaws.example.product.dao.ProductDao;


//@Startup
@ApplicationScoped
public class AmazonDynamoDBPrimingResource implements Resource {
	
	private static final Logger logger = LoggerFactory.getLogger(AmazonDynamoDBPrimingResource.class);
	
	private final ProductDao productDao = new DynamoProductDao();
	
	
    @PostConstruct
	public void init () {
		Core.getGlobalContext().register(this);
	}

	@Override
	public void beforeCheckpoint(org.crac.Context<? extends Resource> context) throws Exception {
		 logger.info("enter before checkpoint method");
		 productDao.getProduct("0");
	}

	@Override
	public void afterRestore(org.crac.Context<? extends Resource> context) throws Exception {
	}
	
}