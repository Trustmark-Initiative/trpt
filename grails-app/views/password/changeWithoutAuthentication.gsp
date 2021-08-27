<!DOCTYPE html>
<html>
    <head>
        <asset:javascript src="password_changeWithoutAuthentication.js"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'password', action: 'resetStatusSubmit')}",
                "${createLink(controller:'password', action: 'changeWithoutAuthenticationSubmit')}")
        </script>

        <meta name="layout" content="main"/>
    </head>

    <body>
        <div class="container pt-4" style="max-width: 540px;" id="password-reset-status">
            <div class="alert alert-primary">
                The system is retrieving your password reset request.
            </div>
        </div>

        <div class="container pt-4 d-none" style="max-width: 540px;" id="password-reset-status-response-failure">
            <div class="alert alert-primary">
                The password reset request has expired.
            </div>
        </div>

        <div class="container pt-4 d-none" style="max-width: 540px;" id="password-reset-status-response-success">
            <div class="border rounded card" autocomplete="off">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-12">
                            <div>Change Password</div>
                        </div>
                    </div>
                </div>

                <div class="card-body">

                    <input type="hidden" id="password-input-external" name="password-external" value="${params.external}"/>

                    <div class="row pb-2">
                        <label id="password-label-password-new-1" class="col-5 col-form-label text-end" for="password-input-password-new-1">New Password</label>

                        <div class="col-7">
                            <input type="password" id="password-input-password-new-1" name="password-new-1" class="form-control"/>

                            <div id="password-invalid-feedback-password-new-1" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="password-label-password-new-2" class="col-5 col-form-label text-end" for="password-input-password-new-2">Confirm New Password</label>

                        <div class="col-7">
                            <input type="password" id="password-input-password-new-2" name="password-new-2" class="form-control"/>

                            <div id="password-invalid-feedback-password-new-2" class="invalid-feedback"></div>
                        </div>
                    </div>
                </div>

                <div class="card-footer text-start">
                    <div class="row">
                        <div class="col-5"></div>

                        <div class="col-7">
                            <button id="password-action-submit" class="btn btn-primary">Change Password</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container pt-4 d-none" style="max-width: 540px;" id="password-change-without-authentication-response-success">
            <div class="alert alert-primary">
                The system has reset your password.
            </div>
        </div>
    </body>
</html>
