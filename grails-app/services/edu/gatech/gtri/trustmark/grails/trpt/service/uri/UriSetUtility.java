package edu.gatech.gtri.trustmark.grails.trpt.service.uri;

import edu.gatech.gtri.trustmark.grails.trpt.domain.Uri;
import grails.gorm.PagedResultList;

import java.util.List;
import java.util.stream.Collectors;

public class UriSetUtility {

    public static <T1 extends Uri> UriSetResponsePage uriSetResponsePage(final long offset, final long max, final PagedResultList<T1> uriPage) {

        return new UriSetResponsePage(
                uriPage.getTotalCount(),
                offset,
                max,
                uriResponseList(uriPage));
    }

    public static <T1 extends Uri> List<UriResponse> uriResponseList(final PagedResultList<T1> uriPage) {

        return uriPage.stream().map(UriSetUtility::uriResponse).collect(Collectors.toList());
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
