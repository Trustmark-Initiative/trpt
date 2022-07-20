<%@ page import="java.time.format.DateTimeFormatter; org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <asset:javascript src="protectedSystemDashboardPartnerSystemCandidate_manage.js"/>
        <asset:javascript src="trustExpression.js"/>
        <asset:stylesheet src="protectedSystem_dashboard.css"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'protectedSystemDashboardPartnerSystemCandidate', action: 'findOne')}",
                "${createLink(controller:'protectedSystem', action: 'findOne')}",
                "${createLink(controller:'protectedSystem', action: 'update')}")
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2 class="d-flex justify-content-between">
                <div>
                    Trust Dashboard for <span class="protected-system-element-name"></span> with <span class="protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-name"></span>
                </div>

                <div class="d-flex">
                    <a target="_blank" id="protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-uri-entity-descriptor">(SAML)</a>

                    <div class="d-flex justify-content-end form-check form-switch">
                        <input type="checkbox" class="form-check-input partner-system-candidate-element-trust" data-bs-toggle="modal" data-bs-target="#modal-trust"/>
                    </div>
                </div>
            </h2>

            <div class="pt-2">
                This page provides details about how well  <span class="protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-name"></span>
                satisfies the trust policy for <span class="protected-system-element-name"></span> at <span class="protected-system-element-organization-name"></span>.</div>
        </div>

        <div class="container pt-4" id="protected-system-element-protected-system-partner-system-candidate-partner-system-candidate-trust-interoperability-profile-list">
            <template id="protected-system-template-protected-system-partner-system-candidate-partner-system-candidate-trust-interoperability-profile">
                <div class="TrustInteroperabilityProfileContainer Body">
                    <div class="TrustInteroperabilityProfile">
                        <div class="TrustExpressionEvaluation Body">
                            <div class="TrustExpressionContainer">
                                <div class="Body"></div>
                            </div>
                        </div>

                        <div class="TrustExpressionEvaluatorFailureListContainer Body">
                            <div class="TrustExpressionEvaluatorFailure">
                            </div>
                        </div>
                    </div>
                </div>
            </template>
        </div>

        <div class="container pt-4" style="max-width: 540px;">
            <div class="border rounded card">
                <div class="card-header fw-bold">
                    <div class="row">
                        <div class="col-12">
                            <div>Legend</div>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="TrustInteroperabilityProfileContainer Body">
                        <div class="TrustInteroperabilityProfile">
                            <div class="TrustExpressionEvaluation Body">
                                <div class="TrustExpressionContainer">
                                    <div class="Body">
                                        <div class="TrustExpressionTop TRUE">
                                            <label class="TrustInteroperabilityProfileInner">Black text indicates an expression that evaluates to the boolean value true.</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="TrustInteroperabilityProfileContainer Body">
                        <div class="TrustInteroperabilityProfile">
                            <div class="TrustExpressionEvaluation Body">
                                <div class="TrustExpressionContainer">
                                    <div class="Body">
                                        <div class="TrustExpressionTop FALSE">
                                            <label class="TrustInteroperabilityProfileInner">Red text on a grey background indicates an expression that evaluates to the boolean value false.</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="TrustInteroperabilityProfileContainer Body">
                        <div class="TrustInteroperabilityProfile">
                            <div class="TrustExpressionEvaluation Body">
                                <div class="TrustExpressionContainer">
                                    <div class="Body">
                                        <div class="TrustExpressionTop FAILURE">
                                            <label class="TrustInteroperabilityProfileInner">Red italic text on a grey brackground indicates an expression that fails to evaluate.</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="TrustInteroperabilityProfileContainer Body">
                        <div class="TrustInteroperabilityProfile">
                            <div class="TrustExpressionEvaluation Body">
                                <div class="TrustExpressionContainer">
                                    <div class="Body">
                                        <div class="TrustExpressionTop UNKNOWN">
                                            <label class="TrustInteroperabilityProfileInner">Orange text indicates an expression that evaluates to a non-boolean value; for example, a string.</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="TrustInteroperabilityProfileContainer Body">
                        <div class="TrustInteroperabilityProfile">
                            <div class="TrustExpressionEvaluation Body">
                                <div class="TrustExpressionContainer">
                                    <div class="Body">
                                        <div class="TrustExpressionTop INCOMPLETE">
                                            <label class="TrustInteroperabilityProfileInner">Grey italic text indicates an expression that the system has not yet evaluated.</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
                            <li class="mb-3 d-none protected-system-element-type-certificate-relying-party">You must download a copy of the certificate for <span class="partner-system-candidate-element-name fw-bold"></span>,
                            and configure it within <span class="protected-system-element-name fw-bold"></span>, to enable <span class="protected-system-element-name fw-bold"></span>
                                to trust <span class="partner-system-candidate-element-name fw-bold"></span>.
                            This process heavily depends on the implementation details of <span class="protected-system-element-name fw-bold"></span>.
                            To download a copy of the certificate for <span class="partner-system-candidate-element-name fw-bold"></span>, click <a target="_blank" class="partner-system-candidate-element-trust-fabric-metadata fw-bold"></a>.
                            </li>

                            <li class="mb-3 d-none protected-system-element-type-other">You must download a copy of the SAML metadata for <span class="partner-system-candidate-element-name fw-bold"></span>,
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
