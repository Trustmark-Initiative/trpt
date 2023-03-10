function initialize(
    profileFindOneUrl,
    entityPartnerCandidateFindOneUrl,
    entityFindOneUrl,
    entityUpdateUrl) {

    initializeHelper(
        profileFindOneUrl,
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
