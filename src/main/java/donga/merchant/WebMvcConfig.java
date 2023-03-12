package donga.merchant;

import donga.merchant.web.argumentresolver.LoginMemberArgumentResolver;
import donga.merchant.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**", "**/write", "**/delete", "**/edit")
                .excludePathPatterns(
                        "/css/**", "/fontawesome/**", "/img/**", "/js/**",
                        "/", "/login/**", "/posts", "/posts/{path:[0-9]+}", "/error", "/my-page", "/items", "/posts/images/**"
                );
    }
}
