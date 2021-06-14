package fr.medialo.api.pbmyaml.mapping;

import fr.medialo.api.pbmyaml.PbmMap;

import java.util.function.Consumer;


public class MappingNode extends Mapping {

    public MappingNode(PbmMap map) {
        super("", map);
    }

    public MappingNode(PbmMap map, String oldKey, String newKey) {
        super(oldKey + "." + newKey + ".", map);
    }

    public MappingNode add(String key, Object o) {
        this.map.set(this.key + key, o);
        return this;
    }

    public MappingNode add(String key, Consumer<MappingNode> mappingChild) {
        mappingChild.accept(new MappingNode(this.map, this.key, key));
        return this;
    }

    public MappingNode addList(String key, Consumer<MappingList> mappingChild) {
        return addValues(key, mappingChild);
    }

    public MappingNode addValues(String key, Consumer<MappingList> mappingChild) {
        mappingChild.accept(new MappingList(this.map, this.key, key));
        return this;
    }

    public MappingNode add(String key) {

        return new MappingNode(this.map, this.key, key);
    }

    public MappingNode add(String key, MappingList o) {

        return new MappingNode(this.map, this.key, key);
    }


//    static class MapperBuilder {
//
//        public static Mapping start(){
//            return new Mapping(new LinkedHashMap<>());
//        }
//
//        public MapperBuilder add(){
//
//            return this;
//        }
//
//    }


}


/*

Mapping m = Mapping.create(


);

 */