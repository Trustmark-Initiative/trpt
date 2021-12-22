package edu.gatech.gtri.trustmark.trpt.service.uri;

import edu.gatech.gtri.trustmark.trpt.domain.Uri;

import java.util.List;

public class UriSetUtility {

    public static <T1 extends Uri> List<UriResponse> uriResponseList(final org.gtri.fj.data.List<T1> uriList) {

        return uriList.map(UriSetUtility::uriResponse).toJavaList();
    }

    public static UriResponse uriResponse(final Uri uri) {

        return new UriResponse(
                uri.getUri(),
                uri.getDocumentRequestLocalDateTime(),
                uri.getDocumentSuccessLocalDateTime(),
                uri.getDocumentFailureLocalDateTime(),
                uri.getDocumentChangeLocalDateTime(),
                uri.getDocumentFailureMessage(),
                uri.getServerRequestLocalDateTime(),
                uri.getServerSuccessLocalDateTime(),
                uri.getServerFailureLocalDateTime(),
                uri.getServerChangeLocalDateTime(),
                uri.getServerFailureMessage());
    }
}
