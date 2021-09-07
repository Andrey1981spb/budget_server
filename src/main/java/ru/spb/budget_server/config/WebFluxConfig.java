package ru.spb.budget_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Конфигурация WebFlux
 *
 * Author A.Dmitriev
 */
@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
}
