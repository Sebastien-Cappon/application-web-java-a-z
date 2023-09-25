package com.paymybuddy.ewallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the application. It contains only the run method. This
 * project is a SpringBoot Application. This annotation is a merged of
 * <code>@EnableAutoConfiguration</code>, <code>@ComponentScan</code> and
 * <code>@Configuration</code> with their default configuration.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@SpringBootApplication()
public class EWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(EWalletApplication.class, args);
	}

}