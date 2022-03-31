package edu.gatech.gtri.trustmark.trpt.service.job.resolver;

import edu.gatech.gtri.trustmark.v1_0.io.ArtifactResolver;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F4;
import org.gtri.fj.function.Try;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public abstract class DatabaseCacheResolver<T1> implements ArtifactResolver<T1> {

    private final ArtifactResolver<T1> artifactResolver;
    private final F1<String, Option<String>> findByUri;
    private final F1<String, Validation<NonEmptyList<Exception>, T1>> synchronize;

    public DatabaseCacheResolver(
            final ArtifactResolver<T1> artifactResolver,
            final F1<String, Option<String>> findByUri,
            final F1<String, Validation<NonEmptyList<Exception>, T1>> synchronize) {

        requireNonNull(artifactResolver);
        requireNonNull(findByUri);

        this.artifactResolver = artifactResolver;
        this.findByUri = findByUri;
        this.synchronize = synchronize;
    }

    private T1 resolveByUri(final String uriString, final Boolean aBoolean) throws ResolveException {

        final Validation<NonEmptyList<Exception>, T1> validation = Validation.sequenceOption(Try.<Option<String>, Exception>f(() -> findByUri.f(uriString))._1().f().map(NonEmptyList::nel))
                .map(documentValidation -> documentValidation.bind(document -> Try.<T1, Exception>f(() -> resolve(document, aBoolean))._1().f().map(NonEmptyList::nel)))
                .orSome(() -> Try.<Validation<NonEmptyList<Exception>, T1>, Exception>f(() -> synchronize.f(uriString))._1().f().map(NonEmptyList::nel).bind(validationInner -> validationInner));

        if (validation.isSuccess()) {
            return validation.success();
        } else {
            throw new ResolveException(validation.fail().head());
        }
    }

    public <T2> T2 resolve(
            final URL url,
            final F2<URL, T1, T2> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T2> onServerSuccess,
            final F4<URL, ResolveException, URL, ResolveException, T2> onServerFailure) {
        throw new RuntimeException();
    }

    public <T2> T2 resolve(
            final URL url,
            final Boolean validate,
            final F2<URL, T1, T2> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T2> onServerSuccess,
            final F4<URL, ResolveException, URL, ResolveException, T2> onServerFailure) {
        throw new RuntimeException();
    }

    public <T2> T2 resolve(
            final URI uri,
            final F2<URI, T1, T2> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T2> onServerSuccess,
            final F4<URI, ResolveException, URI, ResolveException, T2> onServerFailure) {
        throw new RuntimeException();
    }

    public <T2> T2 resolve(
            final URI uri,
            final Boolean validate,
            final F2<URI, T1, T2> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T2> onServerSuccess,
            final F4<URI, ResolveException, URI, ResolveException, T2> onServerFailure) {
        throw new RuntimeException();
    }

    public T1 resolve(final URL url) throws ResolveException {

        return resolveByUri(url.toString(), false);
    }

    public T1 resolve(final URL url, final Boolean aBoolean) throws ResolveException {

        return resolveByUri(url.toString(), aBoolean);

    }

    public T1 resolve(final URI uri) throws ResolveException {

        return resolveByUri(uri.toString(), false);

    }

    public T1 resolve(final URI uri, final Boolean aBoolean) throws ResolveException {

        return resolveByUri(uri.toString(), aBoolean);
    }

    public T1 resolve(final File file) throws ResolveException {

        return artifactResolver.resolve(file);
    }

    public T1 resolve(final File file, final Boolean aBoolean) throws ResolveException {

        return artifactResolver.resolve(file, aBoolean);
    }

    public T1 resolve(final InputStream inputStream) throws ResolveException {

        return artifactResolver.resolve(inputStream);
    }

    public T1 resolve(final InputStream inputStream, final Boolean aBoolean) throws ResolveException {

        return artifactResolver.resolve(inputStream, aBoolean);
    }

    public T1 resolve(final Reader reader) throws ResolveException {

        return artifactResolver.resolve(reader);
    }

    public T1 resolve(final Reader reader, final Boolean aBoolean) throws ResolveException {

        return artifactResolver.resolve(reader, aBoolean);
    }

    public T1 resolve(final String s) throws ResolveException {

        return artifactResolver.resolve(s);
    }

    public T1 resolve(final String s, final Boolean aBoolean) throws ResolveException {

        return artifactResolver.resolve(s, aBoolean);
    }
}
