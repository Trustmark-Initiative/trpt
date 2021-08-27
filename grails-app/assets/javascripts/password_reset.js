function initialize(
    resetSubmit) {

    document.onreadystatechange = function () {

        if (document.readyState === "complete") {
            onReady()
        }
    }

    function onReady() {

        formInitialize()
    }

    function formInitialize() {

        document.getElementById("password-action-submit").addEventListener("click", onSubmit)
    }

    function onSubmit() {

        formReset()

        fetchPost(resetSubmit, {
            username: document.getElementById("password-input-username").value,
        })
            .then(response => response.status !== 200 ?
                onFailure(response.json()) :
                onSuccess())
    }

    function formReset() {

        document.getElementById("password-response-failure").classList.add("d-none")
        document.getElementById("password-response-success").classList.add("d-none")
    }

    function onFailure(failureMapPromise) {

        document.getElementById("password-response-failure").classList.remove("d-none")
        document.getElementById("password-response-success").classList.add("d-none")
    }

    function onSuccess() {

        document.getElementById("password-response-failure").classList.add("d-none")
        document.getElementById("password-response-success").classList.remove("d-none")
    }
}
