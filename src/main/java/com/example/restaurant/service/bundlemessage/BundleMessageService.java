package com.example.restaurant.service.bundlemessage;

import com.example.restaurant.helper.BundleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BundleMessageService {

    private  ResourceBundleMessageSource messageSource;

    @Autowired
    public BundleMessageService(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public BundleMessage getBundleMessageArEn(String key){

            return new BundleMessage(
                    messageSource.getMessage(key,null,new Locale("ar")),
                    messageSource.getMessage(key,null,new Locale("en"))
            );

    }
}
