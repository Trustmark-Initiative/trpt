function initializeHelper(
    profileFindOneUrl,
    entityPartnerCandidateFindOneUrl,
    entityFindOneUrl,
    entityUpdateUrl,
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

        profile(profileFindOneUrl)
            .then(role => role === undefined ? Promise.resolve() : fetchGet(entityPartnerCandidateFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id"), "partnerCandidate": (new URLSearchParams(document.location.search)).get("partnerCandidate")}))
                .then(response => response.json())
                .then(entityPartnerCandidate => fetchGet(entityFindOneUrl + "?" + new URLSearchParams({"id": (new URLSearchParams(document.location.search)).get("id")}))
                    .then(response => response.json())
                    .then(entity => afterFindOne(entityPartnerCandidate, entity))))
    }

    function afterFindOne(entityPartnerCandidate, entity) {

        stateReset()

        document.getElementById("TrustInteroperabilityProfileContainerList").innerHTML = ""

        Array.from(document.querySelectorAll(".entity-element-name"))
            .map(element => element.innerHTML = entityPartnerCandidate.name)

        Array.from(document.querySelectorAll(".entity-element-organization-name"))
            .map(element => element.innerHTML = entityPartnerCandidate.organization.name)

        Array.from(document.querySelectorAll(".entity-element-partner-candidate-name"))
            .map(element => element.innerHTML = entityPartnerCandidate.entityPartnerCandidateList[0].partnerCandidate.name)

        if (entityPartnerCandidate.entityPartnerCandidateList.length === 0 || entityPartnerCandidate.entityPartnerCandidateList[0].partnerCandidateTrustInteroperabilityProfileList.length === 0) {
            //
        } else {
            entityPartnerCandidate.entityPartnerCandidateList[0].partnerCandidateTrustInteroperabilityProfileList
                .forEach((partnerCandidateTrustInteroperabilityProfile, partnerCandidateTrustInteroperabilityProfileIndex) => partnerCandidateTrustInteroperabilityProfile.evaluationList
                    .sort((evaluation1, evaluation2) => evaluation1.evaluationLocalDateTime == null && evaluation2.evaluationLocalDateTime == null ? 0 :
                        evaluation1.evaluationLocalDateTime == null ? 1 :
                            evaluation2.evaluationLocalDateTime == null ? -1 :
                                moment(evaluation2.evaluationLocalDateTime).toDate() - moment(evaluation1.evaluationLocalDateTime).toDate())
                    .forEach((evaluation, evaluationIndex) => {
                        const partnerCandidateTrustInteroperabilityProfileElement = document.getElementById("entity-template-partner-candidate-trust-interoperability-profile").content.firstElementChild.cloneNode(true)
                        partnerCandidateTrustInteroperabilityProfileElement.id = `id-${partnerCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}`
                        if (evaluationIndex !== 0) partnerCandidateTrustInteroperabilityProfileElement.classList.add("d-none")

                        const body = partnerCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionContainer .Body")

                        if (evaluation.evaluation) {

                            body.innerHTML = trustExpressionAll(
                                "",
                                evaluation.evaluation["TrustExpression"],
                                evaluation.evaluationLocalDateTime ? moment(evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC') : "",
                                generator(`id-${partnerCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}-`));

                            if (evaluation.evaluation["TrustExpressionEvaluatorFailureList"].length === 0) {

                                partnerCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer").parentNode.removeChild(partnerCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer"))
                            } else {
                                const trustExpressionEvaluatorFailureElement = partnerCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailure")

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
                            partnerCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer").parentNode.removeChild(partnerCandidateTrustInteroperabilityProfileElement.querySelector(".TrustExpressionEvaluatorFailureListContainer"))

                            body.innerHTML = `<div class="TrustExpressionTop INCOMPLETE">` +
                                `<label class="TrustInteroperabilityProfileInner">` +
                                `<span class="glyphicon bi-list-ul"></span> ${partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name ? partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.name : partnerCandidateTrustInteroperabilityProfile.trustInteroperabilityProfile.uri}` +
                                `<span class="EvaluationLocalDateTime">(not yet evaluated)</span>` +
                                `</label>` +
                                `</div>`;
                        }

                        document.getElementById("TrustInteroperabilityProfileContainerList").appendChild(partnerCandidateTrustInteroperabilityProfileElement)

                        const label = partnerCandidateTrustInteroperabilityProfileElement.querySelector(".EvaluationLocalDateTime")
                        const select = document.createElement("select")
                        select.classList.add("EvaluationLocalDateTime")
                        select.classList.add("form-select")
                        select.innerHTML = partnerCandidateTrustInteroperabilityProfile.evaluationList
                            .sort((evaluation1, evaluation2) => evaluation1.evaluationLocalDateTime == null && evaluation2.evaluationLocalDateTime == null ? 0 :
                                evaluation1.evaluationLocalDateTime == null ? 1 :
                                    evaluation2.evaluationLocalDateTime == null ? -1 :
                                        moment(evaluation2.evaluationLocalDateTime).toDate() - moment(evaluation1.evaluationLocalDateTime).toDate())
                            .map((evaluation, evaluationIndex) => `<option value="id-${partnerCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}">${evaluation.evaluationLocalDateTime ? moment(evaluation.evaluationLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC') : "(not yet evaluated)"}</option>`)
                            .join("")
                        select.value = `id-${partnerCandidateTrustInteroperabilityProfileIndex}-${evaluationIndex}`
                        select.addEventListener("change", function (event) {
                            partnerCandidateTrustInteroperabilityProfileElement.classList.add("d-none")
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

        if (entity.entityPartnerCandidateList.length === 0) {

        } else {

            entity.entityPartnerCandidateList
                .filter(entityPartnerCandidate => entityPartnerCandidate.partnerCandidate.id === parseInt((new URLSearchParams(document.location.search)).get("partnerCandidate")))
                .forEach(entityPartnerCandidate => {

                    document.querySelector(".partner-candidate-element-trust").outerHTML = document.querySelector(".partner-candidate-element-trust").outerHTML
                    const partnerCandidateElementTrust = document.querySelector(".partner-candidate-element-trust")

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
                })
        }

        amendFindOne(entityPartnerCandidate, entity)
    }

    function onUpdateSubmitForPartnerCandidateRemove(entity, id) {

        const entityPost = {
            id: entity.id,
            name: entity.name,
            uri: entity.uri,
            description: entity.description,
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

        formResetValue()
    }

    function formResetValue() {

        formResetValidation()
    }

    function formResetValidation() {
    }
}
