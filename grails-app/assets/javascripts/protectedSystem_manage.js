function initialize(
    profileFindOneUrl,
    entityFindAll,
    entityFindOne,
    entityInsert,
    entityUpdate,
    entityDelete,
    entityDashboard,
    organizationFindAll,
    typeFindAll) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function onComplete() {

        findAll()
    }

    function findAll() {

        profile(profileFindOneUrl)
            .then(role => role === undefined ? Promise.resolve() : fetchGet(entityFindAll)
                .then(response => response.json())
                .then(afterFindAll))
    }

    function afterFindAll(entityList) {

        stateReset()

        if (document.getElementById("entity-action-insert") != null) {
            document.getElementById("entity-action-insert").outerHTML = document.getElementById("entity-action-insert").outerHTML
            document.getElementById("entity-action-insert").addEventListener("click", beforeInsertOpen)
        }

        document.getElementById("entity-action-delete").outerHTML = document.getElementById("entity-action-delete").outerHTML
        document.getElementById("entity-action-delete").addEventListener("click", () => onDeleteSubmit(entityList))

        const entityTBody = document.getElementById("entity-tbody")
        entityTBody.innerHTML = ""

        if (entityList.length === 0) {

            const entityElement = document.getElementById("entity-template-empty").content.cloneNode(true)

            entityTBody.appendChild(entityElement)

        } else {

            entityList.forEach(entity => {

                const entityElement = document.getElementById("entity-template-summary").content.cloneNode(true)

                const entityElementActionUpdate = entityElement.querySelector(".entity-action-update")
                const entityElementActionDeleteQueue = entityElement.querySelector(".entity-action-delete-queue")
                const entityElementName = entityElement.querySelector(".entity-element-name")

                // specific to this entity
                const entityElementType = entityElement.querySelector(".protected-system-element-type")
                const entityElementOrganization = entityElement.querySelector(".protected-system-element-organization")
                const entityElementTipCount = entityElement.querySelector(".protected-system-element-tip-count")

                entityElement.firstElementChild.dataset.id = entity.id
                entityElementActionUpdate.addEventListener("click", () => onUpdateOpen(entity))
                entityElementActionDeleteQueue.dataset.id = entity.id
                entityElementName.innerHTML = entity.name
                entityElementName.title = entity.name
                entityElementName.href = entityDashboard + "?" + new URLSearchParams({"id": entity.id})

                // specific to this entity
                entityElementType.innerHTML = entity.type.label
                entityElementType.title = entity.type.label
                entityElementOrganization.innerHTML = entity.organization.name
                entityElementOrganization.title = entity.organization.name
                entityElementTipCount.innerHTML = entity.entityTrustInteroperabilityProfileList.length
                entityElementTipCount.title = entity.entityTrustInteroperabilityProfileList.length

                entityTBody.appendChild(entityElement)
            })
        }
    }

    function beforeInsertOpen() {
        const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())
        const typeListPromise = fetchGet(typeFindAll).then(response => response.json())

        Promise.all([organizationListPromise, typeListPromise]).then(array => onInsertOpen(array[0], array[1]))
    }

    function onInsertOpen(organizationList, typeList) {

        stateReset()

        document.getElementById("entity-action-cancel").outerHTML = document.getElementById("entity-action-cancel").outerHTML
        document.getElementById("entity-action-submit-insert").outerHTML = document.getElementById("entity-action-submit-insert").outerHTML

        document.getElementById("entity-action-cancel").addEventListener("click", onCancel)
        document.getElementById("entity-action-submit-insert").addEventListener("click", onInsertSubmit)

        document.getElementById("entity-form").classList.remove("d-none")
        document.getElementById("entity-form-header-insert").classList.remove("d-none")
        document.getElementById("entity-action-submit-insert").classList.remove("d-none")

        // specific to this entity
        organizationList.map(organization => {
            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name

            document.getElementById("protected-system-input-organization").add(option)
        })

        typeList.map(type => {

            const option = document.createElement("option")
            option.value = type.value
            option.innerHTML = type.label

            document.getElementById("protected-system-input-type").add(option)
        })
    }

    function onUpdateOpen(entity) {

        const entityPromise = fetchGet(entityFindOne + "?" + new URLSearchParams({"id": entity.id})).then(response => response.json())

        // specific to this entity
        const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())
        const typeListPromise = fetchGet(typeFindAll).then(response => response.json())

        Promise.all([entityPromise, organizationListPromise, typeListPromise]).then(array => afterFindOne(array[0], array[1], array[2]))
    }

    function afterFindOne(entity, organizationList, typeList) {

        stateReset()

        document.getElementById("entity-action-cancel").outerHTML = document.getElementById("entity-action-cancel").outerHTML
        document.getElementById("entity-action-submit-update").outerHTML = document.getElementById("entity-action-submit-update").outerHTML

        document.getElementById("entity-action-cancel").addEventListener("click", onCancel)
        document.getElementById("entity-action-submit-update").addEventListener("click", () => onUpdateSubmit(entity))

        document.getElementById("entity-form").classList.remove("d-none")
        document.getElementById("entity-form-header-update").classList.remove("d-none")
        document.getElementById("entity-action-submit-update").classList.remove("d-none")

        document.getElementById("protected-system-input-id").value = entity.id
        document.getElementById("protected-system-input-name").value = entity.name

        // specific to this entity
        organizationList.map(organization => {

            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name
            option.selected = organization.id === entity.organization.id

            document.getElementById("protected-system-input-organization").add(option)
        })

        typeList.map(type => {

            const option = document.createElement("option")
            option.value = type.value
            option.innerHTML = type.label
            option.selected = type.value === entity.type.value

            document.getElementById("protected-system-input-type").add(option)
        })
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit() {

        fetchPost(entityInsert, {
            name: document.getElementById("protected-system-input-name").value,
            type: document.getElementById("protected-system-input-type").value,
            organization: document.getElementById("protected-system-input-organization").value,
            entityTrustInteroperabilityProfileList: [],
            partnerCandidateList: []
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onUpdateSubmit(entity) {

        fetchPost(entityUpdate, {
            id: document.getElementById("protected-system-input-id").value,
            name: document.getElementById("protected-system-input-name").value,
            type: document.getElementById("protected-system-input-type").value,
            organization: document.getElementById("protected-system-input-organization").value,
            entityTrustInteroperabilityProfileList: entity.entityTrustInteroperabilityProfileList
                .map(entityTrustInteroperabilityProfileInner => {
                    return {
                        "mandatory": entityTrustInteroperabilityProfileInner.mandatory,
                        "uri": entityTrustInteroperabilityProfileInner.trustInteroperabilityProfile.uri
                    }
                }),
            partnerCandidateList: entity.entityPartnerCandidateList
                .filter(entityPartnerCandidate => entityPartnerCandidate.trust)
                .map(entityPartnerCandidate => entityPartnerCandidate.partnerCandidate.id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onDeleteSubmit(entityList) {

        const idList = Array.from(document.querySelectorAll(".entity-action-delete-queue:checked"))
            .map(element => element.dataset.id)

        fetchPost(entityDelete, {
            idList: idList
        })
            .then(response => response.status !== 200 ?
                onFailureForDelete(entityList, idList, response.json()) :
                findAll())
    }

    function onFailure(failureMapPromise) {

        formResetValidation()

        const labelFor = {
            "name": "name",
            "type": "type",
            "organization": "organization",
        }

        messageMapShow(failureMapPromise, "protected-system", labelFor)
    }

    function onFailureForDelete(entityList, idList, failureMapPromise) {

        stateReset();

        document.getElementById("entity-delete-response-failure").classList.remove("d-none")

        const labelFor = {
            "name": "name",
            "type": "type",
            "organization": "organization",
            "idlist": "protected systems",
            "id": "protected system",
        }

        failureMapPromise.then(failureMap => {

            const messageMapResponse = messageMap(failureMap, field => labelFor[field])

            if (messageMapResponse["idList"] !== undefined && messageMapResponse["idList"][0] !== undefined) {

                const entityDeleteResponseFailure = document.getElementById("entity-delete-response-failure")
                entityDeleteResponseFailure.innerHTML = ""

                Object.entries(messageMapResponse["idList"][0]).forEach(entry => {

                    const entityElement = document.getElementById("entity-template-delete-response-failure").content.cloneNode(true)
                    const entityElementName = entityElement.querySelector(".entity-element-name")
                    const entityElementMessage = entityElement.querySelector(".entity-element-message")

                    entityElementName.innerHTML = `Could not delete "${entityList.find(entity => entity.id == idList[entry[0]]).name}":`

                    Object.entries(entry[1]).forEach(entryInner => {

                        entryInner[1].forEach(message => {

                            const entityElementMessageInner = document.createElement("li")
                            entityElementMessageInner.innerHTML = message;

                            entityElementMessage.appendChild(entityElementMessageInner)
                        })
                    })

                    entityDeleteResponseFailure.appendChild(entityElement)
                })
            }
        })
    }

    function stateReset() {

        document.getElementById("entity-form").classList.add("d-none")
        document.getElementById("entity-form-header-insert").classList.add("d-none")
        document.getElementById("entity-form-header-update").classList.add("d-none")
        document.getElementById("entity-action-submit-insert").classList.add("d-none")
        document.getElementById("entity-action-submit-update").classList.add("d-none")
        document.getElementById("entity-delete-response-failure").classList.add("d-none")
        document.getElementById("entity-delete-response-failure").innerHTML = ""

        formResetValue()
    }

    function formResetValue() {

        document.getElementById("protected-system-input-id").value = ""
        document.getElementById("protected-system-input-name").value = ""

        // specific to this entity
        document.getElementById("protected-system-input-type").innerHTML = ""
        document.getElementById("protected-system-input-organization").innerHTML = ""

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("protected-system-input-name").classList.remove("is-invalid")
        document.getElementById("protected-system-invalid-feedback-name").innerHTML = ""

        // specific to this entity
        document.getElementById("protected-system-input-type").classList.remove("is-invalid")
        document.getElementById("protected-system-invalid-feedback-type").innerHTML = ""
        document.getElementById("protected-system-input-organization").classList.remove("is-invalid")
        document.getElementById("protected-system-invalid-feedback-organization").innerHTML = ""
    }
}
