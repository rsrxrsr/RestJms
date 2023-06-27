package rest.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersConfig {

	  @Bean
	  public FilterRegistrationBean<LogFilter> logFilter() {
		  FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
		  registrationBean.setFilter(new LogFilter());
		  registrationBean.addUrlPatterns("/restapi/*");
	      return registrationBean;	  
	  }
	  @Bean
	  public FilterRegistrationBean<BrodcastFilter> brodcastFilter() {
		  FilterRegistrationBean<BrodcastFilter> registrationBean = new FilterRegistrationBean<>();
		  registrationBean.setFilter(new BrodcastFilter());
		  registrationBean.addUrlPatterns("/restapi/*");
	      return registrationBean;	  
	  }
    
}
