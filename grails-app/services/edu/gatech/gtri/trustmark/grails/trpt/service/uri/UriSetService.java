package edu.gatech.gtri.trustmark.grails.trpt.service.uri;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistryOrganizationMapUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistryOrganizationTrustmarkMapUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkBindingRegistrySystemMapUriType;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkDefinitionUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkStatusReportUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustmarkUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionName;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.PermissionUtility;
import edu.gatech.gtri.trustmark.grails.trpt.service.permission.Role;
import edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage;
import grails.gorm.transactions.Transactional;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import static edu.gatech.gtri.trustmark.grails.trpt.service.uri.UriSetUtility.uriSetResponsePage;
import static org.gtri.fj.data.Validation.success;

@Transactional
public class UriSetService {

    public Validation<NonEmptyList<ValidationMessage<UriSetField>>, UriSetResponse> findOne(
            final OAuth2AuthenticationToken requesterUsername,
            final UriSetFindAllRequest uriSetFindAllRequest) {

        return PermissionUtility.userMay(requesterUsername, PermissionName.URI_SET_SELECT, (requesterUser, requesterOrganizationList, requesterRoleList) -> findOneHelper(requesterUser, requesterOrganizationList, requesterRoleList, uriSetFindAllRequest));
    }

    private Validation<NonEmptyList<ValidationMessage<UriSetField>>, UriSetResponse> findOneHelper(
            final User requesterUser,
            final List<Organization> requesterOrganizationList,
            final List<Role> requesterRoleList,
            final UriSetFindAllRequest uriSetFindAllRequest) {

        return success(new UriSetResponse(
                uriSetResponsePage(
                        uriSetFindAllRequest.getTrustInteroperabilityProfileUriOffset(),
                        uriSetFindAllRequest.getTrustInteroperabilityProfileUriMax(),
                        TrustInteroperabilityProfileUri.findAllHelper(
                                uriSetFindAllRequest.getTrustInteroperabilityProfileUriOffset(),
                                uriSetFindAllRequest.getTrustInteroperabilityProfileUriMax())),
                uriSetResponsePage(
                        uriSetFindAllRequest.getTrustmarkBindingRegistryUriTypeOffset(),
                        uriSetFindAllRequest.getTrustmarkBindingRegistryUriTypeMax(),
                        TrustmarkBindingRegistrySystemMapUriType.findAllHelper(
                                uriSetFindAllRequest.getTrustmarkBindingRegistryUriTypeOffset(),
                                uriSetFindAllRequest.getTrustmarkBindingRegistryUriTypeMax())),
                uriSetResponsePage(
                        uriSetFindAllRequest.getTrustmarkDefinitionUriOffset(),
                        uriSetFindAllRequest.getTrustmarkDefinitionUriMax(),
                        TrustmarkDefinitionUri.findAllHelper(
                                uriSetFindAllRequest.getTrustmarkDefinitionUriOffset(),
                                uriSetFindAllRequest.getTrustmarkDefinitionUriMax())),
                uriSetResponsePage(
                        uriSetFindAllRequest.getTrustmarkStatusReportUriOffset(),
                        uriSetFindAllRequest.getTrustmarkStatusReportUriMax(),
                        TrustmarkStatusReportUri.findAllHelper(
                                uriSetFindAllRequest.getTrustmarkStatusReportUriOffset(),
                                uriSetFindAllRequest.getTrustmarkStatusReportUriMax())),
                uriSetResponsePage(
                        uriSetFindAllRequest.getTrustmarkUriOffset(),
                        uriSetFindAllRequest.getTrustmarkUriMax(),
                        TrustmarkUri.findAllHelper(
                                uriSetFindAllRequest.getTrustmarkUriOffset(),
                                uriSetFindAllRequest.getTrustmarkUriMax())),
                uriSetResponsePage(
                        uriSetFindAllRequest.getTrustmarkBindingRegistryOrganizationMapUriOffset(),
                        uriSetFindAllRequest.getTrustmarkBindingRegistryOrganizationMapUriMax(),
                        TrustmarkBindingRegistryOrganizationMapUri.findAllHelper(
                                uriSetFindAllRequest.getTrustmarkBindingRegistryOrganizationMapUriOffset(),
                                uriSetFindAllRequest.getTrustmarkBindingRegistryOrganizationMapUriMax())),
                uriSetResponsePage(
                        uriSetFindAllRequest.getTrustmarkBindingRegistryOrganizationTrustmarkMapUriOffset(),
                        uriSetFindAllRequest.getTrustmarkBindingRegistryOrganizationTrustmarkMapUriMax(),
                        TrustmarkBindingRegistryOrganizationTrustmarkMapUri.findAllHelper(
                                uriSetFindAllRequest.getTrustmarkBindingRegistryOrganizationTrustmarkMapUriOffset(),
                                uriSetFindAllRequest.getTrustmarkBindingRegistryOrganizationTrustmarkMapUriMax()))));
    }
}
