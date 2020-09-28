package tm.relying.party.tool

import grails.core.GrailsApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.ServletContext
import java.util.regex.Pattern
import tm.relying.party.tool.util.TRPTProperties

class BootStrap {

    protected static final Logger log = LoggerFactory.getLogger(BootStrap.class)
    public static final String TRPT_CONFIG_FILE = "/WEB-INF/config/trpt_config.properties"

    //==================================================================================================================
    //  Services/Injected Beans
    //==================================================================================================================
    GrailsApplication grailsApplication

    def init = { servletContext ->
        log.debug("Starting Trustmark Binding Registry Tool...")

        Properties props = readProps(servletContext)

        checkSecurityInit()

        printInitMessage()
    }

    def destroy = {
    }

    private Properties readProps(ServletContext servletContext) {
        Properties props = TRPTProperties.getProperties()
        servletContext.setAttribute("tbrConfigProps", props)
        return props
    }

    private void printInitMessage(){
        def config = grailsApplication.config

        String msg = """
--------------------------------------------------------------------------------------------------------------------
|  GTRI Trustmark Relying Party Tool
|
|    TMF API Information
|
|    Configuration Information (@see /WEB-INF/config/trpt_config.properties)
|
--------------------------------------------------------------------------------------------------------------------

"""
        log.info(msg)
    }

    private void checkSecurityInit() {
        Role.withTransaction {
            log.debug "Checking security..."
            List<Role> roles = Role.findAll()
            if (roles.size() == 0) {
                log.info "Creating security roles..."
                log.debug("Adding role[@|cyan ${Role.ROLE_ADMIN}|@]...")
                new Role(authority: Role.ROLE_ADMIN).save(failOnError: true)
                log.debug("Adding role[@|cyan ${Role.ROLE_ORG_ADMIN}|@]...")
                new Role(authority: Role.ROLE_ORG_ADMIN).save(failOnError: true)
                log.debug("Adding role[@|cyan ${Role.ROLE_USER}|@]...")
                new Role(authority: Role.ROLE_USER).save(failOnError: true)
                log.debug("Adding role[@|cyan ${Role.ROLE_REVIEWER}|@]...")
                new Role(authority: Role.ROLE_REVIEWER).save(failOnError: true)
            } else {
                log.debug "Successfully found @|green ${roles.size()}|@ roles."
            }
        }

        if (User.count() == 0) {
            log.info "Creating default users..."
            User.withTransaction {
                createSingleUser()
            }
        } else {
            log.debug("Found @|green ${User.count()}|@ users in the database already.")
        }
    }


    private void createSingleUser() {
        User user = new User(
                username: grailsApplication.config.trpt.org.user,
                password: grailsApplication.config.trpt.org.pswd,
                name: grailsApplication.config.trpt.org.username,
                accountExpired: false,
                accountLocked: false,
                passwordExpired: false
        )
        user.save(failOnError: true)

        String rolesForThisUser = "ROLE_ADMIN, ROLE_ORG_ADMIN, ROLE_USER, ROLE_REVIEWER"

        for (String roleName : rolesForThisUser.split(Pattern.quote(","))) {
            roleName = roleName.trim()
            Role role = Role.findByAuthority(roleName)
            UserRole.create(user, role, true)
        }

        log.debug "Successfully created user: @|cyan " + user.name + "|@ <@|magenta " + user.username + "|@>"
    }
}
