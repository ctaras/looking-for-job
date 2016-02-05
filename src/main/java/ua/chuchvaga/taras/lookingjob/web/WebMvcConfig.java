package ua.chuchvaga.taras.lookingjob.web;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.AppendingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;

@Configuration
public class WebMvcConfig {
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect(new AppendingStrategy());
    }

    @Bean
    public JasperReportsViewResolver getJasperReportsViewResolver() {

        JasperReportsViewResolver resolver = new JasperReportsViewResolver();
        resolver.setPrefix("classpath:/");
        resolver.setSuffix(".jrxml");
        resolver.setViewNames("reports/*");

        //resolver.setReportDataKey("datasource");
        resolver.setViewClass(JasperReportsMultiFormatView.class);
        resolver.setOrder(1);
        return resolver;
    }
}
