package tm.relying.party.tool

import grails.plugin.springsecurity.SpringSecurityService

class User {

    transient SpringSecurityService springSecurityService;

    String username
    String password
    String name
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static transients = ['springSecurityService']

    static constraints = {
        username blank: false, unique: true
        name blank: false
        password blank: false, password: true
    }

    static mapping = {
        table name: 'tbr_user'
        password column: '`pass_hash`'
    }

    Boolean isAdmin() {
        Set<UserRole> roles = UserRole.findAllByUser(this)
        boolean hasRole = false
        roles.each { UserRole role ->
            if( role.role.authority == Role.ROLE_ADMIN )
                hasRole = true
        }
        return hasRole
    }

    Boolean isUser() {
        Set<UserRole> roles = UserRole.findAllByUser(this)
        boolean hasRole = false
        roles.each { UserRole role ->
            if( role.role.authority == Role.ROLE_USER )
                hasRole = true
        }
        return hasRole
    }


    Boolean isReportOnly() {
        Set<UserRole> roles = UserRole.findAllByUser(this)
        boolean hasRole = false
        roles.each { UserRole role ->
            if( role.role.authority == Role.ROLE_REPORTS_ONLY )
                hasRole = true
        }
        return hasRole
    }


    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set<Role>
    }

    String toString() {
        return username
    }

    Map toJsonMap(boolean shallow = true) {
        def json = [
                id: this.id,
                username: this.username,
                enabled: this.enabled,
                admin: this.isAdmin(),
                orgAdmin: this.isOrgAdmin(),
                reviewer: this.isReviewer(),
                developer: this.isDeveloper()
        ]
        if( !shallow ){
            // TODO Create rest of data model...
        }
        return json;
    }//end toJsonMap

}
