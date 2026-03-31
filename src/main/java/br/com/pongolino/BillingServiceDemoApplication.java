package br.com.pongolino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan("br.com.pongolino.infrastructure.config")
public class BillingServiceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceDemoApplication.class, args);
    }

}
