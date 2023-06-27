package rest.jms;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class JmsService {

	static JmsTemplate jmsTemplate;

	public JmsService() {}

	public JmsService(ConfigurableApplicationContext context) {
		jmsTemplate = context.getBean(JmsTemplate.class);
		System.out.println("***** Jms Configuration");
	}
	
	@SendTo("jmsbox")
	public JmsDto send(JmsDto jmsDto) {
	    System.out.println("***** Sending Jms");
	    jmsTemplate.convertAndSend("jmsbox", jmsDto);
	    return jmsDto;
	}

	@SendTo("jmsbox")
	public JmsDto send(String hdr, String requestBody, String responseBody) {
	    System.out.println("***** Sending Jms");
	    JmsDto jmsDto = new JmsDto(hdr, requestBody, responseBody);
	    jmsTemplate.convertAndSend("jmsbox", jmsDto);
	    return jmsDto;
	}
	
}
