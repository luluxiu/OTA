package com.ota.ota;



import com.ota.ota.utils.ViewHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ota.ota.Constants.*;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ViewHelper viewHelper;

    @Autowired
    private Environment env;
/*    
    @Value("${spring.multipart.enabled}")
    private boolean multipartEnabled;
    
    @Value("${spring.multipart.fileSizeThreshold}")
    private String multipartFileSizeThreshold;
    
    @Value("${spring.multipart.location}")
    private String multipartLocation;
    
    @Value("${spring.multipart.maxFileSize}")
    private String multipartMaxFileSize;
    
    @Value("${spring.multipart.maxRequestSize}")
    private String multipartMaxRequestSize;
*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(viewObjectAddingInterceptor());
        super.addInterceptors(registry);
    }

    @PostConstruct
    public void registerJadeViewHelpers(){
        /*viewHelper.setApplicationEnv(this.getApplicationEnv());*/
    }

    @Bean
    public HandlerInterceptor viewObjectAddingInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                viewHelper.setStartTime(System.currentTimeMillis());
                viewHelper.setAppPath(request.getContextPath());

                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) {
                CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (token != null) {
                    view.addObject(token.getParameterName(), token);
                }
            }
        };
    }
    
    
    public String getApplicationEnv(){
        return this.env.acceptsProfiles(ENV_PRODUCTION) ? ENV_PRODUCTION : ENV_DEVELOPMENT;
    }
 	
    /*
	@Bean
	public CommonsMultipartResolver setMaxUploadFileSize() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		
		cmr.setMaxUploadSize(12000000);   //12M
		//cmr.setDefaultEncoding("UTF-8");
		
		return cmr;
	}
	*/
    /*
	@Bean
	public MultipartProperties MSetMultipartProperties() {
		MultipartProperties multipartProperties = new MultipartProperties();
		
		multipartProperties.setEnabled(multipartEnabled);
		multipartProperties.setFileSizeThreshold(multipartFileSizeThreshold);
		multipartProperties.setMaxFileSize(multipartMaxFileSize);
		multipartProperties.setMaxRequestSize(multipartMaxRequestSize);
		
		return multipartProperties;
	}
	*/
}

