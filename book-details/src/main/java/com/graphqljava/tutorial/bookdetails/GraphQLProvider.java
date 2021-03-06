package com.graphqljava.tutorial.bookdetails;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.io.Resources;
import com.graphqljava.tutorial.bookdetails.model.Author;

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
        
        List<FederatedEntityResolver<?, ?>> entityResolvers = List.of(
        		new FederatedEntityResolver<Long, Author>("Author", authorId -> graphQLDataFetchers.getAuthorByIdWithBooks(authorId)) {
                }
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
                        .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher())
                        .dataFetcher("booksByAuthorId", graphQLDataFetchers.getBooksByAuthorIdDataFetcher()))
                //.type(newTypeWiring("Book")
                        //.dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

}
