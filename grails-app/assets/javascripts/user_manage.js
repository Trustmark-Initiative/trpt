function initialize(
    userFindAll,
    userFindOne,
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

        fetchGet(userFindAll)
            .then(response => response.json())
            .then(afterFindAll)
    }

    function afterFindAll(userList) {

        stateReset()

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

        if (userList.length === 0) {

            const userElement = document.getElementById("user-template-empty").content.cloneNode(true)

            userTBody.appendChild(userElement)

        } else {

            userList.forEach(user => {

                const userElement = document.getElementById("user-template-summary").content.cloneNode(true)

                const userElementActionUpdate = userElement.querySelector(".user-action-update")
                const userElementActionDeleteQueue = userElement.querySelector(".user-action-delete-queue")
                const userElementName = userElement.querySelector(".user-element-name")
                const userElementUsername = userElement.querySelector(".user-element-username")
                const userElementTelephone = userElement.querySelector(".user-element-telephone")
                const userElementOrganization = userElement.querySelector(".user-element-organization")
                const userElementRole = userElement.querySelector(".user-element-role")
                const userElementUserDisabled = userElement.querySelector(".user-element-userDisabled")
                const userElementUserLocked = userElement.querySelector(".user-element-userLocked")
                const userElementUserExpired = userElement.querySelector(".user-element-userExpired")
                const userElementPasswordExpired = userElement.querySelector(".user-element-passwordExpired")

                userElementActionUpdate.addEventListener("click", () => onUpdateOpen(user.id))
                userElementActionDeleteQueue.dataset.id = user.id
                userElementName.innerHTML = user.nameFamily + ", " + user.nameGiven
                userElementName.title = user.nameFamily + ", " + user.nameGiven
                userElementUsername.innerHTML = user.username
                userElementUsername.href = "mailto:" + user.username
                userElementUsername.title = user.username
                userElementTelephone.innerHTML = user.telephone
                userElementTelephone.href = "tel:" + user.telephone
                userElementTelephone.title = user.telephone
                userElementOrganization.innerHTML = user.organization.name
                userElementOrganization.title = user.organization.name
                userElementRole.innerHTML = user.role.label
                userElementRole.title = user.role.label

                if (!user.userEnabled) {
                    userElementUserDisabled.classList.add("bi-shield-slash")
                }
                if (user.userLocked) {
                    userElementUserLocked.classList.add("bi-shield-lock")
                }
                if (user.userExpired) {
                    userElementUserExpired.classList.add("bi-shield-x")
                }
                if (user.passwordExpired) {
                    userElementPasswordExpired.classList.add("bi-file-medical")
                }

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

        document.getElementById("user-input-username").value = ""
        document.getElementById("user-input-nameFamily").value = ""
        document.getElementById("user-input-nameGiven").value = ""
        document.getElementById("user-input-telephone").value = ""
        document.getElementById("user-input-userDisabled").checked = false
        document.getElementById("user-input-userLocked").checked = false
        document.getElementById("user-input-userExpired").checked = false
        document.getElementById("user-input-passwordExpired").checked = false

        organizationList.map(organization => {

            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name

            document.getElementById("user-input-organization").add(option)
        })

        roleList.map(role => {

            const option = document.createElement("option")
            option.value = role.id
            option.innerHTML = role.label

            document.getElementById("user-input-role").add(option)
        })
    }

    function onUpdateOpen(userId) {

        const userPromise = fetchGet(userFindOne + "?" + new URLSearchParams({"id": userId})).then(response => response.json())
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

        document.getElementById("user-input-id").value = user.id
        document.getElementById("user-input-username").value = user.username
        document.getElementById("user-input-nameFamily").value = user.nameFamily
        document.getElementById("user-input-nameGiven").value = user.nameGiven
        document.getElementById("user-input-telephone").value = user.telephone
        document.getElementById("user-input-userDisabled").checked = !user.userEnabled
        document.getElementById("user-input-userLocked").checked = user.userLocked
        document.getElementById("user-input-userExpired").checked = user.userExpired
        document.getElementById("user-input-passwordExpired").checked = user.passwordExpired

        organizationList.map(organization => {

            const option = document.createElement("option")
            option.value = organization.id
            option.innerHTML = organization.name
            option.selected = organization.id === user.organization.id
            if (organization.id !== user.organization.id && !user.editable) option.disabled = true

            document.getElementById("user-input-organization").add(option)
        })

        roleList.map(role => {

            const option = document.createElement("option")
            option.value = role.id
            option.innerHTML = role.label
            option.selected = role.id === user.role.id
            if (role.id !== user.role.id && !user.editable) option.disabled = true

            document.getElementById("user-input-role").add(option)
        })

        document.getElementById("user-input-username").disabled = !user.editable
        document.getElementById("user-input-nameFamily").disabled = !user.editable
        document.getElementById("user-input-nameGiven").disabled = !user.editable
        document.getElementById("user-input-telephone").disabled = !user.editable
        document.getElementById("user-input-userDisabled").disabled = !user.editable
        document.getElementById("user-input-userLocked").disabled = !user.editable
        document.getElementById("user-input-userExpired").disabled = !user.editable
        document.getElementById("user-input-passwordExpired").disabled = !user.editable
        document.getElementById("user-input-organization").disabled = !user.editable
        document.getElementById("user-input-role").disabled = !user.editable
        document.getElementById("user-action-submit-update").disabled = !user.editable
    }

    function onCancel() {

        stateReset()
    }

    function onInsertSubmit() {

        fetchPost(userInsert, {
            username: document.getElementById("user-input-username").value,
            nameFamily: document.getElementById("user-input-nameFamily").value,
            nameGiven: document.getElementById("user-input-nameGiven").value,
            telephone: document.getElementById("user-input-telephone").value,
            userEnabled: !document.getElementById("user-input-userDisabled").checked,
            userLocked: document.getElementById("user-input-userLocked").checked,
            userExpired: document.getElementById("user-input-userExpired").checked,
            passwordExpired: document.getElementById("user-input-passwordExpired").checked,
            organization: document.getElementById("user-input-organization").value,
            role: document.getElementById("user-input-role").value
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                findAll())
    }

    function onUpdateSubmit() {

        fetchPost(userUpdate, {
            id: document.getElementById("user-input-id").value,
            username: document.getElementById("user-input-username").value,
            nameFamily: document.getElementById("user-input-nameFamily").value,
            nameGiven: document.getElementById("user-input-nameGiven").value,
            telephone: document.getElementById("user-input-telephone").value,
            userEnabled: !document.getElementById("user-input-userDisabled").checked,
            userLocked: document.getElementById("user-input-userLocked").checked,
            userExpired: document.getElementById("user-input-userExpired").checked,
            passwordExpired: document.getElementById("user-input-passwordExpired").checked,
            organization: document.getElementById("user-input-organization").value,
            role: document.getElementById("user-input-role").value
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
            "telephone": "telephone",
            "userEnabled": "userDisabled",
            "userLocked": "userLocked",
            "userExpired": "userExpired",
            "passwordExpired": "passwordExpired",
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
        document.getElementById("user-input-nameFamily").value = ""
        document.getElementById("user-input-nameGiven").value = ""
        document.getElementById("user-input-telephone").value = ""
        document.getElementById("user-input-userDisabled").checked = false
        document.getElementById("user-input-userLocked").checked = false
        document.getElementById("user-input-userExpired").checked = false
        document.getElementById("user-input-passwordExpired").checked = false
        document.getElementById("user-input-organization").innerHTML = ""
        document.getElementById("user-input-role").innerHTML = ""

        document.getElementById("user-input-username").disabled = false
        document.getElementById("user-input-nameFamily").disabled = false
        document.getElementById("user-input-nameGiven").disabled = false
        document.getElementById("user-input-telephone").disabled = false
        document.getElementById("user-input-userDisabled").disabled = false
        document.getElementById("user-input-userLocked").disabled = false
        document.getElementById("user-input-userExpired").disabled = false
        document.getElementById("user-input-passwordExpired").disabled = false
        document.getElementById("user-input-organization").disabled = false
        document.getElementById("user-input-role").disabled = false
        document.getElementById("user-action-submit-update").disabled = false

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("user-input-username").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-username").innerHTML = ""
        document.getElementById("user-input-nameFamily").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-nameFamily").innerHTML = ""
        document.getElementById("user-input-nameGiven").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-nameGiven").innerHTML = ""
        document.getElementById("user-input-telephone").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-telephone").innerHTML = ""
        document.getElementById("user-input-userDisabled").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-userDisabled").innerHTML = ""
        document.getElementById("user-input-userLocked").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-userLocked").innerHTML = ""
        document.getElementById("user-input-userExpired").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-userExpired").innerHTML = ""
        document.getElementById("user-input-passwordExpired").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-passwordExpired").innerHTML = ""
        document.getElementById("user-input-organization").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-organization").innerHTML = ""
        document.getElementById("user-input-role").classList.remove("is-invalid")
        document.getElementById("user-invalid-feedback-role").innerHTML = ""
    }
}
