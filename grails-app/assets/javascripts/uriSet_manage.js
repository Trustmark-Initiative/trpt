function initialize(
    profileFindOneUrl,
    uriSetFindOneUrl) {

    document.addEventListener("readystatechange", function () {

        if (document.readyState === "complete") {
            onComplete()
        }
    })

    function onComplete() {

        find()
    }

    function find() {

        profile(profileFindOneUrl)
            .then(role => role === undefined ? Promise.resolve() : fetchGet(uriSetFindOneUrl + "?" + new URLSearchParams({
                "trustInteroperabilityProfileUriOffset": (document.getElementById("trust-interoperability-profile-uri-page").innerHTML - 1) * 10,
                "trustInteroperabilityProfileUriMax": 10,
                "trustmarkBindingRegistryUriTypeOffset": (document.getElementById("trustmark-binding-registry-uri-page").innerHTML - 1) * 10,
                "trustmarkBindingRegistryUriTypeMax": 10,
                "trustmarkDefinitionUriOffset": (document.getElementById("trustmark-definition-uri-page").innerHTML - 1) * 10,
                "trustmarkDefinitionUriMax": 10,
                "trustmarkStatusReportUriOffset": (document.getElementById("trustmark-status-report-uri-page").innerHTML - 1) * 10,
                "trustmarkStatusReportUriMax": 10,
                "trustmarkUriOffset": (document.getElementById("trustmark-uri-page").innerHTML - 1) * 10,
                "trustmarkUriMax": 10,
                "trustmarkBindingRegistryOrganizationMapUriOffset": (document.getElementById("trustmark-binding-registry-organization-map-uri-page").innerHTML - 1) * 10,
                "trustmarkBindingRegistryOrganizationMapUriMax": 10,
                "trustmarkBindingRegistryOrganizationTrustmarkMapUriOffset": (document.getElementById("trustmark-binding-registry-organization-trustmark-map-uri-page").innerHTML - 1) * 10,
                "trustmarkBindingRegistryOrganizationTrustmarkMapUriMax": 10,
            }))
                .then(response => response.json())
                .then(afterFind))
    }

    function afterFind(uriSet) {

        afterFindHelper(uriSet.trustmarkBindingRegistryUriTypeList,
            document.getElementById("trustmark-binding-registry-uri-tbody"),
            document.getElementById("trustmark-binding-registry-uri-template-empty"),
            document.getElementById("trustmark-binding-registry-uri-template-summary"),
            "trustmark-binding-registry-uri-prev",
            "trustmark-binding-registry-uri-page",
            "trustmark-binding-registry-uri-next")
        afterFindHelper(uriSet.trustInteroperabilityProfileUriList,
            document.getElementById("trust-interoperability-profile-uri-tbody"),
            document.getElementById("trust-interoperability-profile-uri-template-empty"),
            document.getElementById("trust-interoperability-profile-uri-template-summary"),
            "trust-interoperability-profile-uri-prev",
            "trust-interoperability-profile-uri-page",
            "trust-interoperability-profile-uri-next")
        afterFindHelper(uriSet.trustmarkDefinitionUriList,
            document.getElementById("trustmark-definition-uri-tbody"),
            document.getElementById("trustmark-definition-uri-template-empty"),
            document.getElementById("trustmark-definition-uri-template-summary"),
            "trustmark-definition-uri-prev",
            "trustmark-definition-uri-page",
            "trustmark-definition-uri-next")
        afterFindHelper(uriSet.trustmarkUriList,
            document.getElementById("trustmark-uri-tbody"),
            document.getElementById("trustmark-uri-template-empty"),
            document.getElementById("trustmark-uri-template-summary"),
            "trustmark-uri-prev",
            "trustmark-uri-page",
            "trustmark-uri-next")
        afterFindHelper(uriSet.trustmarkStatusReportUriList,
            document.getElementById("trustmark-status-report-uri-tbody"),
            document.getElementById("trustmark-status-report-uri-template-empty"),
            document.getElementById("trustmark-status-report-uri-template-summary"),
            "trustmark-status-report-uri-prev",
            "trustmark-status-report-uri-page",
            "trustmark-status-report-uri-next")
        afterFindHelper(uriSet.trustmarkBindingRegistryOrganizationMapUriList,
            document.getElementById("trustmark-binding-registry-organization-map-uri-tbody"),
            document.getElementById("trustmark-binding-registry-organization-map-uri-template-empty"),
            document.getElementById("trustmark-binding-registry-organization-map-uri-template-summary"),
            "trustmark-binding-registry-organization-map-uri-prev",
            "trustmark-binding-registry-organization-map-uri-page",
            "trustmark-binding-registry-organization-map-uri-next")
        afterFindHelper(uriSet.trustmarkBindingRegistryOrganizationTrustmarkMapUriList,
            document.getElementById("trustmark-binding-registry-organization-trustmark-map-uri-tbody"),
            document.getElementById("trustmark-binding-registry-organization-trustmark-map-uri-template-empty"),
            document.getElementById("trustmark-binding-registry-organization-trustmark-map-uri-template-summary"),
            "trustmark-binding-registry-organization-trustmark-map-uri-prev",
            "trustmark-binding-registry-organization-trustmark-map-uri-page",
            "trustmark-binding-registry-organization-trustmark-map-uri-next")
    }

    function afterFindHelper(uriPage, tbody, templateEmpty, templateSummary, elementIdForPrev, elementIdForPage, elementIdForNext) {

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

        document.getElementById(elementIdForPage).innerHTML = (uriPage.offset / 10) + 1

        document.getElementById(elementIdForPrev).outerHTML = document.getElementById(elementIdForPrev).outerHTML

        if (uriPage.offset == 0) {
            document.getElementById(elementIdForPrev).parentElement.classList.add("disabled")
        } else {
            document.getElementById(elementIdForPrev).parentElement.classList.remove("disabled")
            document.getElementById(elementIdForPrev).addEventListener("click", function () {
                document.getElementById(elementIdForPage).innerHTML = (uriPage.offset / 10)
                find()
            })
        }

        document.getElementById(elementIdForNext).outerHTML = document.getElementById(elementIdForNext).outerHTML

        if (Math.floor(uriPage.count / 10) == uriPage.offset / 10) {
            document.getElementById(elementIdForNext).parentElement.classList.add("disabled")
        } else {
            document.getElementById(elementIdForNext).parentElement.classList.remove("disabled")
            document.getElementById(elementIdForNext).addEventListener("click", function () {
                document.getElementById(elementIdForPage).innerHTML = (uriPage.offset / 10) + 2
                find()
            })
        }

        tbody.innerHTML = ""

        if (uriPage.list.length === 0) {

            const uriElement = templateEmpty.content.cloneNode(true)
            tbody.appendChild(uriElement)

        } else {

            uriPage.list.sort((uri1, uri2) => uri1.uri.localeCompare(uri2.uri)).forEach(uri => {

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
