<!DOCTYPE html>
<html>
    <head>
        <asset:javascript src="password_changeWithAuthentication.js"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'password', action: 'changeWithAuthenticationSubmit')}")
        </script>

        <meta name="layout" content="main"/>
    </head>

    <body>
        <div class="container pt-4" style="max-width: 540px;">
            <div class="border rounded card" id="password-form" autocomplete="off">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-12">
                            <div>Change Password</div>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="password-label-password-old" class="col-5 col-form-label text-end" for="password-input-password-old">Current Password</label>

                        <div class="col-7">
                            <input type="password" id="password-input-password-old" name="password-old" class="form-control"/>

                            <div id="password-invalid-feedback-password-old" class="invalid-feedback"></div>
                        </div>
                    </div>

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

        <div class="container pt-4 d-none" style="max-width: 540px;" id="password-response-success">
            <div class="alert alert-primary">
                The system has reset your password.
            </div>
        </div>
    </body>
</html>
