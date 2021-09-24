function initialize(
    mailFindUrl,
    mailUpdateUrl,
    mailTestUrl) {

    document.onreadystatechange = function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    }

    function onComplete() {

        find()
    }

    function find() {

        fetchGet(mailFindUrl)
            .then(response => response.json())
            .then(afterFind)
    }

    function afterFind(mail) {

        stateReset()

        document.getElementById("mail-setting-action-submit").outerHTML = document.getElementById("mail-setting-action-submit").outerHTML
        document.getElementById("mail-test-action-submit").outerHTML = document.getElementById("mail-test-action-submit").outerHTML

        document.getElementById("mail-setting-action-submit").addEventListener("click", onUpdateSubmit)
        document.getElementById("mail-test-action-submit").addEventListener("click", onTestSubmit)

        document.getElementById("mail-setting-input-username").value = mail.username
        document.getElementById("mail-setting-input-password").value = mail.password
        document.getElementById("mail-setting-input-host").value = mail.host
        document.getElementById("mail-setting-input-port").value = mail.port
        document.getElementById("mail-setting-input-author").value = mail.author
    }

    function stateReset() {

        formResetValue()
    }

    function formResetValue() {

        document.getElementById("mail-setting-input-username").value = ""
        document.getElementById("mail-setting-input-password").value = ""
        document.getElementById("mail-setting-input-host").value = ""
        document.getElementById("mail-setting-input-port").value = ""
        document.getElementById("mail-setting-input-author").value = ""
        document.getElementById("mail-test-input-recipient").value = ""

        formResetValidation()
    }

    function formResetValidation() {

        document.getElementById("mail-setting-response-success").classList.add("d-none")
        document.getElementById("mail-test-response-success").classList.add("d-none")

        document.getElementById("mail-setting-input-username").classList.remove("is-invalid")
        document.getElementById("mail-setting-invalid-feedback-username").innerHTML = ""
        document.getElementById("mail-setting-input-password").classList.remove("is-invalid")
        document.getElementById("mail-setting-invalid-feedback-password").innerHTML = ""
        document.getElementById("mail-setting-input-host").classList.remove("is-invalid")
        document.getElementById("mail-setting-invalid-feedback-host").innerHTML = ""
        document.getElementById("mail-setting-input-port").classList.remove("is-invalid")
        document.getElementById("mail-setting-invalid-feedback-port").innerHTML = ""
        document.getElementById("mail-setting-input-author").classList.remove("is-invalid")
        document.getElementById("mail-setting-invalid-feedback-author").innerHTML = ""
        document.getElementById("mail-test-input-recipient").classList.remove("is-invalid")
        document.getElementById("mail-test-invalid-feedback-recipient").innerHTML = ""
    }

    function onUpdateSubmit() {

        formResetValidation()

        fetchPost(mailUpdateUrl, {
            username: document.getElementById("mail-setting-input-username").value,
            password: document.getElementById("mail-setting-input-password").value,
            host: document.getElementById("mail-setting-input-host").value,
            port: document.getElementById("mail-setting-input-port").value,
            author: document.getElementById("mail-setting-input-author").value,
        })
            .then(response => response.status !== 200 ?
                onUpdateFailure(response.json()) :
                onUpdateSuccess())
    }

    function onUpdateFailure(failureMapPromise) {

        function labelInnerHtmlFor(entityName, elementName) {

            return document.getElementById(`${entityName}-label-${elementName}`).innerHTML
        }

        function inputFor(entityName, elementName) {

            return document.getElementById(`${entityName}-input-${elementName}`)
        }

        function invalidFeedbackFor(entityName, elementName) {

            return document.getElementById(`${entityName}-invalid-feedback-${elementName}`)
        }

        const labelFor = {
            "username": "username",
            "password": "password",
            "host": "host",
            "port": "port",
            "author": "author",
        }

        failureMapPromise.then(failureMap => {

            if (failureMap["passwordOld"] !== undefined && failureMap["passwordOld"][0] !== undefined && failureMap["passwordOld"][0].type === "ValidationMessageMustBeEqual") {

                inputFor("mail-setting", labelFor["passwordOld"]).classList.add("is-invalid")

                const div = document.createElement("div")
                div.innerHTML = "Current Password invalid."

                invalidFeedbackFor("mail-setting", labelFor["passwordOld"]).appendChild(div);

                delete failureMap["passwordOld"];
            }

            Object.entries(messageMap(failureMap, field => labelInnerHtmlFor("mail-setting", labelFor[field]))).forEach(entry => {

                inputFor("mail-setting", labelFor[entry[0]]).classList.add("is-invalid")

                entry[1].forEach(element => {

                    const div = document.createElement("div")
                    div.innerHTML = element
                    invalidFeedbackFor("mail-setting", labelFor[entry[0]]).appendChild(div);
                })
            })
        });
    }

    function onUpdateSuccess() {

        document.getElementById("mail-setting-response-success").classList.remove("d-none");
    }

    function onTestSubmit() {

        formResetValidation()

        fetchPost(mailTestUrl, {
            recipient: document.getElementById("mail-test-input-recipient").value
        })
            .then(response => response.status !== 200 ?
                onTestFailure(response.json()) :
                onTestSuccess())
    }

    function onTestFailure(failureMapPromise) {

        function labelInnerHtmlFor(entityName, elementName) {

            return document.getElementById(`${entityName}-label-${elementName}`).innerHTML
        }

        function inputFor(entityName, elementName) {

            return document.getElementById(`${entityName}-input-${elementName}`)
        }

        function invalidFeedbackFor(entityName, elementName) {

            return document.getElementById(`${entityName}-invalid-feedback-${elementName}`)
        }

        const labelFor = {
            "recipient": "recipient"
        }

        failureMapPromise.then(failureMap => {
            Object.entries(messageMap(failureMap, field => labelInnerHtmlFor("mail-test", labelFor[field]))).forEach(entry => {

                inputFor("mail-test", labelFor[entry[0]]).classList.add("is-invalid")

                entry[1].forEach(element => {

                    const div = document.createElement("div")
                    div.innerHTML = element
                    invalidFeedbackFor("mail-test", labelFor[entry[0]]).appendChild(div);
                })
            })
        });
    }

    function onTestSuccess() {

        document.getElementById("mail-test-response-success").classList.remove("d-none");
    }
}
