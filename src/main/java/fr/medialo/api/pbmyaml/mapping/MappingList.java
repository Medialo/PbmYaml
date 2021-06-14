package fr.medialo.api.pbmyaml.mapping;

import fr.medialo.api.pbmyaml.PbmMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MappingList extends Mapping {

    private final List<Object> list;

    public MappingList(PbmMap map, String oldKey, String newKey) {
        super(oldKey + "." + newKey, map);
        this.list = new ArrayList<>();
    }

    public MappingList add(Object o) {
        this.list.add(o);
        return this;
    }

    public MappingList addAll(Object... o) {
        this.list.addAll(Arrays.asList(o));
        return this;
    }

    public void confirm() {
        this.map.set(this.key, this.list);
    }

}
