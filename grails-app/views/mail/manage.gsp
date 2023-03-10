<!DOCTYPE html>
<html>
    <head>
        <asset:javascript src="mail_manage.js"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'profile', action: 'findOne')}",
                "${createLink(controller:'mail', action: 'find')}",
                "${createLink(controller:'mail', action: 'update')}",
                "${createLink(controller:'mail', action: 'test')}")
        </script>

        <meta name="layout" content="main"/>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Mail Relay Settings and Test</h2>

            <div class="mt-2">Add mail relay settings. The system connects to the given host to send email, including password reset notifications and trust landscape change notifications.  Your mail relay host may or may not require a username and password.</div>

            <div class="border rounded card mt-2">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            <div>Mail Relay Settings</div>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="mail-setting-label-host" class="col-2 col-form-label text-end label-required" for="mail-setting-input-host">Mail Relay Host</label>

                        <div class="col-10">
                            <input type="text" id="mail-setting-input-host" name="mail-setting-input-host" class="form-control" aria-describedby="mail-setting-invalid-feedback-host"/>

                            <div id="mail-setting-invalid-feedback-host" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="mail-setting-label-port" class="col-2 col-form-label text-end label-required" for="mail-setting-input-port">Mail Relay Port</label>

                        <div class="col-10">
                            <input type="text" id="mail-setting-input-port" name="mail-setting-input-port" class="form-control" aria-describedby="mail-setting-invalid-feedback-port"/>

                            <div id="mail-setting-invalid-feedback-port" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="mail-setting-label-author" class="col-2 col-form-label text-end label-required" for="mail-setting-input-author">Sender Address</label>

                        <div class="col-10">
                            <input type="text" id="mail-setting-input-author" name="mail-setting-input-author" class="form-control" aria-describedby="mail-setting-invalid-feedback-author"/>

                            <div id="mail-setting-invalid-feedback-author" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="mail-setting-label-username" class="col-2 col-form-label text-end" for="mail-setting-input-username">Mail Relay Username</label>

                        <div class="col-10">
                            <input type="text" id="mail-setting-input-username" name="mail-setting-input-username" class="form-control" aria-describedby="mail-setting-invalid-feedback-username"/>

                            <div id="mail-setting-invalid-feedback-username" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="mail-setting-label-password" class="col-2 col-form-label text-end" for="mail-setting-input-username">Mail Relay Password</label>

                        <div class="col-10">
                            <input type="text" id="mail-setting-input-password" name="mail-setting-input-password" class="form-control" aria-describedby="mail-setting-invalid-feedback-password"/>

                            <div id="mail-setting-invalid-feedback-password" class="invalid-feedback"></div>
                        </div>
                    </div>
                </div>

                <div class="card-footer text-start">
                    <div class="row">
                        <div class="col-2"></div>

                        <div class="col-10">
                            <button id="mail-setting-action-submit" type="button" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container pt-4 d-none" style="max-width: 540px;" id="mail-setting-response-success">
            <div class="alert alert-primary">
                The system has updated your mail relay settings.
            </div>
        </div>

        <div class="container pt-4">
            <div class="border rounded card mt-2">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            <div>Mail Relay Test</div>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="mail-test-label-recipient" class="col-2 col-form-label text-end label-required" for="mail-test-input-recipient">Recipient Address</label>

                        <div class="col-10">
                            <input type="text" id="mail-test-input-recipient" name="mail-test-input-recipient" class="form-control" aria-describedby="mail-test-invalid-feedback-recipient"/>

                            <div id="mail-test-invalid-feedback-recipient" class="invalid-feedback"></div>
                        </div>
                    </div>
                </div>

                <div class="card-footer text-start">
                    <div class="row">
                        <div class="col-2"></div>

                        <div class="col-10">
                            <button id="mail-test-action-submit" type="button" class="btn btn-primary">Test</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container pt-4 d-none" style="max-width: 540px;" id="mail-test-response-success">
            <div class="alert alert-primary">
                The system has sent an email.
            </div>
        </div>
    </body>
</html>
