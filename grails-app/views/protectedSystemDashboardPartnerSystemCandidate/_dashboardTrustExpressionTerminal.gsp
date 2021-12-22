<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDateTimeStamp" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDecimal" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeString" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeStringList" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeNone" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>

<g:set var="trustExpressionType" value="${(trustExpression as JSONObject).get("\$Type")}"/>

<g:set var="trustExpressionEvaluatorDataValue" value="${(trustExpression as JSONObject).get("TrustExpressionEvaluatorDataValue")}"/>
<g:set var="trustExpressionEvaluatorDataType" value="${(trustExpression as JSONObject).get("TrustExpressionEvaluatorDataType")}"/>
<g:set var="trustExpressionEvaluatorDataSource" value="${(trustExpression as JSONObject).get("TrustExpressionEvaluatorDataSource") as JSONObject}"/>
<g:set var="trustExpressionEvaluatorDataSourceType" value="${trustExpressionEvaluatorDataSource.get("\$Type")}"/>
<g:set var="trustInteroperabilityProfileList" value="${trustExpressionEvaluatorDataSource.get("TrustInteroperabilityProfileList") as JSONArray}"/>
<g:set var="trustInteroperabilityProfile" value="${trustInteroperabilityProfileList[0] as JSONObject}"/>
<g:set var="trustInteroperabilityProfileURI" value="${trustInteroperabilityProfile.get("Identifier")}"/>
<g:set var="trustInteroperabilityProfileName" value="${trustInteroperabilityProfile.get("Name")}"/>
<g:set var="trustExpressionEvaluatorState" value="${trustInteroperabilityProfileParentURI == "" ? (trustExpressionEvaluatorDataType == TrustExpressionTypeBoolean.TYPE_BOOLEAN.getClass().getSimpleName() ? (trustExpressionEvaluatorDataValue ? "TRUE" : "FALSE") : "FAILURE") : (trustExpressionEvaluatorDataType == TrustExpressionTypeBoolean.TYPE_BOOLEAN.getClass().getSimpleName() ? (trustExpressionEvaluatorDataValue ? "TRUE" : "FALSE") : "UNKNOWN")}"/>

