function initialize(
    entityFindOneUrl,
    entityUpdateUrl,
    partnerCandidateFindAllUrl,
    partnerCandidateDashboard) {

    initializeHelper(
        entityFindOneUrl,
        entityUpdateUrl,
        partnerCandidateFindAllUrl,
        partnerCandidateDashboard,
        (entity) => {
            Array.from(document.querySelectorAll(".protected-system-element-type"))
                .map(element => element.innerHTML = entity.type.label)

            if (entity.type.value == "CERTIFICATE_RELYING_PARTY") {
                Array.from(document.querySelectorAll(".protected-system-element-type-certificate-relying-party")).map(element => element.classList.remove("d-none"))
            } else {
                Array.from(document.querySelectorAll(".protected-system-element-type-other")).map(element => element.classList.remove("d-none"))
            }

            if (entity.entityPartnerCandidateList.length != 0) {

                Array.from(document.querySelectorAll(".partner-candidate-element-type"))
                    .map(element => element.innerHTML = entity.entityPartnerCandidateList[0].partnerCandidate.type.label)
            }

        },
        (entityPost, entity) => {
            entityPost.type = entity.type.value
            entityPost.organization = entity.organization.id
        })
}
