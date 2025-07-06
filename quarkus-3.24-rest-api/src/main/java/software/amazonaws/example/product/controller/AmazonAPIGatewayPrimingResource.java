// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0

package software.amazonaws.example.product.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.crac.Core;
import org.crac.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.serialization.events.LambdaEventSerializers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@Startup
@ApplicationScoped
public class AmazonAPIGatewayPrimingResource implements Resource {
	
	private static final Logger logger = LoggerFactory.getLogger(AmazonAPIGatewayPrimingResource.class);
	
	@Inject 
	ObjectMapper objectMapper;
	
    @PostConstruct
	public void init () {
		Core.getGlobalContext().register(this);
	}

	@Override
	public void beforeCheckpoint(org.crac.Context<? extends Resource> context) throws Exception {
		logger.info("enter before checkpoint method");
		APIGatewayProxyRequestEvent requestEvent = LambdaEventSerializers
				.serializerFor(APIGatewayProxyRequestEvent.class, AmazonAPIGatewayPrimingResource.class.getClassLoader())
				.fromJson(this.getAwsProxyRequestAsJson());
		new QuarkusStreamHandler().handleRequest
		 (new ByteArrayInputStream(this.convertAwsProxyRequestToJsonBytes(requestEvent)), 
				 new ByteArrayOutputStream(), new MockLambdaContext());
	}

	@Override
	public void afterRestore(org.crac.Context<? extends Resource> context) throws Exception {
	}
	
	private byte[] convertAwsProxyRequestToJsonBytes (APIGatewayProxyRequestEvent requestEvent) throws JsonProcessingException {
		return this.objectMapper.writeValueAsBytes(requestEvent);
	}
	
	
	private String getAwsProxyRequestAsJson() throws JsonProcessingException {
		return this.objectMapper.writeValueAsString(getAwsProxyRequest());
	}
    
    private static APIGatewayProxyRequestEvent getAwsProxyRequest () {
    	final APIGatewayProxyRequestEvent aPIGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent ();
    	aPIGatewayProxyRequestEvent.setHttpMethod("GET");
    	aPIGatewayProxyRequestEvent.setPath("/products/0");
    	aPIGatewayProxyRequestEvent.setPathParameters(Map.of("id","0"));
    	//aPIGatewayProxyRequestEvent.setResource("/products/{id}");
    	
    	/*
    	final ProxyRequestContext proxyRequestContext = new ProxyRequestContext();
    	final RequestIdentity requestIdentity= new RequestIdentity();
    	requestIdentity.setApiKey("blabla");
    	proxyRequestContext.setIdentity(requestIdentity);
    	aPIGatewayProxyRequestEvent.setRequestContext(proxyRequestContext);
    	*/
    	return aPIGatewayProxyRequestEvent;
    			
    }
}