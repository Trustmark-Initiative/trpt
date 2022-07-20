function initialize(
    loginUrl,
    loginSuccessUrl) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function login() {

        fetchPost(loginUrl + "?username=" + document.getElementById("login-input-username").value + "&password=" + document.getElementById("login-input-password").value)
            .then(response => response.json())
            .then(response => response.error === undefined ? window.location = loginSuccessUrl : onComplete(response.error))
    }

    function onComplete(message) {

        stateReset()

        document.getElementById("login-action-submit-login").outerHTML = document.getElementById("login-action-submit-login").outerHTML
        document.getElementById("login-input-username").outerHTML = document.getElementById("login-input-username").outerHTML
        document.getElementById("login-input-password").outerHTML = document.getElementById("login-input-password").outerHTML

        document.getElementById("login-action-submit-login").addEventListener("click", login)
        document.getElementById("login-input-username").addEventListener("keypress", event => event.key === "Enter" ? login() : undefined)
        document.getElementById("login-input-password").addEventListener("keypress", event => event.key === "Enter" ? login() : undefined)

        if (message !== undefined) {
            document.getElementById("login-message-container").classList.remove("d-none")
            document.getElementById("login-message").innerHTML = message
        }
    }

    function stateReset() {

        document.getElementById("login-input-username").value = ""
        document.getElementById("login-input-password").value = ""
        document.getElementById("login-message").innerHTML = ""
    }
}
