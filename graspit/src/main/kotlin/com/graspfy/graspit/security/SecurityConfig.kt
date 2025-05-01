package com.graspfy.graspit.security

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector
import org.springframework.security.config.Customizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
@EnableMethodSecurity
//CONFIG DO SWAGGER
/*
@SecurityScheme(
    name="GraspAuthServer",
    type = SecuritySchemeType.HTTP,
    scheme="Bearer",
    bearerFormat = "JWT"
)

 */
//class SecurityConfig(val jwtToken: JwtTokenFilter) { // NO SECURITY

class SecurityConfig() {
    @Bean
    fun mvc(introspector: HandlerMappingIntrospector)=
        MvcRequestMatcher.Builder(introspector)
    @Bean
    fun filterChain(security:HttpSecurity,mvc:MvcRequestMatcher.Builder):DefaultSecurityFilterChain {
        return security.csrf{it.disable()}
            .authorizeHttpRequests{requests -> requests.anyRequest().permitAll()}
            .headers{it.frameOptions{fo-> fo.disable()}}
            .build()
//        return security.cors(Customizer.withDefaults())
//            .csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .exceptionHandling {
//                it.authenticationEntryPoint { _, res, ex ->
//                    res.sendError(
//                        SC_UNAUTHORIZED,
//                        if (ex.message.isNullOrEmpty()) "UNAUTHORIZED" else ex.message
//                    )
//                }
//            }
//            .headers { it.frameOptions { fo -> fo.disable() } }
//            .authorizeHttpRequests { requests ->
//                requests
//                    .requestMatchers(antMatcher(HttpMethod.GET)).permitAll()
//                    .requestMatchers(mvc.pattern(HttpMethod.POST, "/users")).permitAll()
//                    .requestMatchers(mvc.pattern(HttpMethod.POST, "users/login")).permitAll()
//                    .anyRequest().permitAll()
//            }
//            .build()
    }
    @Bean
    fun corsFilter()=CorsConfiguration().apply{
        addAllowedHeader("*")
        addAllowedMethod("*")
        addAllowedOrigin("*")
        addAllowedOriginPattern("*")

    }
        .let { UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**",it)
        }}
        .let { CorsFilter(it) }


}