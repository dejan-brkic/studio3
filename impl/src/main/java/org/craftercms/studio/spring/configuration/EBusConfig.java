package org.craftercms.studio.spring.configuration;

import org.craftrercms.commons.ebus.annotations.EnableEBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;

/**
 * EBus spring configuration.
 *
 * @author Dejan Brkic
 */
@Configuration
@EnableEBus
public class EBusConfig {

    @Bean
    public Reactor repositoryReactor(Environment env) {
        return Reactors.reactor().env(env).dispatcher(Environment.THREAD_POOL).get();
    }
}
