package com.graphqljava.tutorial.authordetails;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.io.Resources;
import com.graphqljava.tutorial.authordetails.model.Author;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.gqljf.federation.FederatedEntityResolver;
import io.gqljf.federation.FederatedSchemaBuilder;
import io.gqljf.federation.tracing.FederatedTracingInstrumentation;

@Component
public class GraphQLProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(GraphQLProvider.class);

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
    	// Old Implementation:
        /*URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();*/
        // Changes related to Gateway Schema Support:
        InputStream url = Resources.getResource("schema.graphqls").openStream();        
        Logger LOGGER2 = LoggerFactory.getLogger(FederatedEntityResolver.class);
        List<FederatedEntityResolver<?, ?>> entityResolvers = List.of(
        		new FederatedEntityResolver<Long, Author>("Author", id -> graphQLDataFetchers.getAuthorById(id)) {
                }
        		/*
                new FederatedEntityResolver<String, Book>("Book", id -> new Book(id)) {
                	
                	//LOGGER2.info("Err: {}", ex);
                	}*/
                );

        GraphQLSchema transformedGraphQLSchema = new FederatedSchemaBuilder()
                .schemaInputStream(url)
                .runtimeWiring(buildWiring())
                .federatedEntitiesResolvers(entityResolvers)
                .build();
        
        //GraphQLSchema transformedGraphQLSchema = new FederatedSchemaBuilder().schemaInputStream(url).runtimeWiring(buildWiring()).build();       
        
        this.graphQL = GraphQL.newGraphQL(transformedGraphQLSchema)
        		.instrumentation(new FederatedTracingInstrumentation())
        		.build();
        
        LOGGER.info(graphQL.toString());
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("authorById", graphQLDataFetchers.getAuthorDataFetcher()))
                //.type(newTypeWiring("Author")
                        //.dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

}
