package fr.medialo.api.pbmyaml;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@SuppressWarnings("unchecked")
public interface DataInteraction {

    void set(final String key, final Object val);

    default void set(final String key, final Object... val) {
        set(key, Arrays.asList(val));
    }

    default void setIfNotExist(final String key, final Object def) {
        if (!contain(key))
            set(key, def);
    }

    default void replaceIfEqual(final String key, final Object def, final Object expected) {
        if (get(key).equals(expected)) {
            set(key, def);
        }
    }

    Object get(final String key);

    default <T> T getOrSetIfNotExist(final String key, final T def) {
        final Object o = get(key);
        if (Objects.isNull(o)) {
            set(key, def);
            return def;
        }
        return (T) o;
    }

    default <T> T get(final String url, final T def) {
        final Object o = get(url);
        return Objects.nonNull(o) ? (T) o : def;
    }

    default String getString(final String key) {
        return get(key, "null");
    }

    default char getChar(final String key) {
        return get(key, '\u0000');
    }

    default byte getByte(final String key) {
        return get(key, (byte) 0);
    }

    default short getShort(final String key) {
        return get(key, (short) 0);
    }

    default int getInt(final String key) {
        return get(key, 0);
    }

    default long getLong(final String key) {
        return get(key, 0L);
    }

    default float getFloat(final String key) {
        return get(key, 0.0f);
    }

    default double getDouble(final String key) {
        return get(key, 0.0d);
    }

    default <T> Collection<T> getList(final String key) {
        return get(key, Collections.emptyList());
    }

    default boolean contain(final String key) {
        return Objects.nonNull(get(key));
    }

    default boolean containsKey(final String key) {
        return get(key) != null;
    }

    void remove(final String key);

}
