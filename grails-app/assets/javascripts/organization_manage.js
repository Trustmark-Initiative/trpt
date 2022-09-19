function initialize(
    entityFindAll,
    entityFindOne,
    entityInsert,
    entityUpdate,
    entityDelete,
    entityDashboard) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function onComplete() {

        findAll()
    }

    function findAll() {

        fetchGet(entityFindAll)
            .then(response => response.json())
            .then(afterFindAll)
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
                const entityElementUri = entityElement.querySelector(".organization-element-uri")
                const entityElementDescription = entityElement.querySelector(".organization-element-description")

                entityElement.firstElementChild.dataset.id = entity.id
                entityElementActionUpdate.addEventListener("click", () => onUpdateOpen(entity))
                entityElementActionDeleteQueue.dataset.id = entity.id
                entityElementName.innerHTML = entity.name
                entityElementName.title = entity.name
                entityElementName.href = entityDashboard + "?" + new URLSearchParams({"id": entity.id})

                // specific to this entity
                entityElementUri.innerHTML = entity.uri
                entityElementUri.href = entity.uri
                entityElementUri.title = entity.uri
                entityElementDescription.innerHTML = entity.description
                entityElementDescription.title = entity.description

                entityTBody.appendChild(entityElement)
            })
        }
    }

    function beforeInsertOpen() {
        onInsertOpen()
    }

    function onInsertOpen() {

        stateReset()

        document.getElementById("entity-action-cancel").outerHTML = document.getElementById("entity-action-cancel").outerHTML
        document.getElementById("entity-action-submit-insert").outerHTML = document.getElementById("entity-action-submit-insert").outerHTML

        document.getElementById("entity-action-cancel").addEventListener("click", onCancel)
        document.getElementById("entity-action-submit-insert").addEventListener("click", onInsertSubmit)

        document.getElementById("entity-form").classList.remove("d-none")
        document.getElementById("entity-form-header-insert").classList.remove("d-none")
        document.getElementById("entity-action-submit-insert").classList.remove("d-none")

        // specific to this entity
        // (nothing)
    }

    function onUpdateOpen(entity) {

        const entityPromise = fetchGet(entityFindOne + "?" + new URLSearchParams({"id": entity.id})).then(response => response.json())

        // specific to this entity
        // (nothing)

        Promise.all([entityPromise]).then(array => afterFindOne(array[0]))
    }

    function afterFindOne(entity) {

        stateReset()

        document.getElementById("entity-action-cancel").outerHTML = document.getElementById("entity-action-cancel").outerHTML
        document.getElementById("entity-action-submit-update").outerHTML = document.getElementById("entity-action-submit-update").outerHTML

        document.getElementById("entity-action-cancel").addEventListener("click", onCancel)
        document.getElementById("entity-action-submit-update").addEventListener("click", () => onUpdateSubmit(entity))

        document.getElementById("entity-form").classList.remove("d-none")
        document.getElementById("entity-form-header-update").classList.remove("d-none")
        document.getElementById("entity-action-submit-update").classList.remove("d-none")

        document.getElementById("organization-input-id").value = entity.id
        document.getElementById("organization-input-name").value = entity.name

        // specific to this entity
        document.getElementById("organization-input-uri").value = entity.uri
        document.getElementById("organization-input-description").value = entity.description
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit() {

        fetchPost(entityInsert, {
            name: document.getElementById("organization-input-name").value,
            uri: document.getElementById("organization-input-uri").value,
            description: document.getElementById("organization-input-description").value,
            entityTrustInteroperabilityProfileList: [],
            partnerCandidateList: []
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onUpdateSubmit(entity) {

        fetchPost(entityUpdate, {
            id: document.getElementById("organization-input-id").value,
            name: document.getElementById("organization-input-name").value,
            uri: document.getElementById("organization-input-uri").value,
            description: document.getElementById("organization-input-description").value,
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
            "uri": "uri",
            "description": "description",
        }

        messageMapShow(failureMapPromise, "organization", labelFor)
    }

    function onFailureForDelete(entityList, idList, failureMapPromise) {

        stateReset();

        document.getElementById("entity-delete-response-failure").classList.remove("d-none")

        const labelFor = {
            "name": "name",
            "uri": "url",
            "description": "description",
            "idList": "organizations",
            "id": "organization",
            "userSet": "users",
            "protectedSystemSet": "protected systems",
            "trustmarkBindingRegistrySet": "trustmark binding registries",
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

        document.getElementById("organization-input-id").value = ""
        document.getElementById("organization-input-name").value = ""

        // specific to this entity
        document.getElementById("organization-input-uri").value = ""
        document.getElementById("organization-input-description").value = ""

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("organization-input-name").classList.remove("is-invalid")
        document.getElementById("organization-invalid-feedback-name").innerHTML = ""

        // specific to this entity
        document.getElementById("organization-input-uri").classList.remove("is-invalid")
        document.getElementById("organization-invalid-feedback-uri").innerHTML = ""
        document.getElementById("organization-input-description").classList.remove("is-invalid")
        document.getElementById("organization-invalid-feedback-description").innerHTML = ""
    }
}
