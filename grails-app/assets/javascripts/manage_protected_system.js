function initialize(
    protectedSystemFindAll,
    protectedSystemFindOne,
    protectedSystemInsert,
    protectedSystemUpdate,
    protectedSystemDelete,
    organizationFindAll,
    typeFindAll,
    protectedSystemDashboard) {

    document.onreadystatechange = function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    }

    function onComplete() {

        findAll()
    }

    function findAll() {

        fetchGet(protectedSystemFindAll)
            .then(response => response.json())
            .then(afterFindAll)
    }

    function afterFindAll(protectedSystemList) {

        stateReset()

        document.getElementById("protected-system-action-insert").outerHTML = document.getElementById("protected-system-action-insert").outerHTML
        document.getElementById("protected-system-action-delete").outerHTML = document.getElementById("protected-system-action-delete").outerHTML

        document.getElementById("protected-system-action-insert").addEventListener("click", () => {

            const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())
            const typeListPromise = fetchGet(typeFindAll).then(response => response.json())

            Promise.all([organizationListPromise, typeListPromise]).then(array => onInsertOpen(array[0], array[1]))
        })
        document.getElementById("protected-system-action-delete").addEventListener("click", onDeleteSubmit)

        const protectedSystemTBody = document.getElementById("protected-system-tbody")
        protectedSystemTBody.innerHTML = ""

        if (protectedSystemList.length === 0) {

            const protectedSystemElement = document.getElementById("protected-system-template-empty").content.cloneNode(true)

            protectedSystemTBody.appendChild(protectedSystemElement)

        } else {

            protectedSystemList.forEach(protectedSystem => {

                const protectedSystemElement = document.getElementById("protected-system-template-summary").content.cloneNode(true)

                const protectedSystemElementActionUpdate = protectedSystemElement.querySelector(".protected-system-action-update")
                const protectedSystemElementActionDeleteQueue = protectedSystemElement.querySelector(".protected-system-action-delete-queue")
                const protectedSystemElementActionDetail = protectedSystemElement.querySelector(".protected-system-action-detail")
                const protectedSystemElementName = protectedSystemElement.querySelector(".protected-system-element-name")
                const protectedSystemElementType = protectedSystemElement.querySelector(".protected-system-element-type")
                const protectedSystemElementOrganization = protectedSystemElement.querySelector(".protected-system-element-organization")

                protectedSystemElementActionUpdate.addEventListener("click", () => onUpdateOpen(protectedSystem))
                protectedSystemElementActionDeleteQueue.dataset.id = protectedSystem.id
                protectedSystemElementActionDetail.href = protectedSystemDashboard + "?" + new URLSearchParams({"id": protectedSystem.id})
                protectedSystemElementName.innerHTML = protectedSystem.name
                protectedSystemElementName.title = protectedSystem.name
                protectedSystemElementType.innerHTML = protectedSystem.type.label
                protectedSystemElementType.title = protectedSystem.type.label
                protectedSystemElementOrganization.innerHTML = protectedSystem.organization.name
                protectedSystemElementOrganization.title = protectedSystem.organization.name

                protectedSystemTBody.appendChild(protectedSystemElement)
            })
        }
    }

    function onInsertOpen(organizationList, typeList) {

        stateReset()

        document.getElementById("protected-system-action-cancel").outerHTML = document.getElementById("protected-system-action-cancel").outerHTML
        document.getElementById("protected-system-action-submit-insert").outerHTML = document.getElementById("protected-system-action-submit-insert").outerHTML

        document.getElementById("protected-system-action-cancel").addEventListener("click", onCancel)
        document.getElementById("protected-system-action-submit-insert").addEventListener("click", onInsertSubmit)

        document.getElementById("protected-system-form").classList.remove("d-none")
        document.getElementById("protected-system-form-header-insert").classList.remove("d-none")
        document.getElementById("protected-system-action-submit-insert").classList.remove("d-none")

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

    function onUpdateOpen(protectedSystem) {

        const protectedSystemPromise = fetchGet(protectedSystemFindOne + "?" + new URLSearchParams({"id": protectedSystem.id})).then(response => response.json())
        const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())
        const typeListPromise = fetchGet(typeFindAll).then(response => response.json())

        Promise.all([protectedSystemPromise, organizationListPromise, typeListPromise]).then(array => afterFindOne(array[0], array[1], array[2]))
    }

    function afterFindOne(protectedSystem, organizationList, typeList) {

        stateReset()

        document.getElementById("protected-system-action-cancel").outerHTML = document.getElementById("protected-system-action-cancel").outerHTML
        document.getElementById("protected-system-action-submit-update").outerHTML = document.getElementById("protected-system-action-submit-update").outerHTML

        document.getElementById("protected-system-action-cancel").addEventListener("click", onCancel)
        document.getElementById("protected-system-action-submit-update").addEventListener("click", () => onUpdateSubmit(protectedSystem))

        document.getElementById("protected-system-form").classList.remove("d-none")
        document.getElementById("protected-system-form-header-update").classList.remove("d-none")
        document.getElementById("protected-system-action-submit-update").classList.remove("d-none")

        document.getElementById("protected-system-input-id").value = protectedSystem.id
        document.getElementById("protected-system-input-name").value = protectedSystem.name

        organizationList.map(organization => {

            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name
            option.selected = organization.id === protectedSystem.organization.id

            document.getElementById("protected-system-input-organization").add(option)
        })

        typeList.map(type => {

            const option = document.createElement("option")
            option.value = type.value
            option.innerHTML = type.label
            option.selected = type.value === protectedSystem.type.value

            document.getElementById("protected-system-input-type").add(option)
        })
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit() {

        fetchPost(protectedSystemInsert, {
            name: document.getElementById("protected-system-input-name").value,
            type: document.getElementById("protected-system-input-type").value,
            organization: document.getElementById("protected-system-input-organization").value,
            protectedSystemTrustInteroperabilityProfileList: [],
            partnerSystemCandidateList: []
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onUpdateSubmit(protectedSystem) {

        fetchPost(protectedSystemUpdate, {
            id: document.getElementById("protected-system-input-id").value,
            name: document.getElementById("protected-system-input-name").value,
            type: document.getElementById("protected-system-input-type").value,
            organization: document.getElementById("protected-system-input-organization").value,
            protectedSystemTrustInteroperabilityProfileList: protectedSystem.protectedSystemTrustInteroperabilityProfileList,
            partnerSystemCandidateList: protectedSystem.protectedSystemPartnerSystemCandidateList
                .filter(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.trust)
                .map(protectedSystemPartnerSystemCandidate => protectedSystemPartnerSystemCandidate.partnerSystemCandidate.id)
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onDeleteSubmit(protectedSystemList) {

        const idList = Array.from(document.querySelectorAll(".protected-system-action-delete-queue:checked"))
            .map(element => element.dataset.id)

        fetchPost(protectedSystemDelete, {
            idList: idList
        })
            .then(response => response.status !== 200 ?
                onFailure(protectedSystemList, idList, response.json()) :
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

    function onFailureForDelete(protectedSystemList, idList, failureMapPromise) {
    }

    function stateReset() {

        document.getElementById("protected-system-form").classList.add("d-none")
        document.getElementById("protected-system-form-header-insert").classList.add("d-none")
        document.getElementById("protected-system-form-header-update").classList.add("d-none")
        document.getElementById("protected-system-action-submit-insert").classList.add("d-none")
        document.getElementById("protected-system-action-submit-update").classList.add("d-none")
        document.getElementById("protected-system-delete-response-failure").classList.add("d-none")
        document.getElementById("protected-system-delete-response-failure").innerHTML = ""

        formResetValue()
    }

    function formResetValue() {

        document.getElementById("protected-system-input-id").value = ""
        document.getElementById("protected-system-input-name").value = ""
        document.getElementById("protected-system-input-type").innerHTML = ""
        document.getElementById("protected-system-input-organization").innerHTML = ""

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("protected-system-input-name").classList.remove("is-invalid")
        document.getElementById("protected-system-invalid-feedback-name").innerHTML = ""
        document.getElementById("protected-system-input-type").classList.remove("is-invalid")
        document.getElementById("protected-system-invalid-feedback-type").innerHTML = ""
        document.getElementById("protected-system-input-organization").classList.remove("is-invalid")
        document.getElementById("protected-system-invalid-feedback-organization").innerHTML = ""
    }
}
