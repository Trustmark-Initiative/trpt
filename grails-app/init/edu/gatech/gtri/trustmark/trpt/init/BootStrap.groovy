package edu.gatech.gtri.trustmark.trpt.init

import edu.gatech.gtri.trustmark.trpt.domain.Organization
import edu.gatech.gtri.trustmark.trpt.domain.Role
import edu.gatech.gtri.trustmark.trpt.domain.RoleName
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistry
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri
import edu.gatech.gtri.trustmark.trpt.domain.User
import edu.gatech.gtri.trustmark.trpt.domain.UserRole
import edu.gatech.gtri.trustmark.trpt.service.job.JobUtility
import grails.converters.JSON
import grails.core.GrailsApplication
import groovy.transform.CompileStatic
import org.gtri.fj.data.NonEmptyList
import org.gtri.fj.data.Option
import org.json.JSONArray
import org.json.JSONObject

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.gtri.fj.data.List.arrayList

@CompileStatic
class BootStrap {

    GrailsApplication grailsApplication

    def init = { servletContext ->
        initializeDatabase()
        initJson()
    }

    def destroy = {
    }

    private void initializeDatabase() {
        initializeRole()
        Organization organization = initializeOrganization()
        initializeUser(organization)
        initializeTrustmarkBindingRegistry(organization)
        initializeTrustInteroperabilityProfileUri()
        initializeEvaluation()
    }

    private void initializeRole() {
        Role.withTransaction {

            final org.gtri.fj.data.List<Role> roleList = Role.findAllHelper()

            arrayList(RoleName.values())
                    .map({ RoleName name -> new Role(name: name.name()) })
                    .filter({ Role roleRequired ->
                        final boolean absent = !roleList.exists({ Role roleInner -> roleInner.name == roleRequired.name })

                        if (absent) {
                            log.info("Role '${roleRequired.name}' does not exist.")
                        } else {
                            log.info("Role '${roleRequired.name}' exists.")
                        }

                        absent
                    })
                    .map({ Role roleRequired ->

                        log.info("Inserting role '${roleRequired.name}' ...")
                        roleRequired.saveHelper()
                    })
        }
    }

    private static final String propertyOrganizationName = 'organization.name'
    private static final String propertyOrganizationUrl = 'organization.uri'
    private static final String propertyOrganizationDescription = 'organization.description'

    private Organization initializeOrganization() {
        Organization.withTransaction {
            final Option<Organization> organizationOption = Organization.findByUriHelper(grailsApplication.config[propertyOrganizationUrl].toString())

            if (organizationOption.isNone()) {

                final Organization organization = new Organization(
                        name: grailsApplication.config[propertyOrganizationName],
                        uri: grailsApplication.config[propertyOrganizationUrl],
                        description: grailsApplication.config[propertyOrganizationDescription])

                log.info("Inserting organization '${organization.name}' (${organization.uri}) ...")
                final Organization organizationSave = organization.saveAndFlushHelper()

                organizationSave

            } else {

                log.info("Organization '${grailsApplication.config[propertyOrganizationName]}' '(${grailsApplication.config[propertyOrganizationUrl]})' exists.");

                organizationOption.some()
            }
        }
    }

    private static final String propertyNameUserUsername = 'user.username'
    private static final String propertyNameUserPassword = 'user.password'
    private static final String propertyNameUserNameGiven = 'user.nameGiven'
    private static final String propertyNameUserNameFamily = 'user.nameFamily'
    private static final String propertyNameUserTelephone = 'user.telephone'
    private static final String propertyNameUserUserEnabled = 'user.userEnabled'
    private static final String propertyNameUserUserLocked = 'user.userLocked'
    private static final String propertyNameUserUserExpired = 'user.userExpired'
    private static final String propertyNameUserPasswordExpired = 'user.passwordExpired'
    private static final String propertyNameRoleName = 'role.name'

    private void initializeUser(final Organization organization) {
        User.withTransaction {
            if (User.count() == 0) {
                final User user = new User(
                        username: grailsApplication.config[propertyNameUserUsername],
                        password: grailsApplication.config[propertyNameUserPassword],
                        nameGiven: grailsApplication.config[propertyNameUserNameFamily],
                        nameFamily: grailsApplication.config[propertyNameUserNameGiven],
                        telephone: grailsApplication.config[propertyNameUserTelephone],
                        userEnabled: grailsApplication.config[propertyNameUserUserEnabled],
                        userLocked: grailsApplication.config[propertyNameUserUserLocked],
                        userExpired: grailsApplication.config[propertyNameUserUserExpired],
                        passwordExpired: grailsApplication.config[propertyNameUserPasswordExpired],
                        organization: organization)

                log.info("Inserting user '${user.username}' ...")
                user.saveAndFlushHelper()

                Role.findByNameHelper(grailsApplication.config[propertyNameRoleName].toString())
                        .map({ Role role ->

                            final UserRole userRole = new UserRole(user: user, role: role);

                            log.info("Inserting user-role relationship '${user.username}'-'${role.name}' ...")
                            userRole.saveAndFlushHelper();
                        })
            } else {

                log.info('Users exist.')
            }
        }
    }

