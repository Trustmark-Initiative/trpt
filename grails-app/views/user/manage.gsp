<html>
    <head>
        <asset:javascript src="user_manage.js"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'user', action: 'findAll')}",
                "${createLink(controller:'user', action: 'findOne')}",
                "${createLink(controller:'user', action: 'insert')}",
                "${createLink(controller:'user', action: 'update')}",
                "${createLink(controller:'user', action: 'delete')}",
                "${createLink(controller:'organization', action: 'findAll')}",
                "${createLink(controller:'role', action: 'findAll')}"
            );
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Users</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col"><a href="#" class="bi-plus-lg" id="user-action-insert"></a></th>
                        <th scope="col"><a href="#" class="bi-trash" id="user-action-delete"></a></th>
                        <th scope="col" style="width: 20%">Name</th>
                        <th scope="col" style="width: 30%">Email</th>
                        <th scope="col" style="width: 15%">Telephone</th>
                        <th scope="col" style="width: 20%">Role</th>
                        <th scope="col" style="width: 15%">Organization</th>
                        <th scope="col"><span class="bi-shield-slash" title="Account Disabled"></span></th>
                        <th scope="col"><span class="bi-shield-lock" title="Account Locked"></span></th>
                        <th scope="col"><span class="bi-shield-x" title="Account Expired"></span></th>
                        <th scope="col"><span class="bi-file-medical" title="Password Expired"></span></th>
                    </tr>
                </thead>
                <template id="user-template-empty">
                    <tr>
                        <td colspan="11">(No users.)</td>
                    </tr>
                </template>
                <template id="user-template-summary">
                    <tr>
                        <td><a href="#" class="bi-pencil user-action-update"></a></td>
                        <td><input type="checkbox" class="form-check-input user-action-delete-queue"></td>
                        <td class="user-element-name"></td>
                        <td><a href="" class="user-element-username"></a></td>
                        <td><a href="" class="user-element-telephone"></a></td>
                        <td class="user-element-role"></td>
                        <td class="user-element-organization"></td>
                        <td class="user-element-userDisabled" title="Account Disabled"></td>
                        <td class="user-element-userLocked" title="Account Locked"></td>
                        <td class="user-element-userExpired" title="Account Expired"></td>
                        <td class="user-element-passwordExpired" title="Password Expired"></td>
                    </tr>
                </template>
                <tbody id="user-tbody">
                </tbody>
            </table>
        </div>

        <template id="user-template-delete-response-failure">
            <div class="alert alert-danger">
                <div class="text-decoration-underline user-element-name"></div>

                <div class="pt-4 user-element-message"></div>
            </div>
        </template>

        <div class="container pt-4 d-none" id="user-delete-response-failure">
        </div>

        <div class="container pt-4 d-none" id="user-form">
            <div class="border rounded card">
                <input id="user-input-id" name="id" type="hidden"/>

                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            <div class="d-none" id="user-form-header-insert">Add User</div>

                            <div class="d-none" id="user-form-header-update">Edit User</div>
                        </div>

                        <div class="col-1 text-end">
                            <button id="user-action-cancel" type="button" class="btn-close p-0 align-middle"></button>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="user-label-organization" class="col-2 col-form-label text-end label-required" for="user-input-organization">Organization</label>

                        <div class="col-10">
                            <select id="user-input-organization" name="organization" class="form-select" aria-describedby="user-invalid-feedback-organization"></select>

                            <div id="user-invalid-feedback-organization" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-role" class="col-2 col-form-label text-end label-required" for="user-input-role">Role</label>

                        <div class="col-10">
                            <select id="user-input-role" name="role" class="form-select" aria-describedby="user-invalid-feedback-role"></select>

                            <div id="user-invalid-feedback-role" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-username" class="col-2 col-form-label text-end label-required" for="user-input-username">Email</label>

                        <div class="col-10">
                            <input type="text" id="user-input-username" name="username" class="form-control" aria-describedby="user-invalid-feedback-username"/>

                            <div id="user-invalid-feedback-username" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-nameGiven" class="col-2 col-form-label text-end label-required" for="user-input-nameGiven">Given Name</label>

                        <div class="col-10">
                            <input type="text" id="user-input-nameGiven" name="nameGiven" class="form-control" aria-describedby="user-invalid-feedback-nameGiven"/>

                            <div id="user-invalid-feedback-nameGiven" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-nameFamily" class="col-2 col-form-label text-end label-required" for="user-input-nameFamily">Family Name</label>

                        <div class="col-10">
                            <input type="text" id="user-input-nameFamily" name="nameFamily" class="form-control" aria-describedby="user-invalid-feedback-nameFamily"/>

                            <div id="user-invalid-feedback-nameFamily" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-telephone" class="col-2 col-form-label text-end label-required" for="user-input-telephone">Telephone</label>

                        <div class="col-10">
                            <input type="text" id="user-input-telephone" name="telephone" class="form-control" aria-describedby="user-invalid-feedback-telephone"/>

                            <div id="user-invalid-feedback-telephone" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-userDisabled" class="col-2 form-check-label text-end" for="user-input-userDisabled">Account Disabled</label>

                        <div class="col-10">
                            <input type="checkbox" id="user-input-userDisabled" name="userDisabled" class=" form-check-input" aria-describedby="user-invalid-feedback-userDisabled"/>

                            <div id="user-invalid-feedback-userDisabled" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-userLocked" class="col-2 form-check-label text-end" for="user-input-userLocked">Account Locked</label>

                        <div class="col-10">
                            <input type="checkbox" id="user-input-userLocked" name="userLocked" class=" form-check-input" aria-describedby="user-invalid-feedback-userLocked"/>

                            <div id="user-invalid-feedback-userLocked" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-userExpired" class="col-2 form-check-label text-end" for="user-input-userExpired">Account Expired</label>

                        <div class="col-10">
                            <input type="checkbox" id="user-input-userExpired" name="userExpired" class=" form-check-input" aria-describedby="user-invalid-feedback-userExpired"/>

                            <div id="user-invalid-feedback-userExpired" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="user-label-passwordExpired" class="col-2 form-check-label text-end" for="user-input-passwordExpired">Password Expired</label>

                        <div class="col-10">
                            <input type="checkbox" id="user-input-passwordExpired" name="passwordExpired" class="form-check-input" aria-describedby="user-invalid-feedback-passwordExpired"/>

                            <div id="user-invalid-feedback-passwordExpired" class="invalid-feedback"></div>
                        </div>
                    </div>
                </div>

                <div class="card-footer text-start">
                    <div class="row">
                        <div class="col-2"></div>

                        <div class="col-10">
                            <button id="user-action-submit-insert" type="button" class="btn btn-primary d-none">Add</button>
                            <button id="user-action-submit-update" type="button" class="btn btn-primary d-none">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
