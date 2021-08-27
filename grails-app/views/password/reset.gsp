<!DOCTYPE html>
<html>
    <head>
        <asset:javascript src="password_reset.js"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'password', action: 'resetSubmit')}")
        </script>

        <meta name="layout" content="main"/>
    </head>

    <body>

        <div class="container pt-4" style="max-width: 540px;">
            <div class="border rounded card" id="password-form" autocomplete="off">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-12">
                            <div>Reset Password</div>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="password-label-username" class="col-3 col-form-label text-end" for="password-input-username">Username</label>

                        <div class="col-9">
                            <input type="text" id="password-input-username" name="username" class="form-control"/>
                        </div>
                    </div>
                </div>

                <div class="card-footer">
                    <div class="row">
                        <div class="col-3"></div>

                        <div class="col-9 text-start">
                            <button id="password-action-submit" class="btn btn-primary">Reset Password</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container pt-4 d-none" style="max-width: 540px;" id="password-response-success">
            <div class="alert alert-primary">
                The system will send an email to the given address, if the email is known to the system.
            </div>
        </div>

        <div class="container pt-4 d-none" style="max-width: 540px;" id="password-response-failure">
            <div class="alert alert-danger">
                The system encountered an error while processing your request.
            </div>
        </div>
    </body>
</html>
