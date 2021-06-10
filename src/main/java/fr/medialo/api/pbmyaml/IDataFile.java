package fr.medialo.api.pbmyaml;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface IDataFile {

    default void load(final File file){
        load(file.toPath());
    }

    default void load(final File file, final String name){
        load(Paths.get(file.getAbsolutePath(),name));
    }

    default void load(final Path path, final String name){
        load(path.resolve(name));
    }

    /**
     * Allow to load a file to instantiate a @{@link PbmMap} .
     * @param path
     */
    void load(final Path path);

    default void save(){
     save(KeepComments.YES);
    }

    default void save(final boolean bool){
        if(bool)
            save(KeepComments.YES);
        else
            save(KeepComments.NO);
    }

    void save(final KeepComments keepComments);

    /**
     * reload method restore the first data's state in the {@link PbmMap} to cancel any change.
     *
     */
    void reload();

}
