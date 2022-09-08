package edu.gatech.gtri.trustmark.trpt.service.job.urisynchronizer;

import edu.gatech.gtri.trustmark.trpt.domain.Uri;
import edu.gatech.gtri.trustmark.trpt.service.job.RetryTemplateUtility;
import edu.gatech.gtri.trustmark.v1_0.io.ArtifactResolver;
import edu.gatech.gtri.trustmark.v1_0.model.HasIdentifier;
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
import org.springframework.security.crypto.codec.Hex;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static edu.gatech.gtri.trustmark.trpt.service.file.FileUtility.fileFor;
import static edu.gatech.gtri.trustmark.trpt.service.job.JobUtility.truncate;
import static java.lang.String.format;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.data.Validation.success;

public class UriSynchronizer<T1 extends HasIdentifier, T2 extends Uri, T3 extends Uri> {

    private final Log log;
    private final String nameForHasSource;
    final ArtifactResolver<T1> resolver;
    private final Try1<T1, byte[], Exception> hasher;
    private final F1<T1, byte[]> serializer;
    private final F0<List<T2>> withTransactionUriFindAll;
    private final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind;
    private final F0<T2> uriSupplier;
    private final Effect2<T1, T2> uriSetter;
    private final F1<T2, T2> uriSave;
    private final F2<T2, T2, T2> uriCoalesce;
    private final Option<F0<T3>> uriHistorySupplier;
    private final Option<Effect2<T2, T3>> uriHistorySetter;
    private final Option<F1<T3, T3>> uriHistorySave;

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final F1<T2, T2> uriSave) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                serializer,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                (hasSource, uri) -> {
                },
                uriSave,
                (uriRequest, uriResolve) -> uriRequest,
                none(),
                none(),
                none());
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final F1<T2, T2> uriSave,
            final F2<T2, T2, T2> uriCoalesce) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                serializer,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                (hasSource, uri) -> {
                },
                uriSave,
                uriCoalesce,
                none(),
                none(),
                none());
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
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
                serializer,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                (hasSource, uri) -> {
                },
                uriSave,
                (uriRequest, uriResolve) -> uriRequest,
                uriHistorySupplier,
                (uri, uriHistory) -> {
                },
                uriHistorySave);
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final F1<T2, T2> uriSave,
            final F2<T2, T2, T2> uriCoalesce,
            final F0<T3> uriHistorySupplier,
            final F1<T3, T3> uriHistorySave) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                serializer,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                (hasSource, uri) -> {
                },
                uriSave,
                uriCoalesce,
                uriHistorySupplier,
                (uri, uriHistory) -> {
                },
                uriHistorySave);
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final Effect2<T1, T2> uriSetter,
            final F1<T2, T2> uriSave) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                serializer,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                uriSetter,
                uriSave,
                (uriRequest, uriResolve) -> uriRequest,
                none(),
                none(),
                none());
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
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
                serializer,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                uriSetter,
                uriSave,
                (uriRequest, uriResolve) -> uriRequest,
                some(uriHistorySupplier),
                some(uriHistorySetter),
                some(uriHistorySave));
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final Effect2<T1, T2> uriSetter,
            final F1<T2, T2> uriSave,
            final F2<T2, T2, T2> uriCoalesce) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                serializer,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                uriSetter,
                uriSave,
                uriCoalesce,
                none(),
                none(),
                none());
    }

    public UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final Effect2<T1, T2> uriSetter,
            final F1<T2, T2> uriSave,
            final F2<T2, T2, T2> uriCoalesce,
            final F0<T3> uriHistorySupplier,
            final Effect2<T2, T3> uriHistorySetter,
            final F1<T3, T3> uriHistorySave) {

        this(
                log,
                nameForHasSource,
                resolver,
                hasher,
                serializer,
                withTransactionUriFindAll,
                withTransactionUriFind,
                uriSupplier,
                uriSetter,
                uriSave,
                uriCoalesce,
                some(uriHistorySupplier),
                some(uriHistorySetter),
                some(uriHistorySave));
    }

    private UriSynchronizer(
            final Log log,
            final String nameForHasSource,
            final ArtifactResolver<T1> resolver,
            final Try1<T1, byte[], Exception> hasher,
            final F1<T1, byte[]> serializer,
            final F0<List<T2>> withTransactionUriFindAll,
            final F2<String, F1<Option<T2>, T2>, T2> withTransactionUriFind,
            final F0<T2> uriSupplier,
            final Effect2<T1, T2> uriSetter,
            final F1<T2, T2> uriSave,
            final F2<T2, T2, T2> uriCoalesce,
            final Option<F0<T3>> uriHistorySupplier,
            final Option<Effect2<T2, T3>> uriHistorySetter,
            final Option<F1<T3, T3>> uriHistorySave) {

        this.log = log;
        this.nameForHasSource = nameForHasSource;
        this.resolver = resolver;
        this.hasher = hasher;
        this.serializer = serializer;
        this.withTransactionUriFindAll = withTransactionUriFindAll;
        this.withTransactionUriFind = withTransactionUriFind;
        this.uriSupplier = uriSupplier;
        this.uriSetter = uriSetter;
        this.uriSave = uriSave;
        this.uriCoalesce = uriCoalesce;
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
        return Try.<URI, Exception>f(() -> new URI(uriString))._1()
                .f().map(NonEmptyList::nel)
                .bind(uri -> this.resolver.resolve(
                        uri,
                        (artifactUri, artifact) -> {
                            updateUri(uriString, fromNull(artifact.getIdentifier()), artifactUrInner -> onDocumentSuccess(now, artifactUrInner, artifact));
                            return success(artifact);
                        },
                        (artifactUri, artifactException, serverUri) -> {
                            updateUri(uriString, none(), artifactUriInner -> onServerSuccess(now, artifactUriInner, artifactException));
                            return fail(nel(artifactException));
                        },
                        (artifactUri, artifactException, serverUri, serverException) -> {
                            updateUri(uriString, none(), artifactUriInner -> onServerFailure(now, artifactUriInner, artifactException, serverException));
                            return fail(nel(artifactException, serverException));
                        }));
    }

    private T2 updateUri(final String uriRequestString, final Option<URI> uriResolveObjectOption, final F1<T2, T2> uriUpdate) {

        // if the requested URI exists in the database
        // * if the resolved URI exists
        // * * if the requested URI and the resolved URI are the same
        // * * * update the requested URI in the database
        // * * otherwise the requested URI and the resolved URI are different
        // * * * if the resolved URI exists in the database
        // * * * * coalesce the requested URI and the resolved URI
        // * * * otherwise the resolved URI does not exist in the database
        // * * * * update the requested URI in the database; use the resolved URI
        // * otherwise the resolved URI does not exist
        // * * update the requested URI in the database
        // otherwise the requested URI does not exist in the database
        // * if the resolved URI exists
        // * * if the requested URI and the resolved URI are the same
        // * * * insert the requested URI into the database
        // * * otherwise the requested URI and the resolved URI are different
        // * * * if the resolved URI exists in the database
        // * * * * update the resolved URI
        // * * * otherwise the resolved URI does not exist in the database
        // * * * * insert the resolved URI into the database
        // * otherwise the resolved URI does not exist
        // * * insert the requested URI into the database

        return withTransactionUriFind.f(uriRequestString, uriRequestOption -> uriRequestOption
                .map(uriRequest -> uriResolveObjectOption
                        .map(uriResolveObject -> uriRequestString.equals(uriResolveObject.toString()) ?
                                updateUri(uriRequest, uriUpdate) :
                                withTransactionUriFind.f(uriResolveObject.toString(), uriResolveOption -> uriResolveOption
                                        .map(uriResolve -> updateUri(uriRequest, uriResolve, uriUpdate))
                                        .orSome(() -> updateUri(uriRequest, uriResolveObject.toString(), uriUpdate))))
                        .orSome(() -> updateUri(uriRequest, uriUpdate)))
                .orSome(() -> uriResolveObjectOption
                        .map(uriResolveObject -> uriRequestString.equals(uriResolveObject.toString()) ?
                                insertUri(uriRequestString, uriUpdate) :
                                withTransactionUriFind.f(uriResolveObject.toString(), uriResolveOption -> uriResolveOption
                                        .map(uriResolve -> updateUri(uriResolve, uriUpdate))
                                        .orSome(() -> insertUri(uriRequestString, uriUpdate))))
                        .orSome(() -> insertUri(uriRequestString, uriUpdate))));
    }

    /**
     * Insert the given URI into the database. The database does not contain the URI string.
     *
     * @param uriString the URI string
     * @param uriUpdate the update function
     * @return the URI
     */
    private T2 insertUri(final String uriString, final F1<T2, T2> uriUpdate) {
        final T2 uri = uriSupplier.f();
        uri.setUri(uriString);
        return uriSave.f(uriUpdate.f(uri));
    }

    /**
     * Update the given URI in the database. The database contains the URI string, and it has not changed.
     *
     * @param uri       the URI
     * @param uriUpdate the update function
     * @return the URI
     */
    private T2 updateUri(final T2 uri, final F1<T2, T2> uriUpdate) {
        return uriSave.f(uriUpdate.f(uri));
    }

    /**
     * Update the given URI in the database. The database contains the requested URI string, but not the resolved URI string; update the requested URI string to the resolved URI string.
     *
     * @param uri       the URI
     * @param uriString the URI string
     * @param uriUpdate the update function
     * @return the URI
     */
    private T2 updateUri(final T2 uri, final String uriString, final F1<T2, T2> uriUpdate) {
        uri.setUri(uriString);
        return uriSave.f(uriUpdate.f(uri));
    }

    /**
     * Update the given URI in the database. The database contains the requested URI string and the resolved URI string; coalesce the requested URI and the resolved URI into the resolved URI.
     *
     * @param uriRequest the URI
     * @param uriResolve the URI string
     * @param uriUpdate  the update function
     * @return the URI
     */
    private T2 updateUri(final T2 uriRequest, final T2 uriResolve, final F1<T2, T2> uriUpdate) {
        return uriSave.f(uriUpdate.f(uriCoalesce.f(uriRequest, uriResolve)));
    }

    private T2 onDocumentSuccess(
            final LocalDateTime now,
            final T2 uri,
            final T1 hasSource) {

        final String hash = new String(Hex.encode(Try.f(() -> this.hasher.f(hasSource))._1().orSuccess(new byte[]{})));

        uri.setDocumentRequestLocalDateTime(now);
        uri.setDocumentSuccessLocalDateTime(now);
        uri.setServerRequestLocalDateTime(now);
        uri.setServerSuccessLocalDateTime(now);

        if (uri.getHash() == null || !uri.getHash().equals(hash)) {

            log.info(format("%s uri '%s' document changed.", nameForHasSource, uri.getUri()));

            uri.setHash(hash);
            uri.fileHelper(fileFor(serializer.f(hasSource)));
            uri.setDocumentChangeLocalDateTime(now);
            uri.setServerChangeLocalDateTime(now);
            uriSetter.f(hasSource, uri);

            insertHistory(uri);

        } else {

            log.trace(format("%s uri '%s' document did not change.", nameForHasSource, uri.getUri()));
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
            uriHistory.fileHelper(uri.fileHelper());
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
