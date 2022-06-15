function initialize(
    uriSetFindOneUrl) {

    document.onreadystatechange = function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    }

    function onComplete() {

        find()
    }

    function find() {

        fetchGet(uriSetFindOneUrl)
            .then(response => response.json())
            .then(afterFind)
    }

    function afterFind(uriSet) {

        afterFindHelper(uriSet.trustmarkBindingRegistryUriTypeList, document.getElementById("trustmark-binding-registry-uri-tbody"), document.getElementById("trustmark-binding-registry-uri-template-empty"), document.getElementById("trustmark-binding-registry-uri-template-summary"))
        afterFindHelper(uriSet.trustInteroperabilityProfileUriList, document.getElementById("trust-interoperability-profile-uri-tbody"), document.getElementById("trust-interoperability-profile-uri-template-empty"), document.getElementById("trust-interoperability-profile-uri-template-summary"))
        afterFindHelper(uriSet.trustmarkDefinitionUriList, document.getElementById("trustmark-definition-uri-tbody"), document.getElementById("trustmark-definition-uri-template-empty"), document.getElementById("trustmark-definition-uri-template-summary"))
        afterFindHelper(uriSet.trustmarkUriList, document.getElementById("trustmark-uri-tbody"), document.getElementById("trustmark-uri-template-empty"), document.getElementById("trustmark-uri-template-summary"))
        afterFindHelper(uriSet.trustmarkStatusReportUriList, document.getElementById("trustmark-status-report-uri-tbody"), document.getElementById("trustmark-status-report-uri-template-empty"), document.getElementById("trustmark-status-report-uri-template-summary"))
        afterFindHelper(uriSet.trustmarkBindingRegistryOrganizationMapUriList, document.getElementById("trustmark-binding-registry-organization-map-uri-tbody"), document.getElementById("trustmark-binding-registry-organization-map-uri-template-empty"), document.getElementById("trustmark-binding-registry-organization-map-uri-template-summary"))
        afterFindHelper(uriSet.trustmarkBindingRegistryOrganizationTrustmarkMapUriList, document.getElementById("trustmark-binding-registry-organization-trustmark-map-uri-tbody"), document.getElementById("trustmark-binding-registry-organization-trustmark-map-uri-template-empty"), document.getElementById("trustmark-binding-registry-organization-trustmark-map-uri-template-summary"))
    }

    function afterFindHelper(uriList, tbody, templateEmpty, templateSummary) {

        function uriForDocument(uri) {

            return uri.documentSuccessLocalDateTime == null ?
                uri.documentFailureLocalDateTime == null ?
                    uri.documentRequestLocalDateTime == null ?
                        {localDateTime: "", status: "UNKNOWN", message: ""} :
                        {localDateTime: moment(uri.documentRequestLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "UNKNOWN", message: ""} :
                    {localDateTime: moment(uri.documentFailureLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "FAILURE", message: uri.documentFailureMessage} :
                uri.documentFailureLocalDateTime == null ?
                    {localDateTime: moment(uri.documentSuccessLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "SUCCESS", message: ""} :
                    uri.documentSuccessLocalDateTime <= uri.documentFailureLocalDateTime ?
                        {localDateTime: moment(uri.documentFailureLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "FAILURE", message: uri.documentFailureMessage} :
                        {localDateTime: moment(uri.documentSuccessLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "SUCCESS", message: ""};
        }

        function uriForServer(uri) {

            return uri.serverSuccessLocalDateTime == null ?
                uri.serverFailureLocalDateTime == null ?
                    uri.serverRequestLocalDateTime == null ?
                        {localDateTime: "", status: "UNKNOWN", message: ""} :
                        {localDateTime: moment(uri.serverRequestLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "UNKNOWN", message: ""} :
                    {localDateTime: moment(uri.serverFailureLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "FAILURE", message: uri.serverFailureMessage} :
                uri.serverFailureLocalDateTime == null ?
                    {localDateTime: moment(uri.serverSuccessLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "SUCCESS", message: ""} :
                    uri.serverSuccessLocalDateTime <= uri.serverFailureLocalDateTime ?
                        {localDateTime: moment(uri.serverFailureLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "FAILURE", message: uri.serverFailureMessage} :
                        {localDateTime: moment(uri.serverSuccessLocalDateTime).format('MM-DD-YYYY hh:mm:ss A UTC'), status: "SUCCESS", message: ""};
        }


        if (uriList.length === 0) {

            const uriElement = templateEmpty.content.cloneNode(true)
            tbody.appendChild(uriElement)

        } else {

            uriList.sort((uri1, uri2) => uri1.uri.localeCompare(uri2.uri)).forEach(uri => {

                const uriElement = templateSummary.content.cloneNode(true)

                const uriElementRowList = uriElement.querySelectorAll(".uri-element")
                const uriElementUri = uriElement.querySelector(".uri-element-uri")
                const uriElementDocumentLocalDateTime = uriElement.querySelector(".uri-element-document-date-time")
                const uriElementDocumentStatus = uriElement.querySelector(".uri-element-document-status")
                const uriElementDocumentMessage = uriElement.querySelector(".uri-element-document-message")
                const uriElementServerLocalDateTime = uriElement.querySelector(".uri-element-server-date-time")
                const uriElementServerStatus = uriElement.querySelector(".uri-element-server-status")
                const uriElementServerMessage = uriElement.querySelector(".uri-element-server-message")

                uriElementUri.href = uri.uri;
                uriElementUri.innerHTML = uri.uri;
                uriElementUri.title = uri.uri

                const uriDocument = uriForDocument(uri)
                uriElementDocumentLocalDateTime.innerHTML = uriDocument.localDateTime
                uriElementDocumentStatus.innerHTML = uriDocument.status
                uriElementDocumentMessage.innerHTML = uriDocument.message

                const uriServer = uriForServer(uri)
                uriElementServerLocalDateTime.innerHTML = uriServer.localDateTime
                uriElementServerStatus.innerHTML = uriServer.status
                uriElementServerMessage.innerHTML = uriServer.message

                if (uriDocument.status === "SUCCESS") {
                    uriElementRowList.forEach(uriElementRow => uriElementRow.classList.add("text-success"))
                } else {
                    if (uriServer.status === "FAILURE") {
                        uriElementRowList.forEach(uriElementRow => uriElementRow.classList.add("text-danger"))
                    } else {
                        uriElementRowList.forEach(uriElementRow => uriElementRow.classList.add("text-warning"))
                    }
                }

                tbody.appendChild(uriElement)
            })
        }
    }
}
