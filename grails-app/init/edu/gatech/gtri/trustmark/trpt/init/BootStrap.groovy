package edu.gatech.gtri.trustmark.trpt.init

import edu.gatech.gtri.trustmark.trpt.domain.Mail
import edu.gatech.gtri.trustmark.trpt.domain.Organization
import edu.gatech.gtri.trustmark.trpt.domain.Role
import edu.gatech.gtri.trustmark.trpt.domain.RoleName
import edu.gatech.gtri.trustmark.trpt.domain.Server
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistry
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUri
import edu.gatech.gtri.trustmark.trpt.domain.User
import edu.gatech.gtri.trustmark.trpt.domain.UserRole
import edu.gatech.gtri.trustmark.trpt.job.JobForMailEvaluationUpdate
import edu.gatech.gtri.trustmark.trpt.job.JobForMailPasswordReset
import edu.gatech.gtri.trustmark.trpt.job.JobForPartnerSystemCandidateTrustInteroperabilityProfileUri
import edu.gatech.gtri.trustmark.trpt.job.JobForTrustInteroperabilityProfileUri
import edu.gatech.gtri.trustmark.trpt.job.JobForTrustmarkBindingRegistryUri
import edu.gatech.gtri.trustmark.trpt.job.JobForTrustmarkDefinitionUri
import edu.gatech.gtri.trustmark.trpt.job.JobForTrustmarkStatusReportUri
import edu.gatech.gtri.trustmark.trpt.job.JobForTrustmarkUri
import edu.gatech.gtri.trustmark.trpt.service.ApplicationProperties
import edu.gatech.gtri.trustmark.trpt.service.job.JobUtilityForTrustmarkBindingRegistry
import edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer.UriSynchronizerForTrustInteroperabilityProfile
import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.core.GrailsApplication
import org.gtri.fj.data.NonEmptyList
import org.gtri.fj.data.Option
import org.json.JSONArray
import org.json.JSONObject
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SchedulerFactory
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import static org.gtri.fj.data.List.arrayList
import static org.quartz.CronScheduleBuilder.cronSchedule

@GrailsCompileStatic
class BootStrap {

    GrailsApplication grailsApplication

    def init = { servletContext ->
        initializeDatabase()
        initializeJson()
    }

    def destroy = {
    }

    private void initializeDatabase() {

        final LocalDateTime now = LocalDateTime.now();

        initializeJob()
        initializeServer()
        initializeMail()
        initializeRole()
        Organization organization = initializeOrganization()
        initializeUser(organization)
        initializeTrustmarkBindingRegistry(organization, now)
        initializeTrustInteroperabilityProfileUri()
    }

    private Server initializeServer() {
        Server.withTransaction {

            final org.gtri.fj.data.List<Server> serverList = Server.findAllHelper()

            if (serverList.isEmpty()) {

                final Server server = new Server(
                        url: grailsApplication.config[ApplicationProperties.propertyNameServerUrl])

                log.info("Inserting server ('${server.url}') ...")

                final Server serverSave = server.saveAndFlushHelper()

                serverSave

            } else {

                log.info("Server ('${serverList.head().url}') exists.")

                serverList.head()
            }
        }
    }