<div class="${trustInteroperabilityProfileParentURI == "" ? "TrustExpressionTop" : trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI ? "TrustExpressionSub" : "TrustExpression"} ${trustExpressionEvaluatorState}">
    <g:if test="${trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI}">
        <g:set var="id" value="${UUID.randomUUID().toString()}"/>
        <input type="checkbox" id="id-${id}" ${trustInteroperabilityProfileParentURI != "" ? "checked" : ""}>
        <label class="TrustInteroperabilityProfileInner" for="id-${id}"><span class="glyphicon bi-list-ul"></span>${trustInteroperabilityProfileName}
            <span class="EvaluationLocalDateTime">${evaluationLocalDateTime}</span>
        </label>
    </g:if>

    <div class="TrustExpressionTerminal">
        <g:if test="${trustExpressionEvaluatorDataSourceType.equals("TrustExpressionEvaluatorSourceValue")}">

            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionTypeBoolean.TYPE_BOOLEAN.getClass().getSimpleName()}">
                <label for="id-${id}" title="A literal boolean."><span class="glyphicon bi-chat-left"></span>${trustExpressionEvaluatorDataValue}</label>
            </g:if>
            <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP.getClass().getSimpleName()}">
                <label for="id-${id}" title="A literal date time stamp."><span class="glyphicon bi-chat-left"></span>${trustExpressionEvaluatorDataValue}</label>
            </g:elseif>
            <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDecimal.TYPE_DECIMAL.getClass().getSimpleName()}">
                <label for="id-${id}" title="A literal decimal."><span class="glyphicon bi-chat-left"></span>${trustExpressionEvaluatorDataValue}</label>
            </g:elseif>
            <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeString.TYPE_STRING.getClass().getSimpleName()}">
                <label for="id-${id}" title="A literal string."><span class="glyphicon bi-chat-left"></span>"${trustExpressionEvaluatorDataValue}"</label>
            </g:elseif>
            <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeStringList.TYPE_STRING_LIST.getClass().getSimpleName()}">
                <g:if test="${(trustExpressionEvaluatorDataValue as JSONArray).isEmpty()}">
                    <label for="id-${id}" title="A literal string list."><span class="glyphicon bi-chat-left"></span>()</label>
                </g:if>
                <g:elseif test="${(trustExpressionEvaluatorDataValue as JSONArray).length() == 1}">
                    <label for="id-${id}" title="A literal string list."><span class="glyphicon bi-chat-left"></span>("${(trustExpressionEvaluatorDataValue as JSONArray)[0]}")</label>
                </g:elseif>
                <g:else>
                    <label for="id-${id}" title="A literal string list."><span class="glyphicon bi-chat-left"></span><g:each in="${trustExpressionEvaluatorDataValue as JSONArray}" var="it" status="index">
                        <g:if test="${index == 0}">("${it}"</g:if>
                        <g:elseif test="${index == (trustExpressionEvaluatorDataValue as JSONArray).length() - 1}">, "${it}")</g:elseif>
                        <g:else>, "${it}"</g:else>
                    </g:each>
                    </label>
                </g:else>
            </g:elseif>
            <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeNone.TYPE_NONE.getClass().getSimpleName()}">
                <label for="id-${id}" title="A literal none."><span class="glyphicon bi-chat-left"></span>none</label>
            </g:elseif>
            </label>

            <div class="TrustExpressionDetail">

                <div class="Head">
                    Evaluation:
                    <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionTypeBoolean.TYPE_BOOLEAN.getClass().getSimpleName()}">Boolean</g:if>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP.getClass().getSimpleName()}">Date Time Stamp</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDecimal.TYPE_DECIMAL.getClass().getSimpleName()}">Decimal</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeString.TYPE_STRING.getClass().getSimpleName()}">String</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeStringList.TYPE_STRING_LIST.getClass().getSimpleName()}">String List</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeNone.TYPE_NONE.getClass().getSimpleName()}">None</g:elseif>
                </div>

                <div>
                    A literal <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionTypeBoolean.TYPE_BOOLEAN.getClass().getSimpleName()}">boolean: ${trustExpressionEvaluatorDataValue}.</g:if>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP.getClass().getSimpleName()}">date time stamp: ${trustExpressionEvaluatorDataValue}.</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDecimal.TYPE_DECIMAL.getClass().getSimpleName()}">decimal: ${trustExpressionEvaluatorDataValue}.</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeString.TYPE_STRING.getClass().getSimpleName()}">string: "${trustExpressionEvaluatorDataValue}".</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeStringList.TYPE_STRING_LIST.getClass().getSimpleName()}">
                        <g:if test="${(trustExpressionEvaluatorDataValue as JSONArray).isEmpty()}">list of strings; the list is empty.</g:if>
                        <g:elseif test="${(trustExpressionEvaluatorDataValue as JSONArray).length() == 1}">list of strings: "${(trustExpressionEvaluatorDataValue as JSONArray)[0]}".</g:elseif>
                        <g:else>list of strings: <ul>
                            <g:each in="${trustExpressionEvaluatorDataValue as JSONArray}" var="it" status="index">
                                <g:if test="${index == 0}"><li>"${it}"</li></g:if>
                                <g:else><li>"${it}"</li></g:else>
                            </g:each>
                        </ul>
                        </g:else>
                    </g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeNone.TYPE_NONE.getClass().getSimpleName()}">none.</g:elseif>
                </div>

                <div class="Head">
                    Trust Interoperability Profile Trace
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
        </g:if>
        <g:elseif test="${trustExpressionEvaluatorDataSourceType.equals("TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement")}">

            <g:set var="trustmarkDefinitionRequirement" value="${trustExpressionEvaluatorDataSource.get("TrustmarkDefinitionRequirement") as JSONObject}"/>
            <g:set var="trustmarkDefinitionRequirementName" value="${trustmarkDefinitionRequirement.get("Name")}"/>
            <g:set var="trustmarkList" value="${trustExpressionEvaluatorDataSource.get("TrustmarkList") as JSONArray}"/>
            <g:set var="trustmarkVerifierFailureList" value="${trustExpressionEvaluatorDataSource.get("TrustmarkVerifierFailureList") as JSONArray}"/>

            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}" title="${trustmarkList.isEmpty() ? "This trustmark does not appear to be bound to the candidate system." : "The candidate system satisfies the trustmark requirement."}"><span class="glyphicon bi-tag"></span>${trustmarkDefinitionRequirementName}</label>

            <div class="TrustExpressionDetail">
                <g:if test="${trustmarkList.isEmpty() && trustmarkVerifierFailureList.isEmpty()}">
                    <div class="Head">
                        Evaluation: False
                    </div>

                    <div>
                        This trustmark does not appear to be bound to the candidate system.
                    </div>
                </g:if>
                <g:elseif test="${trustmarkList.isEmpty() && !trustmarkVerifierFailureList.isEmpty()}">
                    <div class="Head">
                        Evaluation: False
                    </div>

                    <div>
                        A trustmark was bound to the candidate system, but the trustmark relying party tool could not verify that trustmark.
                    </div>

                    <ul>
                        <g:each in="${trustmarkVerifierFailureList}" var="it" status="index">
                            <g:if test="${index == 0}"><li>"${it}"</li></g:if>
                            <g:else><li>"${it}"</li></g:else>
                        </g:each>
                    </ul>
                </g:elseif>
                <g:else>
                    <div class="Head">
                        Evaluation: True
                    </div>

                    <div>
                        The candidate system satisfies the trustmark requirement.
                    </div>

                    <g:each in="${trustmarkList}" var="it" status="index">
                        <div class="code">
                            <a target="_blank" href="${(it as JSONObject).get("Identifier")}">${(it as JSONObject).get("Identifier")}</a>
                        </div>
                    </g:each>
                </g:else>

                <div class="Head">
                    Trust Interoperability Profile Trace
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
        <g:elseif test="${trustExpressionEvaluatorDataSourceType.equals("TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter")}">

            <g:set var="trustmarkDefinitionRequirement" value="${trustExpressionEvaluatorDataSource.get("TrustmarkDefinitionRequirement") as JSONObject}"/>
            <g:set var="trustmarkDefinitionRequirementName" value="${trustmarkDefinitionRequirement.get("Name")}"/>
            <g:set var="trustmarkDefinitionParameter" value="${trustExpressionEvaluatorDataSource.get("TrustmarkDefinitionParameter")}"/>
            <g:set var="trustmarkList" value="${trustExpressionEvaluatorDataSource.get("TrustmarkList") as JSONArray}"/>

            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}">
            <label for="id-${id}" title="A trustmark parameter binding value."><span class="glyphicon bi-tag"></span>${trustmarkDefinitionParameter}</label>

            <div class="TrustExpressionDetail">
                <div class="Head">
                    Evaluation:
                    <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionTypeBoolean.TYPE_BOOLEAN.getClass().getSimpleName()}">Boolean</g:if>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP.getClass().getSimpleName()}">Date Time Stamp</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDecimal.TYPE_DECIMAL.getClass().getSimpleName()}">Decimal</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeString.TYPE_STRING.getClass().getSimpleName()}">String</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeStringList.TYPE_STRING_LIST.getClass().getSimpleName()}">String List</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeNone.TYPE_NONE.getClass().getSimpleName()}">None</g:elseif>
                </div>

                <div>
                    The candidate system satisfies the trustmark requirement.
                </div>

                <g:each in="${trustmarkList}" var="it" status="index">
                    <div class="code">
                        <a target="_blank" href="${(it as JSONObject).get("Identifier")}">${(it as JSONObject).get("Identifier")}</a>
                    </div>
                </g:each>

                <div>
                    The parameter "${trustmarkDefinitionParameter}" is bound to a <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionTypeBoolean.TYPE_BOOLEAN.getClass().getSimpleName()}">boolean: ${trustExpressionEvaluatorDataValue}.</g:if>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP.getClass().getSimpleName()}">date time stamp: ${trustExpressionEvaluatorDataValue}.</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeDecimal.TYPE_DECIMAL.getClass().getSimpleName()}">decimal: ${trustExpressionEvaluatorDataValue}.</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeString.TYPE_STRING.getClass().getSimpleName()}">string: "${trustExpressionEvaluatorDataValue}".</g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeStringList.TYPE_STRING_LIST.getClass().getSimpleName()}">
                        <g:if test="${(trustExpressionEvaluatorDataValue as JSONArray).isEmpty()}">list of strings; the list is empty.</g:if>
                        <g:elseif test="${(trustExpressionEvaluatorDataValue as JSONArray).length() == 1}">list of strings: "${(trustExpressionEvaluatorDataValue as JSONArray)[0]}".</g:elseif>
                        <g:else>list of strings:</g:else>
                    </g:elseif>
                    <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionTypeNone.TYPE_NONE.getClass().getSimpleName()}">none.</g:elseif>
                </div>

                <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionTypeStringList.TYPE_STRING_LIST.getClass().getSimpleName() && (trustExpressionEvaluatorDataValue as JSONArray).length() > 1}">
                    <ul>
                        <g:each in="${trustExpressionEvaluatorDataValue as JSONArray}" var="it" status="index">
                            <g:if test="${index == 0}"><li>"${it}"</li></g:if>
                            <g:else><li>"${it}"</li></g:else>
                        </g:each>
                    </ul>
                </g:if>

                <div class="Head">
                    Trust Interoperability Profile Trace
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
    </div>
</div>

