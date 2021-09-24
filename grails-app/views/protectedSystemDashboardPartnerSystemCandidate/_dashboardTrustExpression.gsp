<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType; org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>

<g:set var="trustExpressionType" value="${(trustExpression as JSONObject).get("\$Type")}"/>

<g:if test="${trustExpressionType.equals("TrustExpressionOperatorNot")}">

    <g:set var="trustExpressionData" value="${(trustExpression as JSONObject).get("TrustExpressionData") as JSONObject}"/>

    <g:if test="${trustExpressionData.has("TrustExpressionFailureList")}">

        <g:set var="trustExpressionFailureList" value="${trustExpressionData.get("TrustExpressionFailureList") as JSONArray}"/>
        <g:set var="trustExpressionFailure" value="${trustExpressionFailureList[0] as JSONObject}"/>
        <g:set var="trustExpressionFailureType" value="${trustExpressionFailure.get("\$Type")}"/>
        <g:set var="trustInteroperabilityProfileList" value="${trustExpressionFailure.get("TrustInteroperabilityProfileList") as JSONArray}"/>
        <g:set var="trustInteroperabilityProfile" value="${trustInteroperabilityProfileList[0] as JSONObject}"/>
        <g:set var="trustInteroperabilityProfileURI" value="${trustInteroperabilityProfile.get("Identifier")}"/>
        <g:set var="trustInteroperabilityProfileName" value="${trustInteroperabilityProfile.get("Name")}"/>

        <div class="${trustInteroperabilityProfileParentURI == "" ? "TrustExpressionTop" : trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI ? "TrustExpressionSub" : "TrustExpression"} FAILURE">
            <g:if test="${trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI}">
                <g:set var="id" value="${UUID.randomUUID().toString()}"/>
                <input type="checkbox" id="id-${id}" ${trustInteroperabilityProfileParentURI != "" ? "checked" : ""}>
                <label class="TrustInteroperabilityProfileInner" for="id-${id}"><span class="glyphicon bi-list-ul"></span>${trustInteroperabilityProfileName}</label>
            </g:if>

            <div class="TrustExpressionOperatorUnary">
                <div><div>
                    <g:if test="${trustExpressionType.equals("TrustExpressionOperatorNot")}">NOT</g:if>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorExists")}">EXISTS</g:elseif>
                    <g:else>?</g:else>
                </div></div>

                <div>
                    <g:render template="dashboardTrustExpression"
                              model="${[trustExpression                      : (trustExpression as JSONObject).get("TrustExpression"),
                                        trustInteroperabilityProfileParentURI: trustInteroperabilityProfileURI]}"/>
                </div>
            </div>
        </div>
    </g:if>
    <g:else>
        <g:set var="trustInteroperabilityProfileList" value="${(trustExpressionData.get("TrustExpressionEvaluatorDataSource") as JSONObject).get("TrustInteroperabilityProfileList") as JSONArray}"/>
        <g:set var="trustInteroperabilityProfile" value="${trustInteroperabilityProfileList[0] as JSONObject}"/>
        <g:set var="trustInteroperabilityProfileURI" value="${trustInteroperabilityProfile.get("Identifier")}"/>
        <g:set var="trustInteroperabilityProfileName" value="${trustInteroperabilityProfile.get("Name")}"/>

        <g:set var="trustExpressionEvaluatorDataType" value="${trustExpressionData.get("TrustExpressionEvaluatorDataType")}"/>
        <g:set var="trustExpressionEvaluatorDataValue" value="${trustExpressionData.get("TrustExpressionEvaluatorDataValue")}"/>
        <g:set var="trustExpressionEvaluatorState" value="${(trustInteroperabilityProfileParentURI == "" && trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString() && trustExpressionEvaluatorDataValue == true) || trustInteroperabilityProfileParentURI != "" ? "SUCCESS" : "FAILURE"}"/>

        <div class="${trustInteroperabilityProfileParentURI == "" ? "TrustExpressionTop" : trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI ? "TrustExpressionSub" : "TrustExpression"} ${trustExpressionEvaluatorState}">
            <g:if test="${trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI}">
                <g:set var="id" value="${UUID.randomUUID().toString()}"/>
                <input type="checkbox" id="id-${id}" ${trustInteroperabilityProfileParentURI != "" ? "checked" : ""}>
                <label class="TrustInteroperabilityProfileInner" for="id-${id}"><span class="glyphicon bi-list-ul"></span>${trustInteroperabilityProfileName}</label>
            </g:if>

            <div class="TrustExpressionOperatorUnary">
                <div><div>
                    <g:if test="${trustExpressionType.equals("TrustExpressionOperatorNot")}">NOT</g:if>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorExists")}">EXISTS</g:elseif>
                    <g:else>?</g:else>
                </div></div>

                <div>
                    <g:render template="dashboardTrustExpression"
                              model="${[trustExpression                      : (trustExpression as JSONObject).get("TrustExpression"),
                                        trustInteroperabilityProfileParentURI: trustInteroperabilityProfileURI]}"/>
                </div>
            </div>
        </div>
    </g:else>
