package edu.gatech.gtri.trustmark.trpt.listener

import groovy.transform.CompileStatic
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent

@CompileStatic
class AuthFailureSecurityListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    private static final Logger log = LoggerFactory.getLogger(AuthFailureSecurityListener.class);

    @Override
    void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        log.error("AUTHENTICATION FAILURE: " + event.getException().toString());
    }
}
