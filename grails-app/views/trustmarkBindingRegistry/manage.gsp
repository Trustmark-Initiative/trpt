<html>
    <head>
        <asset:javascript src="trustmarkBindingRegistry_manage.js"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'trustmarkBindingRegistry', action: 'findAll')}",
                "${createLink(controller:'trustmarkBindingRegistry', action: 'findOne')}",
                "${createLink(controller:'trustmarkBindingRegistry', action: 'insert')}",
                "${createLink(controller:'trustmarkBindingRegistry', action: 'update')}",
                "${createLink(controller:'trustmarkBindingRegistry', action: 'delete')}",
                "${createLink(controller:'organization', action: 'findAll')}");
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Trustmark Binding Registries</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col"><a href="#" class="bi-plus-lg" id="trustmark-binding-registry-action-insert"></a></th>
                        <th scope="col"><a href="#" class="bi-trash" id="trustmark-binding-registry-action-delete"></a></th>
                        <th scope="col" style="width: 25%">Name</th>
                        <th scope="col" style="width: 35%">URL</th>
                        <th scope="col" style="width: 20%">Organization</th>
                        <th scope="col" style="width: 20%">Description</th>
                        <th scope="col"><span class="bi-laptop" title="Partner System Candidate Count"></span></th>
                        <th scope="col"><span class="bi-cloud" title="Trustmark Binding Registry Down"></span></th>
                    </tr>
                </thead>
                <template id="trustmark-binding-registry-template-empty">
                    <tr>
                        <td colspan="6">(No trustmark binding registries.)</td>
                    </tr>
                </template>
                <template id="trustmark-binding-registry-template-summary">
                    <tr>
                        <td><a href="#" class="bi-pencil trustmark-binding-registry-action-update"></a></td>
                        <td><input type="checkbox" class="form-check-input trustmark-binding-registry-action-delete-queue"></td>
                        <td><div class="trustmark-binding-registry-element-name"></div></td>
                        <td><a href="" target="_blank" class="trustmark-binding-registry-element-uri"></a></td>
                        <td><div class="trustmark-binding-registry-element-organization"></div></td>
                        <td><div class="trustmark-binding-registry-element-description"></div></td>
                        <td class="trustmark-binding-registry-element-partner-system-candidate-count text-center"></td>
                        <td class="trustmark-binding-registry-element-status"></td>
                    </tr>
                </template>
                <tbody id="trustmark-binding-registry-tbody">
                </tbody>
            </table>
        </div>

        <template id="trustmark-binding-registry-template-delete-response-failure">
            <div class="alert alert-danger">
                <div class="text-decoration-underline trustmark-binding-registry-element-name"></div>

                <div class="pt-4 trustmark-binding-registry-element-message"></div>
            </div>
        </template>

        <div class="container pt-4 d-none" id="trustmark-binding-registry-delete-response-failure">
        </div>

        <div class="container pt-4 d-none" id="trustmark-binding-registry-form">
            <div class="border rounded card">
                <input id="trustmark-binding-registry-input-id" name="id" type="hidden"/>

                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            <div class="d-none" id="trustmark-binding-registry-form-header-insert">Add Trustmark Binding Registry</div>

                            <div class="d-none" id="trustmark-binding-registry-form-header-update">Edit Trustmark Binding Registry</div>
                        </div>

                        <div class="col-1 text-end">
                            <button id="trustmark-binding-registry-action-cancel" type="button" class="btn-close p-0 align-middle"></button>
                        </div>
                    </div>
                </div>


                <div class="card-body">
                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-organization" class="col-3 col-form-label text-end label-required" for="trustmark-binding-registry-input-organization">Organization</label>

                        <div class="col-9">
                            <select id="trustmark-binding-registry-input-organization" name="organization" class="form-select" aria-describedby="trustmark-binding-registryinvalid-feedback-organization"></select>

                            <div id="trustmark-binding-registry-invalid-feedback-organization" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-name" class="col-3 col-form-label text-end label-required" for="trustmark-binding-registry-input-name">Name</label>

                        <div class="col-9">
                            <input type="text" id="trustmark-binding-registry-input-name" name="name" class="form-control" aria-describedby="trustmark-binding-registry-invalid-feedback-name"/>

                            <div id="trustmark-binding-registry-invalid-feedback-name" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-uri" class="col-3 col-form-label text-end label-required" for="trustmark-binding-registry-input-uri">URL</label>

                        <div class="col-9">
                            <input type="text" id="trustmark-binding-registry-input-uri" name="uri" class="form-control" aria-describedby="trustmark-binding-registry-invalid-feedback-uri">

                            <div id="trustmark-binding-registry-invalid-feedback-uri" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-description" class="col-3 col-form-label text-end" for="trustmark-binding-registry-input-description">Description</label>

                        <div class="col-9">
                            <textarea id="trustmark-binding-registry-input-description" name="description" class="form-control" aria-describedby="description-invalid-feedback"></textarea>

                            <div id="trustmark-binding-registry-invalid-feedback-description" class="invalid-feedback"></div>
                        </div>
                    </div>
                </div>

                <div class="card-footer text-start">
                    <div class="row">
                        <div class="col-3"></div>

                        <div class="col-9">
                            <button id="trustmark-binding-registry-action-submit-insert" type="button" class="btn btn-primary d-none">Add</button>
                            <button id="trustmark-binding-registry-action-submit-update" type="button" class="btn btn-primary d-none">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container pt-4 d-none" id="trustmark-binding-registry-status">
            <div class="border rounded card">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            Trustmark Binding Registry Status
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-partner-system-candidate-count" class="col-3 col-form-label text-end" for="trustmark-binding-registry-input-partner-system-candidate-count">Partner System Candidate Count</label>

                        <div class="col-9">
                            <input type="text" id="trustmark-binding-registry-input-partner-system-candidate-count" name="partner-system-candidate-count" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-request-date-time" class="col-3 col-form-label text-end" for="trustmark-binding-registry-input-request-date-time">Last Request</label>

                        <div class="col-9">
                            <input type="text" id="trustmark-binding-registry-input-request-date-time" name="request-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-success-date-time" class="col-3 col-form-label text-end" for="trustmark-binding-registry-input-success-date-time">Last Success</label>

                        <div class="col-9">
                            <input type="text" id="trustmark-binding-registry-input-success-date-time" name="success-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-failure-date-time" class="col-3 col-form-label text-end" for="trustmark-binding-registry-input-failure-date-time">Last Failure</label>

                        <div class="col-9">
                            <input type="text" id="trustmark-binding-registry-input-failure-date-time" name="success-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trustmark-binding-registry-label-failure-message" class="col-3 col-form-label text-end" for="trustmark-binding-registry-input-failure-message">Last Failure Message</label>

                        <div class="col-9">
                            <input type="text" id="trustmark-binding-registry-input-failure-message" name="failure-message" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
