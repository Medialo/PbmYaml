package fr.medialo.api.pbmyaml.mapping;

import fr.medialo.api.pbmyaml.PbmMap;

import java.util.Map;
import java.util.function.Consumer;

public class MappingNodeRoot extends MappingNode {

    public MappingNodeRoot(PbmMap map) {
        super(map);
    }

    public MappingNodeRoot(PbmMap map, String oldKey, String newKey) {
        super(map, oldKey, newKey);
    }

    @Override
    public MappingNodeRoot add(String key, Object o) {
        this.map.set(this.key + key, o);
        return this;
    }

    @Override
    public MappingNodeRoot add(String key, Consumer<MappingNode> mappingChild) {
        mappingChild.accept(new MappingNode(this.map, this.key, key));
        return this;
    }

    @Override
    public MappingNodeRoot addList(String key, Consumer<MappingList> mappingChild) {
        return (MappingNodeRoot) super.addValues(key, mappingChild);
    }

    @Override
    public MappingNodeRoot addValues(String key, Consumer<MappingList> mappingChild) {
        mappingChild.accept(new MappingList(this.map, this.key, key));
        return this;
    }

    public Map<String, Object> build() {
        return this.map.getData();
    }

}
