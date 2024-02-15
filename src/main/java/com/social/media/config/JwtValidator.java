package com.social.media.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.social.media.constant.JWTConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(JWTConstants.JWT_TOKEN);
		
		if(jwt!=null) {
			
			try {
				String email = JwtProvider.getEmailFromJwtToken(jwt);
				
				List<GrantedAuthority> authorities = new ArrayList<>();
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {

				throw new BadCredentialsException("Invalid Token...!");
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
