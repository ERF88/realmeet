package com.github.erf88.realmeet.filter;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.github.erf88.realmeet.domain.entity.Client;
import com.github.erf88.realmeet.domain.repository.ClientRepository;
import java.io.IOException;
import java.io.Writer;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class VerifyApiKeyFilter extends GenericFilterBean {
	private static final String HEADER_API_KEY = "api-key";

	private final ClientRepository clientRepository;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String apiKey = request.getHeader(HEADER_API_KEY);

		if(!isBlank(apiKey) && !isValidApiKey(apiKey)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			sendUnauthorizedError(response, apiKey);
		}
	}

	private void sendUnauthorizedError(HttpServletResponse response, String apiKey) throws IOException {
		String errorMessage = isBlank(apiKey) ? "API Key is missing" : "API Key is invalid";
		log.error(errorMessage);

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentLength(errorMessage.length());
		response.setContentType("plain/text");

		try (Writer out = response.getWriter()) {
			out.write(errorMessage);
		}
	}

	private boolean isValidApiKey(String apiKey) {
		return clientRepository.findById(apiKey)
			.filter(Client::getActive)
			.stream()
			.peek(c -> log.info("Valid API Key: '{}' ({})", c.getApiKey(), c.getDescription()))
			.findFirst()
			.isPresent();
	}
}
