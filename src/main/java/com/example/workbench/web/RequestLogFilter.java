package com.example.workbench.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestLogFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        // Java 8 servlet lifecycle hook kept explicit for modernization surface.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest http = (HttpServletRequest) request;
        long started = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            log.info("{} {} -> {} ms", http.getMethod(), http.getRequestURI(), System.currentTimeMillis() - started);
        }
    }

    @Override
    public void destroy() {
        // no-op
    }
}
