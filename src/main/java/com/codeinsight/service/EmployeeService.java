package com.codeinsight.service;

import java.util.List;

import com.codeinsight.bean.UiEmployee;
import com.codeinsight.entity.Employee;

public interface EmployeeService {

	public void addNewEmployee(UiEmployee employee) throws Exception;
	
	public List<UiEmployee> getAllEmployees() throws Exception;
	
	public Employee getAuthenticUserByUsername( String username ) throws Exception;
	
	public UiEmployee get( Long id ) throws Exception;
	
}
