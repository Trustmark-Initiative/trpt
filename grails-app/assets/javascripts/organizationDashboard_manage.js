function initialize(
    organizationFindOneUrl,
    organizationUpdateUrl,
    partnerOrganizationCandidateFindAllUrl,
    partnerOrganizationCandidateDashboard) {

    document.onreadystatechange = function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    }

    function onComplete() {

        findOne()
    }

    function findOne() {

        fetchGet(organizationFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id")}))
            .then(response => response.json())
            .then(afterFindOne)
    }

    function afterFindOne(organization) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-insert").outerHTML = document.getElementById("trust-interoperability-profile-action-insert").outerHTML;
        document.getElementById("trust-interoperability-profile-action-delete").outerHTML = document.getElementById("trust-interoperability-profile-action-delete").outerHTML;

        document.getElementById("trust-interoperability-profile-action-insert").addEventListener("click", () => onInsertOpen(organization))
        document.getElementById("trust-interoperability-profile-action-delete").addEventListener("click", () => onDeleteSubmit(organization))

        Array.from(document.querySelectorAll(".organization-element-name"))
            .map(element => element.innerHTML = organization.name)

        const trustInteroperabilityProfileTBody = document.getElementById("trust-interoperability-profile-tbody")
        trustInteroperabilityProfileTBody.innerHTML = ""

        if (organization.organizationTrustInteroperabilityProfileList.length === 0) {

            const trustInteroperabilityProfileElement = document.getElementById("trust-interoperability-profile-template-empty").content.cloneNode(true)

            trustInteroperabilityProfileTBody.appendChild(trustInteroperabilityProfileElement)

        } else {

            organization.organizationTrustInteroperabilityProfileList.forEach(organizationTrustInteroperabilityProfile => {

                const trustInteroperabilityProfileElement = document.getElementById("trust-interoperability-profile-template-summary").content.cloneNode(true)

                const trustInteroperabilityProfileElementActionUpdate = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-action-update")
                const trustInteroperabilityProfileElementActionDeleteQueue = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-action-delete-queue")
                const trustInteroperabilityProfileElementName = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-name")
                const trustInteroperabilityProfileElementUri = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-uri")
                const trustInteroperabilityProfileElementDescription = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-description")
                const trustInteroperabilityProfileElementIssuer = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-issuer")
                const trustInteroperabilityProfileElementIssuerIdentifier = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-issuer-identifier")
                const trustInteroperabilityProfileElementMandatory = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-mandatory")

                trustInteroperabilityProfileElementActionUpdate.addEventListener("click", () => onUpdateOpen(organization, organizationTrustInteroperabilityProfile))
                trustInteroperabilityProfileElementActionDeleteQueue.dataset.uri = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
                trustInteroperabilityProfileElementName.innerHTML = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.name === null ? organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.uri : organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.name;
                trustInteroperabilityProfileElementName.title = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.name === null ? organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.uri : organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.name;
                trustInteroperabilityProfileElementName.href = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
                trustInteroperabilityProfileElementDescription.innerHTML = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.description
                trustInteroperabilityProfileElementDescription.title = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.description
                trustInteroperabilityProfileElementIssuer.innerHTML = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
                trustInteroperabilityProfileElementIssuer.title = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
                trustInteroperabilityProfileElementIssuer.href = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerIdentifier

                if (organizationTrustInteroperabilityProfile.mandatory) {

                    trustInteroperabilityProfileElementMandatory.classList.add("bi-exclamation-circle")
                    trustInteroperabilityProfileElementMandatory.title = "Require Full Compliance"
                }

                trustInteroperabilityProfileTBody.appendChild(trustInteroperabilityProfileElement)
            })
        }

        const partnerOrganizationCandidateTBody = document.getElementById("partner-organization-candidate-tbody")
        partnerOrganizationCandidateTBody.innerHTML = ""

        if (organization.organizationPartnerOrganizationCandidateList.length === 0) {

            const partnerOrganizationCandidateElement = document.getElementById("partner-organization-candidate-template-empty").content.cloneNode(true)

            partnerOrganizationCandidateTBody.appendChild(partnerOrganizationCandidateElement)

        } else {

            organization.organizationPartnerOrganizationCandidateList.forEach(organizationPartnerOrganizationCandidate => {

                const partnerOrganizationCandidateElement = document.getElementById("partner-organization-candidate-template-summary").content.cloneNode(true)

                const partnerOrganizationCandidateElementTrust = partnerOrganizationCandidateElement.querySelector(".partner-organization-candidate-element-trust")
                const partnerOrganizationCandidateElementName = partnerOrganizationCandidateElement.querySelector(".partner-organization-candidate-element-name")
                const partnerOrganizationCandidateElementTrustmarkBindingRegistry = partnerOrganizationCandidateElement.querySelector(".partner-organization-candidate-element-trustmark-binding-registry")
                const partnerOrganizationCandidateElementTrustFabricMetadata = partnerOrganizationCandidateElement.querySelector(".partner-organization-candidate-element-trust-fabric-metadata")
                const partnerOrganizationCandidateElementPercentTrustmarkDefinitionRequirement = partnerOrganizationCandidateElement.querySelector(".partner-organization-candidate-element-percent-trustmark-definition-requirement")
                const partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile = partnerOrganizationCandidateElement.querySelector(".partner-organization-candidate-element-percent-trust-interoperability-profile")
                const partnerOrganizationCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTime = partnerOrganizationCandidateElement.querySelector(".partner-organization-candidate-element-trust-interoperability-profile-evaluation-local-date-time")
                const partnerOrganizationCandidateElementActionDetail = partnerOrganizationCandidateElement.querySelector(".partner-organization-candidate-action-detail")

                if (!organizationPartnerOrganizationCandidate.trustable) {
                    partnerOrganizationCandidateElementTrust.disabled = true
                    partnerOrganizationCandidateElementTrust.parentNode.title = "This candidate partner system does not satisfy a required TIP."
                }
                partnerOrganizationCandidateElementTrust.checked = organizationPartnerOrganizationCandidate.trust
                if (partnerOrganizationCandidateElementTrust.checked) {
                    partnerOrganizationCandidateElementTrust.addEventListener("click", () => {

                        document.getElementById("modal-header-trust").classList.add("d-none")
                        document.getElementById("modal-body-trust").classList.add("d-none")

                        document.getElementById("modal-header-do-not-trust").classList.remove("d-none")
                        document.getElementById("modal-body-do-not-trust").classList.remove("d-none")

                        Array.from(document.querySelectorAll("#modal-trust .partner-organization-candidate-element-trust-fabric-metadata"))
                            .forEach(element => {
                                if (organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor == null) {

                                    element.innerHTML = "the link"
                                    element.title = ""
                                    element.href = ""

                                } else {

                                    element.innerHTML = `${organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor}`
                                    element.title = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor
                                    element.href = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor
                                }
                            })

                        Array.from(document.querySelectorAll("#modal-trust .partner-organization-candidate-element-name"))
                            .forEach(element => element.innerHTML = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.name)

                        onUpdateSubmitForPartnerOrganizationCandidateRemove(organization, organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id)
                    });
                } else {
                    partnerOrganizationCandidateElementTrust.addEventListener("click", () => {

                        document.getElementById("modal-header-trust").classList.remove("d-none")
                        document.getElementById("modal-body-trust").classList.remove("d-none")

                        document.getElementById("modal-header-do-not-trust").classList.add("d-none")
                        document.getElementById("modal-body-do-not-trust").classList.add("d-none")

                        Array.from(document.querySelectorAll("#modal-trust .partner-organization-candidate-element-trust-fabric-metadata"))
                            .forEach(element => {
                                if (organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor == null) {

                                    element.innerHTML = "the link"
                                    element.title = ""
                                    element.href = ""

                                } else {

                                    element.innerHTML = `${organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor}`
                                    element.title = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor
                                    element.href = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor
                                }
                            })

                        Array.from(document.querySelectorAll("#modal-trust .partner-organization-candidate-element-name"))
                            .forEach(element => element.innerHTML = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.name)

                        onUpdateSubmitForPartnerOrganizationCandidateAdd(organization, organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id)
                    });
                }

                partnerOrganizationCandidateElementName.innerHTML = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.name
                partnerOrganizationCandidateElementName.title = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.name
                partnerOrganizationCandidateElementName.href = partnerOrganizationCandidateDashboard + "?" + new URLSearchParams({"id": organization.id, "partnerOrganizationCandidate": organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id})

                partnerOrganizationCandidateElementTrustmarkBindingRegistry.innerHTML = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.trustmarkBindingRegistry.name
                partnerOrganizationCandidateElementTrustmarkBindingRegistry.title = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.trustmarkBindingRegistry.name
                partnerOrganizationCandidateElementTrustmarkBindingRegistry.href = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.trustmarkBindingRegistry.uri

                if (organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor == null) {

                    partnerOrganizationCandidateElementTrustFabricMetadata.parentNode.removeChild(partnerOrganizationCandidateElementTrustFabricMetadata)
                } else {
                    partnerOrganizationCandidateElementTrustFabricMetadata.innerHTML = `(${organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor})`
                    partnerOrganizationCandidateElementTrustFabricMetadata.title = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor
                    partnerOrganizationCandidateElementTrustFabricMetadata.href = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.uriEntityDescriptor
                }

                partnerOrganizationCandidateElementPercentTrustmarkDefinitionRequirement.innerHTML =
                    organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        Math.round(
                            organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementSatisfied /
                            (organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementSatisfied) *
                            100) + "%"
                partnerOrganizationCandidateElementPercentTrustmarkDefinitionRequirement.title =
                    organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        `${organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementSatisfied} of ${(organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + organizationPartnerOrganizationCandidate.evaluationTrustmarkDefinitionRequirementSatisfied)}`

                const partnerOrganizationCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList = organizationPartnerOrganizationCandidate.partnerOrganizationCandidateTrustInteroperabilityProfileList
                    .map(partnerOrganizationCandidateTrustInteroperabilityProfile => partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationLocalDateTime)
                    .filter(evaluationLocalDateTime => evaluationLocalDateTime !== null)
                    .sort((evaluationLocalDateTime1, evaluationLocalDateTime2) => new Date(evaluationLocalDateTime1) - new Date(evaluationLocalDateTime2))

                partnerOrganizationCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTime.innerHTML = partnerOrganizationCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList.length === 0 ?
                    "(NA)" :
                    moment(partnerOrganizationCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList[0]).format('MMMM Do YYYY, h:mm:ss A UTC')

                partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML = ""
                partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.addEventListener("click", () => onToggleDetail(organizationPartnerOrganizationCandidate.partnerOrganizationCandidate))

                organizationPartnerOrganizationCandidate.partnerOrganizationCandidateTrustInteroperabilityProfileList.forEach(partnerOrganizationCandidateTrustInteroperabilityProfile => {

                    if (partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationLocalDateTime == null) {
                        partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-question-circle" title="${partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name}"></span>`
                    } else if (partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustExpressionSatisfied === true) {
                        partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-check-circle-fill text-success" title="${partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}"></span>`
                    } else if (partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustExpressionSatisfied === false) {
                        partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-x-circle-fill text-danger" title="${partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}"></span>`
                    } else {
                        partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerOrganizationCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="" title="${partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}">&#9888;</span>`
                    }
                });

                partnerOrganizationCandidateTBody.appendChild(partnerOrganizationCandidateElement)

                organizationPartnerOrganizationCandidate.partnerOrganizationCandidateTrustInteroperabilityProfileList.forEach(partnerOrganizationCandidateTrustInteroperabilityProfile => {

                    const partnerOrganizationCandidateDetailElement = document.getElementById("partner-organization-candidate-template-detail").content.cloneNode(true)
                    partnerOrganizationCandidateDetailElement.firstElementChild.dataset.partnerOrganizationCandidateId = organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id

                    const partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileName = partnerOrganizationCandidateDetailElement.querySelector(".trust-interoperability-profile-name")
                    const partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileEvaluationLocalDateTime = partnerOrganizationCandidateDetailElement.querySelector(".trust-interoperability-profile-evaluation-local-date-time")
                    const partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileSatisfied = partnerOrganizationCandidateDetailElement.querySelector(".trust-interoperability-profile-evaluation-satisfied")
                    const partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition = partnerOrganizationCandidateDetailElement.querySelector(".trust-interoperability-profile-trustmark-definition")

                    partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileName.innerHTML = partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name == null ?
                        partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.uri :
                        partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name

                    partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileEvaluationLocalDateTime.innerHTML = partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationLocalDateTime === null ?
                        "(NA)" :
                        moment(partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')

                    partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileSatisfied.innerHTML = partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationLocalDateTime === null ?
                        `<span class="bi-question-circle"></span>` :
                        partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustExpressionSatisfied === true ?
                            `<span class="bi-check-circle-fill text-success"></span>` :
                            partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustExpressionSatisfied === false ?
                            `<span class="bi-x-circle-fill text-danger"></span>` :
                                `<span class="">&#9888;</span>`


                    partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition.innerHTML = partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        Math.round(partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied /
                            (partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied) *
                            100) + "%"

                    partnerOrganizationCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition.title = partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        `${partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied} of ${(partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationTrustmarkDefinitionRequirementSatisfied)}`

                    partnerOrganizationCandidateTBody.appendChild(partnerOrganizationCandidateDetailElement);
                });
            })
        }
    }

    function onInsertOpen(organization) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-cancel").outerHTML = document.getElementById("trust-interoperability-profile-action-cancel").outerHTML
        document.getElementById("trust-interoperability-profile-action-submit-insert").outerHTML = document.getElementById("trust-interoperability-profile-action-submit-insert").outerHTML;

        document.getElementById("trust-interoperability-profile-action-cancel").addEventListener("click", onCancel)
        document.getElementById("trust-interoperability-profile-action-submit-insert").addEventListener("click", () => onInsertSubmit(organization))

        document.getElementById("trust-interoperability-profile-form").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-form-header-insert").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-action-submit-insert").classList.remove("d-none")
    }

    function onUpdateOpen(organization, organizationTrustInteroperabilityProfile) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-cancel").outerHTML = document.getElementById("trust-interoperability-profile-action-cancel").outerHTML
        document.getElementById("trust-interoperability-profile-action-submit-update").outerHTML = document.getElementById("trust-interoperability-profile-action-submit-update").outerHTML;

        document.getElementById("trust-interoperability-profile-action-cancel").addEventListener("click", onCancel)
        document.getElementById("trust-interoperability-profile-action-submit-update").addEventListener("click", () => onUpdateSubmit(organization, organizationTrustInteroperabilityProfile))

        document.getElementById("trust-interoperability-profile-form").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-form-header-update").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-action-submit-update").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-status").classList.remove("d-none")

        document.getElementById("trust-interoperability-profile-input-uri").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
        document.getElementById("trust-interoperability-profile-input-mandatory").checked = organizationTrustInteroperabilityProfile.mandatory
        document.getElementById("trust-interoperability-profile-input-name").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.name
        document.getElementById("trust-interoperability-profile-input-description").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.description
        document.getElementById("trust-interoperability-profile-input-issuer").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
        document.getElementById("trust-interoperability-profile-input-issuer-identifier").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerIdentifier
        document.getElementById("trust-interoperability-profile-input-document-request-date-time").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.documentRequestLocalDateTime == null ? "" : moment(organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.documentRequestLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-success-date-time").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.documentSuccessLocalDateTime == null ? "" : moment(organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.documentSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-failure-date-time").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureLocalDateTime == null ? "" : moment(organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-failure-message").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureMessage
        document.getElementById("trust-interoperability-profile-input-server-request-date-time").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.serverRequestLocalDateTime == null ? "" : moment(organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.serverRequestLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-success-date-time").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.serverSuccessLocalDateTime == null ? "" : moment(organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.serverSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-failure-date-time").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureLocalDateTime == null ? "" : moment(organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-failure-message").value = organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureMessage
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit(organization) {

        fetchPost(organizationUpdateUrl, {
            id: organization.id,
            name: organization.name,
            uri: organization.uri,
            description: organization.description,
            organizationTrustInteroperabilityProfileList: organization.organizationTrustInteroperabilityProfileList
                .map(organizationTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": organizationTrustInteroperabilityProfileInner.mandatory,
                        "uri": organizationTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                })
                .concat(
                    [{
                        "mandatory": document.getElementById("trust-interoperability-profile-input-mandatory").checked,
                        "uri": document.getElementById("trust-interoperability-profile-input-uri").value
                    }]),
            partnerOrganizationCandidateList: organization.organizationPartnerOrganizationCandidateList
                .filter(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.trust)
                .map(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmit(organization, organizationTrustInteroperabilityProfile) {

        fetchPost(organizationUpdateUrl, {
            id: organization.id,
            name: organization.name,
            uri: organization.uri,
            description: organization.description,
            organizationTrustInteroperabilityProfileList:
                organization.organizationTrustInteroperabilityProfileList
                    .filter(organizationTrustInteroperabilityProfileInner => organizationTrustInteroperabilityProfile.trustInteroperabilityProfile.uri !== organizationTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri)
                    .map(organizationTrustInteroperabilityProfileInner => {
                        return {
                            "mandatory": organizationTrustInteroperabilityProfileInner.mandatory,
                            "uri": organizationTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                        }
                    })
                    .concat(
                        [{
                            "mandatory": document.getElementById("trust-interoperability-profile-input-mandatory").checked,
                            "uri": document.getElementById("trust-interoperability-profile-input-uri").value
                        }]),
            partnerOrganizationCandidateList: organization.organizationPartnerOrganizationCandidateList
                .filter(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.trust)
                .map(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onDeleteSubmit(organization) {

        fetchPost(organizationUpdateUrl, {
            id: organization.id,
            name: organization.name,
            uri: organization.uri,
            description: organization.description,
            organizationTrustInteroperabilityProfileList:
                organization.organizationTrustInteroperabilityProfileList
                    .map(organizationTrustInteroperabilityProfileInner => {
                        return {
                            "mandatory": organizationTrustInteroperabilityProfileInner.mandatory,
                            "uri": organizationTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                        }
                    })
                    .filter(organizationTrustInteroperabilityProfileInner => !Array.from(document.querySelectorAll(".trust-interoperability-profile-action-delete-queue:checked")).map(element => element.dataset.uri).includes(organizationTrustInteroperabilityProfileInner.uri)),
            partnerOrganizationCandidateList: organization.organizationPartnerOrganizationCandidateList
                .filter(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.trust)
                .map(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmitForPartnerOrganizationCandidateRemove(organization, id) {

        fetchPost(organizationUpdateUrl, {
            id: organization.id,
            name: organization.name,
            uri: organization.uri,
            description: organization.description,
            organizationTrustInteroperabilityProfileList: organization.organizationTrustInteroperabilityProfileList
                .map(organizationTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": organizationTrustInteroperabilityProfileInner.mandatory,
                        "uri": organizationTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                }),
            partnerOrganizationCandidateList: organization.organizationPartnerOrganizationCandidateList
                .filter(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.trust)
                .map(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id).filter(idInner => idInner !== id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmitForPartnerOrganizationCandidateAdd(organization, id) {

        fetchPost(organizationUpdateUrl, {
            id: organization.id,
            name: organization.name,
            uri: organization.uri,
            description: organization.description,
            organizationTrustInteroperabilityProfileList: organization.organizationTrustInteroperabilityProfileList
                .map(organizationTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": organizationTrustInteroperabilityProfileInner.mandatory,
                        "uri": organizationTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                }),
            partnerOrganizationCandidateList: organization.organizationPartnerOrganizationCandidateList
                .filter(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.trust)
                .map(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id).concat([id])
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onToggleDetail(partnerOrganizationCandidate) {

        Array.from(document.querySelectorAll(`.partner-organization-candidate-detail[data-partner-organization-candidate-id="${partnerOrganizationCandidate.id}"]`))
            .forEach(element => element.classList.contains("d-none") ? element.classList.remove("d-none") : element.classList.add("d-none"));
    }

    function onFailure(failureMapPromise) {

        formResetValidation()

        const labelFor = {
            "organizationTrustInteroperabilityProfileList": "URL",
            "uri": "URL"
        }

        failureMapPromise.then(failureMap => {
            const messageMapResponse = messageMap(failureMap, field => labelFor[field])["organizationTrustInteroperabilityProfileList"][0];

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
