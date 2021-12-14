package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.Uri;
import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import org.apache.commons.logging.Log;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Effect0;
import org.gtri.fj.function.Effect1;
import org.gtri.fj.function.Effect2;
import org.gtri.fj.function.F0;
import org.gtri.fj.function.F1;
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
import static org.gtri.fj.data.Either.reduce;
import static org.gtri.fj.product.P.p;

public class UriSynchronizer<T1 extends HasSource, T2 extends Uri, T3 extends Uri> {

    private final Log log;
    private final String nameForHasSource;
    private final Try1<URI, T1, Exception> resolver;
    private final Try1<T1, byte[], Exception> hasher;
    private final Effect1<Effect0> withTransaction;
    private final F0<List<T2>> uriFindAll;
    private final F1<String, Option<T2>> uriFind;
    private final F0<T2> uriSupplier;
    private final Effect2<T1, T2> uriSetter;
    private final F1<T2, T2> uriSave;
    private final F0<T3> uriHistorySupplier;
    private final Effect2<T2, T3> uriHistorySetter;
    private final F1<T3, T3> uriHistorySave;
    private final boolean history;

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final Try1<URI, T1, Exception> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final Effect1<Effect0> withTransaction,
            final F0<List<T2>> uriFindAll,
            final F1<String, Option<T2>> uriFind,
            final F0<T2> uriSupplier,
            final F1<T2, T2> uriSave,
            final F0<T3> uriHistorySupplier,
            final F1<T3, T3> uriHistorySave,
            final boolean history) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                withTransaction,
                uriFindAll,
                uriFind,
                uriSupplier,
                (hasSource, uri) -> {
                },
                uriSave,
                uriHistorySupplier,
                (uri, uriHistory) -> {
                },
                uriHistorySave,
                history);
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final Try1<URI, T1, Exception> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final Effect1<Effect0> withTransaction,
            final F0<List<T2>> uriFindAll,
            final F1<String, Option<T2>> uriFind,
            final F0<T2> uriSupplier,
            final Effect2<T1, T2> uriSetter,
            final F1<T2, T2> uriSave,
            final F0<T3> uriHistorySupplier,
            final Effect2<T2, T3> uriHistorySetter,
            final F1<T3, T3> uriHistorySave,
            final boolean history) {

        this.log = log;
        this.nameForHasSource = nameForHasSource;
        this.resolver = resolver;
        this.hasher = hasher;
        this.withTransaction = withTransaction;
        this.uriFindAll = uriFindAll;
        this.uriFind = uriFind;
        this.uriSupplier = uriSupplier;
        this.uriSetter = uriSetter;
        this.uriSave = uriSave;
        this.uriHistorySupplier = uriHistorySupplier;
        this.uriHistorySetter = uriHistorySetter;
        this.uriHistorySave = uriHistorySave;
        this.history = history;
    }

    public void synchronizeUri() {

        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        uriFindAll.f()
                .map(uri -> uri.getUri())
                .map(uriString -> synchronizeUri(now, uriString));
    }

    public Validation<NonEmptyList<Exception>, T1> synchronizeUri(final LocalDateTime now, final String uriString) {

        final P2<String, Validation<Exception, P3<URI, T1, String>>> p = createResolveHash(nameForHasSource, uriString, resolver, hasher);

        withTransaction.f(() -> {
            final T2 uri = upsert(uriString);

            log.info(format("%s uri '%s' document request.", nameForHasSource, uri.getUri()));

            uriSave.f(reduce(p._2().toEither().bimap(
                    exception -> onDocumentFailure(now, uri, exception),
                    pInner -> onDocumentSuccess(now, uri, pInner._2(), pInner._3()))));
        });

        return p._2().map(p3 -> p3._2()).f().map(NonEmptyList::nel);
    }

    private <T1> P2<String, Validation<Exception, P3<URI, T1, String>>> createResolveHash(
            final String entityName,
            final String uriString,
            final Try1<URI, T1, Exception> resolver,
            final Try1<T1, byte[], Exception> hasher) {

        log.info(format("%s uri '%s' document reading ...", entityName, uriString));

        final Validation<Exception, P3<URI, T1, String>> validation = Try.<URI, Exception>f(() -> URI.create(uriString))._1()
                .bind(uri -> Try.<T1, Exception>f(() -> resolver.f(uri))._1().map(entity -> p(uri, entity)))
                .bind(p -> Try.<String, Exception>f(() -> new String(Hex.encode(hasher.f(p._2()))))._1().map(hash -> p(p._1(), p._2(), hash)));

        validation.f().forEach(exception -> log.info(format("%s uri '%s' document read failure: '%s'.", entityName, uriString, exception.getMessage())));

        return p(uriString, validation);
    }

    private T2 upsert(final String uriString) {
        return uriFind.f(uriString)
                .orSome(() -> {
                    final T2 uri = uriSupplier.f();
                    uri.setUri(uriString);
                    return uri;
                });
    }

    private T2 onDocumentFailure(final LocalDateTime now, final T2 uri, final Exception exception) {

        uri.setDocumentRequestLocalDateTime(now);
        uri.setDocumentFailureLocalDateTime(now);
        uri.setDocumentFailureMessage(truncate(exception.getMessage(), 1000));

        log.info(format("%s uri '%s' document relying on cache, if any.", nameForHasSource, uri.getUri()));

        log.info(format("%s uri '%s' server request.", nameForHasSource, uri.getUri()));

        final Validation<Exception, P2<Integer, String>> validation = Try.<URI, Exception>f(() -> URI.create(uri.getUri()))._1()
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
                exceptionInner -> {

                    log.info(format("%s uri '%s' server read failure: %s", nameForHasSource, uri.getUri(), exceptionInner.getMessage()));

                    uri.setServerRequestLocalDateTime(now);
                    uri.setServerFailureLocalDateTime(now);
                    uri.setServerFailureMessage(truncate(exceptionInner.getMessage(), 1000));
                },
                response -> {
                    uri.setServerRequestLocalDateTime(now);
                    uri.setServerSuccessLocalDateTime(now);
                }
        );

        if (history) {
            insertHistory(uri);
        }

        return uri;
    }

    private T2 onDocumentSuccess(final LocalDateTime now, final T2 uri, final T1 hasSource, final String hash) {

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

            if (history) {
                insertHistory(uri);
            }

        } else {

            log.info(format("%s uri '%s' document did not change.", nameForHasSource, uri.getUri()));
        }

        return uri;
    }

    private void insertHistory(final T2 uri) {
        final T3 uriHistory = uriHistorySupplier.f();
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
        uriHistorySetter.f(uri, uriHistory);
        uriHistorySave.f(uriHistory);
    }
}
