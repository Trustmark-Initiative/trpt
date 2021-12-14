package edu.gatech.gtri.trustmark.trpt.service.uri;

import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkBindingRegistryUriType;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkDefinitionUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkStatusReportUri;
import edu.gatech.gtri.trustmark.trpt.domain.TrustmarkUri;

import grails.gorm.transactions.Transactional;

import static edu.gatech.gtri.trustmark.trpt.service.uri.UriSetUtility.uriResponseList;

@Transactional
public class UriSetService {

    public UriSetResponse findOne(
            final String requesterUsername,
            final UriSetFindAllRequest uriSetFindAllRequest) {

        return new UriSetResponse(
                uriResponseList(TrustInteroperabilityProfileUri.findAllHelper()),
                uriResponseList(TrustmarkBindingRegistryUriType.findAllHelper()),
                uriResponseList(TrustmarkDefinitionUri.findAllHelper()),
                uriResponseList(TrustmarkStatusReportUri.findAllHelper()),
                uriResponseList(TrustmarkUri.findAllHelper()));
    }
}
