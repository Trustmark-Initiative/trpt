package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.trpt.domain.MailEvaluationUpdate;
import edu.gatech.gtri.trustmark.trpt.domain.ProtectedSystem;
import edu.gatech.gtri.trustmark.trpt.domain.Server;
import edu.gatech.gtri.trustmark.trpt.domain.User;
import edu.gatech.gtri.trustmark.trpt.service.mail.MailUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.Ordering;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;
import static org.gtri.fj.Ord.ord;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.lang.StringUtility.stringOrd;

public class JobUtilityForMailEvaluationUpdate {

    private JobUtilityForMailEvaluationUpdate() {
    }

    private static final Log log = LogFactory.getLog(JobUtilityForMailEvaluationUpdate.class);

    public static void synchronizeMailEvaluationUpdate() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        ProtectedSystem.withTransactionHelper(() -> {

            ProtectedSystem.findAllByMailEvaluationUpdateMailLocalDateTimeIsNullHelper()
                    .groupBy(p -> p._1(), p -> p._2(), ord((o1, o2) -> Ordering.fromInt(Long.compare(o1.idHelper(), o2.idHelper()))))
                    .toList()
                    .groupBy(p -> p._1().organizationHelper(), p -> p, ord((o1, o2) -> Ordering.fromInt(Long.compare(o1.idHelper(), o2.idHelper()))))
                    .toList()
                    .filter(p -> p._1().userSetHelper().isNotEmpty())
                    .forEach(p -> {

                        final String body = String.join(
                                System.lineSeparator(),
                                "The trust dashboard for the following protected systems has changed." + System.lineSeparator(),
                                String.join(
                                        System.lineSeparator(),
                                        p._2()
                                                .sort(ord((o1, o2) -> Ordering.fromInt(o1._1().getName().compareTo(o2._1().getName()))))
                                                .map(pInner ->
                                                        String.join(
                                                                System.lineSeparator(),
                                                                arrayList(
                                                                        format("Protected System: %s", pInner._1().getName()),
                                                                        format("%sprotectedSystemDashboard/manage?id=%s%n", Server.findAllHelper().head().getUrl(), pInner._1().idHelper()),
                                                                        String.join(
                                                                                System.lineSeparator(),
                                                                                pInner._2()
                                                                                        .sort(ord((o1, o2) -> o1.partnerSystemCandidateTrustInteroperabilityProfileUriHelper().partnerSystemCandidateHelper().getName().compareTo(o2.partnerSystemCandidateTrustInteroperabilityProfileUriHelper().partnerSystemCandidateHelper().getName()) != 0 ?
                                                                                                Ordering.fromInt(o1.partnerSystemCandidateTrustInteroperabilityProfileUriHelper().partnerSystemCandidateHelper().getName().compareTo(o2.partnerSystemCandidateTrustInteroperabilityProfileUriHelper().partnerSystemCandidateHelper().getName())) :
                                                                                                Ordering.fromInt(o1.partnerSystemCandidateTrustInteroperabilityProfileUriHelper().trustInteroperabilityProfileUriHelper().getName().compareTo(o2.partnerSystemCandidateTrustInteroperabilityProfileUriHelper().trustInteroperabilityProfileUriHelper().getName()))))
                                                                                        .map(mailEvaluationUpdate -> mailEvaluationUpdate.partnerSystemCandidateTrustInteroperabilityProfileUriHelper())
                                                                                        .map(partnerSystemCandidateTrustInteroperabilityProfileUri ->
                                                                                                String.join(
                                                                                                        System.lineSeparator(),
                                                                                                        arrayList(
                                                                                                                format("Candidate Partner System: %s", partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().getName()),
                                                                                                                format("Trust Interoperability Profile: %s", partnerSystemCandidateTrustInteroperabilityProfileUri.trustInteroperabilityProfileUriHelper().getName()),
                                                                                                                format("%sprotectedSystemDashboardPartnerSystemCandidate/manage?id=%s&partnerSystemCandidate=%s%n", Server.findAllHelper().head().getUrl(), pInner._1().idHelper(), partnerSystemCandidateTrustInteroperabilityProfileUri.partnerSystemCandidateHelper().idHelper())).toJavaList()))))) + System.lineSeparator())));

                        MailUtility.send(
                                nel(p._1().userSetHelper().head(), p._1().userSetHelper().tail()).map(User::getUsername).sort(stringOrd),
                                "The trust dashboard for your protected systems has changed.",
                                body);
                    });

            MailEvaluationUpdate.findAllByMailDateTimeIsNullHelper()
                    .forEach(mailEvaluationUpdate -> {
                        mailEvaluationUpdate.setMailDateTime(now);
                        mailEvaluationUpdate.saveHelper();
                    });
        });
    }
}


