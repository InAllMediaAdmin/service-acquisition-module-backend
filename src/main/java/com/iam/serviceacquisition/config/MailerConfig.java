package com.iam.serviceacquisition.config;

import com.iam.mailer.common.config.MailerCommonConfig;
import com.iam.mailer.common.mailer.MailerTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailerConfig extends MailerCommonConfig {

    @Override
    @Bean
    public MailerTransport mailerTransport(){
        return super.mailerTransport();
    }
}
