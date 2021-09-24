package edu.gatech.gtri.trustmark.trpt.init

import edu.gatech.gtri.trustmark.trpt.domain.Mail
import edu.gatech.gtri.trustmark.trpt.domain.Organization
import edu.gatech.gtri.trustmark.trpt.domain.Role
import edu.gatech.gtri.trustmark.trpt.domain.RoleName
import edu.gatech.gtri.trustmark.trpt.domain.Server
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistry
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri
import edu.gatech.gtri.trustmark.trpt.domain.User
import edu.gatech.gtri.trustmark.trpt.domain.UserRole
import edu.gatech.gtri.trustmark.trpt.job.JobForMailPasswordReset
import edu.gatech.gtri.trustmark.trpt.job.JobForPartnerSystemCandidateTrustInteroperabilityProfileUri
import edu.gatech.gtri.trustmark.trpt.job.JobForTrustInteroperabilityProfileUri
import edu.gatech.gtri.trustmark.trpt.job.JobForTrustmarkBindingRegistryUri
import edu.gatech.gtri.trustmark.trpt.job.JobForTrustmarkUri
import grails.converters.JSON
import grails.core.GrailsApplication
import groovy.transform.CompileStatic
import org.gtri.fj.data.NonEmptyList
import org.gtri.fj.data.Option
import org.json.JSONArray
import org.json.JSONObject
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SchedulerFactory
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.gtri.fj.data.List.arrayList
import static org.quartz.CronScheduleBuilder.cronSchedule

@CompileStatic
class BootStrap {

    GrailsApplication grailsApplication

    def init = { servletContext ->
        initializeDatabase()
        initializeJson()
    }

    def destroy = {
    }

    private void initializeDatabase() {
        initializeJob()
        initializeServer()
        initializeMail()
        initializeRole()
        Organization organization = initializeOrganization()
        initializeUser(organization)
        initializeTrustmarkBindingRegistry(organization)
        initializeTrustInteroperabilityProfileUri()
    }

    private static final String propertyNameServerUrl = 'server.url'

    private Server initializeServer() {
        Server.withTransaction {

            final org.gtri.fj.data.List<Server> serverList = Server.findAllHelper()

            if (serverList.isEmpty()) {

                final Server server = new Server(
                        url: grailsApplication.config[propertyNameServerUrl])

                log.info("Inserting server ('${server.url}') ...")

                final Server serverSave = server.saveAndFlushHelper()

                serverSave

            } else {

                log.info("Server ('${serverList.head().url}') exists.")

                serverList.head()
            }
        }
    }

    private static final String propertyNameJavaMailSenderHost = 'javaMailSender.host'
    private static final String propertyNameJavaMailSenderPort = 'javaMailSender.port'
    private static final String propertyNameJavaMailSenderUsername = 'javaMailSender.username'
    private static final String propertyNameJavaMailSenderPassword = 'javaMailSender.password'
    private static final String propertyNameJavaMailSenderAuthor = 'javaMailSender.author'

