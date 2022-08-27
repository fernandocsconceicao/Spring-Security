package com.example.springkotlinuserauthsqldatasource.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity.RequestMatcherConfigurer
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.validation.annotation.Validated
import javax.sql.DataSource
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Validated
@EnableWebSecurity
@ConfigurationProperties(prefix = "authorization")
data class SecurityConfig(
    val endpoints: List<EndpointProperties>,
    val dataSource: DataSource,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .requestMatchers { getAntMatchers(it) }
            .authorizeHttpRequests { getAntMatchers(it) }
            .httpBasic(withDefaults())
            .csrf().disable()
        return http.build()
    }

    @Bean
    fun users(dataSource: DataSource): UserDetailsManager {
        return JdbcUserDetailsManager(dataSource)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun getAntMatchers(requestMatcherConfigurer: RequestMatcherConfigurer): RequestMatcherConfigurer {
        endpoints.forEach {
            it.methods.forEach { method -> requestMatcherConfigurer.antMatchers(method, it.path) }
        }
        return requestMatcherConfigurer
    }

    private fun getAntMatchers(
        authorizeHttpRequestsConfigurer: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry,
    ): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry {
        endpoints.forEach {
            it.methods.forEach { method ->
                authorizeHttpRequestsConfigurer.antMatchers(method, it.path).hasAnyRole(*it.roles.toTypedArray())
            }
        }
        return authorizeHttpRequestsConfigurer
    }
}

@ConstructorBinding
data class EndpointProperties(
    @field:NotBlank val path: String,
    @field:NotEmpty val methods: List<HttpMethod>,
    @field:NotEmpty val roles: List<String>,
)