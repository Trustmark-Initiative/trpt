function initialize(
    organizationFindOneUrl,
    organizationUpdateUrl) {

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
