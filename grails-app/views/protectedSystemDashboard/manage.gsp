<%@ page import="org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <asset:javascript src="manage_protected_system_dashboard.js"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'protectedSystem', action: 'findOne')}",
                "${createLink(controller:'protectedSystem', action: 'update')}",
                "${createLink(controller:'partnerSystemCandidate', action: 'findAll')}",
                "${createLink(controller:'protectedSystemDashboardPartnerSystemCandidate', action: 'manage')}")
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Trust Dashboard for <span id="protected-system-element-name"></span></h2>

            <table class="pt-2 table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" colspan="9">Trust Interoperability Profiles</th>
                    </tr>
                    <tr>
                        <th scope="col"><a href="#" class="bi-plus-lg" id="trust-interoperability-profile-action-insert"></a></th>
                        <th scope="col"><a href="#" class="bi-trash" id="trust-interoperability-profile-action-delete"></a></th>
                        <th scope="col" style="width: 20%">URL</th>
                        <th scope="col" style="width: 20%">Name</th>
                        <th scope="col" style="width: 20%">Description</th>
                        <th scope="col" style="width: 20%">Issuer</th>
                        <th scope="col" style="width: 20%">Issuer Identifier</th>
                        <th scope="col"><span class="bi-exclamation-circle" title="Trust Interoperability Profile Required"></span></th>
                        <th scope="col"><span class="bi-cloud" title="Trust Interoperability Profile Down"></span></th>
                    </tr>
                </thead>
                <template id="trust-interoperability-profile-template-empty">
                    <tr>
                        <td colspan="6">(No trust interoperability profiles.)</td>
                    </tr>
                </template>
                <template id="trust-interoperability-profile-template-summary">
                    <tr class="trust-interoperability-profile-summary">
                        <td><a href="#" class="bi-pencil trust-interoperability-profile-action-update"></a></td>
                        <td><input type="checkbox" class="form-check-input trust-interoperability-profile-action-delete-queue"></td>
                        <td><a target="_blank" class="trust-interoperability-profile-element-uri"></a></td>
                        <td><div class="trust-interoperability-profile-element-name"></div></td>
                        <td><div class="trust-interoperability-profile-element-description"></div></td>
                        <td><div class="trust-interoperability-profile-element-issuer"></div></td>
                        <td><a target="_blank" class="trust-interoperability-profile-element-issuer-identifier"></a></td>
                        <td class="trust-interoperability-profile-element-mandatory"></td>
                        <td class="trust-interoperability-profile-element-status"></td>
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

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-request-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-request-date-time">Last Request</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-request-date-time" name="request-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-success-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-success-date-time">Last Success</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-success-date-time" name="success-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-failure-date-time" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-failure-date-time">Last Failure</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-failure-date-time" name="success-date-time" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="row pb-2">
                        <label id="trust-interoperability-profile-label-failure-message" class="col-3 col-form-label text-end" for="trust-interoperability-profile-input-failure-message">Last Failure Message</label>

                        <div class="col-9">
                            <input type="text" id="trust-interoperability-profile-input-failure-message" name="failure-message" class="form-control" readonly>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container pt-4">
            <table class="pt-2 table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" colspan="9">Partner System Candidates</th>
                    </tr>
                    <tr>
                        <th scope="col"><span class="bi-award" title="Trust"></span></th>
                        <th scope="col" style="width: 33%">Name</th>
                        <th scope="col" style="width: 33%">Type</th>
                        <th scope="col" style="width: 33%">Trust Fabric Metadata</th>
                        <th scope="col">TDR%</th>
                        <th scope="col">TIP%</th>
                        <th scope="col"><span class="bi-box-arrow-in-right" title="Detail"></span></th>
                    </tr>
                </thead>
                <template id="partner-system-candidate-template-empty">
                    <tr>
                        <td colspan="6">(No partner system candidates.)</td>
                    </tr>
                </template>
                <template id="partner-system-candidate-template-summary">
                    <tr class="partner-system-candidate-summary">
                        <td><input type="checkbox" form-check-input class="partner-system-candidate-element-trust"/></td>
                        <td><div class="partner-system-candidate-element-name"></div></td>
                        <td><div class="partner-system-candidate-element-type"></div></td>
                        <td><a target="_blank" class="partner-system-candidate-element-trust-fabric-metadata"></a></td>
                        <td class="text-center partner-system-candidate-element-percent-trustmark-definition-requirement"></td>
                        <td class="text-center partner-system-candidate-element-percent-trust-interoperability-profile"></td>
                        <td><a href="#" class="bi-box-arrow-in-right partner-system-candidate-action-detail"></a></td>
                    </tr>
                </template>
                <tbody id="partner-system-candidate-tbody">
                </tbody>
            </table>
        </div>

        %{--        <div>${protectedSystem.name} is a ${protectedSystem.protectedSystemTypeLabel}. The following systems are ${protectedSystem.partnerSystemCandidateList.get(0).typeLabel}s that the system may elect to trust.</div>--}%

        %{--        <div class="ProtectedSystemSummary">--}%
        %{--            <div class="Body">--}%
        %{--                <div>Organization</div>--}%

        %{--                <div><a href="${protectedSystem.organizationUri}">${protectedSystem.organizationName}</a></div>--}%

        %{--                <div>Type</div>--}%

        %{--                <div>${protectedSystem.protectedSystemTypeLabel}</div>--}%

        %{--                <div>Description</div>--}%

        %{--                <div>${protectedSystem.organizationDescription}</div>--}%
        %{--            </div>--}%
        %{--        </div>--}%

        %{--        <g:each in="${protectedSystem.partnerSystemCandidateList}" var="partnerSystemCandidate">--}%
        %{--            <div class="PartnerSystemCandidateSummary Separate">--}%
        %{--                <div class="Head">--}%
        %{--                    <div>--}%
        %{--                        <a href="${createLink(controller: 'protectedSystem', action: 'partnerSystemCandidate', params: [protectedSystemId: protectedSystem.id, partnerSystemCandidateId: partnerSystemCandidate.id])}">--}%
        %{--                            ${partnerSystemCandidate.name}--}%
        %{--                        </a>--}%
        %{--                    </div>--}%

        %{--                    <div>--}%
        %{--                        Full 1--}%
        %{--                    </div>--}%

        %{--                    <div>--}%
        %{--                        Partial  1--}%
        %{--                    </div>--}%

        %{--                    <div>--}%
        %{--                        None 1--}%
        %{--                    </div>--}%

        %{--                    <div>--}%
        %{--                        Trusted? <input type="checkbox">--}%
        %{--                    </div>--}%
        %{--                </div>--}%

        %{--                <div class="Body">--}%
        %{--                    <div>Type</div>--}%

        %{--                    <div>${partnerSystemCandidate.typeLabel}</div>--}%

        %{--                    <div>URI</div>--}%

        %{--                    <div>--}%
        %{--                        <g:if test="${partnerSystemCandidate.uri}">--}%
        %{--                            <a href="${partnerSystemCandidate.uri}">${partnerSystemCandidate.uri}</a>--}%
        %{--                        </g:if>--}%
        %{--                        <g:else>--}%
        %{--                            (none)--}%
        %{--                        </g:else>--}%
        %{--                    </div>--}%
        %{--                </div>--}%
        %{--            </div>--}%
        %{--        </g:each>--}%
    </body>
</html>
