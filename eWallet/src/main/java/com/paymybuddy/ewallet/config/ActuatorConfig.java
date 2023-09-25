package com.paymybuddy.ewallet.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class that activates the <code>httpExchanges</code> actuator.
 * Since Spring 3, <code>httpTrace</code> no longer exists and has been replaced
 * by <code>httpEchanges</code>, which is not activated by default. A bean is
 * required for it to function properly.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Configuration
public class ActuatorConfig {

	/**
	 * The Bean required to run <code>httpExchanges</code>. Don't worry, this bean
	 * is not catastrophic, unlike the eponymous Mister. Yes, I've kept this comment
	 * from the previous project. I'm pretty sure nobody reads my Javadoc...
	 * 
	 * @return <code>InMemoryHttpExchangeRepository()</code>
	 */
	@Bean
	HttpExchangeRepository httpTraceRepository() {
		return new InMemoryHttpExchangeRepository();
	}
}