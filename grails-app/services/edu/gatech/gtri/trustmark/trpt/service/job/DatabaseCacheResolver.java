package edu.gatech.gtri.trustmark.trpt.service.job;

import edu.gatech.gtri.trustmark.v1_0.io.ArtifactResolver;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.Try;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public abstract class DatabaseCacheResolver<T1, T2> implements ArtifactResolver<T1> {

    private static final Log log = LogFactory.getLog(DatabaseCacheResolver.class);

    private final ArtifactResolver<T1> artifactResolver;
    private final F1<String, Option<T2>> findByUri;
    private final F1<T2, String> serialize;
    private final F1<String, Validation<NonEmptyList<Exception>, T1>> synchronize;

    public DatabaseCacheResolver(
            final ArtifactResolver<T1> artifactResolver,
            final F1<String, Option<T2>> findByUri,
            final F1<T2, String> serialize,
            final F1<String, Validation<NonEmptyList<Exception>, T1>> synchronize) {

        requireNonNull(artifactResolver);
        requireNonNull(findByUri);
        requireNonNull(serialize);

        this.artifactResolver = artifactResolver;
        this.findByUri = findByUri;
        this.serialize = serialize;
        this.synchronize = synchronize;
    }

    private T1 resolveByUri(final String uriString, final Boolean aBoolean) throws ResolveException {

        final Validation<NonEmptyList<Exception>, T1> validation = findByUri.f(uriString)
                .map(artifact -> Try.<T1, Exception>f(() -> resolve(serialize.f(artifact), aBoolean))._1().f().map(NonEmptyList::nel))
                .orSome(() -> synchronize.f(uriString));

        if (validation.isSuccess()) {
            return validation.success();
        } else {
            throw new ResolveException(validation.fail().head());
        }
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
