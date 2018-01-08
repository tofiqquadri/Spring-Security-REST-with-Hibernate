package com.codeinsight.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.codeinsight.entity.Employee;

@Repository
public class EmployeeDao extends AbstractDao<Employee>{
	
	public EmployeeDao() {
		super(Employee.class);
	}
	
	public Employee findByUsername(String userName)
	{
		
	    StringBuilder hql = new StringBuilder(); 

		hql.append( "SELECT distinct employee " );
		hql.append( "FROM Employee employee " );
		hql.append( "WHERE 1=1 AND " );
		hql.append( "employee.name=:name" );		
		
		TypedQuery<Employee> query = getCurrentSession().createQuery( hql.toString(), Employee.class ); 
	
		query.setParameter("name", userName);
		
		return query.getSingleResult();
	}


}
