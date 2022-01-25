<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDateTimeStamp" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDecimal" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeString" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeStringList" contentType="text/html;charset=UTF-8" %>
<%@ page import="edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeNone" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONObject" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.JSONArray" contentType="text/html;charset=UTF-8" %>

<g:set var="trustExpressionType" value="${(trustExpression as JSONObject).get("\$Type")}"/>

<g:if test="${trustExpressionType.equals("TrustExpressionOperatorNoop") ||
        trustExpressionType.equals("TrustExpressionOperatorNot") ||
        trustExpressionType.equals("TrustExpressionOperatorExists")}">
    <g:render template="dashboardTrustExpressionUnary"
              model="${[trustExpression: trustExpression, trustInteroperabilityProfileParentURI: trustInteroperabilityProfileParentURI]}"/>
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
    <g:render template="dashboardTrustExpressionBinary"
              model="${[trustExpression: trustExpression, trustInteroperabilityProfileParentURI: trustInteroperabilityProfileParentURI]}"/>
</g:elseif>
<g:elseif test="${trustExpressionType.equals("TrustExpressionEvaluatorDataValueBoolean") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueDateTimeStamp") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueDecimal") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueString") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueStringList") ||
        trustExpressionType.equals("TrustExpressionEvaluatorDataValueNone")}">
    <g:render template="dashboardTrustExpressionTerminal"
              model="${[trustExpression: trustExpression, trustInteroperabilityProfileParentURI: trustInteroperabilityProfileParentURI]}"/>
</g:elseif>
<g:elseif test="${trustExpressionType.equals("TrustExpressionFailure")}">
    <g:render template="dashboardTrustExpressionFailure"
              model="${[trustExpression: trustExpression, trustInteroperabilityProfileParentURI: trustInteroperabilityProfileParentURI]}"/>
</g:elseif>
