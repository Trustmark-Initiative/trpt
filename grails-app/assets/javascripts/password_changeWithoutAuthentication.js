function initialize(
    resetStatusSubmit,
    changeWithoutAuthenticationSubmit) {

    document.onreadystatechange = function () {

        if (document.readyState === "complete") {
            onReady()
        }
    }

    function onReady() {

        formInitialize()

        fetchPost(resetStatusSubmit, {
            external: document.getElementById("password-input-external").value
        })
            .then(response => response.status !== 200 ?
                onResetStatusFailure(response.json()) :
                onResetStatusSuccess())
    }

    function formInitialize() {

        document.getElementById("password-action-submit").addEventListener("click", onSubmit)
    }

    function onResetStatusFailure() {

        document.getElementById("password-reset-status").classList.add("d-none");
        document.getElementById("password-reset-status-response-failure").classList.remove("d-none");
        document.getElementById("password-reset-status-response-success").classList.add("d-none");
        document.getElementById("password-change-without-authentication-response-success").classList.add("d-none");
    }

    function onResetStatusSuccess() {

        document.getElementById("password-reset-status").classList.add("d-none");
        document.getElementById("password-reset-status-response-failure").classList.add("d-none");
        document.getElementById("password-reset-status-response-success").classList.remove("d-none");
        document.getElementById("password-change-without-authentication-response-success").classList.add("d-none");
    }

    function onSubmit() {

        formReset();

        fetchPost(changeWithoutAuthenticationSubmit, {
            external: document.getElementById("password-input-external").value,
            passwordNew1: document.getElementById("password-input-password-new-1").value,
            passwordNew2: document.getElementById("password-input-password-new-2").value,
        })
            .then(response => response.status !== 200 ?
                onChangeWithoutAuthenticationFailure(response.json()) :
                onChangeWithoutAuthenticationSuccess())
    }

    function formReset() {

        document.getElementById("password-reset-status").classList.add("d-none");
        document.getElementById("password-reset-status-response-failure").classList.add("d-none");
        document.getElementById("password-reset-status-response-success").classList.remove("d-none");
        document.getElementById("password-change-without-authentication-response-success").classList.add("d-none");

        document.getElementById("password-input-password-new-1").classList.remove("is-invalid")
        document.getElementById("password-invalid-feedback-password-new-1").innerHTML = ""
        document.getElementById("password-input-password-new-2").classList.remove("is-invalid")
        document.getElementById("password-invalid-feedback-password-new-2").innerHTML = ""
    }

    function onChangeWithoutAuthenticationFailure(failureMapPromise) {

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
            "passwordNew1": "password-new-1",
            "passwordNew2": "password-new-2",
        }

        failureMapPromise.then(failureMap => {

            if (failureMap["external"] !== undefined && failureMap["external"][0] !== undefined && failureMap["external"][0].type === "ValidationMessageMustBeReference") {
                onResetStatusFailure()
            } else {
                Object.entries(messageMap(failureMap, field => labelInnerHtmlFor("password", labelFor[field]))).forEach(entry => {

                    inputFor("password", labelFor[entry[0]]).classList.add("is-invalid")

                    entry[1].forEach(element => {

                        const div = document.createElement("div")
                        div.innerHTML = element
                        invalidFeedbackFor("password", labelFor[entry[0]]).appendChild(div);
                    })
                })
            }
        });
    }

    function onChangeWithoutAuthenticationSuccess() {

        document.getElementById("password-change-without-authentication-response-success").classList.remove("d-none");
    }
}
