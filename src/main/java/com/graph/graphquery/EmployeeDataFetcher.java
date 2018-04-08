package com.graph.graphquery;

import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class EmployeeDataFetcher implements DataFetcher<Employee> {

	@Override
	public Employee get(DataFetchingEnvironment arg0) {
		Employee emp = new Employee();
		emp.setId("1");
		emp.setName("Mani");
		emp.setAddress("Karur");

		return emp;
	}

}
