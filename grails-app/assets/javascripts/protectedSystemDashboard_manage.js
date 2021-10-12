function initialize(
    protectedSystemFindOneUrl,
    protectedSystemUpdateUrl,
    partnerSystemCandidateFindAllUrl,
    partnerSystemCandidateDashboard) {

    document.onreadystatechange = function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    }

    function onComplete() {

        findOne()
    }

    function findOne() {

        fetchGet(protectedSystemFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id")}))
            .then(response => response.json())
            .then(afterFindOne)
    }

    function afterFindOne(protectedSystem) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-insert").outerHTML = document.getElementById("trust-interoperability-profile-action-insert").outerHTML;
        document.getElementById("trust-interoperability-profile-action-delete").outerHTML = document.getElementById("trust-interoperability-profile-action-delete").outerHTML;

        document.getElementById("trust-interoperability-profile-action-insert").addEventListener("click", () => onInsertOpen(protectedSystem))
        document.getElementById("trust-interoperability-profile-action-delete").addEventListener("click", () => onDeleteSubmit(protectedSystem))

        Array.from(document.querySelectorAll(".protected-system-element-name"))
            .map(element => element.innerHTML = protectedSystem.name)

        Array.from(document.querySelectorAll(".protected-system-element-type"))
            .map(element => element.innerHTML = protectedSystem.type.label)

        const trustInteroperabilityProfileTBody = document.getElementById("trust-interoperability-profile-tbody")
        trustInteroperabilityProfileTBody.innerHTML = ""

        if (protectedSystem.protectedSystemTrustInteroperabilityProfileList.length === 0) {

            const trustInteroperabilityProfileElement = document.getElementById("trust-interoperability-profile-template-empty").content.cloneNode(true)

            trustInteroperabilityProfileTBody.appendChild(trustInteroperabilityProfileElement)

        } else {

            protectedSystem.protectedSystemTrustInteroperabilityProfileList.forEach(protectedSystemTrustInteroperabilityProfile => {

                const trustInteroperabilityProfileElement = document.getElementById("trust-interoperability-profile-template-summary").content.cloneNode(true)

                const trustInteroperabilityProfileElementActionUpdate = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-action-update")
                const trustInteroperabilityProfileElementActionDeleteQueue = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-action-delete-queue")
                const trustInteroperabilityProfileElementName = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-name")
                const trustInteroperabilityProfileElementUri = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-uri")
                const trustInteroperabilityProfileElementDescription = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-description")
                const trustInteroperabilityProfileElementIssuer = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-issuer")
                const trustInteroperabilityProfileElementIssuerIdentifier = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-issuer-identifier")
                const trustInteroperabilityProfileElementMandatory = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-mandatory")

                trustInteroperabilityProfileElementActionUpdate.addEventListener("click", () => onUpdateOpen(protectedSystem, protectedSystemTrustInteroperabilityProfile))
                trustInteroperabilityProfileElementActionDeleteQueue.dataset.uri = protectedSystemTrustInteroperabilityProfile.uri
                trustInteroperabilityProfileElementName.innerHTML = protectedSystemTrustInteroperabilityProfile.name
                trustInteroperabilityProfileElementName.title = protectedSystemTrustInteroperabilityProfile.name
                trustInteroperabilityProfileElementName.href = protectedSystemTrustInteroperabilityProfile.uri
                trustInteroperabilityProfileElementDescription.innerHTML = protectedSystemTrustInteroperabilityProfile.description
                trustInteroperabilityProfileElementDescription.title = protectedSystemTrustInteroperabilityProfile.description
                trustInteroperabilityProfileElementIssuer.innerHTML = protectedSystemTrustInteroperabilityProfile.issuerName
                trustInteroperabilityProfileElementIssuer.title = protectedSystemTrustInteroperabilityProfile.issuerName
                trustInteroperabilityProfileElementIssuer.href = protectedSystemTrustInteroperabilityProfile.issuerIdentifier

                if (protectedSystemTrustInteroperabilityProfile.mandatory) {

                    trustInteroperabilityProfileElementMandatory.classList.add("bi-exclamation-circle")
                }

                trustInteroperabilityProfileTBody.appendChild(trustInteroperabilityProfileElement)
            })
        }

        const partnerSystemCandidateTBody = document.getElementById("partner-system-candidate-tbody")
        partnerSystemCandidateTBody.innerHTML = ""

        if (protectedSystem.protectedSystemPartnerSystemCandidateList.length === 0) {

            const partnerSystemCandidateElement = document.getElementById("partner-system-candidate-template-empty").content.cloneNode(true)

            partnerSystemCandidateTBody.appendChild(partnerSystemCandidateElement)

        } else {

            protectedSystem.protectedSystemPartnerSystemCandidateList.forEach(protectedSystemPartnerSystemCandidate => {

                Array.from(document.querySelectorAll(".partner-system-candidate-element-type"))
                    .map(element => element.innerHTML = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.type.label)

                const partnerSystemCandidateElement = document.getElementById("partner-system-candidate-template-summary").content.cloneNode(true)

                const partnerSystemCandidateElementTrust = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-trust")
                const partnerSystemCandidateElementName = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-name")
                const partnerSystemCandidateElementTrustFabricMetadata = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-trust-fabric-metadata")
                const partnerSystemCandidateElementPercentTrustmarkDefinitionRequirement = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-percent-trustmark-definition-requirement")
                const partnerSystemCandidateElementPercentTrustInteroperabilityProfile = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-percent-trust-interoperability-profile")
                const partnerSystemCandidateElementActionDetail = partnerSystemCandidateElement.querySelector(".partner-system-candidate-action-detail")

                partnerSystemCandidateElementTrust.checked = protectedSystemPartnerSystemCandidate.trust
                if (partnerSystemCandidateElementTrust.checked) {
                    partnerSystemCandidateElementTrust.addEventListener("click", () => {

                        document.getElementById("modal-header-trust").classList.add("d-none")
                        document.getElementById("modal-body-trust").classList.add("d-none")

                        document.getElementById("modal-header-do-not-trust").classList.remove("d-none")
                        document.getElementById("modal-body-do-not-trust").classList.remove("d-none")

                        Array.from(document.querySelectorAll("#modal-trust .partner-system-candidate-element-trust-fabric-metadata"))
                            .forEach(element => {
                                if (protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor == null) {

                                    element.innerHTML = "the link"
                                    element.title = ""
                                    element.href = ""

                                } else {

                                    element.innerHTML = `${protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor}`
                                    element.title = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor
                                    element.href = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor
                                }
                            })

                        Array.from(document.querySelectorAll("#modal-trust .partner-system-candidate-element-name"))
                            .forEach(element => element.innerHTML = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.name)

                        onUpdateSubmitForPartnerSystemCandidateRemove(protectedSystem, protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id)
                    });
                } else {
                    partnerSystemCandidateElementTrust.addEventListener("click", () => {

                        document.getElementById("modal-header-trust").classList.remove("d-none")
                        document.getElementById("modal-body-trust").classList.remove("d-none")

                        document.getElementById("modal-header-do-not-trust").classList.add("d-none")
                        document.getElementById("modal-body-do-not-trust").classList.add("d-none")

                        Array.from(document.querySelectorAll("#modal-trust .partner-system-candidate-element-trust-fabric-metadata"))
                            .forEach(element => {
                                if (protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor == null) {

                                    element.innerHTML = "the link"
                                    element.title = ""
                                    element.href = ""

                                } else {

                                    element.innerHTML = `${protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor}`
                                    element.title = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor
                                    element.href = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor
                                }
                            })

                        Array.from(document.querySelectorAll("#modal-trust .partner-system-candidate-element-name"))
                            .forEach(element => element.innerHTML = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.name)

                        onUpdateSubmitForPartnerSystemCandidateAdd(protectedSystem, protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id)
                    });
                }

                partnerSystemCandidateElementName.innerHTML = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.name
                partnerSystemCandidateElementName.title = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.name
                partnerSystemCandidateElementName.href = partnerSystemCandidateDashboard + "?" + new URLSearchParams({"id": protectedSystem.id, "partnerSystemCandidate": protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id})

                if (protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor == null) {

                    partnerSystemCandidateElementTrustFabricMetadata.parentNode.removeChild(partnerSystemCandidateElementTrustFabricMetadata)
                } else {
                    partnerSystemCandidateElementTrustFabricMetadata.innerHTML = `(${protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor})`
                    partnerSystemCandidateElementTrustFabricMetadata.title = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor
                    partnerSystemCandidateElementTrustFabricMetadata.href = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor
                }

                partnerSystemCandidateElementPercentTrustmarkDefinitionRequirement.innerHTML =
                    protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        Math.round(
                            protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementSatisfied /
                            (protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementSatisfied) *
                            100) + "%"
                partnerSystemCandidateElementPercentTrustmarkDefinitionRequirement.title =
                    protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        `${protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementSatisfied} of ${(protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + protectedSystemPartnerSystemCandidate.evaluationTrustmarkDefinitionRequirementSatisfied)}`

                partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML =
                    protectedSystemPartnerSystemCandidate.evaluationTrustExpressionUnsatisfied + protectedSystemPartnerSystemCandidate.evaluationTrustExpressionSatisfied === 0 ?
                        "(NA)" :
                        Math.round(protectedSystemPartnerSystemCandidate.evaluationTrustExpressionSatisfied /
                            (protectedSystemPartnerSystemCandidate.evaluationTrustExpressionUnsatisfied + protectedSystemPartnerSystemCandidate.evaluationTrustExpressionSatisfied) *
                            100) + "%"
                partnerSystemCandidateElementPercentTrustInteroperabilityProfile.title =
                    protectedSystemPartnerSystemCandidate.evaluationTrustExpressionUnsatisfied + protectedSystemPartnerSystemCandidate.evaluationTrustExpressionSatisfied === 0 ?
                        "(NA)" :
                        `${protectedSystemPartnerSystemCandidate.evaluationTrustExpressionSatisfied} of ${(protectedSystemPartnerSystemCandidate.evaluationTrustExpressionUnsatisfied + protectedSystemPartnerSystemCandidate.evaluationTrustExpressionSatisfied)}`

                partnerSystemCandidateTBody.appendChild(partnerSystemCandidateElement)
            })
        }
    }

    function onInsertOpen(protectedSystem) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-cancel").outerHTML = document.getElementById("trust-interoperability-profile-action-cancel").outerHTML
        document.getElementById("trust-interoperability-profile-action-submit-insert").outerHTML = document.getElementById("trust-interoperability-profile-action-submit-insert").outerHTML;

        document.getElementById("trust-interoperability-profile-action-cancel").addEventListener("click", onCancel)
        document.getElementById("trust-interoperability-profile-action-submit-insert").addEventListener("click", () => onInsertSubmit(protectedSystem))

        document.getElementById("trust-interoperability-profile-form").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-form-header-insert").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-action-submit-insert").classList.remove("d-none")
    }

    function onUpdateOpen(protectedSystem, protectedSystemTrustInteroperabilityProfile) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-cancel").outerHTML = document.getElementById("trust-interoperability-profile-action-cancel").outerHTML
        document.getElementById("trust-interoperability-profile-action-submit-update").outerHTML = document.getElementById("trust-interoperability-profile-action-submit-update").outerHTML;

        document.getElementById("trust-interoperability-profile-action-cancel").addEventListener("click", onCancel)
        document.getElementById("trust-interoperability-profile-action-submit-update").addEventListener("click", () => onUpdateSubmit(protectedSystem, protectedSystemTrustInteroperabilityProfile))

        document.getElementById("trust-interoperability-profile-form").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-form-header-update").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-action-submit-update").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-status").classList.remove("d-none")

        document.getElementById("trust-interoperability-profile-input-uri").value = protectedSystemTrustInteroperabilityProfile.uri
        document.getElementById("trust-interoperability-profile-input-mandatory").checked = protectedSystemTrustInteroperabilityProfile.mandatory
        document.getElementById("trust-interoperability-profile-input-name").value = protectedSystemTrustInteroperabilityProfile.name
        document.getElementById("trust-interoperability-profile-input-description").value = protectedSystemTrustInteroperabilityProfile.description
        document.getElementById("trust-interoperability-profile-input-issuer").value = protectedSystemTrustInteroperabilityProfile.issuerName
        document.getElementById("trust-interoperability-profile-input-issuer-identifier").value = protectedSystemTrustInteroperabilityProfile.issuerIdentifier
        document.getElementById("trust-interoperability-profile-input-request-date-time").value = protectedSystemTrustInteroperabilityProfile.requestLocalDateTime
        document.getElementById("trust-interoperability-profile-input-success-date-time").value = protectedSystemTrustInteroperabilityProfile.successLocalDateTime
        document.getElementById("trust-interoperability-profile-input-failure-date-time").value = protectedSystemTrustInteroperabilityProfile.failureLocalDateTime
        document.getElementById("trust-interoperability-profile-input-failure-message").value = protectedSystemTrustInteroperabilityProfile.failureMessage
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit(protectedSystem) {

        fetchPost(protectedSystemUpdateUrl, {
            id: protectedSystem.id,
            name: protectedSystem.name,
            type: protectedSystem.type.value,
            organization: protectedSystem.organization.id,
            protectedSystemTrustInteroperabilityProfileList: protectedSystem.protectedSystemTrustInteroperabilityProfileList.concat(
                [{
                    "mandatory": document.getElementById("trust-interoperability-profile-input-mandatory").checked,
                    "uri": document.getElementById("trust-interoperability-profile-input-uri").value
                }]),
            partnerSystemCandidateList: protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.trust)
                .map(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmit(protectedSystem, protectedSystemTrustInteroperabilityProfile) {

        fetchPost(protectedSystemUpdateUrl, {
            id: protectedSystem.id,
            name: protectedSystem.name,
            type: protectedSystem.type.value,
            organization: protectedSystem.organization.id,
            protectedSystemTrustInteroperabilityProfileList:
                protectedSystem.protectedSystemTrustInteroperabilityProfileList
                    .filter(protectedSystemTrustInteroperabilityProfileInner => protectedSystemTrustInteroperabilityProfile.uri !== protectedSystemTrustInteroperabilityProfileInner.uri)
                    .concat(
                        [{
                            "mandatory": document.getElementById("trust-interoperability-profile-input-mandatory").checked,
                            "uri": document.getElementById("trust-interoperability-profile-input-uri").value
                        }]),
            partnerSystemCandidateList: protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.trust)
                .map(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onDeleteSubmit(protectedSystem) {

        fetchPost(protectedSystemUpdateUrl, {
            id: protectedSystem.id,
            name: protectedSystem.name,
            type: protectedSystem.type.value,
            organization: protectedSystem.organization.id,
            protectedSystemTrustInteroperabilityProfileList:
                protectedSystem.protectedSystemTrustInteroperabilityProfileList
                    .filter(protectedSystemTrustInteroperabilityProfileInner => !Array.from(document.querySelectorAll(".trust-interoperability-profile-action-delete-queue:checked")).map(element => element.dataset.uri).includes(protectedSystemTrustInteroperabilityProfileInner.uri)),
            partnerSystemCandidateList: protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.trust)
                .map(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmitForPartnerSystemCandidateRemove(protectedSystem, id) {

        fetchPost(protectedSystemUpdateUrl, {
            id: protectedSystem.id,
            name: protectedSystem.name,
            type: protectedSystem.type.value,
            organization: protectedSystem.organization.id,
            protectedSystemTrustInteroperabilityProfileList: protectedSystem.protectedSystemTrustInteroperabilityProfileList,
            partnerSystemCandidateList: protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.trust)
                .map(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id).filter(idInner => idInner !== id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmitForPartnerSystemCandidateAdd(protectedSystem, id) {

        fetchPost(protectedSystemUpdateUrl, {
            id: protectedSystem.id,
            name: protectedSystem.name,
            type: protectedSystem.type.value,
            organization: protectedSystem.organization.id,
            protectedSystemTrustInteroperabilityProfileList: protectedSystem.protectedSystemTrustInteroperabilityProfileList,
            partnerSystemCandidateList: protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.trust)
                .map(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id).concat([id])
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onFailure(failureMapPromise) {

        formResetValidation()

        const labelFor = {
            "protectedSystemTrustInteroperabilityProfileList": "URL",
            "uri": "URL"
        }

        failureMapPromise.then(failureMap => {
            const messageMapResponse = messageMap(failureMap, field => labelFor[field])["protectedSystemTrustInteroperabilityProfileList"][0];

            document.getElementById("trust-interoperability-profile-input-uri").classList.add("is-invalid")

            if (typeof messageMapResponse === 'string' || messageMapResponse instanceof String) {
                document.getElementById("trust-interoperability-profile-invalid-feedback-uri").innerHTML = messageMapResponse
            } else {
                Object.entries(messageMapResponse).forEach(entry => {
                    if (entry[1]["uri"] !== undefined) {
                        document.getElementById("trust-interoperability-profile-invalid-feedback-uri").innerHTML = entry[1]["uri"]
                    }
                })
            }
        })
    }

    function stateReset() {

        document.getElementById("trust-interoperability-profile-form").classList.add("d-none")
        document.getElementById("trust-interoperability-profile-form-header-insert").classList.add("d-none")
        document.getElementById("trust-interoperability-profile-form-header-update").classList.add("d-none")
        document.getElementById("trust-interoperability-profile-action-submit-insert").classList.add("d-none")
        document.getElementById("trust-interoperability-profile-action-submit-update").classList.add("d-none")
        document.getElementById("trust-interoperability-profile-status").classList.add("d-none")

        formResetValue()
    }

    function formResetValue() {

        document.getElementById("trust-interoperability-profile-input-uri").value = ""
        document.getElementById("trust-interoperability-profile-input-mandatory").checked = false
        document.getElementById("trust-interoperability-profile-input-name").value = ""
        document.getElementById("trust-interoperability-profile-input-description").value = ""
        document.getElementById("trust-interoperability-profile-input-issuer").value = ""
        document.getElementById("trust-interoperability-profile-input-issuer-identifier").value = ""
        document.getElementById("trust-interoperability-profile-input-request-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-success-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-failure-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-failure-message").value = ""

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("trust-interoperability-profile-input-uri").classList.remove("is-invalid")
        document.getElementById("trust-interoperability-profile-invalid-feedback-uri").innerHTML = ""

        document.getElementById("trust-interoperability-profile-input-mandatory").classList.remove("is-invalid")
        document.getElementById("trust-interoperability-profile-invalid-feedback-mandatory").innerHTML = ""
    }
}
