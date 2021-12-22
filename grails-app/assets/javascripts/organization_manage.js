function initialize(
    organizationFindAll,
    organizationFindOne,
    organizationInsert,
    organizationUpdate,
    organizationDelete) {

    document.onreadystatechange = function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    }

    function onComplete() {

        findAll()
    }

    function findAll() {

        fetchGet(organizationFindAll)
            .then(response => response.json())
            .then(afterFindAll)
    }

    function afterFindAll(organizationList) {

        stateReset()

        if(document.getElementById("organization-action-insert") != null) {
            document.getElementById("organization-action-insert").outerHTML = document.getElementById("organization-action-insert").outerHTML
            document.getElementById("organization-action-insert").addEventListener("click", onInsertOpen)
        }

        document.getElementById("organization-action-delete").outerHTML = document.getElementById("organization-action-delete").outerHTML
        document.getElementById("organization-action-delete").addEventListener("click", () => onDeleteSubmit(organizationList))

        const organizationTBody = document.getElementById("organization-tbody")
        organizationTBody.innerHTML = ""

        if (organizationList.length === 0) {

            const organizationElement = document.getElementById("organization-template-empty").content.cloneNode(true)

            organizationTBody.appendChild(organizationElement)

        } else {

            organizationList.forEach(organization => {

                const organizationElement = document.getElementById("organization-template-summary").content.cloneNode(true)

                const organizationElementActionUpdate = organizationElement.querySelector(".organization-action-update")
                const organizationElementActionDeleteQueue = organizationElement.querySelector(".organization-action-delete-queue")
                const organizationElementName = organizationElement.querySelector(".organization-element-name")
                const organizationElementUri = organizationElement.querySelector(".organization-element-uri")
                const organizationElementDescription = organizationElement.querySelector(".organization-element-description")

                organizationElement.firstElementChild.dataset.id = organization.id
                organizationElementActionUpdate.addEventListener("click", () => onUpdateOpen(organization.id))
                organizationElementActionDeleteQueue.dataset.id = organization.id
                organizationElementName.innerHTML = organization.name
                organizationElementName.title = organization.name
                organizationElementUri.innerHTML = organization.uri
                organizationElementUri.href = organization.uri
                organizationElementUri.title = organization.uri
                organizationElementDescription.innerHTML = organization.description
                organizationElementDescription.title = organization.description

                organizationTBody.appendChild(organizationElement)
            })
        }
    }

    function onInsertOpen() {

        stateReset()

        document.getElementById("organization-action-cancel").outerHTML = document.getElementById("organization-action-cancel").outerHTML
        document.getElementById("organization-action-submit-insert").outerHTML = document.getElementById("organization-action-submit-insert").outerHTML

        document.getElementById("organization-action-cancel").addEventListener("click", onCancel)
        document.getElementById("organization-action-submit-insert").addEventListener("click", onInsertSubmit)

        document.getElementById("organization-form").classList.remove("d-none")
        document.getElementById("organization-form-header-insert").classList.remove("d-none")
        document.getElementById("organization-action-submit-insert").classList.remove("d-none")
    }

    function onUpdateOpen(organizationId) {

        fetchGet(organizationFindOne + "?" + new URLSearchParams({"id": organizationId}))
            .then(response => response.json())
            .then(afterFindOne)
    }

    function afterFindOne(organization) {

        stateReset()

        document.getElementById("organization-action-cancel").outerHTML = document.getElementById("organization-action-cancel").outerHTML
        document.getElementById("organization-action-submit-update").outerHTML = document.getElementById("organization-action-submit-update").outerHTML

        document.getElementById("organization-action-cancel").addEventListener("click", onCancel)
        document.getElementById("organization-action-submit-update").addEventListener("click", onUpdateSubmit)

        document.getElementById("organization-form").classList.remove("d-none")
        document.getElementById("organization-form-header-update").classList.remove("d-none")
        document.getElementById("organization-action-submit-update").classList.remove("d-none")

        document.getElementById("organization-input-id").value = organization.id
        document.getElementById("organization-input-name").value = organization.name
        document.getElementById("organization-input-uri").value = organization.uri
        document.getElementById("organization-input-description").value = organization.description
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit() {

        fetchPost(organizationInsert, {
            name: document.getElementById("organization-input-name").value,
            uri: document.getElementById("organization-input-uri").value,
            description: document.getElementById("organization-input-description").value,
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onUpdateSubmit() {

        fetchPost(organizationUpdate, {
            id: document.getElementById("organization-input-id").value,
            name: document.getElementById("organization-input-name").value,
            uri: document.getElementById("organization-input-uri").value,
            description: document.getElementById("organization-input-description").value,
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onDeleteSubmit(organizationList) {

        const idList = Array.from(document.querySelectorAll(".organization-action-delete-queue:checked"))
            .map(element => element.dataset.id)

        fetchPost(organizationDelete, {
            idList: idList
        })
            .then(response => response.status !== 200 ?
                onFailureForDelete(organizationList, idList, response.json()) :
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

    function onFailureForDelete(organizationList, idList, failureMapPromise) {

        stateReset();

        document.getElementById("organization-delete-response-failure").classList.remove("d-none")

        const labelFor = {
            "name": "Name",
            "uri": "URL",
            "description": "Description",
            "idList": "Organizations",
            "id": "Organization",
            "userSet": "users",
            "protectedSystemSet": "protected systems",
            "trustmarkBindingRegistrySet": "trustmark binding registries",
        }

        failureMapPromise.then(failureMap => {

            const messageMapResponse = messageMap(failureMap, field => labelFor[field])

            if (messageMapResponse["idList"] !== undefined && messageMapResponse["idList"][0] !== undefined) {

                const organizationDeleteResponseFailure = document.getElementById("organization-delete-response-failure")
                organizationDeleteResponseFailure.innerHTML = ""

                Object.entries(messageMapResponse["idList"][0]).forEach(entry => {

                    const organizationElement = document.getElementById("organization-template-delete-response-failure").content.cloneNode(true)
                    const organizationElementName = organizationElement.querySelector(".organization-element-name")
                    const organizationElementMessage = organizationElement.querySelector(".organization-element-message")

                    organizationElementName.innerHTML = `Could not delete "${organizationList.find(organization => organization.id == idList[entry[0]]).name}":`

                    Object.entries(entry[1]).forEach(entryInner => {

                        entryInner[1].forEach(message => {

                            const organizationElementMessageInner = document.createElement("li")
                            organizationElementMessageInner.innerHTML = message;

                            organizationElementMessage.appendChild(organizationElementMessageInner)
                        })
                    })

                    organizationDeleteResponseFailure.appendChild(organizationElement)
                })
            }
        })
    }

    function stateReset() {

        document.getElementById("organization-form").classList.add("d-none")
        document.getElementById("organization-form-header-insert").classList.add("d-none")
        document.getElementById("organization-form-header-update").classList.add("d-none")
        document.getElementById("organization-action-submit-insert").classList.add("d-none")
        document.getElementById("organization-action-submit-update").classList.add("d-none")
        document.getElementById("organization-delete-response-failure").classList.add("d-none")
        document.getElementById("organization-delete-response-failure").innerHTML = ""

        formResetValue()
    }

    function formResetValue() {

        document.getElementById("organization-input-id").value = ""
        document.getElementById("organization-input-name").value = ""
        document.getElementById("organization-input-uri").value = ""
        document.getElementById("organization-input-description").value = ""

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("organization-input-name").classList.remove("is-invalid")
        document.getElementById("organization-invalid-feedback-name").innerHTML = ""
        document.getElementById("organization-input-uri").classList.remove("is-invalid")
        document.getElementById("organization-invalid-feedback-uri").innerHTML = ""
        document.getElementById("organization-input-description").classList.remove("is-invalid")
        document.getElementById("organization-invalid-feedback-description").innerHTML = ""
    }
}
