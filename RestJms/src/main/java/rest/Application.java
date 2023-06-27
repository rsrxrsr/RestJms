package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
//import org.springframework.context.annotation.Bean;
//import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
//import org.springframework.jms.support.converter.MessageConverter;
//import org.springframework.jms.support.converter.MessageType;
/*
import javax.jms.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
*/
//import rest.filter.LogFilter;
import rest.jms.JmsService;

@SpringBootApplication
@EnableJms
public class Application {
/*
  @Bean
  public JmsListenerContainerFactory<?> jmsFactory(ConnectionFactory connectionFactory,
                          DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    // This provides all boot's default to this factory, including the message converter
    configurer.configure(factory, connectionFactory);
    // You could still override some of Boot's default if necessary.
    return factory;
  }
//
  @Bean // Serialize message content to json using TextMessage
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }
//
  @Bean
  public FilterRegistrationBean<LogFilter> logFilter() {
	  FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
	  registrationBean.setFilter(new LogFilter());
	  registrationBean.addUrlPatterns("/restapi/*");
      return registrationBean;	  
  }
*/
	
  public static void main(String[] args) {
    // Launch the application
	System.out.println("***** Launch Application.");
	ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
	System.out.println("***** Configure Application.");
	//
	JmsService jmsService = new JmsService(context);
    // Send initial message 
    System.out.println("***** Initial message.");
    jmsService.send("JMS Start Up", "Recive", "Response");
    System.out.println("***** End Start Up Application.");
    //  
  }

}