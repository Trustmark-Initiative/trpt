function initialize(
    changeWithAuthenticationSubmit) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onReady()
        }
    })

    function onReady() {

        formInitialize()
    }

    function formInitialize() {

        document.getElementById("password-action-submit").addEventListener("click", onSubmit)
    }

    function onSubmit() {

        formReset()

        fetchPost(changeWithAuthenticationSubmit, {
            passwordOld: document.getElementById("password-input-password-old").value,
            passwordNew1: document.getElementById("password-input-password-new-1").value,
            passwordNew2: document.getElementById("password-input-password-new-2").value,
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                onSuccess())
    }

    function formReset() {

        document.getElementById("password-response-success").classList.add("d-none")

        document.getElementById("password-input-password-old").classList.remove("is-invalid")
        document.getElementById("password-invalid-feedback-password-old").innerHTML = ""
        document.getElementById("password-input-password-new-1").classList.remove("is-invalid")
        document.getElementById("password-invalid-feedback-password-new-1").innerHTML = ""
        document.getElementById("password-input-password-new-2").classList.remove("is-invalid")
        document.getElementById("password-invalid-feedback-password-new-2").innerHTML = ""
    }

    function onFailure(failureMapPromise) {

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
            "username": "password-old",
            "passwordOld": "password-old",
            "passwordNew1": "password-new-1",
            "passwordNew2": "password-new-2",
        }

        failureMapPromise.then(failureMap => {

            if (failureMap["passwordOld"] !== undefined && failureMap["passwordOld"][0] !== undefined && failureMap["passwordOld"][0].type === "ValidationMessageMustBeEqual") {

                inputFor("password", labelFor["passwordOld"]).classList.add("is-invalid")

                const div = document.createElement("div")
                div.innerHTML = "Current Password invalid."

                invalidFeedbackFor("password", labelFor["passwordOld"]).appendChild(div);

                delete failureMap["passwordOld"];
            }

            Object.entries(messageMap(failureMap, field => labelInnerHtmlFor("password", labelFor[field]))).forEach(entry => {

                inputFor("password", labelFor[entry[0]]).classList.add("is-invalid")

                entry[1].forEach(element => {

                    const div = document.createElement("div")
                    div.innerHTML = element
                    invalidFeedbackFor("password", labelFor[entry[0]]).appendChild(div);
                })
            })
        });
    }

    function onSuccess() {

        document.getElementById("password-response-success").classList.remove("d-none");
    }
}
