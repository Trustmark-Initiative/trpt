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
        },
        (entityPost, entity) => {
            entityPost.uri = entity.uri
            entityPost.description = entity.description
        })
}
