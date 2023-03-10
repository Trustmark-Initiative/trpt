function initialize(
    profileFindOneUrl,
    userFindAll,
    userFindOne,
    userFindAllWithoutOrganization,
    userFindOneWithoutOrganization,
    userInsert,
    userUpdate,
    userDelete,
    organizationFindAll,
    roleFindAll) {

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
            .then(role => role === undefined ? Promise.resolve() : Promise.all([
                fetchGet(userFindAll).then(response => response.status >= 400 ? undefined : response.json()),
                fetchGet(userFindAllWithoutOrganization).then(response => response.status >= 400 ? undefined : response.json())
            ])
                .then(array => afterFindAll(array[0], array[1])))
    }

    function afterFindAll(userList, userListWithoutOrganization) {

        stateReset()
        afterFindAllUserList(userList)
        afterFindAllUserListWithoutOrganization(userListWithoutOrganization)
    }

    function afterFindAllUserList(userList) {

        document.getElementById("user-action-insert").outerHTML = document.getElementById("user-action-insert").outerHTML
        document.getElementById("user-action-delete").outerHTML = document.getElementById("user-action-delete").outerHTML

        document.getElementById("user-action-insert").addEventListener("click", () => {

            const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())
            const roleListPromise = fetchGet(roleFindAll).then(response => response.json())
            Promise.all([organizationListPromise, roleListPromise]).then(array => onInsertOpen(array[0], array[1]))
        })
        document.getElementById("user-action-delete").addEventListener("click", onDeleteSubmit)

        const userTBody = document.getElementById("user-tbody")
        userTBody.innerHTML = ""

        if (userList === undefined || userList.length === 0) {

            const userElement = document.getElementById("user-template-empty").content.cloneNode(true)

            userTBody.appendChild(userElement)

        } else {

            userList.forEach(user => {

                const userElement = document.getElementById("user-template-summary").content.cloneNode(true)

                const userElementActionUpdate = userElement.querySelector(".user-action-update")
                const userElementActionDeleteQueue = userElement.querySelector(".user-action-delete-queue")
                const userElementName = userElement.querySelector(".user-element-name")
                const userElementUsername = userElement.querySelector(".user-element-username")
                const userElementEmail = userElement.querySelector(".user-element-email")
                const userElementOrganization = userElement.querySelector(".user-element-organization")
                const userElementRole = userElement.querySelector(".user-element-role")

                userElementActionUpdate.addEventListener("click", () => onUpdateOpen(user.id))
                userElementActionDeleteQueue.dataset.id = user.id
                userElementName.innerHTML = (user.nameFamily == null ? "(none)" : user.nameFamily) + ", " + (user.nameGiven == null ? "(none)" : user.nameGiven)
                userElementName.title = (user.nameFamily == null ? "(none)" : user.nameFamily) + ", " + (user.nameGiven == null ? "(none)" : user.nameGiven)
                userElementUsername.innerHTML = user.username == null ? "(none)" : user.username
                userElementEmail.innerHTML = user.contactEmail == null ? "(none)" : user.contactEmail
                userElementEmail.href = user.contactEmail == null ? "" : "mailto:" + user.contactEmail
                userElementEmail.title = user.contactEmail == null ? "(none)" : user.contactEmail
                userElementOrganization.innerHTML = user.organization == null ? "(none)" : user.organization.name
                userElementOrganization.title = user.organization == null ? "(none)" : user.organization.name
                userElementRole.innerHTML = user.role == null ? "(none)" : user.role.label
                userElementRole.title = user.role == null ? "(none)" : user.role.label

                userTBody.appendChild(userElement)
            })
        }
    }

    function afterFindAllUserListWithoutOrganization(userList) {

        document.getElementById("user-action-delete-without-organization").outerHTML = document.getElementById("user-action-delete-without-organization").outerHTML
        document.getElementById("user-action-delete-without-organization").addEventListener("click", onDeleteSubmit)

        const userTBody = document.getElementById("user-tbody-without-organization")
        userTBody.innerHTML = ""

        if (userList === undefined || userList.length === 0) {

            const userContainer = document.getElementById("user-container-without-organization")
            userContainer.classList.add("d-none")

        } else {

            const userContainer = document.getElementById("user-container-without-organization")
            userContainer.classList.remove("d-none")

            userList.forEach(user => {

                const userElement = document.getElementById("user-template-summary-without-organization").content.cloneNode(true)

                const userElementActionUpdate = userElement.querySelector(".user-action-update")
                const userElementActionDeleteQueue = userElement.querySelector(".user-action-delete-queue")
                const userElementName = userElement.querySelector(".user-element-name")
                const userElementUsername = userElement.querySelector(".user-element-username")
                const userElementEmail = userElement.querySelector(".user-element-email")
                const userElementRole = userElement.querySelector(".user-element-role")

                userElementActionUpdate.addEventListener("click", () => onUpdateOpenWithoutOrganization(user.id))
                userElementActionDeleteQueue.dataset.id = user.id
                userElementName.innerHTML = (user.nameFamily == null ? "(none)" : user.nameFamily) + ", " + (user.nameGiven == null ? "(none)" : user.nameGiven)
                userElementName.title = (user.nameFamily == null ? "(none)" : user.nameFamily) + ", " + (user.nameGiven == null ? "(none)" : user.nameGiven)
                userElementUsername.innerHTML = user.username == null ? "(none)" : user.username
                userElementEmail.innerHTML = user.contactEmail == null ? "(none)" : user.contactEmail
                userElementEmail.href = user.contactEmail == null ? "" : "mailto:" + user.contactEmail
                userElementEmail.title = user.contactEmail == null ? "(none)" : user.contactEmail
                userElementRole.innerHTML = user.role == null ? "(none)" : user.role.label
                userElementRole.title = user.role == null ? "(none)" : user.role.label

                userTBody.appendChild(userElement)
            })
        }
    }

    function onInsertOpen(organizationList, roleList) {

        stateReset()

        document.getElementById("user-action-cancel").outerHTML = document.getElementById("user-action-cancel").outerHTML
        document.getElementById("user-action-submit-insert").outerHTML = document.getElementById("user-action-submit-insert").outerHTML

        document.getElementById("user-action-cancel").addEventListener("click", onCancel)
        document.getElementById("user-action-submit-insert").addEventListener("click", onInsertSubmit)

        document.getElementById("user-form").classList.remove("d-none")
        document.getElementById("user-form-header-insert").classList.remove("d-none")
        document.getElementById("user-action-submit-insert").classList.remove("d-none")

        document.getElementById("user-row-organization").style.display = "flex"
        document.getElementById("user-row-role").style.display = "none"
        document.getElementById("user-row-username").style.display = "flex"
        document.getElementById("user-row-email").style.display = "none"
        document.getElementById("user-row-nameGiven").style.display = "none"
        document.getElementById("user-row-nameFamily").style.display = "none"

        organizationList.map(organization => {

            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name

            document.getElementById("user-input-organization").add(option)
        })

        const option = document.createElement("option")
        option.value = -1
        option.innerHTML = "(none)"
        document.getElementById("user-input-organization").add(option)

        document.getElementById("user-input-role").value = ""
        document.getElementById("user-input-username").value = ""
        document.getElementById("user-input-email").value = ""
        document.getElementById("user-input-nameFamily").value = ""
        document.getElementById("user-input-nameGiven").value = ""

        document.getElementById("user-input-organization").disabled = false
        document.getElementById("user-input-role").disabled = false
        document.getElementById("user-input-username").disabled = false
        document.getElementById("user-input-email").disabled = false
        document.getElementById("user-input-nameFamily").disabled = false
        document.getElementById("user-input-nameGiven").disabled = false
    }

    function onUpdateOpen(userId) {

        const userPromise = fetchGet(userFindOne + "?" + new URLSearchParams({"id": userId})).then(response => response.json())
        const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())
        const roleListPromise = fetchGet(roleFindAll).then(response => response.json())

        Promise.all([userPromise, organizationListPromise, roleListPromise]).then(array => afterFindOne(array[0], array[1], array[2]))
    }

    function onUpdateOpenWithoutOrganization(userId) {

        const userPromise = fetchGet(userFindOneWithoutOrganization + "?" + new URLSearchParams({"id": userId})).then(response => response.json())
        const organizationListPromise = fetchGet(organizationFindAll).then(response => response.json())
        const roleListPromise = fetchGet(roleFindAll).then(response => response.json())

        Promise.all([userPromise, organizationListPromise, roleListPromise]).then(array => afterFindOne(array[0], array[1], array[2]))
    }

    function afterFindOne(user, organizationList, roleList) {

        stateReset()

        document.getElementById("user-action-cancel").outerHTML = document.getElementById("user-action-cancel").outerHTML
        document.getElementById("user-action-submit-update").outerHTML = document.getElementById("user-action-submit-update").outerHTML

        document.getElementById("user-action-cancel").addEventListener("click", onCancel)
        document.getElementById("user-action-submit-update").addEventListener("click", onUpdateSubmit)

        document.getElementById("user-form").classList.remove("d-none")
        document.getElementById("user-form-header-update").classList.remove("d-none")
        document.getElementById("user-action-submit-update").classList.remove("d-none")

        document.getElementById("user-row-organization").style.display = "flex"
        document.getElementById("user-row-role").style.display = "flex"
        document.getElementById("user-row-username").style.display = "flex"
        document.getElementById("user-row-email").style.display = "flex"
        document.getElementById("user-row-nameGiven").style.display = "flex"
        document.getElementById("user-row-nameFamily").style.display = "flex"

        organizationList.map(organization => {

            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name
            option.selected = user.organization != null && organization.id === user.organization.id
            if (!user.editable) option.disabled = true

            document.getElementById("user-input-organization").add(option)
        })

        const option = document.createElement("option")
        option.value = -1
        option.innerHTML = "(none)"
        option.selected = user.organization == null
        if (!user.editable) option.disabled = true
        document.getElementById("user-input-organization").add(option)

        document.getElementById("user-input-id").value = user.id
        document.getElementById("user-input-role").value = user.role == null ? "(none)" : user.role.label
        document.getElementById("user-input-username").value = user.username
        document.getElementById("user-input-email").value = user.contactEmail
        document.getElementById("user-input-nameFamily").value = user.nameFamily
        document.getElementById("user-input-nameGiven").value = user.nameGiven

        document.getElementById("user-input-organization").disabled = !user.editable
        document.getElementById("user-input-role").disabled = true
        document.getElementById("user-input-username").disabled = true
        document.getElementById("user-input-email").disabled = true
        document.getElementById("user-input-nameFamily").disabled = true
        document.getElementById("user-input-nameGiven").disabled = true

        document.getElementById("user-action-submit-update").disabled = !user.editable
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit() {

        fetchPost(userInsert, {
            username: document.getElementById("user-input-username").value,
            organization: document.getElementById("user-input-organization").value == -1 ? null : document.getElementById("user-input-organization").value,
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onUpdateSubmit() {

        fetchPost(userUpdate, {
            id: document.getElementById("user-input-id").value,
            organization: document.getElementById("user-input-organization").value == -1 ? null : document.getElementById("user-input-organization").value,
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onDeleteSubmit(userList) {

        const idList = Array.from(document.querySelectorAll(".user-action-delete-queue:checked"))
            .map(element => element.dataset.id)

        fetchPost(userDelete, {
            idList: idList
        })
            .then(response => response.status !== 200 ?
                onFailureForDelete(userList, idList, response.json()) :
                findAll())
    }

    function onFailure(failureMapPromise) {

        formResetValidation()

        const labelFor = {
            "username": "username",
            "nameFamily": "nameFamily",
            "nameGiven": "nameGiven",
            "organization": "organization",
            "role": "role",
        }

        messageMapShow(failureMapPromise, "user", labelFor)
    }

    function onFailureForDelete(userList, idList, failureMapPromise) {
    }

    function stateReset() {

        document.getElementById("user-form").classList.add("d-none")
        document.getElementById("user-form-header-insert").classList.add("d-none")
        document.getElementById("user-form-header-update").classList.add("d-none")
        document.getElementById("user-action-submit-insert").classList.add("d-none")
        document.getElementById("user-action-submit-update").classList.add("d-none")
        document.getElementById("user-delete-response-failure").classList.add("d-none")
        document.getElementById("user-delete-response-failure").innerHTML = ""

        formResetValue()
    }

    function formResetValue() {

        document.getElementById("user-input-id").value = ""
        document.getElementById("user-input-username").value = ""
        document.getElementById("user-input-email").value = ""
        document.getElementById("user-input-nameFamily").value = ""
        document.getElementById("user-input-nameGiven").value = ""
        document.getElementById("user-input-organization").innerHTML = ""
        document.getElementById("user-input-role").innerHTML = ""

        document.getElementById("user-input-username").disabled = false
        document.getElementById("user-input-email").disabled = false
        document.getElementById("user-input-nameFamily").disabled = false
        document.getElementById("user-input-nameGiven").disabled = false
        document.getElementById("user-input-organization").disabled = false
        document.getElementById("user-input-role").disabled = false
        document.getElementById("user-action-submit-update").disabled = false

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("user-input-username").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-username").innerHTML = ""
        document.getElementById("user-input-email").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-email").innerHTML = ""
        document.getElementById("user-input-nameFamily").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-nameFamily").innerHTML = ""
        document.getElementById("user-input-nameGiven").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-nameGiven").innerHTML = ""
        document.getElementById("user-input-organization").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-organization").innerHTML = ""
        document.getElementById("user-input-role").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-role").innerHTML = ""
    }
}
