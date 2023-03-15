<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>

        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
        <asset:javascript src="utility.js"/>
        <asset:javascript src="profile.js"/>
        <asset:javascript src="actuator.js"/>

        <script type="text/javascript">
            profile(
                "${createLink(controller:'profile', action: 'findOne')}");

            actuator(
                "${createLink(uri:'/actuator/info')}");
        </script>

        <title>${grailsApplication.config.getProperty('server.title')}</title>

        <g:layoutHead/>
    </head>

    <body>
        <main>
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item fw-bold pe-4"><a class="nav-link" href="${createLink(uri: "/")}">Trustmark Relying Party Tool</a></li>
                        <li class="d-none nav-item ps-4 pe-4 role-administrator-organization role-administrator"><a class="nav-link" href="${createLink(controller: "protectedSystem", action: "manage")}">Protected Systems</a></li>
                        <li class="d-none nav-item pe-4      role-administrator-organization role-administrator"><a class="nav-link" href="${createLink(controller: "trustmarkBindingRegistry", action: "manage")}">Trustmark Binding Registries</a></li>
                        <li class="d-none nav-item pe-4      role-administrator-organization role-administrator"><a class="nav-link" href="${createLink(controller: "organization", action: "manage")}">Organizations</a></li>
                        <li class="d-none nav-item pe-4      role-administrator-organization role-administrator"><a class="nav-link" href="${createLink(controller: "user", action: "manage")}">Users</a></li>
                        <li class="d-none nav-item pe-4                                      role-administrator"><a class="nav-link" href="${createLink(controller: "oidcClientRegistration", action: "manage")}">Clients</a></li>
                        <li class="d-none nav-item pe-4                                      role-administrator"><a class="nav-link" href="${createLink(controller: "email", action: "settings")}">Mail</a></li>
                        <li class="d-none nav-item pe-4                                      role-administrator"><a class="nav-link" href="${createLink(controller: "uriSet", action: "manage")}">Status</a></li>
                    </ul>
                    <ul class="navbar-nav">
                        <li class="d-none log-in nav-item dropdown">
                            <a id="navbarDropdown" class="nav-link" href="#" role="button"></a>
                        </li>
                    </ul>
                </div>
            </nav>

            <div class="container pt-4">
                <div class="row">
                    <div class="col-12">
                        <asset:image src="tmi-header.png" height="90em"/>
                    </div>
                </div>
            </div>

            <g:layoutBody/>

            <div class="modal" tabindex="-1" id="modal-role-organization">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Welcome</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body">
                            <p class="d-none" id="modal-role-organization-user-without-role">${raw(grailsApplication.config.getProperty('server.userWithoutRole'))}</p class="d-none">

                            <p class="d-none" id="modal-role-organization-user-without-organization">${raw(grailsApplication.config.getProperty('server.userWithoutOrganization'))}</p>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <footer class="navbar navbar-expand-lg navbar-dark bg-dark mt-4 p-2">
            <div class="container">
                <div class="navbar-nav mx-auto">
                    <a class="nav-link">Version <g:meta name="info.app.version"/>; Build Date <span id="git-commit-time"></span></a>
                </div>
            </div>
        </footer>
    </body>
</html>
