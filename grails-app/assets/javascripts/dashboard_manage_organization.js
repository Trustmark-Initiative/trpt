function initialize(
    profileFindOneUrl,
    entityFindOneUrl,
    entityUpdateUrl,
    partnerCandidateFindAllUrl,
    partnerCandidateDashboard) {

    initializeHelper(
        profileFindOneUrl,
        entityFindOneUrl,
        entityUpdateUrl,
        partnerCandidateFindAllUrl,
        partnerCandidateDashboard,
        (entity) => {
        },
        (entityPost, entity) => {
            entityPost.uri = entity.uri
            entityPost.description = entity.description
        })
}
