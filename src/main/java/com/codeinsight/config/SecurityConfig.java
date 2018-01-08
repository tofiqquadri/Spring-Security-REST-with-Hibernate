package com.codeinsight.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.codeinsight.security.CustomUsernamePasswordAuthenticationFilter;
import com.codeinsight.security.MySavedRequestAwareAuthenticationSuccessHandler;
import com.codeinsight.security.RestAuthenticationEntryPoint;
import com.codeinsight.security.UserAuthDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
@ComponentScan("com.codeinsight.com")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
 
    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler
      authenticationSuccessHandler;
    
    @Resource(name = "userAuthDetailService")
    private UserDetailsService userDetailsService;
    
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//      throws Exception {
//  
//        auth.inMemoryAuthentication()
//          .withUser("temporary").password("temporary").roles("ADMIN")
//          .and()
//          .withUser("user").password("userPass").roles("USER");
//    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception { 
        http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and().addFilterBefore( authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .formLogin().loginPage("/login").permitAll()
        .successHandler(authenticationSuccessHandler)
        .failureHandler(new SimpleUrlAuthenticationFailureHandler())
        .and()
        .logout();
        
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
    }
    
    
    
    @Bean
    public CustomUsernamePasswordAuthenticationFilter authenticationFilter() throws Exception{
    	
    	CustomUsernamePasswordAuthenticationFilter authFilter = new CustomUsernamePasswordAuthenticationFilter();
    	
    	authFilter.setRequiresAuthenticationRequestMatcher( new AntPathRequestMatcher("/login", "POST") );
    	authFilter.setAuthenticationManager(authenticationManagerBean());
    	authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    	authFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
    	authFilter.setUsernameParameter("username");
    	authFilter.setPasswordParameter("password");
    	
    	return authFilter;
    	
    }
 
    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler(){
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }
    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure( AuthenticationManagerBuilder authenticationManagerBuilder ) throws Exception
    {
    	authenticationManagerBuilder.userDetailsService(userDetailsService);
    	
    }
}