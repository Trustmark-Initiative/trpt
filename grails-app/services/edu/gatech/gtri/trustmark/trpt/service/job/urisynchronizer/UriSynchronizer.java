package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.Uri;
import edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility;
import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import org.apache.commons.logging.Log;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Effect2;
import org.gtri.fj.function.F0;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.Try;
import org.gtri.fj.function.Try1;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P3;
import org.springframework.security.crypto.codec.Hex;

import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.truncate;
import static java.lang.String.format;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.product.P.p;

public class UriSynchronizer<T1 extends HasSource, T2 extends Uri, T3 extends Uri> {

    private final Log log;
    private final String nameForHasSource;
    private final Try1<URI, T1, Exception> resolver;
    private final Try1<T1, byte[], Exception> hasher;
    private final F0<List<T2>> withTransactionUriFindAll;
    private final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind;
    private final F0<T2> uriSupplier;
    private final Effect2<T1, T2> uriSetter;
    private final F1<T2, T2> uriSave;
    private final Option<F0<T3>> uriHistorySupplier;
    private final Option<Effect2<T2, T3>> uriHistorySetter;
    private final Option<F1<T3, T3>> uriHistorySave;

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final Try1<URI, T1, Exception> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final F1<T2, T2> uriSave) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                (hasSource, uri) -> {
                },
                uriSave,
                none(),
                none(),
                none());
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final Try1<URI, T1, Exception> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final F1<T2, T2> uriSave,
            final F0<T3> uriHistorySupplier,
            final F1<T3, T3> uriHistorySave) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                (hasSource, uri) -> {
                },
                uriSave,
                uriHistorySupplier,
                (uri, uriHistory) -> {
                },
                uriHistorySave);
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final Try1<URI, T1, Exception> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final Effect2<T1, T2> uriSetter,
            final F1<T2, T2> uriSave,
            final F0<T3> uriHistorySupplier,
            final Effect2<T2, T3> uriHistorySetter,
            final F1<T3, T3> uriHistorySave) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                uriSetter,
                uriSave,
                some(uriHistorySupplier),
                some(uriHistorySetter),
                some(uriHistorySave));
    }

    private UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final Try1<URI, T1, Exception> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final Effect2<T1, T2> uriSetter,
            final F1<T2, T2> uriSave,
            final Option<F0<T3>> uriHistorySupplier,
            final Option<Effect2<T2, T3>> uriHistorySetter,
            final Option<F1<T3, T3>> uriHistorySave) {

        this.log = log;
        this.nameForHasSource = nameForHasSource;
        this.resolver = resolver;
        this.hasher = hasher;
        this.withTransactionUriFindAll = withTransactionUriFindAll;
        this.withTransactionUriFind = withTransactionUriFind;
        this.uriSupplier = uriSupplier;
        this.uriSetter = uriSetter;
        this.uriSave = uriSave;
        this.uriHistorySupplier = uriHistorySupplier;
        this.uriHistorySetter = uriHistorySetter;
        this.uriHistorySave = uriHistorySave;
    }

    public void synchronizeUri() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        RetryTemplateUtility.retry(withTransactionUriFindAll, log)
                .map(uri -> uri.getUri())
                .map(uriString -> synchronizeUri(now, uriString));
    }

    public Validation<NonEmptyList<Exception>, T1> synchronizeUri(final LocalDateTime now, final String uriString) {

        return this.<T1>synchronizeDocument(uriString)
                .f().map(exceptionForDocument -> org.gtri.fj.data.Either.reduce(synchronizeServer(uriString).toEither().bimap(
                        exceptionForServer -> {
                            updateUri(uriString, uri -> onServerFailure(now, uri, exceptionForDocument, exceptionForServer));
                            return nel(exceptionForDocument, exceptionForServer);
                        },
                        server -> {
                            updateUri(uriString, uri -> onServerSuccess(now, uri, exceptionForDocument));
                            return nel(exceptionForDocument);
                        })))
                .map(document -> {
                    updateUri(uriString, uri -> onDocumentSuccess(now, uri, document._2(), document._3()));
                    return document._2();
                });
    }

    private T2 updateUri(final String uriString, final F1<T2, T2> uriUpdate) {

        return RetryTemplateUtility.retry(() -> withTransactionUriFind.f(uriString, uriOption -> {
            final T2 uri = uriOption.orSome(() -> {
                final T2 uriInner = uriSupplier.f();
                uriInner.setUri(uriString);
                return uriInner;
            });

            return uriSave.f(uriUpdate.f(uri));
        }), log);
    }

    /**
     * Request the URI, return the URI, the resolved entity, and the hash for the resolved entity; return the exception if the system cannot convert the string to a URI, if the system cannot resolve the URI, or if the system cannot hash the resolved entity.
     *
     * @param uriString the uri
     * @param <T1>      the type of the resolved entty
     * @return the URI, the resolved entity, and the hash for the resolved entity
     */
    private <T1> Validation<Exception, P3<URI, T1, String>> synchronizeDocument(
            final String uriString) {

        log.info(format("%s uri '%s' document request ...", nameForHasSource, uriString));

        final Validation<Exception, P3<URI, T1, String>> validation = Try.<URI, Exception>f(() -> URI.create(uriString))._1()
                .bind(uri -> Try.<T1, Exception>f(() -> ((Try1<URI, T1, Exception>) this.resolver).f(uri))._1().map(entity -> p(uri, entity)))
                .bind(p -> Try.<String, Exception>f(() -> new String(Hex.encode(((Try1<T1, byte[], Exception>) this.hasher).f(p._2()))))._1().map(hash -> p(p._1(), p._2(), hash)));

        validation.toEither().foreachDoEffect(
                exception -> log.info(format("%s uri '%s' document request failure: '%s'", nameForHasSource, uriString, exception.getMessage())),
                p -> log.info(format("%s uri '%s' document request success.", nameForHasSource, uriString)));

        return validation;
    }

    /**
     * Request the URI's server, return the status code and the status line; return the exception if the system cannot convert the string to a URI, if the system cannot resolve the URI, or if the status code is not 200.
     *
     * @param uriString the uri
     * @return the status code and the status line
     */
    private Validation<Exception, P2<Integer, String>> synchronizeServer(
            final String uriString) {

        log.info(format("%s uri '%s' server request ...", nameForHasSource, uriString));

        final Validation<Exception, P2<Integer, String>> validation = Try.<URI, Exception>f(() -> URI.create(uriString))._1()
                .bind(uriInner -> Validation.condition(uriInner.getScheme() == null || uriInner.getScheme().equals("http") || uriInner.getScheme().equals("https"),
                        new Exception("The scheme is neither 'http' nor 'https'."),
                        uriInner))
                .bind(uriInner -> Try.<URI, Exception>f(() -> new URI(uriInner.getScheme(), uriInner.getAuthority(), null, null, null))._1())
                .bind(uriInner -> Try.<P2<Integer, String>, Exception>f(() -> {
                    final HttpURLConnection httpURLConnection = (HttpURLConnection) uriInner.toURL().openConnection();
                    return p(httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                })._1())
                .bind(response -> Validation.condition(response._1() == 200,
                        new Exception(format("%s: %s", response._1(), response._2())),
                        response));

        validation.toEither().foreachDoEffect(
                exception -> log.info(format("%s uri '%s' server request failure: '%s'", nameForHasSource, uriString, exception.getMessage())),
                p -> log.info(format("%s uri '%s' server request success.", nameForHasSource, uriString)));

        return validation;
    }

    private T2 onDocumentSuccess(
            final LocalDateTime now,
            final T2 uri,
            final T1 hasSource,
            final String hash) {

        uri.setDocumentRequestLocalDateTime(now);
        uri.setDocumentSuccessLocalDateTime(now);
        uri.setServerRequestLocalDateTime(now);
        uri.setServerSuccessLocalDateTime(now);

        if (uri.getHash() == null || !uri.getHash().equals(hash)) {

            log.info(format("%s uri '%s' document changed.", nameForHasSource, uri.getUri()));

            uri.setHash(hash);
            uri.setDocument(hasSource.getOriginalSource());
            uri.setDocumentChangeLocalDateTime(now);
            uri.setServerChangeLocalDateTime(now);
            uriSetter.f(hasSource, uri);

            insertHistory(uri);

        } else {

            log.info(format("%s uri '%s' document did not change.", nameForHasSource, uri.getUri()));
        }

        return uri;
    }

    private T2 onServerFailure(
            final LocalDateTime now,
            final T2 uri,
            final Exception exceptionForDocument,
            final Exception exceptionForServer) {

        uri.setDocumentRequestLocalDateTime(now);
        uri.setDocumentFailureLocalDateTime(now);
        uri.setDocumentFailureMessage(truncate(exceptionForDocument.getMessage(), 1000));

        uri.setServerRequestLocalDateTime(now);
        uri.setServerFailureLocalDateTime(now);
        uri.setServerFailureMessage(truncate(exceptionForServer.getMessage(), 1000));

        insertHistory(uri);

        return uri;
    }

    private T2 onServerSuccess(
            final LocalDateTime now,
            final T2 uri,
            final Exception exceptionForDocument) {

        uri.setDocumentRequestLocalDateTime(now);
        uri.setDocumentFailureLocalDateTime(now);
        uri.setDocumentFailureMessage(truncate(exceptionForDocument.getMessage(), 1000));

        uri.setServerRequestLocalDateTime(now);
        uri.setServerSuccessLocalDateTime(now);

        insertHistory(uri);

        return uri;
    }

    private Option<T3> insertHistory(final T2 uri) {
        return uriHistorySupplier.bind(uriHistorySetter, uriHistorySave, uriHistorySupplierInner -> uriHistorySetterInner -> uriHistorySaveInner -> {
            final T3 uriHistory = uriHistorySupplierInner.f();
            uriHistory.setUri(uri.getUri());
            uriHistory.setHash(uri.getHash());
            uriHistory.setDocument(uri.getDocument());
            uriHistory.setDocumentRequestLocalDateTime(uri.getDocumentRequestLocalDateTime());
            uriHistory.setDocumentSuccessLocalDateTime(uri.getDocumentSuccessLocalDateTime());
            uriHistory.setDocumentFailureLocalDateTime(uri.getDocumentFailureLocalDateTime());
            uriHistory.setDocumentChangeLocalDateTime(uri.getDocumentChangeLocalDateTime());
            uriHistory.setDocumentFailureMessage(uri.getDocumentFailureMessage());
            uriHistory.setServerRequestLocalDateTime(uri.getServerRequestLocalDateTime());
            uriHistory.setServerSuccessLocalDateTime(uri.getServerSuccessLocalDateTime());
            uriHistory.setServerFailureLocalDateTime(uri.getServerFailureLocalDateTime());
            uriHistory.setServerChangeLocalDateTime(uri.getServerChangeLocalDateTime());
            uriHistory.setServerFailureMessage(uri.getServerFailureMessage());
            uriHistorySetterInner.f(uri, uriHistory);
            return uriHistorySaveInner.f(uriHistory);
        });
    }
}
