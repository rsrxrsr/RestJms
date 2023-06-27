package rest.jms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import rest.entity.Empresa;
import rest.brodcast.BrodcastService;

@Component
public class Receiver {
	
@SuppressWarnings("unused")
@Autowired
  private BrodcastService brodcastService;

  //@JmsListener(destination = "jmsbox", containerFactory = "jmsFactory")
  @JmsListener(destination = "jmsbox")
  public void receiveMessage(JmsDto jmsDto) {
    System.out.println("***** ReceivedMessage() " + jmsDto);
    facade(jmsDto);
  }

  public void facade(JmsDto jmsDto) {
	  if (  jmsDto.getHdr().contains("/empresa")
		 &&	jmsDto.getHdr().contains("POS"))
	  {
		formatEmpresa(jmsDto);  
	  } else
	  if (  jmsDto.getHdr().contains("/empresa")
		 &&	jmsDto.getHdr().contains("PUT"))
	  {
		formatEmpresa(jmsDto);  
	  } 
  }
  
  public void formatEmpresa(JmsDto jmsDto) {
	  ObjectMapper objectMapper = new ObjectMapper();
	  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	  try {
		Empresa empresa = objectMapper.readValue(jmsDto.getRequestBody(), Empresa.class);
	    System.out.println("***** Empresa " + empresa);
	    //brodcastService.brodcast(jmsDto);	    
	  } catch (IOException e) {
		e.printStackTrace();
	} 
  }

}