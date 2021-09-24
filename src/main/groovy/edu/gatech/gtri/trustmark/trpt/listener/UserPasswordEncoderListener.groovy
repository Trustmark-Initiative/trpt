package edu.gatech.gtri.trustmark.trpt.listener

import edu.gatech.gtri.trustmark.trpt.domain.User
import grails.events.annotation.gorm.Listener
import grails.plugin.springsecurity.SpringSecurityService
import groovy.transform.CompileStatic
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired

@CompileStatic
class UserPasswordEncoderListener implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(UserPasswordEncoderListener.class);

    @Autowired
    SpringSecurityService springSecurityService

    @Listener(User)
    void onPreInsertEvent(PreInsertEvent event) {
        encodePasswordForEvent(event)
    }

    @Listener(User)
    void onPreUpdateEvent(PreUpdateEvent event) {
        encodePasswordForEvent(event)
    }

    private void encodePasswordForEvent(AbstractPersistenceEvent event) {
        if (event.entityObject instanceof User) {
            User user = event.entityObject as User
            if (user.password && ((event instanceof PreInsertEvent) || (event instanceof PreUpdateEvent && user.isDirty('password')))) {
                event.getEntityAccess().setProperty('password', encodePassword(user.password))
            }
        } else {
            log.warn("The system expects a User object; it is ${event?.entityObject}.");
        }
    }

    private String encodePassword(String password) {
        String passwordEncoded = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
        if (passwordEncoded.equals(password)) {
            log.error("The system could not encode the User password.");
        }
        return passwordEncoded
    }

    @Override
    void afterPropertiesSet() throws Exception {
        if (springSecurityService == null)
            throw new UnsupportedOperationException("springSecurityService null.");

        log.info("The system configured '${this.getClass().getSimpleName()}'.");
    }
}
