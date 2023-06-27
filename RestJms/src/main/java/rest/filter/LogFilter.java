package rest.filter;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
/*
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
*/

import rest.jms.JmsDto;
import rest.jms.JmsService;
import rest.brodcast.BrodcastService;

@Order(10)
//@Component
public class LogFilter implements Filter{
	//public class LogFilter extends OncePerRequestFilter {
	//private final static Logger logger = getLog();
	//private final static String Log = "C:/Users/RSR/Documents/Software/java/logs/App.log";
	private final static Logger logger = LoggerFactory.getLogger("AppLog");
		
	//@Autowired
	//private JmsService jmsService;
	private JmsService jmsService = new JmsService();

	private BrodcastService brodcastService = new BrodcastService();
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {}
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse  response, FilterChain chain) throws IOException, ServletException  {
    //public void doFilterInternal(HttpServletRequest request, HttpServletResponse  response, FilterChain chain) throws IOException, ServletException  {
       Timestamp timestamp = new Timestamp(System.currentTimeMillis());
       String idtime, hdr, url="";
       
       idtime = "{Id: "+timestamp;
       hdr = idtime + ", User: " + getUsuario();
       if (request instanceof HttpServletRequest) {
    	   url = ((HttpServletRequest)request).getRequestURL().toString();
    	   hdr += ", Url: " + url    	   
    		   +  ", Method: " + ((HttpServletRequest)request).getMethod() + "}";    	   
       }
       //if ("POST".equalsIgnoreCase(((HttpServletRequest)request).getMethod())) {
       //   msg += "  \\r " + request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
       //}
       ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
       ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);       	   
       try {
           chain.doFilter(requestWrapper, responseWrapper);
    	   //chain.doFilter(request, response);
       } finally {
    	   //if (url.contains("/restapi/")) {
    	       //String requestBody = performRequestAudit(requestWrapper);
	           //String responseBody = performResponseAudit(responseWrapper);
	           //logger.info("***** "+hdr+" Request Body:: " + requestBody + "Reponse Body:: "+responseBody);
	           //JmsDto jsmDto = jmsService.send(hdr, requestBody, responseBody);
	           JmsDto jmsDto = new JmsDto(hdr, performRequestAudit(requestWrapper), performResponseAudit(responseWrapper));
	           logger.info(jmsDto.toString());
	           jmsService.send(jmsDto);
	           brodcastService.brodcast(jmsDto);
    	   //}
    	   responseWrapper.copyBodyToResponse();
       }
    }

    @Override
    public void destroy() {}
    
    private String getUsuario() {
    	String usuario="public";
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        Object principal =  auth.getPrincipal();
        if (principal != null && principal.getClass() != String.class) {
        	UserDetails userDetails = (UserDetails) principal; 
            usuario = userDetails.getUsername();        	
        }
        return usuario;
    }

    private String performRequestAudit(ContentCachingRequestWrapper requestWrapper) {
    	String payload="";
        if (requestWrapper != null
        		&& requestWrapper.getContentAsByteArray() != null 
        		&& requestWrapper.getContentAsByteArray().length > 0) {
        	payload = getPayLoadFromByteArray(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding());

        	/*
        	logger.info( "\\n Headers:: {} "
            		+  new ServletServerHttpRequest((HttpServletRequest)requestWrapper.getRequest()).getHeaders()
                    + "\\n Request Body:: {}" + getPayLoadFromByteArray(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding()));
            logger.info(idtime+"Request Body:: "
            	+ getPayLoadFromByteArray(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding()));
			*/
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
            //logger.info(idtime+"Reponse Body:: "+payload);
            //logger.info(idtime+"Reponse Body:: "
            //    + getPayLoadFromByteArray(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding()));
            //responseWrapper.copyBodyToResponse();
        } else {
            performErrorResponseAudit(responseWrapper);
        }
        return payload;
    }

    private void performErrorResponseAudit(ContentCachingResponseWrapper responseWrapper) {
        //logger.warning("Response void:: HTTP Satus Code = " + responseWrapper.getStatus());
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

    /*
    private static Logger getLog() {
    	// Logger.getLogger(AppFilter.class.getName());
	    Logger logger = Logger.getLogger("AppLog");  
	    FileHandler fh;  	
	    try {  	
	        fh = new FileHandler(Log);  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	        //logger.setUseParentHandlers(false); // Remove Console
	        logger.info("Start log");  	
	    } catch (SecurityException | IOException e) {  
	        e.printStackTrace();  
	    }  
	    return logger;
    }
    */

}