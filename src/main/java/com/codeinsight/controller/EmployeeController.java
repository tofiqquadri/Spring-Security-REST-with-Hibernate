package com.codeinsight.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeinsight.bean.UiEmployee;
import com.codeinsight.service.EmployeeService;

@RestController
public class EmployeeController {

	@Resource
	private EmployeeService employeeService;

	
	//CREATE AN EMPLOYEE
	@PostMapping( path = { "/employee/create" } )
	public void createEmployee( @RequestBody UiEmployee employee)
	{
		
		try {			
			employeeService.addNewEmployee(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//DELETE AN EMPLOYEE BY ID
	
	//GET ALL EMPLOYEES
	@GetMapping( path = { "/employee/getall" } )
	public @ResponseBody List<UiEmployee> getAllEmployees( )
	{
		
		List<UiEmployee> beans = new ArrayList<>();
		
		try {
			beans = employeeService.getAllEmployees();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return beans;
		
	}
	
	
	//GET AN EMPLOYEE BY ID
	
	
}
