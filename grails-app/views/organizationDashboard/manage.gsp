<%@ page import="org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <asset:javascript src="organizationDashboard_manage.js"/>
        <asset:stylesheet src="organizationDashboard_manage.css"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'organization', action: 'findOne')}",
                "${createLink(controller:'organization', action: 'update')}",
                "${createLink(controller:'partnerOrganizationCandidate', action: 'findAll')}",
                "${createLink(controller:'organizationDashboardPartnerOrganizationCandidate', action: 'manage')}")
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Trust Dashboard for <span class="organization-element-name"></span></h2>

            <div class="fw-bold mt-2">Trust Policy for <span class="organization-element-name"></span></div>

            <div class="mt-2">
                Add trust interoperability profiles (TIPs) to this table to define the trust policy for <span class="organization-element-name"></span></span>.
            </div>
            <table class="mt-2 table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col"><a href="#" class="bi-plus-lg" id="trust-interoperability-profile-action-insert"></a></th>
                        <th scope="col"><a href="#" class="bi-trash" id="trust-interoperability-profile-action-delete"></a></th>
                        <th scope="col" style="width: 75%">TIP Name</th>
                        <th scope="col" style="width: 25%">TIP Issuer</th>
                        <th scope="col"><span class="bi-exclamation-circle" title="Require Full Compliance"></span></th>
                    </tr>
                </thead>
                <template id="trust-interoperability-profile-template-empty">
                    <tr>
                        <td colspan="5">(No trust interoperability profiles.)</td>
                    </tr>
                </template>
                <template id="trust-interoperability-profile-template-summary">
                    <tr class="trust-interoperability-profile-summary">
                        <td><a href="#" class="bi-pencil trust-interoperability-profile-action-update"></a></td>
                        <td><input type="checkbox" class="form-check-input trust-interoperability-profile-action-delete-queue"></td>
                        <td class="unhack">
                            <a target="_blank" class="trust-interoperability-profile-element-name"></a>

                            <div class="trust-interoperability-profile-element-description"></div>
                        </td>
                        <td><a target="_blank" class="trust-interoperability-profile-element-issuer"></a></td>
                        <td class="trust-interoperability-profile-element-mandatory"></td>
                    </tr>
                </template>
                <tbody id="trust-interoperability-profile-tbody">
                </tbody>
            </table>
        </div>

        <div class="container pt-4 d-none" id="trust-interoperability-profile-form">
            <div class="border rounded card">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            <div class="d-none" id="trust-interoperability-profile-form-header-insert">Add Trust Interoperability Profile</div>

                            <div class="d-none" id="trust-interoperability-profile-form-header-update">Edit Trust Interoperability Profile</div>
                        </div>

                        <div class="col-1 text-end">
                            <button id="trust-interoperability-profile-action-cancel" type="button" class="btn-close p-0 align-middle"></button>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-uri" class="col-3 col-form-label text-end label-required" for="trust-interoperability-profile-input-uri">URL</label>

                        <div class="col-9">
                            <input id="trust-interoperability-profile-input-uri" name="uri" class="form-control" aria-describedby="trust-interoperability-profile-invalid-feedback-uri"/>

                            <div id="trust-interoperability-profile-invalid-feedback-uri" class="invalid-feedback"></div>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-mandatory" class="col-3 form-check-label text-end" for="trust-interoperability-profile-input-mandatory">Require Full Compliance</label>

                        <div class="col-9">
                            <input type="checkbox" id="trust-interoperability-profile-input-mandatory" name="mandatory" class="form-check-input" aria-describedby="trust-interoperability-profile-invalid-feedback-mandatory"/>

                            <div id="trust-interoperability-profile-invalid-feedback-mandatory" class="invalid-feedback"></div>
                        </div>
                    </div>
                </div>

                <div class="card-footer text-start">
                    <div class="row">
                        <div class="col-3"></div>

                        <div class="col-9">
                            <button id="trust-interoperability-profile-action-submit-insert" type="button" class="btn btn-primary d-none">Add</button>
                            <button id="trust-interoperability-profile-action-submit-update" type="button" class="btn btn-primary d-none">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container pt-4 d-none" id="trust-interoperability-profile-status">
            <div class="border rounded card">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            Trust Interoperability Profile Status
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-name" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-name">Name</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-name" name="name" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-description" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-description">Description</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-description" name="description" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-issuer" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-issuer">Issuer</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-issuer" name="issuer" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-issuer-identifier" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-issuer-identifier">Issuer Identifier</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-issuer-identifier" name="issuer-identifier" class="form-control" readonly>
                        </div>
                    </div>

                </div>

                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            Document Status
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-document-request-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-document-request-date-time">Last Request</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-document-request-date-time" name="document-request-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-document-success-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-document-success-date-time">Last Success</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-document-success-date-time" name="document-success-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-document-failure-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-document-failure-date-time">Last Failure</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-document-failure-date-time" name="document-success-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-document-failure-message" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-document-failure-message">Last Failure Message</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-document-failure-message" name="failure-message" class="form-control" readonly>
                        </div>
                    </div>
                </div>

                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-11">
                            Server Status
                        </div>
                    </div>
                </div>

                <div class="card-body">

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-server-request-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-server-request-date-time">Last Request</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-server-request-date-time" name="server-request-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-server-success-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-server-success-date-time">Last Success</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-server-success-date-time" name="server-success-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-server-failure-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-server-failure-date-time">Last Failure</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-server-failure-date-time" name="server-success-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-server-failure-message" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-server-failure-message">Last Failure Message</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-server-failure-message" name="failure-message" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container pt-4">
            <div class="fw-bold">Candidate Partner Organizations for <span class="organization-element-name"></span></div>

            <div class="mt-2">
                This table allows you to control which candidate partner organizations are trusted by <span class="organization-element-name"></span>.
            </div>

            <table class="mt-2 table table-bordered mb-0">
                <thead>
                    <tr>
                        <th scope="col" style="width: 25%">Organization Name</th>
                        <th scope="col" style="width: 25%">Trustmark Binding Registry</th>
                        <th scope="col" style="width: 25%">Last Evaluation</th>
                        <th scope="col" style="width: 25%" class="text-center">TIP Satisfaction</th>
                        <th scope="col" class="text-center">&nbsp;&nbsp;&nbsp;TD%&nbsp;&nbsp;</th>
                        <th scope="col" class="text-center">Trusted</th>
                    </tr>
                </thead>
                <template id="partner-organization-candidate-template-empty">
                    <tr>
                        <td colspan="6">(No candidate partner organizations.)</td>
                    </tr>
                </template>
                <template id="partner-organization-candidate-template-summary">
                    <tr class="partner-organization-candidate-summary">
                        <td>
                            <a class="partner-organization-candidate-element-name"></a>
                            <a target="_blank" class="partner-organization-candidate-element-trust-fabric-metadata"></a>
                        </td>
                        <td>
                            <a class="partner-organization-candidate-element-trustmark-binding-registry"></a>
                        </td>
                        <td class="partner-organization-candidate-element-trust-interoperability-profile-evaluation-local-date-time"></td>
                        <td>
                            <div class="partner-organization-candidate-element-percent-trust-interoperability-profile d-flex justify-content-around">
                            </div>
                        </td>
                        <td class="partner-organization-candidate-element-percent-trustmark-definition-requirement text-center"></td>
                        <td>
                            <div class="d-flex justify-content-center form-check form-switch">
                                <input type="checkbox" class="form-check-input partner-organization-candidate-element-trust" data-bs-toggle="modal" data-bs-target="#modal-trust"/>
                            </div>
                        </td>
                    </tr>
                </template>
                <template id="partner-organization-candidate-template-detail">
                    <tr class="partner-organization-candidate-detail d-none">
                        <td colspan="2"><span class="glyphicon bi-list-ul me-2"></span><span class="trust-interoperability-profile-name"></span></td>
                        <td class="trust-interoperability-profile-evaluation-local-date-time"></td>
                        <td>
                            <div class="trust-interoperability-profile-evaluation-satisfied d-flex justify-content-around">
                            </div>
                        </td>
                        <td class="trust-interoperability-profile-trustmark-definition text-center"></td>
                        <td></td>
                    </tr>
                </template>
                <tbody id="partner-organization-candidate-tbody">
                </tbody>
            </table>
        </div>

        <div class="modal" tabindex="-1" id="modal-trust">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header d-none" id="modal-header-trust">
                        <h5 class="modal-title">Trust <span class="partner-organization-candidate-element-name"></span></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body d-none" id="modal-body-trust">
                        <p>
                            By toggling this trust control, you have chosen to configure
                            <span class="organization-element-name fw-bold"></span>
                            to trust
                            <span class="partner-organization-candidate-element-name fw-bold"></span>.
                        Please
                        take note of the following implications of your decision.
                        </p>

                        <ol>
                            <li class="mb-3">You must download a copy of the SAML metadata for <span class="partner-organization-candidate-element-name fw-bold"></span>,
                            and install it within <span class="organization-element-name fw-bold"></span>,
                            to enable <span class="organization-element-name fw-bold"></span> to trust <span class="partner-organization-candidate-element-name fw-bold"></span>.
                            This is a manual reconfiguration process of <span class="organization-element-name fw-bold"></span>
                                that depends on its implementation details, including the software platform
                                on which <span class="organization-element-name fw-bold"></span> is built. Completion
                            of this process typically requires you to have administrative access to <span class="organization-element-name fw-bold"></span>.
                            To download a copy of the SAML metadata for <span class="partner-organization-candidate-element-name fw-bold"></span>,
                            click <a target="_blank" class="partner-organization-candidate-element-trust-fabric-metadata fw-bold"></a>.</li>


                            <li>
                                Because you have chosen to trust <span class="partner-organization-candidate-element-name fw-bold"></span>,
                            this tool will begin to closely monitor <span class="partner-organization-candidate-element-name fw-bold"></span>
                                for changes in its trustmark disposition with respect to the trust policy that
                                you have defined for <span class="organization-element-name fw-bold"></span>. If
                            any future changes should occur – e.g., if any trustmark bound to <span class="partner-organization-candidate-element-name fw-bold"></span>
                                should expire or become revoked by its issuer, or if you change your trust policy
                                for <span class="organization-element-name fw-bold"></span> – you will receive an
                            automated email from this tool, informing you of the change and cautioning you about
                            important steps to take at that time. If you receive such an email, you should immediately
                            revisit this Trust Dashboard page, reassess your decision to trust <span class="partner-organization-candidate-element-name fw-bold"></span>,
                            and change <span class="organization-element-name fw-bold"></span>'s trust perspective
                            on <span class="partner-organization-candidate-element-name fw-bold"></span> if appropriate.
                            </li>
                        </ol>
                    </div>

                    <div class="modal-header d-none" id="modal-header-do-not-trust">
                        <h5 class="modal-title">Do Not Trust <span class="partner-organization-candidate-element-name"></span></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body d-none" id="modal-body-do-not-trust">
                        <p>
                            By toggling this trust control, you have chosen to configure <span class="organization-element-name fw-bold"></span>
                            to stop trusting <span class="partner-organization-candidate-element-name fw-bold"></span>.
                        Please take note of the following implications of your decision.</p>

                        <ol>
                            <li class="mb-3">You must immediately reconfigure <span class="organization-element-name fw-bold"></span>
                                so that it no longer trusts <span class="partner-organization-candidate-element-name fw-bold"></span>.
                            This is a manual reconfiguration process of <span class="organization-element-name fw-bold"></span>
                                that depends on its implementation details, including the software platform on
                                which <span class="organization-element-name fw-bold"></span> is built. Typically,
                            this process involves editing <span class="organization-element-name fw-bold"></span>'s
                            list of trusted remote partner systems, so that the list no longer includes <span class="partner-organization-candidate-element-name fw-bold"></span>.
                            Completion of this process typically requires you to have administrative access to
                                <span class="organization-element-name fw-bold"></span>.</li>

                            <li>Because you have chosen to stop trusting <span class="partner-organization-candidate-element-name fw-bold"></span>,
                            this tool will no longer monitor <span class="partner-organization-candidate-element-name fw-bold"></span>
                                for changes in its trustmark disposition with respect to the trust policy for
                                <span class="organization-element-name fw-bold"></span>. If at any future point in
                            time, you want to change <span class="organization-element-name fw-bold"></span>'s trust
                            perspective on <span class="partner-organization-candidate-element-name fw-bold"></span>, you
                            can do so by toggling the trust control for <span class="partner-organization-candidate-element-name fw-bold"></span>
                                on this page.</li>
                        </ol>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
