package edu.gatech.gtri.trustmark.trpt.service.trustInteroperabilityProfile;

import edu.gatech.gtri.trustmark.trpt.domain.TrustInteroperabilityProfileUri;
import org.gtri.fj.data.Either;

import static org.gtri.fj.data.Either.reduce;

public final class TrustInteroperabilityProfileUtility {

    private TrustInteroperabilityProfileUtility() {
    }

    public static TrustInteroperabilityProfileResponse trustInteroperabilityProfileResponse(
            final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {

        return new TrustInteroperabilityProfileResponse(
                trustInteroperabilityProfileUri.idHelper(),
                trustInteroperabilityProfileUri.getUri(),
                trustInteroperabilityProfileUri.getName(),
                trustInteroperabilityProfileUri.getDescription(),
                trustInteroperabilityProfileUri.getPublicationLocalDateTime(),
                trustInteroperabilityProfileUri.getIssuerName(),
                trustInteroperabilityProfileUri.getIssuerIdentifier(),
                trustInteroperabilityProfileUri.getDocumentRequestLocalDateTime(),
                trustInteroperabilityProfileUri.getDocumentSuccessLocalDateTime(),
                trustInteroperabilityProfileUri.getDocumentFailureLocalDateTime(),
                trustInteroperabilityProfileUri.getDocumentFailureMessage(),
                trustInteroperabilityProfileUri.getServerRequestLocalDateTime(),
                trustInteroperabilityProfileUri.getServerSuccessLocalDateTime(),
                trustInteroperabilityProfileUri.getServerFailureLocalDateTime(),
                trustInteroperabilityProfileUri.getServerFailureMessage());
    }

    public static TrustInteroperabilityProfileUri upsertTrustInteroperabilityProfileUri(final Either<String, TrustInteroperabilityProfileUri> trustInteroperabilityProfileUriEither) {
        return reduce(trustInteroperabilityProfileUriEither
                .leftMap(uri -> {
                    final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri = new TrustInteroperabilityProfileUri();
                    trustInteroperabilityProfileUri.setUri(uri);
                    return trustInteroperabilityProfileUri.saveHelper();
                }));
    }
}
