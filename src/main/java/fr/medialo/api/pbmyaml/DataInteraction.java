package fr.medialo.api.pbmyaml;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@SuppressWarnings("unchecked")
public interface DataInteraction {

    default void set(final String key, final Object val){

    }

    default void setIfNotExist(final String key, final Object def){
        if(!contain(key))
            set(key, def);
    }

    Object get(final String key);

    default <T> T getOrSetIfNotExist(final String key, final T def){
        Object o = get(key);
        if(Objects.isNull(o)){
            set(key,def);
            return def;
        }
        return (T) o;
    }

    default <T> T get(final String url,final T def) {
        Object o = get(url);
        return Objects.nonNull(o) ? (T) o : def;
    }

    default String getString(final String key){
        return get(key,"");
    }

    default byte getByte(final String key){
        return get(key,(byte) 0);
    }

    default short getShort(final String key){
        return get(key,(short) 0);
    }

    default int getInt(final String key){
        return get(key, 0);
    }

    default long getLong(final String key){
        return get(key, (long) 0);
    }

    default float getFloat(final String key){
        return get(key, 0.0f);
    }

    default double getDouble(final String key){
        return get(key, 0.0d);
    }

    default <T> Collection<T> getList(final String key){
        return get(key, Collections.emptyList());
    }

    default boolean contain(final String key){
        return Objects.nonNull(get(key));
    }

    void remove(final String key);


}
