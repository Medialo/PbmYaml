package fr.medialo.api.pbmyaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface IDataFile {

    default void load(final File file) {
        load(file.toPath());
    }

    default void load(final File file, final String name) {
        load(Paths.get(file.getAbsolutePath(), name));
    }

    default void load(final Path path, final String name) {
        load(path.resolve(name));
    }

    default void load(String file) {
        if (!file.endsWith("." + getExtension())) {
            file += "." + getExtension();
        }
        load(Paths.get(file));
    }

    /**
     * This method allows you to load a configuration file to force the use of a different extension than the default.
     *
     * @param file                The file name.
     * @param extensionWithoutDot The extension's file.
     */
    default void load(final String file, final String extensionWithoutDot) {
        load(file + "." + extensionWithoutDot);
    }

    default void load(final File file, final String name, final String extensionWithoutDot) {
        load(file, name + "." + extensionWithoutDot);
    }

    default void load(final Path path, final String file, final String extensionWithoutDot) {
        load(path, file + "." + extensionWithoutDot);
    }

    /**
     * Allow to load a file to instantiate a @{@link PbmMap} .
     *
     * @param path
     */
    void load(final Path path);

    default void load0(final Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void delete();

    default void save() {
        save(KeepComments.YES);
    }

    default void save(final boolean bool) {
        if (bool)
            save(KeepComments.YES);
        else
            save(KeepComments.NO);
    }

    void save(final KeepComments keepComments);

    /**
     * reload method restore the first data's state in the {@link PbmMap} to cancel any change.
     */
    void reload();

    String getExtension();
}
