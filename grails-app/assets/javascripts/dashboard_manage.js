function initializeHelper(
    entityFindOneUrl,
    entityUpdateUrl,
    partnerCandidateFindAllUrl,
    partnerCandidateDashboard,
    amendFindOne,
    amendSave) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function onComplete() {

        findOne()
    }

    function findOne() {

        fetchGet(entityFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id")}))
            .then(response => response.json())
            .then(afterFindOne)
    }

    function afterFindOne(entity) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-insert").outerHTML = document.getElementById("trust-interoperability-profile-action-insert").outerHTML;
        document.getElementById("trust-interoperability-profile-action-delete").outerHTML = document.getElementById("trust-interoperability-profile-action-delete").outerHTML;

        document.getElementById("trust-interoperability-profile-action-insert").addEventListener("click", () => onInsertOpen(entity))
        document.getElementById("trust-interoperability-profile-action-delete").addEventListener("click", () => onDeleteSubmit(entity))

        Array.from(document.querySelectorAll(".entity-element-name"))
            .map(element => element.innerHTML = entity.name)

        const trustInteroperabilityProfileTBody = document.getElementById("trust-interoperability-profile-tbody")
        trustInteroperabilityProfileTBody.innerHTML = ""

        if (entity.entityTrustInteroperabilityProfileList.length === 0) {

            const trustInteroperabilityProfileElement = document.getElementById("trust-interoperability-profile-template-empty").content.cloneNode(true)

            trustInteroperabilityProfileTBody.appendChild(trustInteroperabilityProfileElement)

        } else {

            entity.entityTrustInteroperabilityProfileList.forEach(entityTrustInteroperabilityProfile => {

                const trustInteroperabilityProfileElement = document.getElementById("trust-interoperability-profile-template-summary").content.cloneNode(true)

                const trustInteroperabilityProfileElementActionUpdate = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-action-update")
                const trustInteroperabilityProfileElementActionDeleteQueue = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-action-delete-queue")
                const trustInteroperabilityProfileElementName = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-name")
                const trustInteroperabilityProfileElementUri = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-uri")
                const trustInteroperabilityProfileElementDescription = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-description")
                const trustInteroperabilityProfileElementIssuer = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-issuer")
                const trustInteroperabilityProfileElementIssuerIdentifier = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-issuer-identifier")
                const trustInteroperabilityProfileElementMandatory = trustInteroperabilityProfileElement.querySelector(".trust-interoperability-profile-element-mandatory")

                trustInteroperabilityProfileElementActionUpdate.addEventListener("click", () => onUpdateOpen(entity, entityTrustInteroperabilityProfile))
                trustInteroperabilityProfileElementActionDeleteQueue.dataset.uri = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
                trustInteroperabilityProfileElementName.innerHTML = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.name === null ? entityTrustInteroperabilityProfile.trustInteroperabilityProfile.uri : entityTrustInteroperabilityProfile.trustInteroperabilityProfile.name;
                trustInteroperabilityProfileElementName.title = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.name === null ? entityTrustInteroperabilityProfile.trustInteroperabilityProfile.uri : entityTrustInteroperabilityProfile.trustInteroperabilityProfile.name;
                trustInteroperabilityProfileElementName.href = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
                trustInteroperabilityProfileElementDescription.innerHTML = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.description
                trustInteroperabilityProfileElementDescription.title = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.description
                trustInteroperabilityProfileElementIssuer.innerHTML = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
                trustInteroperabilityProfileElementIssuer.title = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
                trustInteroperabilityProfileElementIssuer.href = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerIdentifier

                if (entityTrustInteroperabilityProfile.mandatory) {

                    trustInteroperabilityProfileElementMandatory.classList.add("bi-exclamation-circle")
                    trustInteroperabilityProfileElementMandatory.title = "Require Full Compliance"
                }

                trustInteroperabilityProfileTBody.appendChild(trustInteroperabilityProfileElement)
            })
        }

        const partnerCandidateTBody = document.getElementById("partner-candidate-tbody")
        partnerCandidateTBody.innerHTML = ""

        if (entity.entityPartnerCandidateList.length === 0) {

            const partnerCandidateElement = document.getElementById("partner-candidate-template-empty").content.cloneNode(true)

            partnerCandidateTBody.appendChild(partnerCandidateElement)

        } else {

            entity.entityPartnerCandidateList.forEach(entityPartnerCandidate => {

                const partnerCandidateElement = document.getElementById("partner-candidate-template-summary").content.cloneNode(true)

                const partnerCandidateElementTrust = partnerCandidateElement.querySelector(".partner-candidate-element-trust")
                const partnerCandidateElementName = partnerCandidateElement.querySelector(".partner-candidate-element-name")
                const partnerCandidateElementTrustmarkBindingRegistry = partnerCandidateElement.querySelector(".partner-candidate-element-trustmark-binding-registry")
                const partnerCandidateElementTrustFabricMetadata = partnerCandidateElement.querySelector(".partner-candidate-element-trust-fabric-metadata")
                const partnerCandidateElementPercentTrustmarkDefinitionRequirement = partnerCandidateElement.querySelector(".partner-candidate-element-percent-trustmark-definition-requirement")
                const partnerCandidateElementPercentTrustInteroperabilityProfile = partnerCandidateElement.querySelector(".partner-candidate-element-percent-trust-interoperability-profile")
                const partnerCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTime = partnerCandidateElement.querySelector(".partner-candidate-element-trust-interoperability-profile-evaluation-local-date-time")
                const partnerCandidateElementActionDetail = partnerCandidateElement.querySelector(".partner-candidate-action-detail")

                if (!entityPartnerCandidate.trustable) {
                    partnerCandidateElementTrust.disabled = true
                    partnerCandidateElementTrust.parentNode.title = "This candidate partner system does not satisfy a required TIP."
                }
                partnerCandidateElementTrust.checked = entityPartnerCandidate.trust
                if (partnerCandidateElementTrust.checked) {
                    partnerCandidateElementTrust.addEventListener("click", () => {

                        document.getElementById("modal-header-trust").classList.add("d-none")
                        document.getElementById("modal-body-trust").classList.add("d-none")

                        document.getElementById("modal-header-do-not-trust").classList.remove("d-none")
                        document.getElementById("modal-body-do-not-trust").classList.remove("d-none")

                        Array.from(document.querySelectorAll("#modal-trust .partner-candidate-element-trust-fabric-metadata"))
                            .forEach(element => {
                                if (entityPartnerCandidate.partnerCandidate.uriEntityDescriptor == null) {

                                    element.innerHTML = "the link"
                                    element.title = ""
                                    element.href = ""

                                } else {

                                    element.innerHTML = `${entityPartnerCandidate.partnerCandidate.uriEntityDescriptor}`
                                    element.title = entityPartnerCandidate.partnerCandidate.uriEntityDescriptor
                                    element.href = entityPartnerCandidate.partnerCandidate.uriEntityDescriptor
                                }
                            })

                        Array.from(document.querySelectorAll("#modal-trust .partner-candidate-element-name"))
                            .forEach(element => element.innerHTML = entityPartnerCandidate.partnerCandidate.name)

                        onUpdateSubmitForPartnerCandidateRemove(entity, entityPartnerCandidate.partnerCandidate.id)
                    });
                } else {
                    partnerCandidateElementTrust.addEventListener("click", () => {

                        document.getElementById("modal-header-trust").classList.remove("d-none")
                        document.getElementById("modal-body-trust").classList.remove("d-none")

                        document.getElementById("modal-header-do-not-trust").classList.add("d-none")
                        document.getElementById("modal-body-do-not-trust").classList.add("d-none")

                        Array.from(document.querySelectorAll("#modal-trust .partner-candidate-element-trust-fabric-metadata"))
                            .forEach(element => {
                                if (entityPartnerCandidate.partnerCandidate.uriEntityDescriptor == null) {

                                    element.innerHTML = "the link"
                                    element.title = ""
                                    element.href = ""

                                } else {

                                    element.innerHTML = `${entityPartnerCandidate.partnerCandidate.uriEntityDescriptor}`
                                    element.title = entityPartnerCandidate.partnerCandidate.uriEntityDescriptor
                                    element.href = entityPartnerCandidate.partnerCandidate.uriEntityDescriptor
                                }
                            })

                        Array.from(document.querySelectorAll("#modal-trust .partner-candidate-element-name"))
                            .forEach(element => element.innerHTML = entityPartnerCandidate.partnerCandidate.name)

                        onUpdateSubmitForPartnerCandidateAdd(entity, entityPartnerCandidate.partnerCandidate.id)
                    });
                }

                partnerCandidateElementName.innerHTML = entityPartnerCandidate.partnerCandidate.name
                partnerCandidateElementName.title = entityPartnerCandidate.partnerCandidate.name
                partnerCandidateElementName.href = partnerCandidateDashboard + "?" + new URLSearchParams({"id": entity.id, "partnerCandidate": entityPartnerCandidate.partnerCandidate.id})

                partnerCandidateElementTrustmarkBindingRegistry.innerHTML = entityPartnerCandidate.partnerCandidate.trustmarkBindingRegistry.name
                partnerCandidateElementTrustmarkBindingRegistry.title = entityPartnerCandidate.partnerCandidate.trustmarkBindingRegistry.name
                partnerCandidateElementTrustmarkBindingRegistry.href = entityPartnerCandidate.partnerCandidate.trustmarkBindingRegistry.uri

                if (entityPartnerCandidate.partnerCandidate.uriEntityDescriptor == null) {

                    partnerCandidateElementTrustFabricMetadata.parentNode.removeChild(partnerCandidateElementTrustFabricMetadata)
                } else {
                    partnerCandidateElementTrustFabricMetadata.innerHTML = `(${entityPartnerCandidate.partnerCandidate.uriEntityDescriptor})`
                    partnerCandidateElementTrustFabricMetadata.title = entityPartnerCandidate.partnerCandidate.uriEntityDescriptor
                    partnerCandidateElementTrustFabricMetadata.href = entityPartnerCandidate.partnerCandidate.uriEntityDescriptor
                }

                partnerCandidateElementPercentTrustmarkDefinitionRequirement.innerHTML =
                    entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        Math.round(
                            entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementSatisfied /
                            (entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementSatisfied) *
                            100) + "%"
                partnerCandidateElementPercentTrustmarkDefinitionRequirement.title =
                    entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        `${entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementSatisfied} of ${(entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementUnsatisfied + entityPartnerCandidate.evaluationTrustmarkDefinitionRequirementSatisfied)}`

                const partnerCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList = entityPartnerCandidate.partnerCandidateTrustInteroperabilityProfileList
                    .map(partnerCandidateTrustInteroperabilityProfile => partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationLocalDateTime)
                    .filter(evaluationLocalDateTime => evaluationLocalDateTime !== null)
                    .sort((evaluationLocalDateTime1, evaluationLocalDateTime2) => new Date(evaluationLocalDateTime1) - new Date(evaluationLocalDateTime2))

                partnerCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTime.innerHTML = partnerCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList.length === 0 ?
                    "(NA)" :
                    moment(partnerCandidateElementTrustInteroperabilityProfileEvaluationLocalDateTimeList[0]).format('MMMM Do YYYY, h:mm:ss A UTC')

                partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML = ""
                partnerCandidateElementPercentTrustInteroperabilityProfile.addEventListener("click", () => onToggleDetail(entityPartnerCandidate.partnerCandidate))

                entityPartnerCandidate.partnerCandidateTrustInteroperabilityProfileList.forEach(partnerCandidateTrustInteroperabilityProfile => {

                    if (partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationLocalDateTime == null) {
                        partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-question-circle" title="${partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name}"></span>`
                    } else if (partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustExpressionSatisfied === true) {
                        partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-check-circle-fill text-success" title="${partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}"></span>`
                    } else if (partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustExpressionSatisfied === false) {
                        partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="bi-x-circle-fill text-danger" title="${partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}"></span>`
                    } else {
                        partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML = partnerCandidateElementPercentTrustInteroperabilityProfile.innerHTML +
                            `<span class="" title="${partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name} - ${moment(partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')}">&#9888;️</span>`
                    }
                });

                partnerCandidateTBody.appendChild(partnerCandidateElement)

                entityPartnerCandidate.partnerCandidateTrustInteroperabilityProfileList.forEach(partnerCandidateTrustInteroperabilityProfile => {

                    const partnerCandidateDetailElement = document.getElementById("partner-candidate-template-detail").content.cloneNode(true)
                    partnerCandidateDetailElement.firstElementChild.dataset.partnerCandidateId = entityPartnerCandidate.partnerCandidate.id

                    const partnerCandidateDetailElementTrustInteroperabilityProfileName = partnerCandidateDetailElement.querySelector(".trust-interoperability-profile-name")
                    const partnerCandidateDetailElementTrustInteroperabilityProfileEvaluationLocalDateTime = partnerCandidateDetailElement.querySelector(".trust-interoperability-profile-evaluation-local-date-time")
                    const partnerCandidateDetailElementTrustInteroperabilityProfileSatisfied = partnerCandidateDetailElement.querySelector(".trust-interoperability-profile-evaluation-satisfied")
                    const partnerCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition = partnerCandidateDetailElement.querySelector(".trust-interoperability-profile-trustmark-definition")

                    partnerCandidateDetailElementTrustInteroperabilityProfileName.innerHTML = partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name == null ?
                        partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.uri :
                        partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name

                    partnerCandidateDetailElementTrustInteroperabilityProfileEvaluationLocalDateTime.innerHTML = partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationLocalDateTime === null ?
                        "(NA)" :
                        moment(partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')

                    partnerCandidateDetailElementTrustInteroperabilityProfileSatisfied.innerHTML = partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationLocalDateTime === null ?
                        `<span class="bi-question-circle"></span>` :
                        partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustExpressionSatisfied === true ?
                            `<span class="bi-check-circle-fill text-success"></span>` :
                            partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustExpressionSatisfied === false ?
                                `<span class="bi-x-circle-fill text-danger"></span>` :
                                `<span class="">&#9888;️</span>`

                    partnerCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition.innerHTML = partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        Math.round(partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementSatisfied /
                            (partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementSatisfied) *
                            100) + "%"

                    partnerCandidateDetailElementTrustInteroperabilityProfileTrustmarkDefinition.title = partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementSatisfied === 0 ?
                        "(NA)" :
                        `${partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementSatisfied} of ${(partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementUnsatisfied + partnerCandidateTrustInteroperabilityProfile.evaluation.evaluationTrustmarkDefinitionRequirementSatisfied)}`

                    partnerCandidateTBody.appendChild(partnerCandidateDetailElement);
                });
            })
        }

        amendFindOne(entity)
    }

    function onInsertOpen(entity) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-cancel").outerHTML = document.getElementById("trust-interoperability-profile-action-cancel").outerHTML
        document.getElementById("trust-interoperability-profile-action-submit-insert").outerHTML = document.getElementById("trust-interoperability-profile-action-submit-insert").outerHTML;

        document.getElementById("trust-interoperability-profile-action-cancel").addEventListener("click", onCancel)
        document.getElementById("trust-interoperability-profile-action-submit-insert").addEventListener("click", () => onInsertSubmit(entity))

        document.getElementById("trust-interoperability-profile-form").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-form-header-insert").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-action-submit-insert").classList.remove("d-none")
    }

    function onUpdateOpen(entity, entityTrustInteroperabilityProfile) {

        stateReset()

        document.getElementById("trust-interoperability-profile-action-cancel").outerHTML = document.getElementById("trust-interoperability-profile-action-cancel").outerHTML
        document.getElementById("trust-interoperability-profile-action-submit-update").outerHTML = document.getElementById("trust-interoperability-profile-action-submit-update").outerHTML;

        document.getElementById("trust-interoperability-profile-action-cancel").addEventListener("click", onCancel)
        document.getElementById("trust-interoperability-profile-action-submit-update").addEventListener("click", () => onUpdateSubmit(entity, entityTrustInteroperabilityProfile))

        document.getElementById("trust-interoperability-profile-form").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-form-header-update").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-action-submit-update").classList.remove("d-none")
        document.getElementById("trust-interoperability-profile-status").classList.remove("d-none")

        document.getElementById("trust-interoperability-profile-input-uri").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.uri
        document.getElementById("trust-interoperability-profile-input-mandatory").checked = entityTrustInteroperabilityProfile.mandatory
        document.getElementById("trust-interoperability-profile-input-name").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.name
        document.getElementById("trust-interoperability-profile-input-description").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.description
        document.getElementById("trust-interoperability-profile-input-issuer").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerName
        document.getElementById("trust-interoperability-profile-input-issuer-identifier").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.issuerIdentifier
        document.getElementById("trust-interoperability-profile-input-document-request-date-time").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.documentRequestLocalDateTime == null ? "" : moment(entityTrustInteroperabilityProfile.trustInteroperabilityProfile.documentRequestLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-success-date-time").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.documentSuccessLocalDateTime == null ? "" : moment(entityTrustInteroperabilityProfile.trustInteroperabilityProfile.documentSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-failure-date-time").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureLocalDateTime == null ? "" : moment(entityTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-document-failure-message").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.documentFailureMessage
        document.getElementById("trust-interoperability-profile-input-server-request-date-time").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.serverRequestLocalDateTime == null ? "" : moment(entityTrustInteroperabilityProfile.trustInteroperabilityProfile.serverRequestLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-success-date-time").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.serverSuccessLocalDateTime == null ? "" : moment(entityTrustInteroperabilityProfile.trustInteroperabilityProfile.serverSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-failure-date-time").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureLocalDateTime == null ? "" : moment(entityTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trust-interoperability-profile-input-server-failure-message").value = entityTrustInteroperabilityProfile.trustInteroperabilityProfile.serverFailureMessage
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit(entity) {

        const entityPost = {
            id: entity.id,
            name: entity.name,
            entityTrustInteroperabilityProfileList: entity.entityTrustInteroperabilityProfileList
                .map(entityTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": entityTrustInteroperabilityProfileInner.mandatory,
                        "uri": entityTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                })
                .concat(
                    [{
                        "mandatory": document.getElementById("trust-interoperability-profile-input-mandatory").checked,
                        "uri": document.getElementById("trust-interoperability-profile-input-uri").value
                    }]),
            partnerCandidateList: entity.entityPartnerCandidateList
                .filter(entityPartnerCandidate => entityPartnerCandidate.trust)
                .map(entityPartnerCandidate => entityPartnerCandidate.partnerCandidate.id)
        }

        amendSave(entityPost, entity)

        fetchPost(entityUpdateUrl, entityPost)
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmit(entity, entityTrustInteroperabilityProfile) {

        const entityPost = {
            id: entity.id,
            name: entity.name,
            entityTrustInteroperabilityProfileList:
                entity.entityTrustInteroperabilityProfileList
                    .filter(entityTrustInteroperabilityProfileInner => entityTrustInteroperabilityProfile.trustInteroperabilityProfile.uri !== entityTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri)
                    .map(entityTrustInteroperabilityProfileInner => {
                        return {
                            "mandatory": entityTrustInteroperabilityProfileInner.mandatory,
                            "uri": entityTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                        }
                    })
                    .concat(
                        [{
                            "mandatory": document.getElementById("trust-interoperability-profile-input-mandatory").checked,
                            "uri": document.getElementById("trust-interoperability-profile-input-uri").value
                        }]),
            partnerCandidateList: entity.entityPartnerCandidateList
                .filter(entityPartnerCandidate => entityPartnerCandidate.trust)
                .map(entityPartnerCandidate => entityPartnerCandidate.partnerCandidate.id)
        }

        amendSave(entityPost, entity)

        fetchPost(entityUpdateUrl, entityPost)
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onDeleteSubmit(entity) {

        const entityPost = {
            id: entity.id,
            name: entity.name,
            entityTrustInteroperabilityProfileList:
                entity.entityTrustInteroperabilityProfileList
                    .map(entityTrustInteroperabilityProfileInner => {
                        return {
                            "mandatory": entityTrustInteroperabilityProfileInner.mandatory,
                            "uri": entityTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                        }
                    })
                    .filter(entityTrustInteroperabilityProfileInner => !Array.from(document.querySelectorAll(".trust-interoperability-profile-action-delete-queue:checked")).map(element => element.dataset.uri).includes(entityTrustInteroperabilityProfileInner.uri)),
            partnerCandidateList: entity.entityPartnerCandidateList
                .filter(entityPartnerCandidate => entityPartnerCandidate.trust)
                .map(entityPartnerCandidate => entityPartnerCandidate.partnerCandidate.id)
        }

        amendSave(entityPost, entity)

        fetchPost(entityUpdateUrl, entityPost)
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmitForPartnerCandidateRemove(entity, id) {

        const entityPost = {
            id: entity.id,
            name: entity.name,
            entityTrustInteroperabilityProfileList: entity.entityTrustInteroperabilityProfileList
                .map(entityTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": entityTrustInteroperabilityProfileInner.mandatory,
                        "uri": entityTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                }),
            partnerCandidateList: entity.entityPartnerCandidateList
                .filter(entityPartnerCandidate => entityPartnerCandidate.trust)
                .map(entityPartnerCandidate => entityPartnerCandidate.partnerCandidate.id).filter(idInner => idInner !== id)
        }

        amendSave(entityPost, entity)

        fetchPost(entityUpdateUrl, entityPost)
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onUpdateSubmitForPartnerCandidateAdd(entity, id) {

        const entityPost = {
            id: entity.id,
            name: entity.name,
            entityTrustInteroperabilityProfileList: entity.entityTrustInteroperabilityProfileList
                .map(entityTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": entityTrustInteroperabilityProfileInner.mandatory,
                        "uri": entityTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                }),
            partnerCandidateList: entity.entityPartnerCandidateList
                .filter(entityPartnerCandidate => entityPartnerCandidate.trust)
                .map(entityPartnerCandidate => entityPartnerCandidate.partnerCandidate.id).concat([id])
        }

        amendSave(entityPost, entity)

        fetchPost(entityUpdateUrl, entityPost)
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findOne())
    }

    function onToggleDetail(partnerCandidate) {

        Array.from(document.querySelectorAll(`.partner-candidate-detail[data-partner-candidate-id="${partnerCandidate.id}"]`))
            .forEach(element => element.classList.contains("d-none") ? element.classList.remove("d-none") : element.classList.add("d-none"));
    }

    function onFailure(failureMapPromise) {

        formResetValidation()

        const labelFor = {
            "entityTrustInteroperabilityProfileList": "URL",
            "uri": "URL"
        }

        failureMapPromise.then(failureMap => {
            const messageMapResponse = messageMap(failureMap, field => labelFor[field])["entityTrustInteroperabilityProfileList"][0];

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
