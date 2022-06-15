<!DOCTYPE html>
<html>
    <head>
        <asset:javascript src="uriSet_manage.js"/>

        <meta name="layout" content="main"/>

        <script type="text/javascript">
            initialize(
                "${createLink(controller:'uriSet', action: 'findOne')}"
            );
        </script>
    </head>

    <body>
        <div class="container pt-4">
            <h2>Trustmark Binding Registry URLs</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" rowspan="1" colspan="3">Document</th>
                        <th scope="col" rowspan="1" colspan="3">Server</th>
                    </tr>
                    <tr>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                    </tr>
                </thead>
                <template id="trustmark-binding-registry-uri-template-empty">
                    <tr>
                        <td colspan="6">(No URLs.)</td>
                    </tr>
                </template>
                <template id="trustmark-binding-registry-uri-template-summary">
                    <tr class="uri-element">
                        <td colspan="6"><a href="" target="_blank" class="uri-element-uri"></a></td>
                    </tr>
                    <tr class="uri-element">
                        <td><div class="uri-element-document-date-time"></div></td>
                        <td><div class="uri-element-document-status"></div></td>
                        <td><div class="uri-element-document-message"></div></td>
                        <td><div class="uri-element-server-date-time"></div></td>
                        <td><div class="uri-element-server-status"></div></td>
                        <td><div class="uri-element-server-message"></div></td>
                    </tr>
                </template>
                <tbody id="trustmark-binding-registry-uri-tbody">
                </tbody>
            </table>
        </div>

        <div class="container pt-4">
            <h2>Trust Interoperability Profile URLs</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" rowspan="1" colspan="3">Document</th>
                        <th scope="col" rowspan="1" colspan="3">Server</th>
                    </tr>
                    <tr>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                    </tr>
                </thead>
                <template id="trust-interoperability-profile-uri-template-empty">
                    <tr>
                        <td colspan="6">(No URLs.)</td>
                    </tr>
                </template>
                <template id="trust-interoperability-profile-uri-template-summary">
                    <tr class="uri-element">
                        <td colspan="6"><a href="" target="_blank" class="uri-element-uri"></a></td>
                    </tr>
                    <tr class="uri-element">
                        <td><div class="uri-element-document-date-time"></div></td>
                        <td><div class="uri-element-document-status"></div></td>
                        <td><div class="uri-element-document-message"></div></td>
                        <td><div class="uri-element-server-date-time"></div></td>
                        <td><div class="uri-element-server-status"></div></td>
                        <td><div class="uri-element-server-message"></div></td>
                    </tr>
                </template>
                <tbody id="trust-interoperability-profile-uri-tbody">
                </tbody>
            </table>
        </div>

        <div class="container pt-4">
            <h2>Trustmark Definition URLs</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" rowspan="1" colspan="3">Document</th>
                        <th scope="col" rowspan="1" colspan="3">Server</th>
                    </tr>
                    <tr>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                    </tr>
                </thead>
                <template id="trustmark-definition-uri-template-empty">
                    <tr>
                        <td colspan="6">(No URLs.)</td>
                    </tr>
                </template>
                <template id="trustmark-definition-uri-template-summary">
                    <tr class="uri-element">
                        <td colspan="6"><a href="" target="_blank" class="uri-element-uri"></a></td>
                    </tr>
                    <tr class="uri-element">
                        <td><div class="uri-element-document-date-time"></div></td>
                        <td><div class="uri-element-document-status"></div></td>
                        <td><div class="uri-element-document-message"></div></td>
                        <td><div class="uri-element-server-date-time"></div></td>
                        <td><div class="uri-element-server-status"></div></td>
                        <td><div class="uri-element-server-message"></div></td>
                    </tr>
                </template>
                <tbody id="trustmark-definition-uri-tbody">
                </tbody>
            </table>
        </div>

        <div class="container pt-4">
            <h2>Trustmark URLs</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" rowspan="1" colspan="3">Document</th>
                        <th scope="col" rowspan="1" colspan="3">Server</th>
                    </tr>
                    <tr>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                    </tr>
                </thead>
                <template id="trustmark-uri-template-empty">
                    <tr>
                        <td colspan="6">(No URLs.)</td>
                    </tr>
                </template>
                <template id="trustmark-uri-template-summary">
                    <tr class="uri-element">
                        <td colspan="6"><a href="" target="_blank" class="uri-element-uri"></a></td>
                    </tr>
                    <tr class="uri-element">
                        <td><div class="uri-element-document-date-time"></div></td>
                        <td><div class="uri-element-document-status"></div></td>
                        <td><div class="uri-element-document-message"></div></td>
                        <td><div class="uri-element-server-date-time"></div></td>
                        <td><div class="uri-element-server-status"></div></td>
                        <td><div class="uri-element-server-message"></div></td>
                    </tr>
                </template>
                <tbody id="trustmark-uri-tbody">
                </tbody>
            </table>
        </div>

        <div class="container pt-4">
            <h2>Trustmark Status Report URLs</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" rowspan="1" colspan="3">Document</th>
                        <th scope="col" rowspan="1" colspan="3">Server</th>
                    </tr>
                    <tr>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                    </tr>
                </thead>
                <template id="trustmark-status-report-uri-template-empty">
                    <tr>
                        <td colspan="6">(No URLs.)</td>
                    </tr>
                </template>
                <template id="trustmark-status-report-uri-template-summary">
                    <tr class="uri-element">
                        <td colspan="6"><a href="" target="_blank" class="uri-element-uri"></a></td>
                    </tr>
                    <tr class="uri-element">
                        <td><div class="uri-element-document-date-time"></div></td>
                        <td><div class="uri-element-document-status"></div></td>
                        <td><div class="uri-element-document-message"></div></td>
                        <td><div class="uri-element-server-date-time"></div></td>
                        <td><div class="uri-element-server-status"></div></td>
                        <td><div class="uri-element-server-message"></div></td>
                    </tr>
                </template>
                <tbody id="trustmark-status-report-uri-tbody">
                </tbody>
            </table>
        </div>

        <div class="container pt-4">
            <h2>Trustmark Binding Registry Organization Map URLs</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" rowspan="1" colspan="3">Document</th>
                        <th scope="col" rowspan="1" colspan="3">Server</th>
                    </tr>
                    <tr>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                    </tr>
                </thead>
                <template id="trustmark-binding-registry-organization-map-uri-template-empty">
                    <tr>
                        <td colspan="6">(No URLs.)</td>
                    </tr>
                </template>
                <template id="trustmark-binding-registry-organization-map-uri-template-summary">
                    <tr class="uri-element">
                        <td colspan="6"><a href="" target="_blank" class="uri-element-uri"></a></td>
                    </tr>
                    <tr class="uri-element">
                        <td><div class="uri-element-document-date-time"></div></td>
                        <td><div class="uri-element-document-status"></div></td>
                        <td><div class="uri-element-document-message"></div></td>
                        <td><div class="uri-element-server-date-time"></div></td>
                        <td><div class="uri-element-server-status"></div></td>
                        <td><div class="uri-element-server-message"></div></td>
                    </tr>
                </template>
                <tbody id="trustmark-binding-registry-organization-map-uri-tbody">
                </tbody>
            </table>
        </div>

        <div class="container pt-4">
            <h2>Trustmark Binding Registry Organization Trustmark Map URLs</h2>
            <table class="table table-bordered table-striped-hack mb-0">
                <thead>
                    <tr>
                        <th scope="col" rowspan="1" colspan="3">Document</th>
                        <th scope="col" rowspan="1" colspan="3">Server</th>
                    </tr>
                    <tr>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                        <th scope="col" style="width: 16%">Date</th>
                        <th scope="col" style="width: 16%">Status</th>
                        <th scope="col" style="width: 16%">Message</th>
                    </tr>
                </thead>
                <template id="trustmark-binding-registry-organization-trustmark-map-uri-template-empty">
                    <tr>
                        <td colspan="6">(No URLs.)</td>
                    </tr>
                </template>
                <template id="trustmark-binding-registry-organization-trustmark-map-uri-template-summary">
                    <tr class="uri-element">
                        <td colspan="6"><a href="" target="_blank" class="uri-element-uri"></a></td>
                    </tr>
                    <tr class="uri-element">
                        <td><div class="uri-element-document-date-time"></div></td>
                        <td><div class="uri-element-document-status"></div></td>
                        <td><div class="uri-element-document-message"></div></td>
                        <td><div class="uri-element-server-date-time"></div></td>
                        <td><div class="uri-element-server-status"></div></td>
                        <td><div class="uri-element-server-message"></div></td>
                    </tr>
                </template>
                <tbody id="trustmark-binding-registry-organization-trustmark-map-uri-tbody">
                </tbody>
            </table>
        </div>
    </body>
</html>
