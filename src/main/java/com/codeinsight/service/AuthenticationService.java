package com.codeinsight.service;

import com.codeinsight.security.AuthenticatedUser;

public interface AuthenticationService {

	public AuthenticatedUser getAuthenticatedUser();
	
}
