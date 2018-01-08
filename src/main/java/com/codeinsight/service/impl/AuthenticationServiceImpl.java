package com.codeinsight.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.codeinsight.security.AuthenticatedUser;
import com.codeinsight.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Override
	public AuthenticatedUser getAuthenticatedUser() {

		AuthenticatedUser authenticatedUser = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			Object authenticationObject = authentication.getPrincipal();

			if (authenticationObject != null && authenticationObject instanceof AuthenticatedUser) {
				authenticatedUser = (AuthenticatedUser) authenticationObject;
			}
		}

		return authenticatedUser;
	}

}
