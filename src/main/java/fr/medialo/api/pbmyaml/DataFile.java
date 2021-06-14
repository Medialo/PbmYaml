package fr.medialo.api.pbmyaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class DataFile implements IDataFile, DataInteraction {

    protected PbmMap data;
    protected Path path;

    @Override
    public void reload() {
        load(this.path);
    }

    @Override
    public void set(String key, Object val) {
        this.data.set(key, val);
    }

    @Override
    public Object get(String key) {
        return this.data.get(key);
    }

    @Override
    public void remove(String key) {
        this.data.remove(key);
    }

    @Override
    public void load(Path path) {

    }

    @Override
    public void save(KeepComments keepComments) {

    }

    @Override
    public String getExtension() {
        return null;
    }

    @Override
    public void delete() {
        try {
            Files.deleteIfExists(this.path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
