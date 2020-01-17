/**
 * 
 */
package com.omantel.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Dhiraj Gour
 * @date 22 August 2019
 *
 */
@Component
public class Messages {

	@Autowired
	@Qualifier("messageResourceSB")
    private MessageSource messageSource;

   /* private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
    	Locale lo = LocaleContextHolder.getLocale();
        accessor = new MessageSourceAccessor(messageSource, lo);
    }*/

    public String get(String code) {
        //return accessor.getMessage(code);
    	return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
    
    public String get(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
