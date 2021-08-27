<%@ page import="org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <asset:stylesheet src="protectedSystem_dashboard.css"/>

        <meta name="layout" content="main"/>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Trust Dashboard for ${protectedSystemPartnerSystemCandidate.protectedSystemName} and ${protectedSystemPartnerSystemCandidate.partnerSystemCandidateName}</h2>

            <g:each in="${protectedSystemPartnerSystemCandidate.trustExpressionEvaluationMap.values()}" var="json">

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
            </g:each>
        </div>
    </body>
</html>
