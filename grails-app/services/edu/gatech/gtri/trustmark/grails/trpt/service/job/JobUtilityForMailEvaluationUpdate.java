package edu.gatech.gtri.trustmark.grails.trpt.service.job;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Organization;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerOrganizationCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerOrganizationCandidateMailEvaluationUpdate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerOrganizationCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerSystemCandidate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerSystemCandidateMailEvaluationUpdate;
import edu.gatech.gtri.trustmark.grails.trpt.domain.PartnerSystemCandidateTrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.grails.trpt.domain.Server;
import edu.gatech.gtri.trustmark.grails.trpt.domain.TrustInteroperabilityProfileUri;
import edu.gatech.gtri.trustmark.grails.trpt.domain.User;
import edu.gatech.gtri.trustmark.v1_0.util.OrdUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.Ord;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.TryEffect;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P4;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.grails.trpt.domain.Organization.organizationOrdId;
import static edu.gatech.gtri.trustmark.grails.trpt.domain.ProtectedSystem.protectedSystemOrdId;
import static edu.gatech.gtri.trustmark.grails.trpt.service.job.RetryTemplateUtility.retry;
import static edu.gatech.gtri.trustmark.grails.trpt.service.mail.MailUtility.send;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.lang.System.lineSeparator;
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.single;
import static org.gtri.fj.data.NonEmptyList.fromList;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.data.Option.somes;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public final class JobUtilityForMailEvaluationUpdate {

    private static final Log log = LogFactory.getLog(JobUtilityForMailEvaluationUpdate.class);

    private JobUtilityForMailEvaluationUpdate() {
    }

    public static void synchronizeMailEvaluationUpdate() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        somes(retry(() -> ProtectedSystemHelper.select(), log)
                .map(mailEvaluationUpdate -> reduce(TryEffect.f(() -> send(mailEvaluationUpdate._2(), mailEvaluationUpdate._3(), mailEvaluationUpdate._4()))._1().toEither().bimap(
                        exception -> {
                            log.error(exception.getMessage(), exception);
                            return none();
                        },
                        unit -> some(mailEvaluationUpdate._1())))))
                .forEach(mailEvaluationUpdateList -> mailEvaluationUpdateList
                        .forEach(partnerSystemCandidateMailEvaluationUpdate -> retry(() -> ProtectedSystemHelper.update(now, partnerSystemCandidateMailEvaluationUpdate), log)));

        somes(retry(() -> OrganizationHelper.select(), log)
                .map(mailEvaluationUpdate -> reduce(TryEffect.f(() -> send(mailEvaluationUpdate._2(), mailEvaluationUpdate._3(), mailEvaluationUpdate._4()))._1().toEither().bimap(
                        exception -> {
                            log.error(exception.getMessage(), exception);
                            return none();
                        },
                        unit -> some(mailEvaluationUpdate._1())))))
                .forEach(mailEvaluationUpdateList -> mailEvaluationUpdateList
                        .forEach(partnerOrganizationCandidateMailEvaluationUpdate -> retry(() -> OrganizationHelper.update(now, partnerOrganizationCandidateMailEvaluationUpdate), log)));
    }

    private static class ProtectedSystemHelper {

        private static List<P4<List<PartnerSystemCandidateMailEvaluationUpdate>, NonEmptyList<String>, String, String>> select() {

            return ProtectedSystem.withTransactionHelper(() ->
                    Server.findAllHelper().headOption().toList().bind(server ->
                            ProtectedSystem.findAllByPartnerSystemCandidateMailEvaluationUpdateMailLocalDateTimeIsNullHelper()
                                    .groupBy(protectedSystem -> protectedSystem._1(), protectedSystem -> protectedSystem._2(), protectedSystemOrdId).toList()
                                    .groupBy(protectedSystem -> protectedSystem._1().organizationHelper(), protectedSystem -> protectedSystem, organizationOrdId).toList()
                                    .bind(organization -> fromList(organization._1().userSetHelper())
                                            .map(userList -> p(
                                                    organization._2().bind(protectedSystem -> protectedSystem._2()),
                                                    userList.map(User::getUsername),
                                                    subject(),
                                                    body(
                                                            server,
                                                            organization._2().map(protectedSystem -> p(
                                                                    protectedSystem._1(),
                                                                    protectedSystem._2().map(PartnerSystemCandidateMailEvaluationUpdate::partnerSystemCandidateTrustInteroperabilityProfileUriHelper))))))
                                            .toList())));
        }

        private static PartnerSystemCandidateMailEvaluationUpdate update(
                final LocalDateTime now,
                final PartnerSystemCandidateMailEvaluationUpdate partnerSystemCandidateMailEvaluationUpdate) {

            return PartnerSystemCandidateMailEvaluationUpdate.withTransactionHelper(() -> {
                partnerSystemCandidateMailEvaluationUpdate.setMailDateTime(now);
                return partnerSystemCandidateMailEvaluationUpdate.saveHelper();
            });
        }

        private static String subject() {

            return "The trust dashboard for your protected systems has changed.";
        }

        private static String body(
                final Server server,
                final List<P2<ProtectedSystem, List<PartnerSystemCandidateTrustInteroperabilityProfileUri>>> protectedSystemList) {

            final Ord<P2<ProtectedSystem, List<PartnerSystemCandidateTrustInteroperabilityProfileUri>>> protectedSystemOrd = Ord.contramap(P2::_1, Ord.contramap(ProtectedSystem::getName, stringOrd));

            return join(
                    lineSeparator(),
                    "The trust dashboard for the following protected systems has changed." + lineSeparator(),
                    join(
                            lineSeparator(),
                            protectedSystemList
                                    .sort(protectedSystemOrd)
                                    .map(protectedSystem ->
                                            protectedSystem(server, protectedSystem._1(), protectedSystem._2()) + lineSeparator())));
        }

        private static String protectedSystem(
                final Server server,
                final ProtectedSystem protectedSystem,
                final List<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriList) {

            final Ord<PartnerSystemCandidateTrustInteroperabilityProfileUri> partnerSystemCandidateTrustInteroperabilityProfileUriOrd =
                    OrdUtility.priorityOrd(
                            Ord.contramap(PartnerSystemCandidateTrustInteroperabilityProfileUri::partnerSystemCandidateHelper, Ord.contramap(PartnerSystemCandidate::getName, stringOrd)),
                            Ord.contramap(PartnerSystemCandidateTrustInteroperabilityProfileUri::trustInteroperabilityProfileUriHelper, Ord.contramap(TrustInteroperabilityProfileUri::getName, stringOrd)));

            return join(
                    lineSeparator(),
                    arrayList(
                            format("Protected System: %s", protectedSystem.getName()),
                            protectedSystemUri(server, protectedSystem),
                            join(
                                    lineSeparator(),
                                    partnerSystemCandidateTrustInteroperabilityProfileUriList
                                            .sort(partnerSystemCandidateTrustInteroperabilityProfileUriOrd)
                                            .map(partnerSystemCandidateTrustInteroperabilityProfileUri ->
                                                    partnerSystemCandidate(
                                                            server,
                                                            protectedSystem,
                                                            partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper(),
                                                            partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())))));
        }

        private static String protectedSystemUri(
                final Server server,
                final ProtectedSystem protectedSystem) {

            return format("%sprotectedSystemDashboard/manage?id=%s%n", server.getUrl(), protectedSystem.idHelper());
        }

        private static String partnerSystemCandidate(
                final Server server,
                final ProtectedSystem protectedSystem,
                final PartnerSystemCandidate partnerSystemCandidate,
                final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {

            return join(
                    lineSeparator(),
                    arrayList(
                            format("Candidate Partner System: %s", partnerSystemCandidate.getName()),
                            format("Trust Interoperability Profile: %s", trustInteroperabilityProfileUri.getName()),
                            partnerSystemCandidateUri(server, protectedSystem, partnerSystemCandidate)).toJavaList());
        }

        private static String partnerSystemCandidateUri(
                final Server server,
                final ProtectedSystem protectedSystem,
                final PartnerSystemCandidate partnerSystemCandidate) {

            return format("%sprotectedSystemDashboardPartnerSystemCandidate/manage?id=%s&partnerSystemCandidate=%s%n", server.getUrl(), protectedSystem.idHelper(), partnerSystemCandidate.idHelper());
        }
    }

    private static class OrganizationHelper {

        private static List<P4<List<PartnerOrganizationCandidateMailEvaluationUpdate>, NonEmptyList<String>, String, String>> select() {

            return Organization.withTransactionHelper(() ->
                    Server.findAllHelper().headOption().toList().bind(server ->
                            Organization.findAllByPartnerOrganizationCandidateMailEvaluationUpdateMailLocalDateTimeIsNullHelper()
                                    .groupBy(organization -> organization._1(), organization -> organization._2(), organizationOrdId).toList()
                                    .bind(organization -> fromList(organization._1().userSetHelper())
                                            .map(userList -> p(
                                                    organization._2(),
                                                    userList.map(User::getUsername),
                                                    subject(),
                                                    body(
                                                            server,
                                                            single(p(
                                                                    organization._1(),
                                                                    organization._2().map(PartnerOrganizationCandidateMailEvaluationUpdate::partnerOrganizationCandidateTrustInteroperabilityProfileUriHelper))))))
                                            .toList())));
        }

        private static PartnerOrganizationCandidateMailEvaluationUpdate update(
                final LocalDateTime now,
                final PartnerOrganizationCandidateMailEvaluationUpdate partnerOrganizationCandidateMailEvaluationUpdate) {

            return PartnerOrganizationCandidateMailEvaluationUpdate.withTransactionHelper(() -> {
                partnerOrganizationCandidateMailEvaluationUpdate.setMailDateTime(now);
                return partnerOrganizationCandidateMailEvaluationUpdate.saveHelper();
            });
        }

        private static String subject() {

            return "The trust dashboard for your organizations has changed.";
        }

        private static String body(
                final Server server,
                final List<P2<Organization, List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri>>> organizationList) {

            final Ord<P2<Organization, List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri>>> organizationOrd = Ord.contramap(P2::_1, Ord.contramap(Organization::getName, stringOrd));

            return join(
                    lineSeparator(),
                    "The trust dashboard for the following organizations has changed." + lineSeparator(),
                    join(
                            lineSeparator(),
                            organizationList
                                    .sort(organizationOrd)
                                    .map(organization ->
                                            organization(server, organization._1(), organization._2()) + lineSeparator())));
        }

        private static String organization(
                final Server server,
                final Organization organization,
                final List<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriList) {

            final Ord<PartnerOrganizationCandidateTrustInteroperabilityProfileUri> partnerOrganizationCandidateTrustInteroperabilityProfileUriOrd =
                    OrdUtility.priorityOrd(
                            Ord.contramap(PartnerOrganizationCandidateTrustInteroperabilityProfileUri::partnerOrganizationCandidateHelper, Ord.contramap(PartnerOrganizationCandidate::getName, stringOrd)),
                            Ord.contramap(PartnerOrganizationCandidateTrustInteroperabilityProfileUri::trustInteroperabilityProfileUriHelper, Ord.contramap(TrustInteroperabilityProfileUri::getName, stringOrd)));

            return join(
                    lineSeparator(),
                    arrayList(
                            format("Organization: %s", organization.getName()),
                            organizationUri(server, organization),
                            join(
                                    lineSeparator(),
                                    partnerOrganizationCandidateTrustInteroperabilityProfileUriList
                                            .sort(partnerOrganizationCandidateTrustInteroperabilityProfileUriOrd)
                                            .map(partnerOrganizationCandidateTrustInteroperabilityProfileUri ->
                                                    partnerOrganizationCandidate(
                                                            server,
                                                            organization,
                                                            partnerOrganizationCandidateTrustInteroperabilityProfileUri.partnerOrganizationCandidateHelper(),
                                                            partnerOrganizationCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper())))));
        }

        private static String organizationUri(
                final Server server,
                final Organization organization) {

            return format("%sorganizationDashboard/manage?id=%s%n", server.getUrl(), organization.idHelper());
        }

        private static String partnerOrganizationCandidate(
                final Server server,
                final Organization organization,
                final PartnerOrganizationCandidate partnerOrganizationCandidate,
                final TrustInteroperabilityProfileUri trustInteroperabilityProfileUri) {

            return join(
                    lineSeparator(),
                    arrayList(
                            format("Candidate Partner Organization: %s", partnerOrganizationCandidate.getName()),
                            format("Trust Interoperability Profile: %s", trustInteroperabilityProfileUri.getName()),
                            partnerOrganizationCandidateUri(server, organization, partnerOrganizationCandidate)).toJavaList());
        }

        private static String partnerOrganizationCandidateUri(
                final Server server,
                final Organization organization,
                final PartnerOrganizationCandidate partnerOrganizationCandidate) {

            return format("%sorganizationDashboardPartnerOrganizationCandidate/manage?id=%s&partnerOrganizationCandidate=%s%n", server.getUrl(), organization.idHelper(), partnerOrganizationCandidate.idHelper());
        }
    }
}


