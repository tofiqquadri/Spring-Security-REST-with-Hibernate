package com.codeinsight.security;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeinsight.bean.UiEmployee;
import com.codeinsight.entity.Employee;
import com.codeinsight.service.EmployeeService;

@Service( "userAuthDetailService" )
public class UserAuthDetailService  implements UserDetailsService{

	@Resource
	private EmployeeService employeeService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AuthenticatedUser authenticatedUser = null;
		
		try{
			
			Employee user = employeeService.getAuthenticUserByUsername(username);
			
			if( user == null)
			{
				throw new  UsernameNotFoundException( "Username does not exists" );
			}else{
				
				UiEmployee uiEmployee = employeeService.get( user.getId() );
				
				authenticatedUser = new AuthenticatedUser(uiEmployee.getName(), user.getPassword(), new ArrayList<SimpleGrantedAuthority>());
				
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken( authenticatedUser.getUsername() , authenticatedUser.getPassword(), new ArrayList<SimpleGrantedAuthority>());

				authenticationToken.setDetails( authenticatedUser );
				
				SecurityContextHolder.getContext().setAuthentication( authenticationToken );
				
			}
			
		}catch( Exception e )
		{
			throw new  UsernameNotFoundException( "Username does not exists" );
		}
		
		return authenticatedUser;
	}

}
