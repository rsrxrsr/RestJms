package rest.jms;

import java.io.Serializable;

public class JmsDto implements Serializable {
//public class JmsDto {
	private static final long serialVersionUID = 1L;

	private String hdr;
	  private String requestBody;
	  private String responseBody;

	  public JmsDto() {}

	  public JmsDto(String hdr, String requestBody, String responseBody) {
	    this.hdr = hdr;
	    this.requestBody = requestBody;
	    this.responseBody = responseBody;
	  }

	  public String getHdr() {
	    return hdr;
	  }

	  public void setHdr(String hdr) {
	    this.hdr = hdr;
	  }

	  public String getRequestBody() {
	    return requestBody;
	  }

	  public void setRequestBody(String requestBody) {
	    this.requestBody = requestBody;
	  }
	  
	  public String getResponseBody() {
	    return responseBody;
	  }

	  public void setResponseBody(String responseBody) {
	    this.responseBody = responseBody;
	  }

	  @Override
	  public String toString() {
	    return String.format("jmsDto{hdr: %s, request: %s, response: %s}", getHdr(), getRequestBody(), getResponseBody());
	  }

	}