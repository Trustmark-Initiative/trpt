package edu.gatech.gtri.trustmark.grails.trpt.service.file;

import edu.gatech.gtri.trustmark.grails.trpt.domain.File;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.Try;
import org.gtri.fj.function.TryEffect;
import org.gtri.fj.product.Unit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Validation.success;

public final class FileUtility {

    private FileUtility() {
    }

    public static File fileFor(final String string) {

        requireNonNull(string);

        File file = new File();
        file.setData(gzip(string.getBytes(StandardCharsets.UTF_8)).toOption().orSome(new byte[]{}));
        file.saveAndFlushHelper();

        return file;
    }

    public static File fileFor(final byte[] byteArray) {

        requireNonNull(byteArray);

        File file = new File();
        file.setData(gzip(byteArray).toOption().orSome(new byte[]{}));
        file.saveAndFlushHelper();

        return file;
    }

    public static String stringFor(final File file) {

        requireNonNull(file);

        return new String(gunzip(file.getData()).toOption().orSome(new byte[]{}), StandardCharsets.UTF_8);
    }

    public static byte[] byteArrayFor(final File file) {

        requireNonNull(file);

        return gunzip(file.getData()).toOption().orSome(new byte[]{});
    }

    private static final boolean compress = true;

    public static Validation<IOException, byte[]> gzip(final byte[] byteArray) {

        if (compress) {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            return Try.<GZIPOutputStream, IOException>f(() -> new GZIPOutputStream(byteArrayOutputStream))._1()
                    .bind(gzipOutputStream -> TryEffect.<Unit, IOException>f(() -> gzipOutputStream.write(byteArray))._1()
                            .map(unit -> gzipOutputStream))
                    .bind(gzipOutputStream -> TryEffect.<Unit, IOException>f(() -> gzipOutputStream.flush())._1()
                            .map(unit -> gzipOutputStream))
                    .bind(gzipOutputStream -> TryEffect.<Unit, IOException>f(() -> gzipOutputStream.close())._1()
                            .map(unit -> gzipOutputStream))
                    .map(gzipOutputStream -> byteArrayOutputStream.toByteArray());
        } else {
            return success(byteArray);
        }
    }

    public static Validation<IOException, byte[]> gunzip(final byte[] byteArray) {

        if (compress) {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            final Validation<IOException, byte[]> validation = Try.<GZIPInputStream, IOException>f(() -> new GZIPInputStream(new ByteArrayInputStream(byteArray)))._1()
                    .bind(gzipOutputStream -> TryEffect.<Unit, IOException>f(() -> {
                        int read;
                        while ((read = gzipOutputStream.read()) != -1) {
                            byteArrayOutputStream.write(read);
                        }
                    })._1().map(unit -> gzipOutputStream))
                    .bind(gzipInputStream -> TryEffect.<Unit, IOException>f(() -> gzipInputStream.close())._1()
                            .map(unit -> gzipInputStream))
                    .map(gzipInputStream -> byteArrayOutputStream.toByteArray());

            return validation;
        } else {
            return success(byteArray);
        }
    }
}
