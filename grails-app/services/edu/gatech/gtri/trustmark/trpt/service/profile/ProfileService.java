package edu.gatech.gtri.trustmark.trpt.service.profile;

import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.ApplicationProperties;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static edu.gatech.gtri.trustmark.trpt.service.role.RoleUtility.roleResponse;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class ProfileService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public Validation<NonEmptyList<ValidationMessage<ProfileField>>, ProfileResponse> findOne(
            final String requesterUsername) {

        return requesterUsername == null ?
                success(new ProfileResponse(
                        requesterUsername,
                        null,
                        applicationProperties.getProperty(ApplicationProperties.propertyApplicationVersion),
                        applicationProperties.getProperty(ApplicationProperties.propertyGitCommitIdAbbrev),
                        applicationProperties.getProperty(ApplicationProperties.propertyGitCommitId),
                        applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime) == null ? null : LocalDateTime.parse(applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")))) :
                success(User.findByUsernameHelper(requesterUsername)
                        .map(user -> new ProfileResponse(
                                requesterUsername,
                                roleResponse(user.userRoleSetHelper().iterator().next().roleHelper()),
                                applicationProperties.getProperty(ApplicationProperties.propertyApplicationVersion),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitIdAbbrev),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitId),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime) == null ? null : LocalDateTime.parse(applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))))
                        .orSome(new ProfileResponse(
                                null,
                                null,
                                applicationProperties.getProperty(ApplicationProperties.propertyApplicationVersion),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitIdAbbrev),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitId),
                                applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime) == null ? null : LocalDateTime.parse(applicationProperties.getProperty(ApplicationProperties.propertyGitCommitTime), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")))));
    }
}
