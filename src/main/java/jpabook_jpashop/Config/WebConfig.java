//package jpabook_jpashop.Config;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//@Configuration
//public class WebConfig implements WebMMvcCongigurer{
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
//        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        jsonConverter.setDefaultCharset(StandardCharsets.UTF_8);
//        converters.add(jsonConverter);
//
//    }
//}
