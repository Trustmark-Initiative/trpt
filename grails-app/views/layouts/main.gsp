<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>

        <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
        <asset:javascript src="profile.js"/>

        <script type="text/javascript">
            profile(
                "${createLink(controller:'profile', action: 'findOne')}",
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
                        <li class="d-none nav-item pe-4                                      role-administrator"><a class="nav-link" href="${createLink(controller: "mail", action: "manage")}">Mail</a></li>
                        <li class="d-none nav-item pe-4                                      role-administrator"><a class="nav-link" href="${createLink(controller: "uriSet", action: "manage")}">Status</a></li>
                    </ul>
                    <ul class="navbar-nav">
                        <li class="d-none log-in  nav-item dropdown">
                            <a id="navbarDropdown" class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"></a>

                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${createLink(controller: "password", action: "changeWithAuthentication")}">Change Password</a></li>
                                <li><a class="dropdown-item" href="${createLink(controller: "logout", action: "index")}">Logout</a></li>
                            </ul>
                        </li>
                        <li class="d-none log-out nav-item"><a class="nav-link" href="${createLink(controller: "login")}">Login</a></li>
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
        </main>

        <footer class="navbar navbar-expand-lg navbar-dark bg-dark mt-4 p-2">
            <div class="container">
                <div class="navbar-nav mx-auto">
                    <a class="nav-link">Version <span id="application-version"></span>; Build Date <span id="git-commit-time"></span></a>
                </div>
            </div>
        </footer>
    </body>
</html>
