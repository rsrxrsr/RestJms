package rest.filter;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
//import java.sql.Timestamp;
/*
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
*/

import rest.jms.JmsDto;
//import rest.jms.JmsService;
import rest.brodcast.BrodcastService;

@Order(15)
public class BrodcastFilter implements Filter{

	private final static Logger logger = LoggerFactory.getLogger("AppLog");		
	private BrodcastService brodcastService = new BrodcastService();
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {}
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse  response, FilterChain chain) throws IOException, ServletException  {
       ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
       ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);       	   
       try {
           chain.doFilter(requestWrapper, responseWrapper);
       } finally {
	           JmsDto jmsDto = new JmsDto("hdr", performRequestAudit(requestWrapper), performResponseAudit(responseWrapper));
	           brodcastService.brodcast(jmsDto);
    	   responseWrapper.copyBodyToResponse();
       }
    }

    @Override
    public void destroy() {}
    
    private String performRequestAudit(ContentCachingRequestWrapper requestWrapper) {
    	String payload="";
        if (requestWrapper != null
        		&& requestWrapper.getContentAsByteArray() != null 
        		&& requestWrapper.getContentAsByteArray().length > 0) {
        	payload = getPayLoadFromByteArray(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding());
        }
        return payload;
    }
    
    private String performResponseAudit(ContentCachingResponseWrapper responseWrapper)
            throws IOException {
    	String payload="";
        if (responseWrapper != null
        		&& responseWrapper.getContentAsByteArray() != null
                && responseWrapper.getContentAsByteArray().length > 0) {
            payload = getPayLoadFromByteArray(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());
        } else {
            performErrorResponseAudit(responseWrapper);
        }
        return payload;
    }

    private void performErrorResponseAudit(ContentCachingResponseWrapper responseWrapper) {
        logger.warn("Response void:: HTTP Satus Code = " + responseWrapper.getStatus());
    }

    private String getPayLoadFromByteArray(byte[] requestBuffer, String charEncoding) {
        String payLoad = "";
        try {
            payLoad = new String(requestBuffer, charEncoding);
        } catch (UnsupportedEncodingException unex) {
            payLoad = "Unsupported-Encoding";
        }
        return payLoad;
    }

}