function profile(
    profileFindOneUrl,
    actuatorInfoUrl) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function onComplete() {

        find()
    }

    function find() {

        fetchGet(profileFindOneUrl)
            .then(response => response.json())
            .then(profile => fetchGet(actuatorInfoUrl)
                .then(response => response.json())
                .then(info => afterFind(profile, info)))
    }

    function afterFind(profile, info) {

        if (profile.role !== null) {
            if (profile.role.value === "ROLE_ADMINISTRATOR_ORGANIZATION") {
                Array.from(document.querySelectorAll(".role-administrator-organization"))
                    .forEach(element => element.classList.remove("d-none"))
            }
            if (profile.role.value === "ROLE_ADMINISTRATOR") {
                Array.from(document.querySelectorAll(".role-administrator"))
                    .forEach(element => element.classList.remove("d-none"))
            }
        }

        if (profile.username === null) {

            Array.from(document.querySelectorAll(".log-out"))
                .forEach(element => element.classList.remove("d-none"))

        } else {

            Array.from(document.querySelectorAll(".log-in"))
                .forEach(element => element.classList.remove("d-none"))

            document.getElementById("navbarDropdown").innerHTML = profile.username
        }

        document.getElementById("git-commit-time").innerHTML = `${moment(new Number(info.git.commit.time) * 1000).format("MMMM Do, YYYY")} ${moment(new Number(info.git.commit.time) * 1000).format("h:mm:ss A UTC")}`;
        document.getElementById("application-version").innerHTML = profile.applicationVersion;
    }
}
