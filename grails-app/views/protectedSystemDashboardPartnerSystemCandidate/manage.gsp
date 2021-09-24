<%@ page import="org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <asset:javascript src="manage_protected_system_dashboard_partner_system_candidate.js"/>
        <asset:stylesheet src="protectedSystem_dashboard.css"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'protectedSystem', action: 'findOne')}",
                "${createLink(controller:'protectedSystem', action: 'update')}")
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2 class="d-flex justify-content-between">
                <div>
                    Trust Dashboard for ${protectedSystemPartnerSystemCandidate.protectedSystemName} with ${protectedSystemPartnerSystemCandidate.partnerSystemCandidateName}
                </div>

                <div class="d-flex">
                    <g:if test="${protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor != null}">
                        <a target="_blank" href="${protectedSystemPartnerSystemCandidate.partnerSystemCandidate.uriEntityDescriptor}">(SAML)</a>
                    </g:if>
                    <div class="d-flex justify-content-end form-check form-switch">
                        <input type="checkbox" class="form-check-input partner-system-candidate-element-trust" data-bs-toggle="modal" data-bs-target="#modal-trust"/>
                    </div>
                </div>
            </h2>

            <div class="pt-2 pb-2">
                This page provides details about how well ${protectedSystemPartnerSystemCandidate.partnerSystemCandidateName}
                satisfies the trust policy for ${protectedSystemPartnerSystemCandidate.protectedSystemName}
                at ${protectedSystemPartnerSystemCandidate.organizationName}.</div>

            <g:each in="${protectedSystemPartnerSystemCandidate.trustExpressionEvaluationMap.values()}" var="json">

                <g:if test="${json != null}">
                    <div class="TrustInteroperabilityProfileContainer Body">
                        <g:set var="trustExpression" value="${json.get("TrustExpression") as JSONObject}"/>
                        <g:set var="trustExpressionEvaluatorFailureList" value="${json.get("TrustExpressionEvaluatorFailureList") as JSONArray}"/>

                        <div class="TrustInteroperabilityProfile">
                            <div class="TrustExpressionEvaluation Body">
                                <div class="TrustExpressionContainer">
                                    <div class="Body">
                                        <g:render template="dashboardTrustExpression"
                                                  model="${[trustExpression: trustExpression, trustInteroperabilityProfileParentURI: ""]}"/>
                                    </div>
                                </div>

                                <g:if test="${trustExpressionEvaluatorFailureList.isEmpty()}">
                                </g:if>
                                <g:else>
                                    <div class="TrustExpressionEvaluatorFailureListContainer Body">
                                        <div class="TrustExpressionEvaluatorFailure">

                                            <g:each in="${trustInteroperabilityProfile.trustExpressionEvaluatorFailureList}"
                                                    var="failure">
                                                <g:if test="${(failure as JSONObject).get("\$Type").equals("TrustExpressionFailureURI")}">
                                                    <div class="Message">${(failure as JSONObject).get("Message")}</div>

                                                    <div class="UriString code">${(failure as JSONObject).get("UriString")}</div>
                                                </g:if>
                                            </g:each>
                                            <g:each in="${trustInteroperabilityProfile.trustExpressionEvaluatorFailureList}"
                                                    var="failure">
                                                <g:if test="${(failure as JSONObject).get("\$Type").equals("TrustExpressionEvaluatorFailureResolve")}">
                                                    <div class="Message">${(failure as JSONObject).get("Message")}</div>

                                                    <div class="Uri code">${(failure as JSONObject).get("Uri")}</div>
                                                </g:if>
                                            </g:each>
                                        </div>
                                    </div>
                                </g:else>
                            </div>
                        </div>
                    </div>
                </g:if>
            </g:each>
        </div>

        <div class="modal" tabindex="-1" id="modal-trust">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header d-none" id="modal-header-trust">
                        <h5 class="modal-title">Trust <span class="partner-system-candidate-element-name"></span></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body d-none" id="modal-body-trust">
                        <p>
                            By toggling this trust control, you have chosen to configure
                            <span class="protected-system-element-name fw-bold"></span>
                            to trust
                            <span class="partner-system-candidate-element-name fw-bold"></span>.
                        Please
                        take note of the following implications of your decision.
                        </p>

                        <ol>
                            <li class="mb-3">You must download a copy of the SAML metadata for <span class="partner-system-candidate-element-name fw-bold"></span>,
                            and install it within <span class="protected-system-element-name fw-bold"></span>,
                            to enable <span class="protected-system-element-name fw-bold"></span> to trust <span class="partner-system-candidate-element-name fw-bold"></span>.
                            This is a manual reconfiguration process of <span class="protected-system-element-name fw-bold"></span>
                                that depends on its implementation details, including the software platform
                                on which <span class="protected-system-element-name fw-bold"></span> is built. Completion
                            of this process typically requires you to have administrative access to <span class="protected-system-element-name fw-bold"></span>.
                            To download a copy of the SAML metadata for <span class="partner-system-candidate-element-name fw-bold"></span>,
                            click <a target="_blank" class="partner-system-candidate-element-trust-fabric-metadata fw-bold"></a>.</li>


                            <li>
                                Because you have chosen to trust <span class="partner-system-candidate-element-name fw-bold"></span>,
                            this tool will begin to closely monitor <span class="partner-system-candidate-element-name fw-bold"></span>
                                for changes in its trustmark disposition with respect to the trust policy that
                                you have defined for <span class="protected-system-element-name fw-bold"></span>. If
                            any future changes should occur – e.g., if any trustmark bound to <span class="partner-system-candidate-element-name fw-bold"></span>
                                should expire or become revoked by its issuer, or if you change your trust policy
                                for <span class="protected-system-element-name fw-bold"></span> – you will receive an
                            automated email from this tool, informing you of the change and cautioning you about
                            important steps to take at that time. If you receive such an email, you should immediately
                            revisit this Trust Dashboard page, reassess your decision to trust <span class="partner-system-candidate-element-name fw-bold"></span>,
                            and change <span class="protected-system-element-name fw-bold"></span>'s trust perspective
                            on <span class="partner-system-candidate-element-name fw-bold"></span> if appropriate.
                            </li>
                        </ol>
                    </div>

                    <div class="modal-header d-none" id="modal-header-do-not-trust">
                        <h5 class="modal-title">Do Not Trust <span class="partner-system-candidate-element-name"></span></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body d-none" id="modal-body-do-not-trust">
                        <p>
                            By toggling this trust control, you have chosen to configure <span class="protected-system-element-name fw-bold"></span>
                            to stop trusting <span class="partner-system-candidate-element-name fw-bold"></span>.
                        Please take note of the following implications of your decision.</p>

                        <ol>
                            <li class="mb-3">You must immediately reconfigure <span class="protected-system-element-name fw-bold"></span>
                                so that it no longer trusts <span class="partner-system-candidate-element-name fw-bold"></span>.
                            This is a manual reconfiguration process of <span class="protected-system-element-name fw-bold"></span>
                                that depends on its implementation details, including the software platform on
                                which <span class="protected-system-element-name fw-bold"></span> is built. Typically,
                            this process involves editing <span class="protected-system-element-name fw-bold"></span>'s
                            list of trusted remote partner systems, so that the list no longer includes <span class="partner-system-candidate-element-name fw-bold"></span>.
                            Completion of this process typically requires you to have administrative access to
                                <span class="protected-system-element-name fw-bold"></span>.</li>

                            <li>Because you have chosen to stop trusting <span class="partner-system-candidate-element-name fw-bold"></span>,
                            this tool will no longer monitor <span class="partner-system-candidate-element-name fw-bold"></span>
                                for changes in its trustmark disposition with respect to the trust policy for
                                <span class="protected-system-element-name fw-bold"></span>. If at any future point in
                            time, you want to change <span class="protected-system-element-name fw-bold"></span>'s trust
                            perspective on <span class="partner-system-candidate-element-name fw-bold"></span>, you
                            can do so by toggling the trust control for <span class="partner-system-candidate-element-name fw-bold"></span>
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
