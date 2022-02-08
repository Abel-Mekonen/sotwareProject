package software.project.mainClasses;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticationuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// public Collection<? extends GrantedAuthority> getAuthorities()

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		authorities.forEach(authority -> {
			if (authority.getAuthority().equals("ROLE_TECHNICIAN")) {
				try {
					redirectStrategy.sendRedirect(request, response, "/displayTechnicianProfile");
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else if (authority.getAuthority().equals("ROLE_CUSTOMER")) {
				try {
					redirectStrategy.sendRedirect(request, response, "/displayCustomerProfile");
				} catch (Exception e) {

					e.printStackTrace();
				}

			} else if (authority.getAuthority().equals("ROLE_ADMIN")) {
				try {
					redirectStrategy.sendRedirect(request, response, "/adminDashBoard");
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else {
				throw new IllegalStateException();
			}
		});

	}

}
