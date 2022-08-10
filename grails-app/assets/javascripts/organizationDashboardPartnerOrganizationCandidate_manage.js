function initialize(
    organizationPartnerOrganizationCandidateFindOneUrl,
    organizationFindOneUrl,
    organizationUpdateUrl) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function onComplete() {

        findOne()
    }

    function findOne() {
        fetchGet(organizationPartnerOrganizationCandidateFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id"), "partnerOrganizationCandidate": (new URLSearchParams(document.location.search)).get("partnerOrganizationCandidate")}))
            .then(response => response.json())
            .then(organizationPartnerOrganizationCandidate => fetchGet(organizationFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id")}))
                .then(response => response.json())
                .then(organization => afterFindOne(organizationPartnerOrganizationCandidate, organization)))
    }

    function afterFindOne(organizationPartnerOrganizationCandidate, organization) {

        stateReset()

        Array.from(document.querySelectorAll(".organization-element-name"))
            .map(element => element.innerHTML = organizationPartnerOrganizationCandidate.name)

        Array.from(document.querySelectorAll(".organization-element-organization-name"))
            .map(element => element.innerHTML = organizationPartnerOrganizationCandidate.organization.name)

        Array.from(document.querySelectorAll(".organization-element-organization-partner-organization-candidate-partner-organization-candidate-name"))
            .map(element => element.innerHTML = organizationPartnerOrganizationCandidate.organizationPartnerOrganizationCandidateList[0].partnerOrganizationCandidate.name)

        if (organizationPartnerOrganizationCandidate.organizationPartnerOrganizationCandidateList.length === 0 || organizationPartnerOrganizationCandidate.organizationPartnerOrganizationCandidateList[0].partnerOrganizationCandidateTrustInteroperabilityProfileList.length === 0) {

        } else {

            organizationPartnerOrganizationCandidate.organizationPartnerOrganizationCandidateList[0].partnerOrganizationCandidateTrustInteroperabilityProfileList
                .forEach((partnerOrganizationCandidateTrustInteroperabilityProfile, partnerOrganizationCandidateTrustInteroperabilityProfileIndex) =>
                    partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationList
                        .filter(evaluation => evaluation.evaluationLocalDateTime != null)
                        .sort((evaluation1, evaluation2) => moment(evaluation2.evaluationLocalDateTime).toDate() - moment(evaluation1.evaluationLocalDateTime).toDate())
                        .forEach((evaluation, evaluationIndex) => {

                            const partnerOrganizationCandidateTrustInteroperabilityProfileElement = document.getElementById("organization-template-organization-partner-organization-candidate-partner-organization-candidate-trust-interoperability-profile").content.firstElementChild.cloneNode(true)
                            partnerOrganizationCandidateTrustInteroperabilityProfileElement.id = `id-${partnerOrganizationCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}`
                            if (evaluationIndex !== 0) partnerOrganizationCandidateTrustInteroperabilityProfileElement.classList.add("d-none")

                            const body = partnerOrganizationCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionContainer .Body")

                            if (evaluation.evaluation) {

                                body.innerHTML = trustExpressionAll(
                                    "",
                                    evaluation.evaluation["TrustExpression"],
                                    evaluation.evaluationLocalDateTime ? moment(evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC') : "",
                                    generator(`id-${partnerOrganizationCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}-`));

                                if (evaluation.evaluation["TrustExpressionEvaluatorFailureList"].length === 0) {

                                    partnerOrganizationCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer").parentNode.removeChild(partnerOrganizationCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer"))
                                } else {
                                    const trustExpressionEvaluatorFailureElement = partnerOrganizationCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailure")

                                    evaluation.evaluation["TrustExpressionEvaluatorFailureList"].forEach(trustExpressionEvaluatorFailure => {
                                        if (trustExpressionEvaluatorFailure["$Type"] === "TrustExpressionFailureURI") {

                                            const divMessage = document.createElement("div")
                                            const divCode = document.createElement("div")

                                            divMessage.className = "Message"
                                            divMessage.innerHTML = "The system could not parse the URI."
                                            divCode.className = "UriString code"
                                            divCode.innerHTML = trustExpressionEvaluatorFailure["$Type"]["UriString"]

                                            trustExpressionEvaluatorFailureElement.appendChild(divMessage)
                                            trustExpressionEvaluatorFailureElement.appendChild(divCode)

                                        } else if (trustExpressionEvaluatorFailure["$Type"] === "TrustExpressionEvaluatorFailureResolve") {

                                            const divMessage = document.createElement("div")
                                            const divCode = document.createElement("div")

                                            divMessage.className = "Message"
                                            divMessage.innerHTML = "The system could not resolve the URI."
                                            divCode.className = "Uri code"
                                            divCode.innerHTML = trustExpressionEvaluatorFailure["$Type"]["Uri"]

                                            trustExpressionEvaluatorFailureElement.appendChild(divMessage)
                                            trustExpressionEvaluatorFailureElement.appendChild(divCode)
                                        } else {
                                        }
                                    })
                                }
                            } else {
                                partnerOrganizationCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer").parentNode.removeChild(partnerOrganizationCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer"))

                                body.innerHTML = `<div class="TrustExpressionTop INCOMPLETE">` +
                                    `<label class="TrustInteroperabilityProfileInner">` +
                                    `<span class="glyphicon bi-list-ul"></span> ${partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name ? partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name : partnerOrganizationCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.uri}` +
                                    `<span class="EvaluationLocalDateTime">(not yet evaluated)</span>` +
                                    `</label>` +
                                    `</div>`;
                            }

                            document.getElementById("organization-element-organization-partner-organization-candidate-partner-organization-candidate-trust-interoperability-profile-list").appendChild(partnerOrganizationCandidateTrustInteroperabilityProfileElement)

                            const label = partnerOrganizationCandidateTrustInteroperabilityProfileElement.querySelector(".EvaluationLocalDateTime")
                            const select = document.createElement("select")
                            select.classList.add("EvaluationLocalDateTime")
                            select.classList.add("form-select")
                            select.innerHTML = partnerOrganizationCandidateTrustInteroperabilityProfile.evaluationList
                                .filter(evaluation => evaluation.evaluationLocalDateTime != null)
                                .sort((evaluation1, evaluation2) => moment(evaluation2.evaluationLocalDateTime).toDate() - moment(evaluation1.evaluationLocalDateTime).toDate())
                                .map((evaluation, evaluationIndex) => `<option value="id-${partnerOrganizationCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}">${evaluation.evaluationLocalDateTime ? moment(evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC') : ""}</option>`)
                                .join("")
                            select.value = `id-${partnerOrganizationCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}`
                            select.addEventListener("change", function(event) {
                                partnerOrganizationCandidateTrustInteroperabilityProfileElement.classList.add("d-none")
                                document.getElementById(select.value).classList.remove("d-none")
                                document.getElementById(select.value).querySelector(".EvaluationLocalDateTime").value = select.value
                            });

                            label.parentNode.replaceChild(select, label)

                            const collapse = document.createElement("span")
                            collapse.classList.add("bi")
                            collapse.classList.add("bi-arrows-collapse")
                            collapse.classList.add("EvaluationCollapse")
                            collapse.addEventListener("click", (event) => {
                                Array.from(event.target.parentNode.parentNode.querySelectorAll("input[type='checkbox']")).forEach(input => {
                                    input.checked = false
                                })
                                event.preventDefault()
                            });

                            const expand = document.createElement("span")
                            expand.classList.add("bi")
                            expand.classList.add("bi-arrows-expand")
                            expand.classList.add("EvaluationExpand")
                            expand.addEventListener("click", (event) => {
                                Array.from(event.target.parentNode.parentNode.querySelectorAll("input[type='checkbox']")).forEach(input => {
                                    input.checked = true
                                })
                                event.preventDefault()
                            });

                            select.parentNode.appendChild(collapse)
                            select.parentNode.appendChild(expand)
                        }))
        }

        if (organization.organizationPartnerOrganizationCandidateList.length === 0) {

        } else {

            organization.organizationPartnerOrganizationCandidateList
                .filter(organizationPartnerOrganizationCandidate => organizationPartnerOrganizationCandidate.partnerOrganizationCandidate.id === parseInt((new URLSearchParams(document.location.search)).get("partnerOrganizationCandidate")))
                .forEach(organizationPartnerOrganizationCandidate => {

                    document.querySelector(".partner-organization-candidate-element-trust").outerHTML = document.querySelector(".partner-organization-candidate-element-trust").outerHTML;
                    const partnerOrganizationCandidateElementTrust = document.querySelector(".partner-organization-candidate-element-trust")

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
                })
        }
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

        formResetValue()
    }

    function formResetValue() {

        formResetValidation()
    }

    function formResetValidation() {
    }
}
