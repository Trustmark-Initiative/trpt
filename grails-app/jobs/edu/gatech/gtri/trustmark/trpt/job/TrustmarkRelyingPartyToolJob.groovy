package edu.gatech.gtri.trustmark.trpt.job

import edu.gatech.gtri.trustmark.trpt.service.job.JobUtility
import grails.gorm.transactions.Transactional

@Transactional
class TrustmarkRelyingPartyToolJob {
    static triggers = {
        cron name: 'partnerSystemCandidate', cronExpression: "0 0-59 0-23 1-31 1-12 ? *"
    }

    void execute() {
//        JobUtility.synchronizeTrustInteroperabilityProfileUri()
//        JobUtility.synchronizeTrustmarkBindingRegistry()
//        JobUtility.synchronizeTrustExpressionEvaluation()
    }
}
