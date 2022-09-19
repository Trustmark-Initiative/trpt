function initialize(
    entityPartnerCandidateFindOneUrl,
    entityFindOneUrl,
    entityUpdateUrl) {

    initializeHelper(
        entityPartnerCandidateFindOneUrl,
        entityFindOneUrl,
        entityUpdateUrl,
        (entityPartnerCandidate, entity) => {
        },
        (entityPost, entity) => {
            entityPost.uri = entity.uri
            entityPost.description = entity.description
        })
}
