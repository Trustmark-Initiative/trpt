<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
    </head>

    <body>

        <div class="container pt-4" style="max-width: 540px;">
            <form action="/login/authenticate" method="POST" class="border rounded card" id="login-form" autocomplete="off">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-12">
                            <div>Login</div>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="login-label-username" class="col-3 col-form-label text-end" for="login-input-username">Username</label>

                        <div class="col-9">
                            <input type="text" id="login-input-username" name="username" class="form-control"/>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="login-label-password" class="col-3 col-form-label text-end" for="login-input-password">Password</label>

                        <div class="col-9">
                            <input type="password" id="login-input-password" name="password" class="form-control">
                        </div>
                    </div>
                </div>

                <div class="card-footer">
                    <div class="row">
                        <div class="col-3"></div>

                        <div class="col-1 text-start">
                            <input id="login-action-submit-login" type="submit" class="btn btn-primary" value="Login">
                        </div>

                        <div class="col-8 d-flex align-items-center">
                            <a href="${createLink(controller: "password", action: "reset")}" class="ms-auto">Forgot Password?</a>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <g:if test="${flash.message}">
            <div class="container pt-4" style="max-width: 540px;">
                <div class="alert alert-danger text-center">
                    ${flash.message}
                </div>
            </div>
        </g:if>
    </body>
</html>
