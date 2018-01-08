package com.codeinsight.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codeinsight.bean.UiEmployee;
import com.codeinsight.entity.Employee;

@Component
public class EmployeeConverter extends AbstractConverter<Employee, UiEmployee>{
	
	public UiEmployee getBeanFromEntity( Employee entity )
	{
		
		UiEmployee bean = new UiEmployee();
		
		bean.setName( entity.getName() );
		bean.setSalary( entity.getSalary() );
        bean.setId( entity.getId() );
        
        return bean;
		
	}
	
	public Employee getEntityFromBean( UiEmployee bean)
	{
		
		Employee entity = new Employee();
		
		entity.setName( bean.getName() );
		entity.setSalary( bean.getSalary() );
		
		return entity;
	}

	@Override
	public List<Employee> getEntitiesFromBeans(List<UiEmployee> beans) {

		List<Employee> entities = new ArrayList<>();
		
		for( UiEmployee bean: beans ) {
			
			entities.add( getEntityFromBean(bean) );
			
		}
		
		return entities;
	}

	@Override
	public List<UiEmployee> getBeansFromEntities(List<Employee> entities) {

		List<UiEmployee> beans = new ArrayList<>();
		
		for( Employee entity: entities ) {
			
			beans.add( getBeanFromEntity(entity) );
			
		}		
		
		return beans;
	}

	
}
