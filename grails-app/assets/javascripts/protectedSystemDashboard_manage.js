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

        if (protectedSystem.type.value == "CERTIFICATE_RELYING_PARTY") {
            Array.from(document.querySelectorAll(".protected-system-element-type-certificate-relying-party")).map(element => element.classList.remove("d-none"))
        } else {
            Array.from(document.querySelectorAll(".protected-system-element-type-other")).map(element => element.classList.remove("d-none"))
        }

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
                trustInteroperabilityProfileElementActionDeleteQueue.dataset.uri = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
                trustInteroperabilityProfileElementName.innerHTML = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.name === null ? protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.uri : protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.name;
                trustInteroperabilityProfileElementName.title = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.name === null ? protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.uri : protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.name;
                trustInteroperabilityProfileElementName.href = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
                trustInteroperabilityProfileElementDescription.innerHTML = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.description
                trustInteroperabilityProfileElementDescription.title = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.description
                trustInteroperabilityProfileElementIssuer.innerHTML = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
                trustInteroperabilityProfileElementIssuer.title = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
                trustInteroperabilityProfileElementIssuer.href = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerIdentifier

                if (protectedSystemTrustInteroperabilityProfile.mandatory) {

                    trustInteroperabilityProfileElementMandatory.classList.add("bi-exclamation-circle")
                    trustInteroperabilityProfileElementMandatory.title = "Require Full Compliance"
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
                const partnerSystemCandidateElementTrustmarkBindingRegistry = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-trustmark-binding-registry")
                const partnerSystemCandidateElementTrustFabricMetadata = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-trust-fabric-metadata")
                const partnerSystemCandidateElementPercentTrustmarkDefinitionRequirement = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-percent-trustmark-definition-requirement")
                const partnerSystemCandidateElementPercentTrustInteroperabilityProfile = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-percent-trust-interoperability-profile")
                const partnerSystemCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTime = partnerSystemCandidateElement.querySelector(".partner-system-candidate-element-trust-interoperability-profile-evaluation-local-date-time")
                const partnerSystemCandidateElementActionDetail = partnerSystemCandidateElement.querySelector(".partner-system-candidate-action-detail")

                if (!protectedSystemPartnerSystemCandidate.trustable) {
                    partnerSystemCandidateElementTrust.disabled = true
                    partnerSystemCandidateElementTrust.parentNode.title = "This candidate partner system does not satisfy a required TIP."
                }
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

                partnerSystemCandidateElementTrustmarkBindingRegistry.innerHTML = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.trustmarkBindingRegistry.name
                partnerSystemCandidateElementTrustmarkBindingRegistry.title = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.trustmarkBindingRegistry.name
                partnerSystemCandidateElementTrustmarkBindingRegistry.href = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.trustmarkBindingRegistry.uri

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

                const partnerSystemCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList = protectedSystemPartnerSystemCandidate.partnerSystemCandidateTrustInteroperabilityProfileList
                    .map(partnerSystemCandidateTrustInteroperabilityProfile => partnerSystemCandidateTrustInteroperabilityProfile.evaluationLocalDateTime)
                    .filter(evaluationLocalDateTime => evaluationLocalDateTime !== null)
                    .sort((evaluationLocalDateTime1, evaluationLocalDateTime2) => new Date(evaluationLocalDateTime1) - new Date(evaluationLocalDateTime2))

                partnerSystemCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTime.innerHTML = partnerSystemCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList.length === 0 ?
                    "(NA)" :
                    moment(partnerSystemCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList[0]).format('MMMM Do YYYY, h:mm:ss A UTC')

                partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML = ""
                partnerSystemCandidateElementPercentTrustInteroperabilityProfile.addEventListener("click", () => onToggleDetail(protectedSystemPartnerSystemCandidate.partnerSystemCandidate))

                protectedSystemPartnerSystemCandidate.partnerSystemCandidateTrustInteroperabilityProfileList.forEach(partnerSystemCandidateTrustInteroperabilityProfile => {

                    if (partnerSystemCandidateTrustInteroperabilityProfile.evaluationLocalDateTime == null) {
                        partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-question-circle" title="${partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name}"></span>`
                    } else if (partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustExpressionSatisfied === true) {
                        partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-check-circle-fill text-success" title="${partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerSystemCandidateTrustInteroperabilityProfile.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}"></span>`
                    } else if (partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustExpressionSatisfied === false) {
                        partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-x-circle-fill text-danger" title="${partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerSystemCandidateTrustInteroperabilityProfile.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}"></span>`
                    } else {
                        partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerSystemCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="" title="${partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerSystemCandidateTrustInteroperabilityProfile.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}">&#9888;️</span>`
                    }
                });

                partnerSystemCandidateTBody.appendChild(partnerSystemCandidateElement)

                protectedSystemPartnerSystemCandidate.partnerSystemCandidateTrustInteroperabilityProfileList.forEach(partnerSystemCandidateTrustInteroperabilityProfile => {

                    const partnerSystemCandidateDetailElement = document.getElementById("partner-system-candidate-template-detail").content.cloneNode(true)
                    partnerSystemCandidateDetailElement.firstElementChild.dataset.partnerSystemCandidateId = protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id

                    const partnerSystemCandidateDetailElementTrustInteroperabilityProfileName = partnerSystemCandidateDetailElement.querySelector(".trust-interoperability-profile-name")
                    const partnerSystemCandidateDetailElementTrustInteroperabilityProfileEvaluationLocalDateTime = partnerSystemCandidateDetailElement.querySelector(".trust-interoperability-profile-evaluation-local-date-time")
                    const partnerSystemCandidateDetailElementTrustInteroperabilityProfileSatisfied = partnerSystemCandidateDetailElement.querySelector(".trust-interoperability-profile-evaluation-satisfied")
                    const partnerSystemCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition = partnerSystemCandidateDetailElement.querySelector(".trust-interoperability-profile-trustmark-definition")

                    partnerSystemCandidateDetailElementTrustInteroperabilityProfileName.innerHTML = partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name == null ?
                        partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.uri :
                        partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name

                    partnerSystemCandidateDetailElementTrustInteroperabilityProfileEvaluationLocalDateTime.innerHTML = partnerSystemCandidateTrustInteroperabilityProfile.evaluationLocalDateTime === null ?
                        "(NA)" :
                        moment(partnerSystemCandidateTrustInteroperabilityProfile.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')

                    partnerSystemCandidateDetailElementTrustInteroperabilityProfileSatisfied.innerHTML = partnerSystemCandidateTrustInteroperabilityProfile.evaluationLocalDateTime === null ?
                        `<span class="bi-question-circle"></span>` :
                        partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustExpressionSatisfied === true ?
                            `<span class="bi-check-circle-fill text-success"></span>` :
                            partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustExpressionSatisfied === false ?
                                `<span class="bi-x-circle-fill text-danger"></span>` :
                                `<span class="">&#9888;️</span>`

                    partnerSystemCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition.innerHTML = partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        Math.round(partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied /
                            (partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied) *
                            100) + "%"

                    partnerSystemCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition.title = partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        `${partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied} of ${(partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerSystemCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied)}`

                    partnerSystemCandidateTBody.appendChild(partnerSystemCandidateDetailElement);
                });
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

        document.getElementById("trust-interoperability-profile-input-uri").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
        document.getElementById("trust-interoperability-profile-input-mandatory").checked = protectedSystemTrustInteroperabilityProfile.mandatory
        document.getElementById("trust-interoperability-profile-input-name").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.name
        document.getElementById("trust-interoperability-profile-input-description").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.description
        document.getElementById("trust-interoperability-profile-input-issuer").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
        document.getElementById("trust-interoperability-profile-input-issuer-identifier").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerIdentifier
        document.getElementById("trust-interoperability-profile-input-document-request-date-time").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.documentRequestLocalDateTime == null ? "" : moment(protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.documentRequestLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-success-date-time").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.documentSuccessLocalDateTime == null ? "" : moment(protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.documentSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-failure-date-time").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureLocalDateTime == null ? "" : moment(protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-failure-message").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureMessage
        document.getElementById("trust-interoperability-profile-input-server-request-date-time").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.serverRequestLocalDateTime == null ? "" : moment(protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.serverRequestLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-success-date-time").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.serverSuccessLocalDateTime == null ? "" : moment(protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.serverSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-failure-date-time").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureLocalDateTime == null ? "" : moment(protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-failure-message").value = protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureMessage
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
            protectedSystemTrustInteroperabilityProfileList: protectedSystem.protectedSystemTrustInteroperabilityProfileList
                .map(protectedSystemTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": protectedSystemTrustInteroperabilityProfileInner.mandatory,
                        "uri": protectedSystemTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                })
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

    function onUpdateSubmit(protectedSystem, protectedSystemTrustInteroperabilityProfile) {

        fetchPost(protectedSystemUpdateUrl, {
            id: protectedSystem.id,
            name: protectedSystem.name,
            type: protectedSystem.type.value,
            organization: protectedSystem.organization.id,
            protectedSystemTrustInteroperabilityProfileList:
                protectedSystem.protectedSystemTrustInteroperabilityProfileList
                    .filter(protectedSystemTrustInteroperabilityProfileInner => protectedSystemTrustInteroperabilityProfile.trustInteroperabilityProfile.uri !== protectedSystemTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri)
                    .map(protectedSystemTrustInteroperabilityProfileInner => {
                        return {
                            "mandatory": protectedSystemTrustInteroperabilityProfileInner.mandatory,
                            "uri": protectedSystemTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                        }
                    })
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
                    .map(protectedSystemTrustInteroperabilityProfileInner => {
                        return {
                            "mandatory": protectedSystemTrustInteroperabilityProfileInner.mandatory,
                            "uri": protectedSystemTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                        }
                    })
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
            protectedSystemTrustInteroperabilityProfileList: protectedSystem.protectedSystemTrustInteroperabilityProfileList
                .map(protectedSystemTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": protectedSystemTrustInteroperabilityProfileInner.mandatory,
                        "uri": protectedSystemTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                }),
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
            protectedSystemTrustInteroperabilityProfileList: protectedSystem.protectedSystemTrustInteroperabilityProfileList
                .map(protectedSystemTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": protectedSystemTrustInteroperabilityProfileInner.mandatory,
                        "uri": protectedSystemTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                }),
            partnerSystemCandidateList: protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.trust)
                .map(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id).concat([id])
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onToggleDetail(partnerSystemCandidate) {

        Array.from(document.querySelectorAll(`.partner-system-candidate-detail[data-partner-system-candidate-id="${partnerSystemCandidate.id}"]`))
            .forEach(element => element.classList.contains("d-none") ? element.classList.remove("d-none") : element.classList.add("d-none"));
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
        document.getElementById("trust-interoperability-profile-input-document-request-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-document-success-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-document-failure-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-document-failure-message").value = ""
        document.getElementById("trust-interoperability-profile-input-server-request-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-server-success-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-server-failure-date-time").value = ""
        document.getElementById("trust-interoperability-profile-input-server-failure-message").value = ""

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("trust-interoperability-profile-input-uri").classList.remove("is-invalid")
        document.getElementById("trust-interoperability-profile-invalid-feedback-uri").innerHTML = ""

        document.getElementById("trust-interoperability-profile-input-mandatory").classList.remove("is-invalid")
        document.getElementById("trust-interoperability-profile-invalid-feedback-mandatory").innerHTML = ""
    }
}
