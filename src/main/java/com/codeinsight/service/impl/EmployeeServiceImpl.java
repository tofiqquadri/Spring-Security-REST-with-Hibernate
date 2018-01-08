package com.codeinsight.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeinsight.bean.UiEmployee;
import com.codeinsight.converter.EmployeeConverter;
import com.codeinsight.dao.EmployeeDao;
import com.codeinsight.entity.Employee;
import com.codeinsight.service.EmployeeService;

@Service
@Transactional( readOnly = true )
public class EmployeeServiceImpl implements EmployeeService{

	@Resource
	private EmployeeDao employeeDao;
	
	@Resource
	private EmployeeConverter employeeConverter;
	
	@Transactional( readOnly = false)
	@Override
	public void addNewEmployee(UiEmployee employee) throws Exception{
		
		Employee entity = employeeConverter.getEntityFromBean( employee );
		
		employeeDao.create(entity);
		
	}

	@Override
	@Transactional
	public List<UiEmployee> getAllEmployees() throws Exception{
		
		List<Employee> entities = new ArrayList<>();
		
		entities = employeeDao.findAll();
		
		List<UiEmployee> beans = new ArrayList<>();
		
		beans = employeeConverter.getBeansFromEntities(entities);
		
		return beans;
	}

	@Override
	@Transactional
	public Employee getAuthenticUserByUsername(String username) throws Exception{
		
      return employeeDao.findByUsername(username);
	}

	@Override
	public UiEmployee get(Long id) throws Exception {

		return employeeConverter.getBeanFromEntity( employeeDao.findOne(id));
		
	}
	
}
