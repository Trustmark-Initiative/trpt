package edu.gatech.gtri.trustmark.trpt.service;

import grails.core.GrailsApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationProperties {

    public static final String propertyNameServerUrl = "server.url";
    public static final String propertyNameJavaMailSenderHost = "javaMailSender.host";
    public static final String propertyNameJavaMailSenderPort = "javaMailSender.port";
    public static final String propertyNameJavaMailSenderUsername = "javaMailSender.username";
    public static final String propertyNameJavaMailSenderPassword = "javaMailSender.password";
    public static final String propertyNameJavaMailSenderAuthor = "javaMailSender.author";
    public static final String propertyNameOrganizationName = "organization.name";
    public static final String propertyNameOrganizationUrl = "organization.uri";
    public static final String propertyNameOrganizationDescription = "organization.description";
    public static final String propertyNameUserUsername = "user.username";
    public static final String propertyNameUserPassword = "user.password";
    public static final String propertyNameUserNameGiven = "user.nameGiven";
    public static final String propertyNameUserNameFamily = "user.nameFamily";
    public static final String propertyNameUserTelephone = "user.telephone";
    public static final String propertyNameUserUserEnabled = "user.userEnabled";
    public static final String propertyNameUserUserLocked = "user.userLocked";
    public static final String propertyNameUserUserExpired = "user.userExpired";
    public static final String propertyNameUserPasswordExpired = "user.passwordExpired";
    public static final String propertyNameRoleName = "role.name";
    public static final String propertyNameTrustmarkBindingRegistryName = "trustmarkBindingRegistry.name";
    public static final String propertyNameTrustmarkBindingRegistryUri = "trustmarkBindingRegistry.uri";
    public static final String propertyNameTrustmarkBindingRegistryDescription = "trustmarkBindingRegistry.description";
    public static final String propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriCronExpression = "jobForPartnerSystemCandidateTrustInteroperabilityProfileUri.cronExpression";
    public static final String propertyNameJobForPartnerSystemCandidateTrustInteroperabilityProfileUriEvaluationPeriodMaximum = "jobForPartnerSystemCandidateTrustInteroperabilityProfileUri.evaluationPeriodMaximum";
    public static final String propertyNameJobForTrustmarkUriCronExpression = "jobForTrustmarkUri.cronExpression";
    public static final String propertyNameJobForTrustmarkStatusReportUriCronExpression = "jobForTrustmarkStatusReportUri.cronExpression";
    public static final String propertyNameJobForTrustmarkBindingRegistryUriCronExpression = "jobForTrustmarkBindingRegistryUri.cronExpression";
    public static final String propertyNameJobForTrustInteroperabilityProfileUriCronExpression = "jobForTrustInteroperabilityProfileUri.cronExpression";
    public static final String propertyNameJobForTrustmarkDefinitionUriCronExpression = "jobForTrustmarkDefinitionUri.cronExpression";
    public static final String propertyNameJobForMailPasswordResetCronExpression = "jobForMailPasswordReset.cronExpression";
    public static final String propertyNameJobForMailEvaluationUpdateCronExpression = "jobForMailEvaluationUpdate.cronExpression";
    public static final String propertyForUserPasswordPattern = "passwordService.userPasswordPattern";
    public static final String propertyForUserPasswordPatternDescription = "passwordService.userPasswordPatternDescription";
    public static final String propertyGitCommitIdAbbrev = "git.commit.id.abbrev";
    public static final String propertyGitCommitId = "git.commit.id";
    public static final String propertyGitCommitTime = "git.commit.time";
    public static final String propertyApplicationVersion = "info.app.version";

    @Autowired
    private GrailsApplication grailsApplication;

    public String getProperty(
            final String key) {

        return grailsApplication.getConfig().getProperty(key);
    }
}
