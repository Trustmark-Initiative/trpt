<html>
    <head>
        <asset:javascript src="organization_manage.js"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'profile', action: 'findOne')}",
                "${createLink(controller:'organization', action: 'findAll')}",
                "${createLink(controller:'organization', action: 'findOne')}",
                "${createLink(controller:'organization', action: 'insert')}",
                "${createLink(controller:'organization', action: 'update')}",
                "${createLink(controller:'organization', action: 'delete')}",
                "${createLink(controller:'organizationDashboard', action: 'manage')}")
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Organizations</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col"><a href="#" class="d-none role-administrator bi-plus-lg" id="entity-action-insert"></a><div class="d-none role-administrator-organization" style="width: 17px"></div></th>
                        <th scope="col"><a href="#" class="bi-trash" id="entity-action-delete"></a></th>
                        <th scope="col" style="width:33%">Name</th>
                        <th scope="col" style="width:33%">URL</th>
                        <th scope="col" style="width:33%">Description</th>
                    </tr>
                </thead>
                <template id="entity-template-empty">
                    <tr>
                        <td colspan="6">(No organizations.)</td>
                    </tr>
                </template>
                <template id="entity-template-summary">
                    <tr>
                        <td><a href="#" class="bi-pencil entity-action-update"></a></td>
                        <td><input type="checkbox" class="form-check-input entity-action-delete-queue"></td>
                        <td><a class="entity-element-name"></a></td>
                        <td><a href="" target="_blank" class="organization-element-uri"></a></td>
                        <td><div class="organization-element-description"></div></td>
                    </tr>
                </template>
                <tbody id="entity-tbody" class="placeholder-glow">
                    <td><span class="placeholder-hack col-3"></span></td>
                    <td><span class="placeholder-hack col-3"></span></td>
                    <td><span class="placeholder-hack col-3"></span></td>
                    <td><span class="placeholder-hack col-3"></span></td>
                    <td><span class="placeholder-hack col-3"></span></td>
                </tbody>
            </table>
        </div>

        <template id="entity-template-delete-response-failure">
            <div class="alert alert-danger">
                <div class="text-decoration-underline entity-element-name"></div>

                <ul class="pt-2 mb-0 entity-element-message"></ul>
            </div>
        </template>

        <div class="container pt-4 d-none" id="entity-delete-response-failure">
        </div>

        <div class="container pt-4 d-none" id="entity-form">
            <div class="border rounded card">
                <input id="organization-input-id" name="id" type="hidden"/>

                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            <div class="d-none" id="entity-form-header-insert">Add Organization</div>

                            <div class="d-none" id="entity-form-header-update">Edit Organization</div>
                        </div>

                        <div class="col-1 text-end">
                            <button id="entity-action-cancel" type="button" class="btn-close p-0 align-middle"></button>
                        </div>
                    </div>
                </div>


                <div class="card-body">
                    <div class="row pb-2">
                        <label id="organization-label-name" class="col-3 col-form-label text-end label-required" for="organization-input-name">Name</label>

                        <div class="col-9">
                            <input type="text" id="organization-input-name" name="name" class="form-control" aria-describedby="organization-invalid-feedback-name"/>

                            <div id="organization-invalid-feedback-name" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="organization-label-uri" class="col-3 col-form-label text-end label-required" for="organization-input-uri">URL</label>

                        <div class="col-9">
                            <input type="text" id="organization-input-uri" name="uri" class="form-control" aria-describedby="organization-invalid-feedback-uri">

                            <div id="organization-invalid-feedback-uri" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="organization-label-description" class="col-3 col-form-label text-end" for="organization-input-description">Description</label>

                        <div class="col-9">
                            <textarea id="organization-input-description" name="description" class="form-control" aria-describedby="description-invalid-feedback"></textarea>

                            <div id="organization-invalid-feedback-description" class="invalid-feedback"></div>
                        </div>
                    </div>
                </div>

                <div class="card-footer text-start">
                    <div class="row">
                        <div class="col-3"></div>

                        <div class="col-9">
                            <button id="entity-action-submit-insert" type="button" class="btn btn-primary d-none">Add</button>
                            <button id="entity-action-submit-update" type="button" class="btn btn-primary d-none">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
