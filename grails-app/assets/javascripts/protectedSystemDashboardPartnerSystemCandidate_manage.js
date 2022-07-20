function initialize(
    protectedSystemPartnerSystemCandidateFindOneUrl,
    protectedSystemFindOneUrl,
    protectedSystemUpdateUrl) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function onComplete() {

        findOne()
    }

    function findOne() {
        fetchGet(protectedSystemPartnerSystemCandidateFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id"), "partnerSystemCandidate": (new URLSearchParams(document.location.search)).get("partnerSystemCandidate")}))
            .then(response => response.json())
            .then(protectedSystemPartnerSystemCandidate => fetchGet(protectedSystemFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id")}))
                .then(response => response.json())
                .then(protectedSystem => afterFindOne(protectedSystemPartnerSystemCandidate, protectedSystem)))
    }

    function afterFindOne(protectedSystemPartnerSystemCandidate, protectedSystem) {

        stateReset()

        Array.from(document.querySelectorAll(".protected-system-element-name"))
            .map(element => element.innerHTML = protectedSystemPartnerSystemCandidate.name)

        Array.from(document.querySelectorAll(".protected-system-element-organization-name"))
            .map(element => element.innerHTML = protectedSystemPartnerSystemCandidate.organization.name)

        Array.from(document.querySelectorAll(".protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-name"))
            .map(element => element.innerHTML = protectedSystemPartnerSystemCandidate.protectedSystemPartnerSystemCandidateList[0].partnerSystemCandidate.name)

        if (protectedSystemPartnerSystemCandidate.protectedSystemPartnerSystemCandidateList.length === 0 || protectedSystemPartnerSystemCandidate.protectedSystemPartnerSystemCandidateList[0].partnerSystemCandidateTrustInteroperabilityProfileList.length === 0) {

        } else {

            protectedSystemPartnerSystemCandidate.protectedSystemPartnerSystemCandidateList[0].partnerSystemCandidateTrustInteroperabilityProfileList
                .forEach((partnerSystemCandidateTrustInteroperabilityProfile, partnerSystemCandidateTrustInteroperabilityProfileIndex) =>
                    partnerSystemCandidateTrustInteroperabilityProfile.evaluationList
                        .filter(evaluation => evaluation.evaluationLocalDateTime != null)
                        .sort((evaluation1, evaluation2) => moment(evaluation2.evaluationLocalDateTime).toDate() - moment(evaluation1.evaluationLocalDateTime).toDate())
                        .forEach((evaluation, evaluationIndex) => {

                            const partnerSystemCandidateTrustInteroperabilityProfileElement = document.getElementById("protected-system-template-protected-system-partner-system-candidate-partner-system-candidate-trust-interoperability-profile").content.firstElementChild.cloneNode(true)
                            partnerSystemCandidateTrustInteroperabilityProfileElement.id = `id-${partnerSystemCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}`
                            if(evaluationIndex !== 0) partnerSystemCandidateTrustInteroperabilityProfileElement.classList.add("d-none")

                            const body = partnerSystemCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionContainer .Body")

                            if (evaluation.evaluation) {

                                body.innerHTML = trustExpressionAll(
                                    "",
                                    evaluation.evaluation["TrustExpression"],
                                    evaluation.evaluationLocalDateTime ? moment(evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC') : "",
                                    generator(`id-${partnerSystemCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}-`));

                                if (evaluation.evaluation["TrustExpressionEvaluatorFailureList"].length === 0) {

                                    partnerSystemCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer").parentNode.removeChild(partnerSystemCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer"))
                                } else {
                                    const trustExpressionEvaluatorFailureElement = partnerSystemCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailure")

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
                                partnerSystemCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer").parentNode.removeChild(partnerSystemCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer"))

                                body.innerHTML = `<div class="TrustExpressionTop INCOMPLETE">` +
                                    `<label class="TrustInteroperabilityProfileInner">` +
                                    `<span class="glyphicon bi-list-ul"></span> ${partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name ? partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name : partnerSystemCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.uri}` +
                                    `<span class="EvaluationLocalDateTime">(not yet evaluated)</span>` +
                                    `</label>` +
                                    `</div>`;
                            }

                            document.getElementById("protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-trust-interoperability-profile-list").appendChild(partnerSystemCandidateTrustInteroperabilityProfileElement)

                            const label = partnerSystemCandidateTrustInteroperabilityProfileElement.querySelector(".EvaluationLocalDateTime")
                            const select = document.createElement("select")
                            select.classList.add("EvaluationLocalDateTime")
                            select.classList.add("form-select")
                            select.style.width = "300px"
                            select.style.paddingTop = "0px"
                            select.style.paddingBottom = "0px"
                            select.innerHTML = partnerSystemCandidateTrustInteroperabilityProfile.evaluationList
                                .filter(evaluation => evaluation.evaluationLocalDateTime != null)
                                .sort((evaluation1, evaluation2) => moment(evaluation2.evaluationLocalDateTime).toDate() - moment(evaluation1.evaluationLocalDateTime).toDate())
                                .map((evaluation, evaluationIndex) => `<option value="id-${partnerSystemCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}">${evaluation.evaluationLocalDateTime ? moment(evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC') : ""}</option>`)
                                .join("")
                            select.value = `id-${partnerSystemCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}`
                            select.addEventListener("change", function(event) {
                                partnerSystemCandidateTrustInteroperabilityProfileElement.classList.add("d-none")
                                document.getElementById(select.value).classList.remove("d-none")
                                document.getElementById(select.value).querySelector(".EvaluationLocalDateTime").value = select.value
                            });

                            label.parentNode.replaceChild(select, label)
                        }))
        }

        if (protectedSystemPartnerSystemCandidate.protectedSystemPartnerSystemCandidateList[0].partnerSystemCandidate.uriEntityDescriptor === null) {
            document.getElementById("protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-uri-entity-descriptor").parentNode.removeChild(document.getElementById("protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-uri-entity-descriptor"))
        } else {
            document.getElementById("protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-uri-entity-descriptor").href = protectedSystemPartnerSystemCandidate.protectedSystemPartnerSystemCandidateList[0].partnerSystemCandidate.uriEntityDescriptor;
        }


        if (protectedSystem.type.value == "CERTIFICATE_RELYING_PARTY") {
            Array.from(document.querySelectorAll(".protected-system-element-type-certificate-relying-party")).map(element => element.classList.remove("d-none"))
        } else {
            Array.from(document.querySelectorAll(".protected-system-element-type-other")).map(element => element.classList.remove("d-none"))
        }

        if (protectedSystem.protectedSystemPartnerSystemCandidateList.length === 0) {

        } else {

            protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id === parseInt((new URLSearchParams(document.location.search)).get("partnerSystemCandidate")))
                .forEach(protectedSystemPartnerSystemCandidate => {

                    document.querySelector(".partner-system-candidate-element-trust").outerHTML = document.querySelector(".partner-system-candidate-element-trust").outerHTML
                    const partnerSystemCandidateElementTrust = document.querySelector(".partner-system-candidate-element-trust")

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
                })
        }
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

        formResetValue()
    }

    function formResetValue() {

        formResetValidation()
    }

    function formResetValidation() {
    }
}
