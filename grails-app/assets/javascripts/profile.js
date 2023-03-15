function profile(
    profileFindOneUrl) {

    return fetchGet(profileFindOneUrl)
        .then(response => response.json())
        .then(profile => {

            function modal(userWithoutRole, userWithoutOrganization) {

                if (userWithoutRole) {
                    document.getElementById("modal-role-organization-user-without-role").classList.remove("d-none")
                }

                if (userWithoutOrganization) {
                    document.getElementById("modal-role-organization-user-without-organization").classList.remove("d-none")
                }

                if (userWithoutRole) {
                    new bootstrap.Modal(document.getElementById("modal-role-organization"), {}).show()
                }
            }

            function showUsername() {
                Array.from(document.querySelectorAll(".log-in"))
                    .forEach(element => element.classList.remove("d-none"))

                document.getElementById("navbarDropdown").innerHTML = profile.user.username
            }

            function showRoleAdministratorOrganization(userWithoutOrganization) {
                showUsername()

                Array.from(document.querySelectorAll(".role-administrator-organization"))
                    .forEach(element => element.classList.remove("d-none"))

                modal(false, userWithoutOrganization)
            }

            function showRoleAdministrator(userWithoutOrganization) {
                showUsername()

                Array.from(document.querySelectorAll(".role-administrator"))
                    .forEach(element => element.classList.remove("d-none"))

                modal(false, userWithoutOrganization)
            }

            function roleValue() {
                return profile.user === undefined || profile.user === null || profile.user.role === undefined || profile.user.role === null || !(profile.user.role.value === "trpt-org-admin" || profile.user.role.value === "trpt-admin") ?
                    undefined :
                    profile.user.role.value
            }

            function organization() {
                return profile.user === undefined || profile.user === null || profile.user.organization === undefined || profile.user.organization === null ?
                    undefined :
                    profile.user.organization;
            }

            if (roleValue() === undefined) {
                modal(true, organization() === undefined)
            } else if (roleValue() === "trpt-org-admin") {
                showRoleAdministratorOrganization(organization() === undefined)
            } else if (roleValue() === "trpt-admin") {
                showRoleAdministrator(organization() === undefined)
            }

            return roleValue()
        });
}
