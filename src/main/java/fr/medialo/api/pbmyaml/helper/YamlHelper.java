package fr.medialo.api.pbmyaml.helper;

import fr.medialo.api.pbmyaml.PbmMap;
import fr.medialo.api.pbmyaml.mapping.MappingNodeRoot;

public class YamlHelper {
    public static MappingNodeRoot startMapper() {
        return new MappingNodeRoot(new PbmMap());
    }
}
