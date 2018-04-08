package com.graph.graphquery;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class GraphQLService {

	@Value("classpath:employeeschema.graphql")
	Resource resource;

	private GraphQL graphQL;

	@Autowired
	EmployeeDataFetcher employeeDataFetcher;

	@PostConstruct
	public void loadSchema() throws IOException {
		File schemaFile = resource.getFile();
		TypeDefinitionRegistry typeDefReg = new SchemaParser().parse(schemaFile);
		RuntimeWiring runTimeWiring = buildRunTimeWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefReg, runTimeWiring);
		graphQL = GraphQL.newGraphQL(schema).build();

	}

	private RuntimeWiring buildRunTimeWiring() {
		return RuntimeWiring.newRuntimeWiring().type("Query", tw -> tw.dataFetcher("employee", employeeDataFetcher))
				.build();
	}

	public ExecutionResult executeQuery(String query) {
		return graphQL.execute(query);
	}

	public GraphQL getGraphQL() {
		return graphQL;
	}
}