</g:if>
<g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorAnd") ||
        trustExpressionType.equals("TrustExpressionOperatorOr") ||
        trustExpressionType.equals("TrustExpressionOperatorLessThan") ||
        trustExpressionType.equals("TrustExpressionOperatorLessThanOrEqual") ||
        trustExpressionType.equals("TrustExpressionOperatorGreaterThanOrEqual") ||
        trustExpressionType.equals("TrustExpressionOperatorGreaterThan") ||
        trustExpressionType.equals("TrustExpressionOperatorEqual") ||
        trustExpressionType.equals("TrustExpressionOperatorNotEqual") ||
        trustExpressionType.equals("TrustExpressionOperatorContains")}">

    <g:set var="trustExpressionData" value="${(trustExpression as JSONObject).get("TrustExpressionData") as JSONObject}"/>

    <g:if test="${trustExpressionData.has("TrustExpressionFailureList")}">

        <g:set var="trustExpressionFailureList" value="${trustExpressionData.get("TrustExpressionFailureList") as JSONArray}"/>
        <g:set var="trustExpressionFailure" value="${trustExpressionFailureList[0] as JSONObject}"/>
        <g:set var="trustExpressionFailureType" value="${trustExpressionFailure.get("\$Type")}"/>
        <g:set var="trustInteroperabilityProfileList" value="${trustExpressionFailure.get("TrustInteroperabilityProfileList") as JSONArray}"/>
        <g:set var="trustInteroperabilityProfile" value="${trustInteroperabilityProfileList[0] as JSONObject}"/>
        <g:set var="trustInteroperabilityProfileURI" value="${trustInteroperabilityProfile.get("Identifier")}"/>
        <g:set var="trustInteroperabilityProfileName" value="${trustInteroperabilityProfile.get("Name")}"/>

        <div class="${trustInteroperabilityProfileParentURI == "" ? "TrustExpressionTop" : trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI ? "TrustExpressionSub" : "TrustExpression"} FAILURE">
            <g:if test="${trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI}">
                <g:set var="id" value="${UUID.randomUUID().toString()}"/>
                <input type="checkbox" id="id-${id}" ${trustInteroperabilityProfileParentURI != "" ? "checked" : ""}>
                <label class="TrustInteroperabilityProfileInner" for="id-${id}"><span class="glyphicon bi-list-ul"></span>${trustInteroperabilityProfileName}</label>
            </g:if>

            <div class="TrustExpressionOperatorBinary">
                <div><div>
                    <g:if test="${trustExpressionType.equals("TrustExpressionOperatorAnd")}">AND</g:if>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorOr")}">OR</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorLessThan")}">&lt;</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorLessThanOrEqual")}">&lt;=</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorGreaterThanOrEqual")}">&gt;=</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorGreaterThan")}">&gt;</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorEqual")}">==</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorNotEqual")}">!=</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorContains")}">CONTAINS</g:elseif>
                    <g:else>?</g:else>
                </div></div>

                <div>
                    <g:each in="${((trustExpression as JSONObject).get("TrustExpression") as JSONArray)}">
                        <g:render template="dashboardTrustExpression"
                                  model="${[trustExpression                      : it,
                                            trustInteroperabilityProfileParentURI: trustInteroperabilityProfileURI]}"/>
                    </g:each>
                </div>
            </div>
        </div>
    </g:if>
    <g:else>
        <g:set var="trustInteroperabilityProfileList" value="${(trustExpressionData.get("TrustExpressionEvaluatorDataSource") as JSONObject).get("TrustInteroperabilityProfileList") as JSONArray}"/>
        <g:set var="trustInteroperabilityProfile" value="${trustInteroperabilityProfileList[0] as JSONObject}"/>
        <g:set var="trustInteroperabilityProfileURI" value="${trustInteroperabilityProfile.get("Identifier")}"/>
        <g:set var="trustInteroperabilityProfileName" value="${trustInteroperabilityProfile.get("Name")}"/>

        <g:set var="trustExpressionEvaluatorDataType" value="${trustExpressionData.get("TrustExpressionEvaluatorDataType")}"/>
        <g:set var="trustExpressionEvaluatorDataValue" value="${trustExpressionData.get("TrustExpressionEvaluatorDataValue")}"/>
        <g:set var="trustExpressionEvaluatorState" value="${trustInteroperabilityProfileParentURI == "" ? (trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString() ? (trustExpressionEvaluatorDataValue ? "SUCCESS" : "FAILURE") : "FAILURE") : (trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString() ? (trustExpressionEvaluatorDataValue ? "SUCCESS" : "FAILURE") : "UNKNOWN")}"/>

        <div class="${trustInteroperabilityProfileParentURI == "" ? "TrustExpressionTop" : trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI ? "TrustExpressionSub" : "TrustExpression"} ${trustExpressionEvaluatorState}">
            <g:if test="${trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI}">
                <g:set var="id" value="${UUID.randomUUID().toString()}"/>
                <input type="checkbox" id="id-${id}" ${trustInteroperabilityProfileParentURI != "" ? "checked" : ""}>
                <label class="TrustInteroperabilityProfileInner" for="id-${id}"><span class="glyphicon bi-list-ul"></span>${trustInteroperabilityProfileName}</label>
            </g:if>

            <div class="TrustExpressionOperatorBinary">
                <div><div>
                    <g:if test="${trustExpressionType.equals("TrustExpressionOperatorAnd")}">AND</g:if>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorOr")}">OR</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorLessThan")}">&lt;</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorLessThanOrEqual")}">&lt;=</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorGreaterThanOrEqual")}">&gt;=</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorGreaterThan")}">&gt;</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorEqual")}">==</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorNotEqual")}">!=</g:elseif>
                    <g:elseif test="${trustExpressionType.equals("TrustExpressionOperatorContains")}">CONTAINS</g:elseif>
                    <g:else>?</g:else>
                </div></div>

                <div>
                    <g:each in="${((trustExpression as JSONObject).get("TrustExpression") as JSONArray)}">
                        <g:render template="dashboardTrustExpression"
                                  model="${[trustExpression                      : it,
                                            trustInteroperabilityProfileParentURI: trustInteroperabilityProfileURI]}"/>
                    </g:each>
                </div>
            </div>
        </div>
    </g:else>
