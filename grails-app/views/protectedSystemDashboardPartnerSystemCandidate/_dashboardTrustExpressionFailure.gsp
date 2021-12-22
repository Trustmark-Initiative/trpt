<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDateTimeStamp" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDecimal" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeString" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeStringList" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeNone" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>

<g:set var="trustExpressionType" value="${(trustExpression as JSONObject).get("\$Type")}"/>

<g:set var="trustExpressionFailureList" value="${(trustExpression as JSONObject).get("TrustExpressionFailureList") as JSONArray}"/>
<g:set var="trustExpressionFailure" value="${trustExpressionFailureList[0] as JSONObject}"/>
<g:set var="trustExpressionFailureType" value="${trustExpressionFailure.get("\$Type")}"/>
<g:set var="trustInteroperabilityProfileList" value="${trustExpressionFailure.get("TrustInteroperabilityProfileList") as JSONArray}"/>
<g:set var="trustInteroperabilityProfile" value="${trustInteroperabilityProfileList[0] as JSONObject}"/>
<g:set var="trustInteroperabilityProfileURI" value="${trustInteroperabilityProfile.get("Identifier")}"/>
<g:set var="trustInteroperabilityProfileName" value="${trustInteroperabilityProfile.get("Name")}"/>

<div class="${trustInteroperabilityProfileParentURI == "" ? "TrustExpressionTop" : trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI ? "TrustExpressionSub" : "TrustExpression"} FAILURE">
    <g:if test="${trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI}">
        <div class="TrustInteroperabilityProfileInner"><span class="glyphicon bi-list-ul"></span>${trustInteroperabilityProfileName}
            <span class="EvaluationLocalDateTime">${evaluationLocalDateTime}</span>
        </div>
    </g:if>

    <div class="TrustExpressionTerminal">
        <g:if test="${trustExpressionFailureType == "TrustExpressionFailureURI"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureURI</label>
        </g:if>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureResolveTrustInteroperabilityProfile"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureResolveTrustInteroperabilityProfile</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureResolveTrustmarkDefinition"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureResolveTrustmarkDefinition</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureParser"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureParser</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureIdentifierUnknown"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureIdentifierUnknown</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureNonTerminalUnexpected"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureNonTerminalUnexpected</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureTrustmarkAbsent"}">

            <g:set var="trustmarkDefinitionRequirement" value="${trustExpressionFailure.get("TrustmarkDefinitionRequirement") as JSONObject}"/>
            <g:set var="trustmarkDefinitionRequirementName" value="${trustmarkDefinitionRequirement.get("Name")}"/>
            <g:set var="trustmarkDefinitionParameterIdentifier" value="${trustExpressionFailure.get("TrustmarkDefinitionParameterIdentifier")}"/>

            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}" title="This trustmark does not appear to be bound to the candidate system; the parameter &quot;${trustmarkDefinitionParameterIdentifier}&quot; does not appear to be bound to a value."><span class="glyphicon bi-tag"></span>${trustmarkDefinitionParameterIdentifier}</label>

            <div class="TrustExpressionDetail">
                <div class="Head">
                    Failure
                </div>

                <div>
                    This trustmark does not appear to be bound to the candidate system; the parameter "${trustmarkDefinitionParameterIdentifier}" does not appear to be bound to a value.
                </div>

                <div class="TrustInteroperabilityProfileReferenceList Body">
                    <g:each in="${trustInteroperabilityProfileList}" var="it" status="index">
                        <g:if test="${index == 0}">
                            <div>Referenced in</div>
                        </g:if>
                        <g:else>
                            <div>Referenced by</div>
                        </g:else>
                        <div>
                            <a target="_blank"
                               href="${(it as JSONObject).get("Identifier")}">${(it as JSONObject).has("Name") ? (it as JSONObject).get("Name") : "(no name)"}</a>
                        </div>
                    </g:each>
                </div>
            </div>

        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureTrustmarkVerifierFailure"}">

            <g:set var="trustmarkDefinitionRequirement" value="${trustExpressionFailure.get("TrustmarkDefinitionRequirement") as JSONObject}"/>
            <g:set var="trustmarkDefinitionRequirementName" value="${trustmarkDefinitionRequirement.get("Name")}"/>
            <g:set var="trustmarkVerifierFailureNonEmptyList" value="${trustExpressionFailure.get("TrustmarkVerifierFailureNonEmptyList") as JSONArray}"/>

            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}" title="A trustmark is bound to the candidate system, but the trustmark relying party tool could not verify that trustmark."><span class="glyphicon bi-tag"></span>${trustmarkDefinitionRequirementName}</label>

            <div class="TrustExpressionDetail">
                <div class="Head">
                    Failure
                </div>

                <div>
                    A trustmark is bound to the candidate system, but the trustmark relying party tool could not verify that trustmark.
                </div>

                <ul>
                    <g:each in="${trustmarkVerifierFailureNonEmptyList}" var="it" status="index">
                        <g:if test="${index == 0}"><li>"${it}"</li></g:if>
                        <g:else><li>"${it}"</li></g:else>
                    </g:each>
                </ul>

                <div class="TrustInteroperabilityProfileReferenceList Body">
                    <g:each in="${trustInteroperabilityProfileList}" var="it" status="index">
                        <g:if test="${index == 0}">
                            <div>Referenced in</div>
                        </g:if>
                        <g:else>
                            <div>Referenced by</div>
                        </g:else>
                        <div>
                            <a target="_blank"
                               href="${(it as JSONObject).get("Identifier")}">${(it as JSONObject).has("Name") ? (it as JSONObject).get("Name") : "(no name)"}</a>
                        </div>
                    </g:each>
                </div>
            </div>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureTypeUnexpected"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureTypeUnexpected</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureTypeUnexpectedLeft"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureTypeUnexpectedLeft</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureTypeUnexpectedRight"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureTypeUnexpectedRight</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureTypeMismatch"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureTypeMismatch</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureTypeUnorderableLeft"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureTypeUnorderableLeft</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureTypeUnorderableRight"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureTypeUnorderableRight</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureExpression"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureExpression</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureExpressionLeft"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureExpressionLeft</label>
        </g:elseif>
        <g:elseif test="${trustExpressionFailureType == "TrustExpressionFailureExpressionRight"}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}">TrustExpressionFailureExpressionRight</label>
        </g:elseif>
    </div>
</div>
