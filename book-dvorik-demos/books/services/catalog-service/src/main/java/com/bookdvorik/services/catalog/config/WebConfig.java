package com.bookdvorik.services.catalog.config;

import com.bookdvorik.services.catalog.support.query.SearchQuerySpecifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.nvcm.sugar.search.core.IQueryRefinement;
import net.nvcm.sugar.web.tricks.resolvers.ResourcePathWebArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.Set;

//@Configuration
public class WebConfig extends WebMvcConfigurationSupport {


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired(required = false)
    private List<Converter<?, ?>> converters;

    /**
     * Расказываем web какие есть конвертеры @RequestBody помимо стандартных
     * Добавляем конвертеры для строк, подкрученный маппер и конвертер байт массива
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
    }



    /**
     * Подкручиваем web конвертерт с учетом уже созданного синглтона паммера
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

    /**
     * Есдт в проекте есть кастомные конверторы рассказываем о них web
     * @param registry
     */
    @Override
    protected void addFormatters(FormatterRegistry registry) {
        if(converters != null){
            for (Converter<?, ?> converter : converters) {
                registry.addConverter(converter);
            }
        }
    }

    /**
     * Рассказываем где лежат специфические ресуры
     * Настраиваем для корректной работы сваггера
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public SearchQuerySpecifier specifier(@Autowired(required = false) Set<IQueryRefinement> queryRefinements){
        return new SearchQuerySpecifier(queryRefinements);
    }

    /**
     * Рассказываем web какие у нас есть особенные резолверы
     * Подсовываем резолверы для ISearchQuery и IResourcePath
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new QueryWebArgumentResolver(new SearchQueryBuilder(specifier, objectMapper, messageSource))); //TODO порешать
        resolvers.add(new ResourcePathWebArgumentResolver());
    }

}
