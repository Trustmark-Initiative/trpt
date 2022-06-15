package edu.gatech.gtri.trustmark.trpt.service.uri;

import edu.gatech.gtri.trustmark.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.trpt.domain.Role;
import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationMapUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryOrganizationTrustmarkMapUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkStatusReportUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionName.URI_SET_SELECT;
import static edu.gatech.gtri.trustmark.trpt.service.permission.PermissionUtility.userMay;
import static edu.gatech.gtri.trustmark.trpt.service.uri.UriSetUtility.uriResponseList;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class UriSetService {

    public Validation<NonEmptyList<ValidationMessage<UriSetField>>, UriSetResponse> findOne(
            final String requesterUsername,
            final UriSetFindAllRequest uriSetFindAllRequest) {

        return userMay(requesterUsername, URI_SET_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, uriSetFindAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<UriSetField>>, UriSetResponse> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UriSetFindAllRequest uriSetFindAllRequest) {

        return success(new UriSetResponse(
                uriResponseList(TrustInteroperabilityProfileUri.findAllHelper()),
                uriResponseList(TrustmarkBindingRegistrySystemMapUriType.findAllHelper()),
                uriResponseList(TrustmarkDefinitionUri.findAllHelper()),
                uriResponseList(TrustmarkStatusReportUri.findAllHelper()),
                uriResponseList(TrustmarkUri.findAllHelper()),
                uriResponseList(TrustmarkBindingRegistryOrganizationMapUri.findAllHelper()),
                uriResponseList(TrustmarkBindingRegistryOrganizationTrustmarkMapUri.findAllHelper())));
    }
}
