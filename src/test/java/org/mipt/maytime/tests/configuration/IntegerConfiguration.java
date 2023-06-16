package org.mipt.maytime.tests.configuration;

import org.mipt.maytime.annotations.Bean;
import org.mipt.maytime.annotations.Configuration;

@Configuration
public class IntegerConfiguration {
    
    @Bean
    public Integer magicInteger() {
        return 33;
    }
}
