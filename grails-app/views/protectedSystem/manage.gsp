<html>
    <head>
        <asset:javascript src="protectedSystem_manage.js"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'protectedSystem', action: 'findAll')}",
                "${createLink(controller:'protectedSystem', action: 'findOne')}",
                "${createLink(controller:'protectedSystem', action: 'insert')}",
                "${createLink(controller:'protectedSystem', action: 'update')}",
                "${createLink(controller:'protectedSystem', action: 'delete')}",
                "${createLink(controller:'protectedSystemDashboard', action: 'manage')}",
                "${createLink(controller:'organization', action: 'findAll')}",
                "${createLink(controller:'protectedSystem', action: 'typeFindAll')}")
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Protected Systems</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col"><a href="#" class="bi-plus-lg" id="entity-action-insert"></a></th>
                        <th scope="col"><a href="#" class="bi-trash" id="entity-action-delete"></a></th>
                        <th scope="col" style="width: 50%">Name</th>
                        <th scope="col" style="width: 25%">Type</th>
                        <th scope="col" style="width: 25%">Organization</th>
                        <th scope="col" style="width: 0%">TIP#</th>
                    </tr>
                </thead>
                <template id="entity-template-empty">
                    <tr>
                        <td colspan="6">(No protected systems.)</td>
                    </tr>
                </template>
                <template id="entity-template-summary">
                    <tr>
                        <td><a href="#" class="bi-pencil entity-action-update"></a></td>
                        <td><input type="checkbox" class="form-check-input entity-action-delete-queue"></td>
                        <td><a class="entity-element-name"></a></td>
                        <td><div class="protected-system-element-type"></div></td>
                        <td><div class="protected-system-element-organization"></div></td>
                        <td><div class="text-center protected-system-element-tip-count"></div></td>
                    </tr>
                </template>
                <tbody id="entity-tbody" class="placeholder-glow">
                    <td><span class="placeholder-hack col-3"></span></td>
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
                <input id="protected-system-input-id" name="id" type="hidden"/>

                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            <div class="d-none" id="entity-form-header-insert">Add Protected System</div>

                            <div class="d-none" id="entity-form-header-update">Edit Protected System</div>
                        </div>

                        <div class="col-1 text-end">
                            <button id="entity-action-cancel" type="button" class="btn-close p-0 align-middle"></button>
                        </div>
                    </div>
                </div>


                <div class="card-body">
                    <div class="row pb-2">
                        <label id="protected-system-label-organization" class="col-3 col-form-label text-end label-required" for="protected-system-input-organization">Organization</label>

                        <div class="col-9">
                            <select id="protected-system-input-organization" name="organization" class="form-select" aria-describedby="protected-system-invalid-feedback-organization"></select>

                            <div id="protected-system-invalid-feedback-organization" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="protected-system-label-type" class="col-3 col-form-label text-end label-required" for="protected-system-input-type">Type</label>

                        <div class="col-9">
                            <select id="protected-system-input-type" name="type" class="form-select" aria-describedby="protected-system-invalid-feedback-type"></select>

                            <div id="protected-system-invalid-feedback-type" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="protected-system-label-name" class="col-3 col-form-label text-end label-required" for="protected-system-input-name">Name</label>

                        <div class="col-9">
                            <input type="text" id="protected-system-input-name" name="name" class="form-control" aria-describedby="protected-system-invalid-feedback-name"/>

                            <div id="protected-system-invalid-feedback-name" class="invalid-feedback"></div>
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
