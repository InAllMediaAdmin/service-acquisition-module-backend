package com.iam.serviceacquisition.service;

import com.iam.mailer.common.exception.EmailException;
import com.iam.mailer.common.mailer.MailerTransport;
import com.iam.mailer.common.model.BasicEmailMessage;
import com.iam.mailer.common.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private final MailerTransport mailerTransport;

    @Autowired
    public EmailService(MailerTransport mailerTransport) {
        this.mailerTransport = mailerTransport;
    }

    public void sendEmail(final @Valid @NotNull BasicEmailMessage message) throws EmailException {
        mailerTransport.send(Email.from(message));
    }


    public BasicEmailMessage buildEmailMessage(Map<String, Object> contextMap, String subject, String to,
                                               String template, Map<String, String> mapInlineImages) {

        return BasicEmailMessage.builder()
                .to(List.of(to))
                .html(true)
                .subject(subject)
                .templateName(template)
                .contextMap(contextMap)
                .mapInlineImages(mapInlineImages)
                .build();
    }

    public BasicEmailMessage buildEmailMessage(Map<String, Object> contextMap, String subject, List<String> to,
                                               String from, List<String> cc, String template) {

        return BasicEmailMessage.builder()
                .to(to)
                .html(true)
                .subject(subject)
                .templateName(template)
                .contextMap(contextMap)
                .from(from)
                .cc(cc)
                .build();
    }

    public BasicEmailMessage buildEmailMessageWithoutImages(Map<String, Object> contextMap, String subject, String to,
                                                            String template) {

        return BasicEmailMessage.builder()
                .to(List.of(to))
                .html(true)
                .subject(subject)
                .templateName(template)
                .contextMap(contextMap)
                .build();
    }

    public BasicEmailMessage buildEmailMessageWithBcc(String sender, Map<String, Object> contextMap, String subject, List<String> to,
                                                      String from, List<String> cc, List<String> bcc, String template,
                                                      Map<String, String> mapInlineImages) {

        return BasicEmailMessage.builder()
                .name(sender)
                .to(to)
                .html(true)
                .subject(subject)
                .templateName(template)
                .contextMap(contextMap)
                .from(from)
                .cc(cc)
                .bcc(bcc)
                .mapInlineImages(mapInlineImages)
                .build();
    }
}
