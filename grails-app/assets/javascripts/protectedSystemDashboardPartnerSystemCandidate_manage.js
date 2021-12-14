function initialize(
    protectedSystemFindOneUrl,
    protectedSystemUpdateUrl) {

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

        if (protectedSystem.protectedSystemPartnerSystemCandidateList.length === 0) {

        } else {

            protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate =>  protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id === parseInt((new URLSearchParams(document.location.search)).get("partnerSystemCandidate")))
                .forEach(protectedSystemPartnerSystemCandidate => {

                const partnerSystemCandidateElementTrust = document.querySelector(".partner-system-candidate-element-trust")

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
