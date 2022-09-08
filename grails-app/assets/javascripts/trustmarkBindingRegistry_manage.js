function initialize(
    trustmarkBindingRegistryFindAll,
    trustmarkBindingRegistryFindOne,
    trustmarkBindingRegistryInsert,
    trustmarkBindingRegistryUpdate,
    trustmarkBindingRegistryDelete,
    organizationFindAll) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function onComplete() {

        findAll()
    }

    function findAll() {

        fetchGet(trustmarkBindingRegistryFindAll)
            .then(response => response.json())
            .then(afterFindAll)
    }

    function afterFindAll(trustmarkBindingRegistryList) {

        stateReset()

        document.getElementById("trustmark-binding-registry-action-insert").outerHTML = document.getElementById("trustmark-binding-registry-action-insert").outerHTML
        document.getElementById("trustmark-binding-registry-action-delete").outerHTML = document.getElementById("trustmark-binding-registry-action-delete").outerHTML

        document.getElementById("trustmark-binding-registry-action-insert").addEventListener("click", () => {

            const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())
            Promise.all([organizationListPromise]).then(array => onInsertOpen(array[0]))
        })
        document.getElementById("trustmark-binding-registry-action-delete").addEventListener("click", () => onDeleteSubmit(trustmarkBindingRegistryList))

        const trustmarkBindingRegistryTBody = document.getElementById("trustmark-binding-registry-tbody")
        trustmarkBindingRegistryTBody.innerHTML = ""

        if (trustmarkBindingRegistryList.length === 0) {

            const trustmarkBindingRegistryElement = document.getElementById("trustmark-binding-registry-template-empty").content.cloneNode(true)

            trustmarkBindingRegistryTBody.appendChild(trustmarkBindingRegistryElement)

        } else {

            trustmarkBindingRegistryList.forEach(trustmarkBindingRegistry => {

                const trustmarkBindingRegistryElement = document.getElementById("trustmark-binding-registry-template-summary").content.cloneNode(true)

                const trustmarkBindingRegistryElementActionUpdate = trustmarkBindingRegistryElement.querySelector(".trustmark-binding-registry-action-update")
                const trustmarkBindingRegistryElementActionDeleteQueue = trustmarkBindingRegistryElement.querySelector(".trustmark-binding-registry-action-delete-queue")
                const trustmarkBindingRegistryElementName = trustmarkBindingRegistryElement.querySelector(".trustmark-binding-registry-element-name")
                const trustmarkBindingRegistryElementUri = trustmarkBindingRegistryElement.querySelector(".trustmark-binding-registry-element-uri")
                const trustmarkBindingRegistryElementDescription = trustmarkBindingRegistryElement.querySelector(".trustmark-binding-registry-element-description")
                const trustmarkBindingRegistryElementOrganization = trustmarkBindingRegistryElement.querySelector(".trustmark-binding-registry-element-organization")
                const trustmarkBindingRegistryElementStatus = trustmarkBindingRegistryElement.querySelector(".trustmark-binding-registry-element-status")
                const trustmarkBindingRegistryElementPartnerSystemCandidateCount = trustmarkBindingRegistryElement.querySelector(".trustmark-binding-registry-element-partner-system-candidate-count")

                trustmarkBindingRegistryElementActionUpdate.addEventListener("click", () => onUpdateOpen(trustmarkBindingRegistry.id))
                trustmarkBindingRegistryElementActionDeleteQueue.dataset.id = trustmarkBindingRegistry.id
                trustmarkBindingRegistryElementName.innerHTML = trustmarkBindingRegistry.name
                trustmarkBindingRegistryElementName.title = trustmarkBindingRegistry.name
                trustmarkBindingRegistryElementUri.innerHTML = trustmarkBindingRegistry.uri
                trustmarkBindingRegistryElementUri.href = trustmarkBindingRegistry.uri
                trustmarkBindingRegistryElementUri.title = trustmarkBindingRegistry.uri
                trustmarkBindingRegistryElementOrganization.innerHTML = trustmarkBindingRegistry.organization.name
                trustmarkBindingRegistryElementOrganization.title = trustmarkBindingRegistry.organization.name
                trustmarkBindingRegistryElementDescription.innerHTML = trustmarkBindingRegistry.description
                trustmarkBindingRegistryElementDescription.title = trustmarkBindingRegistry.description
                trustmarkBindingRegistryElementPartnerSystemCandidateCount.innerHTML = trustmarkBindingRegistry.partnerSystemCandidateCount
                trustmarkBindingRegistryElementPartnerSystemCandidateCount.title = trustmarkBindingRegistry.partnerSystemCandidateCount

                if (trustmarkBindingRegistry.documentRequestLocalDateTime == null) {

                    trustmarkBindingRegistryElementStatus.classList.add("bi-question-circle")
                    trustmarkBindingRegistryElementStatus.classList.add("text-warning")

                } else if (trustmarkBindingRegistry.documentSuccessLocalDateTime == null || trustmarkBindingRegistry.documentSuccessLocalDateTime < trustmarkBindingRegistry.documentFailureLocalDateTime) {

                    trustmarkBindingRegistryElementStatus.classList.add("bi-emoji-frown")
                    trustmarkBindingRegistryElementStatus.classList.add("text-danger")
                    trustmarkBindingRegistryElementStatus.title = trustmarkBindingRegistry.documentFailureMessage
                } else {

                    trustmarkBindingRegistryElementStatus.classList.add("bi-emoji-smile")
                    trustmarkBindingRegistryElementStatus.classList.add("text-success")
                    trustmarkBindingRegistryElementStatus.title = moment(trustmarkBindingRegistry.documentSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC')
                }

                trustmarkBindingRegistryTBody.appendChild(trustmarkBindingRegistryElement)
            })
        }
    }

    function onInsertOpen(organizationList) {

        stateReset()

        document.getElementById("trustmark-binding-registry-action-cancel").outerHTML = document.getElementById("trustmark-binding-registry-action-cancel").outerHTML
        document.getElementById("trustmark-binding-registry-action-submit-insert").outerHTML = document.getElementById("trustmark-binding-registry-action-submit-insert").outerHTML

        document.getElementById("trustmark-binding-registry-action-cancel").addEventListener("click", onCancel)
        document.getElementById("trustmark-binding-registry-action-submit-insert").addEventListener("click", onInsertSubmit)

        document.getElementById("trustmark-binding-registry-form").classList.remove("d-none")
        document.getElementById("trustmark-binding-registry-form-header-insert").classList.remove("d-none")
        document.getElementById("trustmark-binding-registry-action-submit-insert").classList.remove("d-none")

        organizationList.map(organization => {
            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name

            document.getElementById("trustmark-binding-registry-input-organization").add(option)
        })
    }

    function onUpdateOpen(trustmarkBindingRegistryId) {

        const trustmarkBindingRegistryPromise = fetchGet(trustmarkBindingRegistryFindOne + "?" + new URLSearchParams({"id": trustmarkBindingRegistryId})).then(response => response.json())
        const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())

        Promise.all([trustmarkBindingRegistryPromise, organizationListPromise]).then(array => afterFindOne(array[0], array[1]))
    }

    function afterFindOne(trustmarkBindingRegistry, organizationList) {

        stateReset()

        document.getElementById("trustmark-binding-registry-action-cancel").outerHTML = document.getElementById("trustmark-binding-registry-action-cancel").outerHTML
        document.getElementById("trustmark-binding-registry-action-submit-update").outerHTML = document.getElementById("trustmark-binding-registry-action-submit-update").outerHTML

        document.getElementById("trustmark-binding-registry-action-cancel").addEventListener("click", onCancel)
        document.getElementById("trustmark-binding-registry-action-submit-update").addEventListener("click", onUpdateSubmit)

        document.getElementById("trustmark-binding-registry-form").classList.remove("d-none")
        document.getElementById("trustmark-binding-registry-form-header-update").classList.remove("d-none")
        document.getElementById("trustmark-binding-registry-action-submit-update").classList.remove("d-none")
        document.getElementById("trustmark-binding-registry-status").classList.remove("d-none")

        document.getElementById("trustmark-binding-registry-input-id").value = trustmarkBindingRegistry.id
        document.getElementById("trustmark-binding-registry-input-name").value = trustmarkBindingRegistry.name
        document.getElementById("trustmark-binding-registry-input-uri").value = trustmarkBindingRegistry.uri
        document.getElementById("trustmark-binding-registry-input-description").value = trustmarkBindingRegistry.description

        organizationList.map(organization => {

            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name
            option.selected = organization.id === trustmarkBindingRegistry.organization.id

            document.getElementById("trustmark-binding-registry-input-organization").add(option)
        })

        document.getElementById("trustmark-binding-registry-input-document-request-date-time").value = trustmarkBindingRegistry.documentRequestLocalDateTime == null ? "" : moment(trustmarkBindingRegistry.documentRequestLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trustmark-binding-registry-input-document-success-date-time").value = trustmarkBindingRegistry.documentSuccessLocalDateTime == null ? "" : moment(trustmarkBindingRegistry.documentSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trustmark-binding-registry-input-document-failure-date-time").value = trustmarkBindingRegistry.documentFailureLocalDateTime == null ? "" : moment(trustmarkBindingRegistry.documentFailureLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trustmark-binding-registry-input-document-failure-message").value = trustmarkBindingRegistry.documentFailureMessage
        document.getElementById("trustmark-binding-registry-input-server-request-date-time").value = trustmarkBindingRegistry.serverRequestLocalDateTime == null ? "" : moment(trustmarkBindingRegistry.serverRequestLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trustmark-binding-registry-input-server-success-date-time").value = trustmarkBindingRegistry.serverSuccessLocalDateTime == null ? "" : moment(trustmarkBindingRegistry.serverSuccessLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trustmark-binding-registry-input-server-failure-date-time").value = trustmarkBindingRegistry.serverFailureLocalDateTime == null ? "" : moment(trustmarkBindingRegistry.serverFailureLocalDateTime).format('MMMM Do YYYY, h:mm:ss A UTC');
        document.getElementById("trustmark-binding-registry-input-server-failure-message").value = trustmarkBindingRegistry.serverFailureMessage
        document.getElementById("trustmark-binding-registry-input-partner-system-candidate-count").value = trustmarkBindingRegistry.partnerSystemCandidateCount

    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit() {

        fetchPost(trustmarkBindingRegistryInsert, {
            organization: document.getElementById("trustmark-binding-registry-input-organization").value,
            name: document.getElementById("trustmark-binding-registry-input-name").value,
            uri: document.getElementById("trustmark-binding-registry-input-uri").value,
            description: document.getElementById("trustmark-binding-registry-input-description").value,
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onUpdateSubmit() {

        fetchPost(trustmarkBindingRegistryUpdate, {
            id: document.getElementById("trustmark-binding-registry-input-id").value,
            organization: document.getElementById("trustmark-binding-registry-input-organization").value,
            name: document.getElementById("trustmark-binding-registry-input-name").value,
            uri: document.getElementById("trustmark-binding-registry-input-uri").value,
            description: document.getElementById("trustmark-binding-registry-input-description").value,
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onDeleteSubmit(trustmarkBindingRegistryList) {

        const idList = Array.from(document.querySelectorAll(".trustmark-binding-registry-action-delete-queue:checked"))
            .map(element => element.dataset.id)

        fetchPost(trustmarkBindingRegistryDelete, {
            idList: idList
        })
            .then(response => response.status !== 200 ?
                onFailureForDelete(trustmarkBindingRegistryList, idList, response.json()) :
                findAll())
    }

    function onFailure(failureMapPromise) {

        formResetValidation()

        const labelFor = {
            "organization": "organization",
            "name": "name",
            "uri": "uri",
            "description": "description",
        }

        messageMapShow(failureMapPromise, "trustmark-binding-registry", labelFor)
    }

    function onFailureForDelete(trustmarkBindingRegistrylist, idList, failureMapPromise) {
    }

    function stateReset() {

        document.getElementById("trustmark-binding-registry-form").classList.add("d-none")
        document.getElementById("trustmark-binding-registry-form-header-insert").classList.add("d-none")
        document.getElementById("trustmark-binding-registry-form-header-update").classList.add("d-none")
        document.getElementById("trustmark-binding-registry-action-submit-insert").classList.add("d-none")
        document.getElementById("trustmark-binding-registry-action-submit-update").classList.add("d-none")
        document.getElementById("trustmark-binding-registry-delete-response-failure").classList.add("d-none")
        document.getElementById("trustmark-binding-registry-delete-response-failure").innerHTML = ""
        document.getElementById("trustmark-binding-registry-status").classList.add("d-none")

        formResetValue()
    }

    function formResetValue() {

        document.getElementById("trustmark-binding-registry-input-id").value = ""
        document.getElementById("trustmark-binding-registry-input-name").value = ""
        document.getElementById("trustmark-binding-registry-input-uri").value = ""
        document.getElementById("trustmark-binding-registry-input-description").value = ""
        document.getElementById("trustmark-binding-registry-input-organization").innerHTML = ""

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("trustmark-binding-registry-input-name").classList.remove("is-invalid")
        document.getElementById("trustmark-binding-registry-invalid-feedback-name").innerHTML = ""
        document.getElementById("trustmark-binding-registry-input-uri").classList.remove("is-invalid")
        document.getElementById("trustmark-binding-registry-invalid-feedback-uri").innerHTML = ""
        document.getElementById("trustmark-binding-registry-input-description").classList.remove("is-invalid")
        document.getElementById("trustmark-binding-registry-invalid-feedback-description").innerHTML = ""
        document.getElementById("trustmark-binding-registry-input-organization").classList.remove("is-invalid")
        document.getElementById("trustmark-binding-registry-invalid-feedback-organization").innerHTML = ""
    }
}