    private Mail initializeMail() {
        Mail.withTransaction {

            final org.gtri.fj.data.List<Mail> mailList = Mail.findAllHelper()

            if (mailList.isEmpty()) {

                final Mail mail = new Mail(
                        host: grailsApplication.config[ApplicationProperties.propertyNameJavaMailSenderHost],
                        port: Long.parseLong(grailsApplication.config[ApplicationProperties.propertyNameJavaMailSenderPort].toString()),
                        username: grailsApplication.config[ApplicationProperties.propertyNameJavaMailSenderUsername],
                        password: grailsApplication.config[ApplicationProperties.propertyNameJavaMailSenderPassword],
                        author: grailsApplication.config[ApplicationProperties.propertyNameJavaMailSenderAuthor])

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
                        roleRequired.saveAndFlushHelper()
                    })
        }
    }

    private Organization initializeOrganization() {
        Organization.withTransaction {
            final Option<Organization> organizationOption = Organization.findByUriHelper(grailsApplication.config[ApplicationProperties.propertyNameOrganizationUrl].toString())

            if (organizationOption.isNone()) {

                final Organization organization = new Organization(
                        name: grailsApplication.config[ApplicationProperties.propertyNameOrganizationName],
                        uri: grailsApplication.config[ApplicationProperties.propertyNameOrganizationUrl],
                        description: grailsApplication.config[ApplicationProperties.propertyNameOrganizationDescription])

                log.info("Inserting organization '${organization.name}' (${organization.uri}) ...")
                final Organization organizationSave = organization.saveAndFlushHelper()

                organizationSave

            } else {

                log.info("Organization '${grailsApplication.config[ApplicationProperties.propertyNameOrganizationName]}' '(${grailsApplication.config[ApplicationProperties.propertyNameOrganizationUrl]})' exists.");

                organizationOption.some()
            }
        }
    }

    private void initializeUser(final Organization organization) {
        User.withTransaction {
            if (User.count() == 0) {
                final User user = new User(
                        username: grailsApplication.config[ApplicationProperties.propertyNameUserUsername],
                        password: grailsApplication.config[ApplicationProperties.propertyNameUserPassword],
                        nameGiven: grailsApplication.config[ApplicationProperties.propertyNameUserNameFamily],
                        nameFamily: grailsApplication.config[ApplicationProperties.propertyNameUserNameGiven],
                        telephone: grailsApplication.config[ApplicationProperties.propertyNameUserTelephone],
                        userEnabled: grailsApplication.config[ApplicationProperties.propertyNameUserUserEnabled],
                        userLocked: grailsApplication.config[ApplicationProperties.propertyNameUserUserLocked],
                        userExpired: grailsApplication.config[ApplicationProperties.propertyNameUserUserExpired],
                        passwordExpired: grailsApplication.config[ApplicationProperties.propertyNameUserPasswordExpired],
                        organization: organization)

                log.info("Inserting user '${user.username}' ...")
                user.saveAndFlushHelper()

                Role.findByNameHelper(grailsApplication.config[ApplicationProperties.propertyNameRoleName].toString())
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

    private void initializeTrustmarkBindingRegistry(final Organization organization, final LocalDateTime now) {

        TrustmarkBindingRegistry.withTransaction {

            final String name = grailsApplication.config[ApplicationProperties.propertyNameTrustmarkBindingRegistryName]
            final String uri = grailsApplication.config[ApplicationProperties.propertyNameTrustmarkBindingRegistryUri]
            final String description = grailsApplication.config[ApplicationProperties.propertyNameTrustmarkBindingRegistryDescription]
            final Duration duration = Duration.parse(grailsApplication.config[ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum].toString())

            final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri = TrustmarkBindingRegistryUri.findByUriHelper(uri)
                    .map({ trustmarkBindingRegistryUri ->

                        log.info("Trustmark Binding Registry URI '${uri}' exists.")

                        trustmarkBindingRegistryUri
                    })
                    .orSome({

                        log.info("Inserting Trustmark Binding Registry URI '${uri}' ...")

                        final TrustmarkBindingRegistryUri trustmarkBindingRegistryUri = new TrustmarkBindingRegistryUri();
                        trustmarkBindingRegistryUri.setUri(grailsApplication.config[ApplicationProperties.propertyNameTrustmarkBindingRegistryUri].toString())
                        trustmarkBindingRegistryUri.saveAndFlushHelper()
                    })

            JobUtilityForTrustmarkBindingRegistry.synchronizeTrustmarkBindingRegistryUriAndDependencies(duration, now, uri);

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

        UriSynchronizerForTrustInteroperabilityProfile.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), "https://trustmark.nief.org/tpat/tips/nief-mandatory-attributes/2.0/")
        UriSynchronizerForTrustInteroperabilityProfile.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), "https://trustmark.nief.org/tpat/tips/nief-highly-recommended-attributes/2.0/")
        UriSynchronizerForTrustInteroperabilityProfile.INSTANCE.synchronizeUri(LocalDateTime.now(ZoneOffset.UTC), "https://trustmark.nief.org/tpat/tips/nief-recommended-attributes/2.0/")
    }

    private void initializeJob() {

        final SchedulerFactory schedulerFactory = new StdSchedulerFactory()
        final Scheduler scheduler = schedulerFactory.getScheduler()

        initializeJobHelper(scheduler, JobForTrustmarkUri.class, ApplicationProperties.propertyNameJobForTrustmarkUriCronExpression, new HashMap<String, Object>())
        initializeJobHelper(scheduler, JobForTrustmarkStatusReportUri.class, ApplicationProperties.propertyNameJobForTrustmarkStatusReportUriCronExpression, new HashMap<String, Object>())
        initializeJobHelper(scheduler, JobForTrustmarkDefinitionUri.class, ApplicationProperties.propertyNameJobForTrustmarkDefinitionUriCronExpression, new HashMap<String, Object>())
        initializeJobHelper(scheduler, JobForTrustmarkBindingRegistryUri.class, ApplicationProperties.propertyNameJobForTrustmarkBindingRegistryUriCronExpression, new HashMap<String, Object>() {
            {
                put(ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum, grailsApplication.config[ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum]);
            }
        })
        initializeJobHelper(scheduler, JobForTrustInteroperabilityProfileUri.class, ApplicationProperties.propertyNameJobForTrustInteroperabilityProfileUriCronExpression, new HashMap<String, Object>())
        initializeJobHelper(scheduler, JobForMailPasswordReset.class, ApplicationProperties.propertyNameJobForMailPasswordResetCronExpression, new HashMap<String, Object>())
        initializeJobHelper(scheduler, JobForMailEvaluationUpdate.class, ApplicationProperties.propertyNameJobForMailEvaluationUpdateCronExpression, new HashMap<String, Object>())
        initializeJobHelper(scheduler, JobForPartnerSystemCandidateTrustInteroperabilityProfileUri.class, ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriCronExpression, new HashMap<String, Object>() {
            {
                put(ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum, grailsApplication.config[ApplicationProperties.propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum]);
            }
        })

        scheduler.start()
    }

    private void initializeJobHelper(final Scheduler scheduler, final Class<? extends Job> jobForClass, final String propertyNameForCronExpression, final Map<String, Object> jobDataMap) {

        final String cronExpression = grailsApplication.config[propertyNameForCronExpression]

        final JobDetail jobDetail = JobBuilder.newJob(jobForClass)
                .withIdentity(jobForClass.simpleName, "trpt")
                .build();

        final Trigger jobTrigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(jobForClass.simpleName, "trpt"))
                .withSchedule(cronSchedule(cronExpression))
                .build()

        jobDataMap.forEach({ key, value -> jobDetail.getJobDataMap().put(key, value) })

        scheduler.scheduleJob(jobDetail, jobTrigger)
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
