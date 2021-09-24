package edu.gatech.gtri.trustmark.trpt.service.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Try;
import org.gtri.fj.function.Try1;
import org.gtri.fj.product.P2;
import org.gtri.fj.product.P3;
import org.springframework.security.crypto.codec.Hex;

import java.net.HttpURLConnection;
import java.net.URI;

import static java.lang.String.format;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.product.P.p;

public class JobUtility {

    private JobUtility() {
    }

    private static final Log log = LogFactory.getLog(JobUtility.class);

    public static String truncate(String string, int length) {

        return string == null ? null : string.substring(0, Math.min(string.length(), length));
    }

    public static Either<String, ArrayNode> httpGetArrayNode(final String urlString) {

        try {
            return httpGetArrayNode(URI.create(urlString));
        } catch (Exception exception) {

            return left(exception.getMessage());
        }
    }

    public static Either<String, ArrayNode> httpGetArrayNode(final URI uri) {

        try {
            final HttpURLConnection httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                final JsonNode jsonNode = new ObjectMapper().readTree(httpURLConnection.getInputStream());

                if (jsonNode.isArray()) {

                    return right((ArrayNode) jsonNode);

                } else {

                    return left("The system expects a JSON array; the response is not a JSON array.");
                }
            } else {

                return left(httpURLConnection.getResponseMessage());
            }
        } catch (Exception exception) {

            return left(exception.getMessage());
        }
    }

    public static <T1> P2<String, Validation<NonEmptyList<Exception>, P3<URI, T1, String>>> createResolveHash(
            final String entityName,
            final String uriString,
            final Try1<URI, T1, Exception> resolver,
            final Try1<T1, byte[], Exception> hasher) {

        log.info(format("%s uri '%s' reading ...", entityName, uriString));

        final Validation<NonEmptyList<Exception>, P3<URI, T1, String>> validation = Try.<URI, Exception>f(() -> URI.create(uriString))._1().f().map(NonEmptyList::nel)
                .bind(uri -> Try.<T1, Exception>f(() -> resolver.f(uri))._1().f().map(NonEmptyList::nel).map(entity -> p(uri, entity)))
                .bind(p -> Try.<String, Exception>f(() -> new String(Hex.encode(hasher.f(p._2()))))._1().f().map(NonEmptyList::nel).map(hash -> p(p._1(), p._2(), hash)));

        validation.f().forEach(nel -> log.info(format("%s uri '%s' read failure: '%s'.", entityName, uriString, nel.head().getMessage())));

        return p(uriString, validation);
    }
}
