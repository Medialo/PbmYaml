package fr.medialo.api.pbmyaml.mapping;

import fr.medialo.api.pbmyaml.PbmMap;

public abstract class Mapping {

    protected final String key;
    protected final PbmMap map;

    public Mapping(String key, PbmMap map) {
        this.key = key;
        this.map = map;
    }
}
