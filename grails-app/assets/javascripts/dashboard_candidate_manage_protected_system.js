function initialize(
    entityPartnerCandidateFindOneUrl,
    entityFindOneUrl,
    entityUpdateUrl) {

    initializeHelper(
        entityPartnerCandidateFindOneUrl,
        entityFindOneUrl,
        entityUpdateUrl,
        (entityPartnerCandidate, entity) => {
            if (entityPartnerCandidate.entityPartnerCandidateList[0].partnerCandidate.uriEntityDescriptor === null) {
                document.getElementById("protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-uri-entity-descriptor").parentNode.removeChild(document.getElementById("protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-uri-entity-descriptor"))
            } else {
                document.getElementById("protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-uri-entity-descriptor").href = entityPartnerCandidate.entityPartnerCandidateList[0].partnerCandidate.uriEntityDescriptor;
            }


            if (entity.type.value == "CERTIFICATE_RELYING_PARTY") {
                Array.from(document.querySelectorAll(".protected-system-element-type-certificate-relying-party")).map(element => element.classList.remove("d-none"))
            } else {
                Array.from(document.querySelectorAll(".protected-system-element-type-other")).map(element => element.classList.remove("d-none"))
            }
        },
        (entityPost, entity) => {
            entityPost.type = entity.type.value
            entityPost.organization = entity.organization.id
        })
}
