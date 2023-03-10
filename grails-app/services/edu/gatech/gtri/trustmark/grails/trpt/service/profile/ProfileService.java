package edu.gatech.gtri.trustmark.grails.trpt.service.profile;

import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.grails.trpt.service.ApplicationProperties;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static edu.gatech.gtri.trustmark.grails.trpt.service.user.UserUtility.userResponse;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class ProfileService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public Validation<NonEmptyList<ValidationMessage<ProfileField>>, ProfileResponse> findOne(
            final OAuth2AuthenticationToken requester) {

        return requester == null ?
                success(new ProfileResponse(
                        null,
                        applicationProperties.getProperty(ApplicationProperties.propertyApplicationVersion),
                        applicationProperties.getProperty(ApplicationProperties.propertyGitCommitIdAbbrev),
                        applicationProperties.getProperty(ApplicationProperties.propertyGitCommitId),
                        applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime) == null ? null : LocalDateTime.parse(applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")))) :
                success(User.findByUsernameHelper(requester.getName())
                        .map(user -> new ProfileResponse(
                                userResponse(user, false),
                                applicationProperties.getProperty(ApplicationProperties.propertyApplicationVersion),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitIdAbbrev),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitId),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime) == null ? null : LocalDateTime.parse(applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))))
                        .orSome(new ProfileResponse(
                                null,
                                applicationProperties.getProperty(ApplicationProperties.propertyApplicationVersion),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitIdAbbrev),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitId),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime) == null ? null : LocalDateTime.parse(applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")))));
    }
}