</g:elseif>
<g:elseif test="${trustExpressionType.equals("TrustExpressionEvaluatorDataValueBoolean") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueDateTimeStamp") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueDecimal") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueString") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueStringList") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueNone")}">

    <g:set var="trustExpressionEvaluatorDataValue" value="${(trustExpression as JSONObject).get("TrustExpressionEvaluatorDataValue")}"/>
    <g:set var="trustExpressionEvaluatorDataType" value="${(trustExpression as JSONObject).get("TrustExpressionEvaluatorDataType")}"/>
    <g:set var="trustExpressionEvaluatorDataSource" value="${(trustExpression as JSONObject).get("TrustExpressionEvaluatorDataSource") as JSONObject}"/>
    <g:set var="trustExpressionEvaluatorDataSourceType" value="${trustExpressionEvaluatorDataSource.get("\$Type")}"/>
    <g:set var="trustInteroperabilityProfileList" value="${trustExpressionEvaluatorDataSource.get("TrustInteroperabilityProfileList") as JSONArray}"/>
    <g:set var="trustInteroperabilityProfile" value="${trustInteroperabilityProfileList[0] as JSONObject}"/>
    <g:set var="trustInteroperabilityProfileURI" value="${trustInteroperabilityProfile.get("Identifier")}"/>
    <g:set var="trustInteroperabilityProfileName" value="${trustInteroperabilityProfile.get("Name")}"/>
    <g:set var="trustExpressionEvaluatorState" value="${trustInteroperabilityProfileParentURI == "" ? (trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString() ? (trustExpressionEvaluatorDataValue ? "SUCCESS" : "FAILURE") : "FAILURE") : (trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString() ? (trustExpressionEvaluatorDataValue ? "SUCCESS" : "FAILURE") : "UNKNOWN")}"/>

    <div class="${trustInteroperabilityProfileParentURI == "" ? "TrustExpressionTop" : trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI ? "TrustExpressionSub" : "TrustExpression"} ${trustExpressionEvaluatorState}">
        <g:if test="${trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI}">
            <g:set var="id" value="${UUID.randomUUID().toString()}"/>
            <input type="checkbox" id="id-${id}" ${trustInteroperabilityProfileParentURI != "" ? "checked" : ""}>
            <label class="TrustInteroperabilityProfileInner" for="id-${id}"><span class="glyphicon bi-list-ul"></span>${trustInteroperabilityProfileName}</label>
        </g:if>

        <div class="TrustExpressionTerminal">
            <g:if test="${trustExpressionEvaluatorDataSourceType.equals("TrustExpressionEvaluatorSourceValue")}">

                <g:set var="id" value="${UUID.randomUUID().toString()}"/>
                <input type="checkbox" id="id-${id}">
                <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString()}">
                    <label for="id-${id}" title="A literal boolean."><span class="glyphicon bi-chat-left"></span>${trustExpressionEvaluatorDataValue}</label>
                </g:if>
                <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DATE_TIME_STAMP.toString()}">
                    <label for="id-${id}" title="A literal date time stamp."><span class="glyphicon bi-chat-left"></span>${trustExpressionEvaluatorDataValue}</label>
                </g:elseif>
                <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DECIMAL.toString()}">
                    <label for="id-${id}" title="A literal decimal."><span class="glyphicon bi-chat-left"></span>${trustExpressionEvaluatorDataValue}</label>
                </g:elseif>
                <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING.toString()}">
                    <label for="id-${id}" title="A literal string."><span class="glyphicon bi-chat-left"></span>"${trustExpressionEvaluatorDataValue}"</label>
                </g:elseif>
                <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING_LIST.toString()}">
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
                <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_NONE.toString()}">
                    <label for="id-${id}" title="A literal none."><span class="glyphicon bi-chat-left"></span>none</label>
                </g:elseif>
                </label>

                <div class="TrustExpressionDetail">

                    <div class="Head">
                        Evaluation:
                        <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString()}">Boolean</g:if>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DATE_TIME_STAMP.toString()}">Date Time Stamp</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DECIMAL.toString()}">Decimal</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING.toString()}">String</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING_LIST.toString()}">String List</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_NONE.toString()}">None</g:elseif>
                    </div>

                    <div>
                        A literal <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString()}">boolean: ${trustExpressionEvaluatorDataValue}.</g:if>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DATE_TIME_STAMP.toString()}">date time stamp: ${trustExpressionEvaluatorDataValue}.</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DECIMAL.toString()}">decimal: ${trustExpressionEvaluatorDataValue}.</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING.toString()}">string: "${trustExpressionEvaluatorDataValue}".</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING_LIST.toString()}">
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
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_NONE.toString()}">none.</g:elseif>
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

                <g:set var="id" value="${UUID.randomUUID().toString()}"/>
                <input type="checkbox" id="id-${id}">
                <label for="id-${id}" title="${trustmarkList.isEmpty() ? "This trustmark does not appear to be bound to the candidate system." : "The candidate system satisfies the trustmark requirement."}"><span class="glyphicon bi-tag"></span>${trustmarkDefinitionRequirementName}</label>

                <div class="TrustExpressionDetail">
                    <g:if test="${trustmarkList.isEmpty()}">
                        <div class="Head">
                            Evaluation: False
                        </div>

                        <div>
                            This trustmark does not appear to be bound to the candidate system.
                        </div>
                    </g:if>
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
                        <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString()}">Boolean</g:if>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DATE_TIME_STAMP.toString()}">Date Time Stamp</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DECIMAL.toString()}">Decimal</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING.toString()}">String</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING_LIST.toString()}">String List</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_NONE.toString()}">None</g:elseif>
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
                        The parameter "${trustmarkDefinitionParameter}" is bound to a <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_BOOLEAN.toString()}">boolean: ${trustExpressionEvaluatorDataValue}.</g:if>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DATE_TIME_STAMP.toString()}">date time stamp: ${trustExpressionEvaluatorDataValue}.</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_DECIMAL.toString()}">decimal: ${trustExpressionEvaluatorDataValue}.</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING.toString()}">string: "${trustExpressionEvaluatorDataValue}".</g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING_LIST.toString()}">
                            <g:if test="${(trustExpressionEvaluatorDataValue as JSONArray).isEmpty()}">list of strings; the list is empty.</g:if>
                            <g:elseif test="${(trustExpressionEvaluatorDataValue as JSONArray).length() == 1}">list of strings: "${(trustExpressionEvaluatorDataValue as JSONArray)[0]}".</g:elseif>
                            <g:else>list of strings:</g:else>
                        </g:elseif>
                        <g:elseif test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_NONE.toString()}">none.</g:elseif>
                    </div>

                    <g:if test="${trustExpressionEvaluatorDataType == TrustExpressionType.TYPE_STRING_LIST.toString() && (trustExpressionEvaluatorDataValue as JSONArray).length() > 1}">
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
</g:elseif>
<g:elseif test="${trustExpressionType.equals("TrustExpressionFailure")}">

    <g:set var="trustExpressionFailureList" value="${(trustExpression as JSONObject).get("TrustExpressionFailureList") as JSONArray}"/>
    <g:set var="trustExpressionFailure" value="${trustExpressionFailureList[0] as JSONObject}"/>
    <g:set var="trustExpressionFailureType" value="${trustExpressionFailure.get("\$Type")}"/>
    <g:set var="trustInteroperabilityProfileList" value="${trustExpressionFailure.get("TrustInteroperabilityProfileList") as JSONArray}"/>
    <g:set var="trustInteroperabilityProfile" value="${trustInteroperabilityProfileList[0] as JSONObject}"/>
    <g:set var="trustInteroperabilityProfileURI" value="${trustInteroperabilityProfile.get("Identifier")}"/>
    <g:set var="trustInteroperabilityProfileName" value="${trustInteroperabilityProfile.get("Name")}"/>

    <div class="${trustInteroperabilityProfileParentURI == "" ? "TrustExpressionTop" : trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI ? "TrustExpressionSub" : "TrustExpression"} FAILURE">
        <g:if test="${trustInteroperabilityProfileParentURI != trustInteroperabilityProfileURI}">
            <div class="TrustInteroperabilityProfileInner"><span class="glyphicon bi-list-ul"></span>${trustInteroperabilityProfileName}</div>
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
        </div>
    </div>
</g:elseif>
