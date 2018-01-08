package com.codeinsight.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.codeinsight.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
 
    private RequestCache requestCache = new HttpSessionRequestCache();
 
    @Resource
    private AuthenticationService authenticationService;
    
    @Override
    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response,
    		                             Authentication authentication) 
      throws ServletException, IOException {
  
    	if ("application/json".equals(request.getHeader("Content-Type"))) {
    	    	   
    	   ObjectMapper objectMapper = new  ObjectMapper();
    	   
           response.getWriter().print( objectMapper.writeValueAsString( authenticationService.getAuthenticatedUser() ) );
           response.getWriter().flush();
    	   
    	}else{
    	
        SavedRequest savedRequest
          = requestCache.getRequest(request, response);
 
        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        
        if (isAlwaysUseDefaultTargetUrl()
          || (targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) 
        {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }
 
        clearAuthenticationAttributes(request);
        
    	}
    }
 
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
