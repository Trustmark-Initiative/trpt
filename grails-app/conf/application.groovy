// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.securityConfigType = 'Annotation'

grails.plugin.springsecurity.authority.className = 'edu.gatech.gtri.trustmark.trpt.domain.Role'
grails.plugin.springsecurity.authority.nameField = 'name'

grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'edu.gatech.gtri.trustmark.trpt.domain.UserRole'

grails.plugin.springsecurity.userLookup.userDomainClassName = 'edu.gatech.gtri.trustmark.trpt.domain.User'
grails.plugin.springsecurity.userLookup.accountExpiredPropertyName = 'userExpired'
grails.plugin.springsecurity.userLookup.accountLockedPropertyName = 'userLocked'
grails.plugin.springsecurity.userLookup.enabledPropertyName = 'userEnabled'
grails.plugin.springsecurity.userLookup.passwordExpiredPropertyName = 'passwordExpired'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/**', access: ['ROLE_ADMINISTRATOR_ORGANIZATION', 'ROLE_ADMINISTRATOR']],
        [pattern: '/password/reset', access: ['permitAll']],
        [pattern: '/password/resetSubmit', access: ['permitAll']],
        [pattern: '/password/resetStatus', access: ['permitAll']],
        [pattern: '/password/resetStatusSubmit', access: ['permitAll']],
        [pattern: '/password/changeWithoutAuthentication', access: ['permitAll']],
        [pattern: '/password/changeWithoutAuthenticationSubmit', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS']
]

// Requires 1 or more beans in resources.groovy which implement org.springframework.context.ApplicationListener
grails.plugin.springsecurity.useSecurityEventListener = true
