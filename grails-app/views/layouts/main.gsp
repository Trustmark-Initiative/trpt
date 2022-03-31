<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>

        <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>

        <title>${grailsApplication.config.getProperty('server.title')}</title>

        <g:layoutHead/>
    </head>

    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <div class="navbar navbar-collapse p-0">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item fw-bold pe-4"><a class="nav-link" href="${createLink(uri: "/")}">Trustmark Relying Party Tool</a></li>
                        <sec:ifLoggedIn>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <li class="nav-item ps-4 pe-4"><a class="nav-link" href="${createLink(controller: "protectedSystem", action: "manage")}">Protected Systems</a></li>
                                <li class="nav-item pe-4"><a class="nav-link" href="${createLink(controller: "trustmarkBindingRegistry", action: "manage")}">Trustmark Binding Registries</a></li>
                                <li class="nav-item pe-4"><a class="nav-link" href="${createLink(controller: "organization", action: "manage")}">Organizations</a></li>
                                <li class="nav-item pe-4"><a class="nav-link" href="${createLink(controller: "user", action: "manage")}">Users</a></li>
                                <li class="nav-item pe-4"><a class="nav-link" href="${createLink(controller: "mail", action: "manage")}">Mail</a></li>
                                <li class="nav-item pe-4"><a class="nav-link" href="${createLink(controller: "uriSet", action: "manage")}">Status</a></li>
                            </sec:ifAllGranted>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR_ORGANIZATION">
                                <li class="nav-item ps-4 pe-4"><a class="nav-link" href="${createLink(controller: "protectedSystem", action: "manage")}">Protected Systems</a></li>
                                <li class="nav-item pe-4"><a class="nav-link" href="${createLink(controller: "trustmarkBindingRegistry", action: "manage")}">Trustmark Binding Registries</a></li>
                                <li class="nav-item pe-4"><a class="nav-link" href="${createLink(controller: "organization", action: "manage")}">Organizations</a></li>
                                <li class="nav-item pe-4"><a class="nav-link" href="${createLink(controller: "user", action: "manage")}">Users</a></li>
                            </sec:ifAllGranted>
                        </sec:ifLoggedIn>
                    </ul>

                    <ul class="navbar-nav">
                        <sec:ifLoggedIn>
                            <li class="nav-item dropdown">
                                <a id="navbarDropdown" class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <sec:username/>
                                </a>

                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="${createLink(controller: "password", action: "changeWithAuthentication")}">Change Password</a></li>
                                    <li><a class="dropdown-item" href="${createLink(controller: "logout", action: "index")}">Logout</a></li>
                                </ul>
                            </li>
                        </sec:ifLoggedIn>
                        <sec:ifNotLoggedIn>
                            <li class="nav-item"><a class="nav-link" href="${createLink(controller: "login")}">Login</a></li>
                        </sec:ifNotLoggedIn>
                    </ul>
                </div>
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

        <footer class="container pt-4">
            <div class="row">
                <div class="col-12 text-center">
                    Copyright &copy; 2021 Georgia Tech Research Institute
                </div>
            </div>
        </footer>
    </body>
</html>
