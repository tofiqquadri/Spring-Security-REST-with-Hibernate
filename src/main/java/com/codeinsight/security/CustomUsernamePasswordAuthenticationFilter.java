package com.codeinsight.security;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codeinsight.bean.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	    private String jsonUsername;
	    private String jsonPassword;

	    @Override
	    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
	    	
	        if ("application/json".equals(request.getHeader("Content-Type"))) {
	            try {
	                /*
	                 * HttpServletRequest can be read only once
	                 */
	                StringBuffer sb = new StringBuffer();
	                String line = null;

	                BufferedReader reader = request.getReader();
	                while ((line = reader.readLine()) != null){
	                    sb.append(line);
	                }

	                //json transformation
	                ObjectMapper mapper = new ObjectMapper();
	                LoginRequest loginRequest = mapper.readValue(sb.toString(), LoginRequest.class);

	                this.jsonUsername = loginRequest.getUsername();
	                this.jsonPassword = loginRequest.getPassword();
	                
	                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
	                		this.jsonUsername, this.jsonPassword);

	    			// Allow subclasses to set the "details" property
	    			setDetails(request, authRequest);

	    			return this.getAuthenticationManager().authenticate(authRequest);
	            
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            throw new InternalAuthenticationServiceException("Failed to parse authentication request body");
	        }
	            
	        }
	        
	        return null;
	    }
	
}