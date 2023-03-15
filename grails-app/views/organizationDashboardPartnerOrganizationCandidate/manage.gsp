<html>
    <head>
        <asset:javascript src="dashboard_candidate_manage.js"/>
        <asset:javascript src="dashboard_candidate_manage_organization.js"/>
        <asset:javascript src="trustExpressionUtility.js"/>
        <asset:javascript src="trustExpression.js"/>
        <asset:stylesheet src="dashboard.css"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'profile', action: 'findOne')}",
                "${createLink(controller:'organizationDashboardPartnerOrganizationCandidate', action: 'findOne')}",
                "${createLink(controller:'organization', action: 'findOne')}",
                "${createLink(controller:'organization', action: 'update')}")
        </script>
    </head>

    <body>
        <div class="container pt-4 placeholder-glow">
            <h2 class="d-flex">
                <div class="flex-grow-1 ">
                    Trust Dashboard for <span class="entity-element-name placeholder-hack col-4"></span> with <span class="entity-element-partner-candidate-name placeholder-hack col-4"></span>
                </div>

                <div class="d-flex">
                    <div class="d-flex justify-content-end form-check form-switch">
                        <input type="checkbox" class="form-check-input partner-candidate-element-trust" data-bs-toggle="modal" data-bs-target="#modal-trust"/>
                    </div>
                </div>
            </h2>

            <div class="pt-2">
                This page provides details about how well <span class="entity-element-partner-candidate-name placeholder-hack col-3"></span>
                satisfies the trust policy for <span class="entity-element-name placeholder-hack col-3"></span> at <span class="entity-element-organization-name placeholder-hack col-3"></span>.</div>
        </div>

        <div class="container pt-4">
            <template id="entity-template-partner-candidate-trust-interoperability-profile">
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

            <div id="TrustInteroperabilityProfileContainerList"></div>
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
                    <div id="TrustInteroperabilityProfileContainerList-legend">
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
        </div>


        <div class="modal" tabindex="-1" id="modal-trust">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header d-none" id="modal-header-trust">
                        <h5 class="modal-title">Trust <span class="partner-candidate-element-name"></span></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body d-none" id="modal-body-trust">
                        <p>
                            By toggling this trust control, you have chosen to configure
                            <span class="entity-element-name fw-bold"></span>
                            to trust
                            <span class="partner-candidate-element-name fw-bold"></span>.
                        Please
                        take note of the following implications of your decision.
                        </p>

                        <ol>
                            <li class="mb-3">You must download a copy of the SAML metadata for <span class="partner-candidate-element-name fw-bold"></span>,
                            and install it within <span class="entity-element-name fw-bold"></span>,
                            to enable <span class="entity-element-name fw-bold"></span> to trust <span class="partner-candidate-element-name fw-bold"></span>.
                            This is a manual reconfiguration process of <span class="entity-element-name fw-bold"></span>
                                that depends on its implementation details, including the software platform
                                on which <span class="entity-element-name fw-bold"></span> is built. Completion
                            of this process typically requires you to have administrative access to <span class="entity-element-name fw-bold"></span>.
                            To download a copy of the SAML metadata for <span class="partner-candidate-element-name fw-bold"></span>,
                            click <a target="_blank" class="partner-candidate-element-trust-fabric-metadata fw-bold"></a>.</li>


                            <li>
                                Because you have chosen to trust <span class="partner-candidate-element-name fw-bold"></span>,
                            this tool will begin to closely monitor <span class="partner-candidate-element-name fw-bold"></span>
                                for changes in its trustmark disposition with respect to the trust policy that
                                you have defined for <span class="entity-element-name fw-bold"></span>. If
                            any future changes should occur – e.g., if any trustmark bound to <span class="partner-candidate-element-name fw-bold"></span>
                                should expire or become revoked by its issuer, or if you change your trust policy
                                for <span class="entity-element-name fw-bold"></span> – you will receive an
                            automated email from this tool, informing you of the change and cautioning you about
                            important steps to take at that time. If you receive such an email, you should immediately
                            revisit this Trust Dashboard page, reassess your decision to trust <span class="partner-candidate-element-name fw-bold"></span>,
                            and change <span class="entity-element-name fw-bold"></span>'s trust perspective
                            on <span class="partner-candidate-element-name fw-bold"></span> if appropriate.
                            </li>
                        </ol>
                    </div>

                    <div class="modal-header d-none" id="modal-header-do-not-trust">
                        <h5 class="modal-title">Do Not Trust <span class="partner-candidate-element-name"></span></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body d-none" id="modal-body-do-not-trust">
                        <p>
                            By toggling this trust control, you have chosen to configure <span class="entity-element-name fw-bold"></span>
                            to stop trusting <span class="partner-candidate-element-name fw-bold"></span>.
                        Please take note of the following implications of your decision.</p>

                        <ol>
                            <li class="mb-3">You must immediately reconfigure <span class="entity-element-name fw-bold"></span>
                                so that it no longer trusts <span class="partner-candidate-element-name fw-bold"></span>.
                            This is a manual reconfiguration process of <span class="entity-element-name fw-bold"></span>
                                that depends on its implementation details, including the software platform on
                                which <span class="entity-element-name fw-bold"></span> is built. Typically,
                            this process involves editing <span class="entity-element-name fw-bold"></span>'s
                            list of trusted remote partner systems, so that the list no longer includes <span class="partner-candidate-element-name fw-bold"></span>.
                            Completion of this process typically requires you to have administrative access to
                                <span class="entity-element-name fw-bold"></span>.</li>

                            <li>Because you have chosen to stop trusting <span class="partner-candidate-element-name fw-bold"></span>,
                            this tool will no longer monitor <span class="partner-candidate-element-name fw-bold"></span>
                                for changes in its trustmark disposition with respect to the trust policy for
                                <span class="entity-element-name fw-bold"></span>. If at any future point in
                            time, you want to change <span class="entity-element-name fw-bold"></span>'s trust
                            perspective on <span class="partner-candidate-element-name fw-bold"></span>, you
                            can do so by toggling the trust control for <span class="partner-candidate-element-name fw-bold"></span>
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