    private Mail initializeMail() {
        Mail.withTransaction {

            final org.gtri.fj.data.List<Mail> mailList = Mail.findAllHelper()

            if (mailList.isEmpty()) {

                final Mail mail = new Mail(
                        host: grailsApplication.config[propertyNameJavaMailSenderHost],
                        port: Long.parseLong(grailsApplication.config[propertyNameJavaMailSenderPort].toString()),
                        username: grailsApplication.config[propertyNameJavaMailSenderUsername],
                        password: grailsApplication.config[propertyNameJavaMailSenderPassword],
                        author: grailsApplication.config[propertyNameJavaMailSenderAuthor])

                log.info("Inserting mail ('${mail.host}', '${mail.port}', '${mail.username}', '${mail.author}') ...")

                final Mail mailSave = mail.saveAndFlushHelper()

                mailSave

            } else {

                log.info("Mail ('${mailList.head().host}', '${mailList.head().port}', '${mailList.head().username}', '${mailList.head().author}') exists.")

                mailList.head()
            }
        }
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

    private static final String propertyNameOrganizationName = 'organization.name'
    private static final String propertyNameOrganizationUrl = 'organization.uri'
    private static final String propertyNameOrganizationDescription = 'organization.description'

    private Organization initializeOrganization() {
        Organization.withTransaction {
            final Option<Organization> organizationOption = Organization.findByUriHelper(grailsApplication.config[propertyNameOrganizationUrl].toString())

            if (organizationOption.isNone()) {

                final Organization organization = new Organization(
                        name: grailsApplication.config[propertyNameOrganizationName],
                        uri: grailsApplication.config[propertyNameOrganizationUrl],
                        description: grailsApplication.config[propertyNameOrganizationDescription])

                log.info("Inserting organization '${organization.name}' (${organization.uri}) ...")
                final Organization organizationSave = organization.saveAndFlushHelper()

                organizationSave

            } else {

                log.info("Organization '${grailsApplication.config[propertyNameOrganizationName]}' '(${grailsApplication.config[propertyNameOrganizationUrl]})' exists.");

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

    private static final String propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriCronExpression = 'jobForPartnerSystemCandidateTrustInteroperabilityProfileUri.cronExpression'
    private static final String propertyNameJobForTrustmarkUriCronExpression = 'jobForTrustmarkUri.cronExpression'
    private static final String propertyNameJobForTrustmarkBindingRegistryUriCronExpression = 'jobForTrustmarkBindingRegistryUri.cronExpression'
    private static final String propertyNameJobForTrustInteroperabilityProfileUriCronExpression = 'jobForTrustInteroperabilityProfileUri.cronExpression'
    private static final String propertyNameJobForMailPasswordResetCronExpression = 'jobForMailPasswordReset.cronExpression'

    private void initializeJob() {

        final SchedulerFactory schedulerFactory = new StdSchedulerFactory()
        final Scheduler scheduler = schedulerFactory.getScheduler()

        final String partnerSystemCandidateTrustInteroperabilityProfileUriJobCronExpressionCronExpression = grailsApplication.config[propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriCronExpression]

        final JobDetail partnerSystemCandidateTrustInteroperabilityProfileUriJob = JobBuilder.newJob(JobForPartnerSystemCandidateTrustInteroperabilityProfileUri.class)
                .withIdentity(JobForPartnerSystemCandidateTrustInteroperabilityProfileUri.simpleName, "trpt")
                .build();

        final Trigger partnerSystemCandidateTrustInteroperabilityProfileUriJobTrigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(JobForPartnerSystemCandidateTrustInteroperabilityProfileUri.simpleName, "trpt"))
                .withSchedule(cronSchedule(partnerSystemCandidateTrustInteroperabilityProfileUriJobCronExpressionCronExpression))
                .build()

        scheduler.scheduleJob(partnerSystemCandidateTrustInteroperabilityProfileUriJob, partnerSystemCandidateTrustInteroperabilityProfileUriJobTrigger)

        final String trustmarkUriJobCronExpressionCronExpression = grailsApplication.config[propertyNameJobForTrustmarkUriCronExpression]

        final JobDetail trustmarkUriJob = JobBuilder.newJob(JobForTrustmarkUri.class)
                .withIdentity(JobForTrustmarkUri.simpleName, "trpt")
                .build();

        final Trigger trustmarkUriJobTrigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(JobForTrustmarkUri.simpleName, "trpt"))
                .withSchedule(cronSchedule(trustmarkUriJobCronExpressionCronExpression))
                .build()

        scheduler.scheduleJob(trustmarkUriJob, trustmarkUriJobTrigger)

        final String trustmarkBindingRegistryUriJobCronExpressionCronExpression = grailsApplication.config[propertyNameJobForTrustmarkBindingRegistryUriCronExpression]

        final JobDetail trustmarkBindingRegistryUriJob = JobBuilder.newJob(JobForTrustmarkBindingRegistryUri.class)
                .withIdentity(JobForTrustmarkBindingRegistryUri.simpleName, "trpt")
                .build();

        final Trigger trustmarkBindingRegistryUriJobTrigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(JobForTrustmarkBindingRegistryUri.simpleName, "trpt"))
                .withSchedule(cronSchedule(trustmarkBindingRegistryUriJobCronExpressionCronExpression))
                .build()

        scheduler.scheduleJob(trustmarkBindingRegistryUriJob, trustmarkBindingRegistryUriJobTrigger)

        final String trustInteroperabilityProfileUriJobCronExpressionCronExpression = grailsApplication.config[propertyNameJobForTrustInteroperabilityProfileUriCronExpression]

        final JobDetail trustInteroperabilityProfileUriJob = JobBuilder.newJob(JobForTrustInteroperabilityProfileUri.class)
                .withIdentity(JobForTrustInteroperabilityProfileUri.simpleName, "trpt")
                .build();

        final Trigger trustInteroperabilityProfileUriJobTrigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(JobForTrustInteroperabilityProfileUri.simpleName, "trpt"))
                .withSchedule(cronSchedule(trustInteroperabilityProfileUriJobCronExpressionCronExpression))
                .build()

        scheduler.scheduleJob(trustInteroperabilityProfileUriJob, trustInteroperabilityProfileUriJobTrigger)

        final String mailPasswordResetJobCronExpressionCronExpression = grailsApplication.config[propertyNameJobForMailPasswordResetCronExpression]

        final JobDetail mailPasswordResetJob = JobBuilder.newJob(JobForMailPasswordReset.class)
                .withIdentity(JobForMailPasswordReset.simpleName, "trpt")
                .build();

        final Trigger mailPasswordResetJobTrigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(JobForMailPasswordReset.simpleName, "trpt"))
                .withSchedule(cronSchedule(mailPasswordResetJobCronExpressionCronExpression))
                .build()

        scheduler.scheduleJob(mailPasswordResetJob, mailPasswordResetJobTrigger)

        scheduler.start()
    }

    private void initializeJson() {
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