    private static final String propertyNameTrustmarkBindingRegistryName = 'trustmarkBindingRegistry.name'
    private static final String propertyNameTrustmarkBindingRegistryUri = 'trustmarkBindingRegistry.uri'
    private static final String propertyNameTrustmarkBindingRegistryDescription = 'trustmarkBindingRegistry.description'

    private void initializeTrustmarkBindingRegistry(final Organization organization) {
        TrustmarkBindingRegistry.withTransaction {

            final String name = grailsApplication.config[propertyNameTrustmarkBindingRegistryName]
            final String uri = grailsApplication.config[propertyNameTrustmarkBindingRegistryUri]
            final String description = grailsApplication.config[propertyNameTrustmarkBindingRegistryDescription]

            final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri = TrustmarkBindingRegistryUri.findByUriHelper(uri)
                    .map({ trustmarkBindingRegistryUri ->

                        log.info("Trustmark Binding Registry URI '${uri}' exists.")

                        trustmarkBindingRegistryUri
                    })
                    .orSome({

                        log.info("Inserting Trustmark Binding Registry URI '${uri}' ...")

                        final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri = new TrustmarkBindingRegistryUri();
                        trustmarkBindingRegistryUri.setUri(grailsApplication.config[propertyNameTrustmarkBindingRegistryUri].toString())
                        trustmarkBindingRegistryUri.saveAndFlushHelper()
                    })

            final TrustmarkBindingRegistry trustmarkBindingRegistry = TrustmarkBindingRegistry.findByOrganizationAndTrustmarkBindingRegistryUriHelper(organization, trustmarkBindingRegistryUri)
                    .map({ trustmarkBindingRegistry ->

                        log.info("Trustmark Binding Registry '${name}' ('${uri}') exists.")

                        trustmarkBindingRegistry
                    })
                    .orSome({

                        log.info("Inserting Trustmark Binding Registry '${name}' ('${uri}') ...")

                        final TrustmarkBindingRegistry trustmarkBindingRegistry = new TrustmarkBindingRegistry()
                        trustmarkBindingRegistry.setName(name)
                        trustmarkBindingRegistry.setDescription(description)
                        trustmarkBindingRegistry.organizationHelper(organization)
                        trustmarkBindingRegistry.trustmarkBindingRegistryUriHelper(trustmarkBindingRegistryUri)
                        trustmarkBindingRegistry.saveAndFlushHelper()
                    })
        }
    }

    private void initializeTrustInteroperabilityProfileUri() {
        TrustInteroperabilityProfileUri.withTransaction {

            TrustInteroperabilityProfileUri.findByUriHelper("https://artifacts.trustmarkinitiative.org/lib/tips/nief-mandatory-attributes/1.0/").orSome({
                final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri = new TrustInteroperabilityProfileUri()
                trustInteroperabilityProfileUri.uri = "https://artifacts.trustmarkinitiative.org/lib/tips/nief-mandatory-attributes/1.0/"
                trustInteroperabilityProfileUri.saveAndFlushHelper()
            })

            TrustInteroperabilityProfileUri.findByUriHelper("https://artifacts.trustmarkinitiative.org/lib/tips/nief-highly-recommended-attributes/1.0/").orSome({
                final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri = new TrustInteroperabilityProfileUri()
                trustInteroperabilityProfileUri.uri = "https://artifacts.trustmarkinitiative.org/lib/tips/nief-highly-recommended-attributes/1.0/"
                trustInteroperabilityProfileUri.saveAndFlushHelper()
            })

            TrustInteroperabilityProfileUri.findByUriHelper("https://artifacts.trustmarkinitiative.org/lib/tips/nief-recommended-attributes/1.0/").orSome({
                final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri = new TrustInteroperabilityProfileUri()
                trustInteroperabilityProfileUri.uri = "https://artifacts.trustmarkinitiative.org/lib/tips/nief-recommended-attributes/1.0/"
                trustInteroperabilityProfileUri.saveAndFlushHelper()
            })
        }
    }

    private void initializeEvaluation() {
        TrustInteroperabilityProfileUri.withTransaction {
            JobUtility.synchronizeTrustInteroperabilityProfileUri()
            JobUtility.synchronizeTrustmarkBindingRegistry()
            JobUtility.synchronizeTrustExpressionEvaluation()
        }
    }

    private void initJson() {
        JSON.registerObjectMarshaller(LocalDateTime) { final LocalDateTime it ->
            return it?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
        JSON.registerObjectMarshaller(JSONObject) { final JSONObject it ->
            return it?.toMap()
        }
        JSON.registerObjectMarshaller(JSONArray) { final JSONArray it ->
            return it?.toList()
        }
        JSON.registerObjectMarshaller(NonEmptyList) { final NonEmptyList it ->
            return it?.toList().toJavaList()
        }
    }

}
