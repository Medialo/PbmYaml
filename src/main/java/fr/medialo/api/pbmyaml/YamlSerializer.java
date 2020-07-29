package fr.medialo.api.pbmyaml;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlSerializer{

    private List<Class<?>> aClass;

    public YamlSerializer() {
        this.aClass = new ArrayList<>();
    }

    public void register(Class<?> cl){
        aClass.add(cl);
    }

    public <T> Map<String, Object> saveObj(Object obj){
        Map<String, Object> map = new HashMap<>();
        for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
            if (aClass.contains(c) || obj.getClass().equals(c))
                for (Field field : c.getDeclaredFields()) {
                    if (!Modifier.isTransient(field.getModifiers())) {
                        try {
                            map.put(field.getName(),field.get(obj));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
        return map;
    }


    public void close() {
        this.aClass.clear();
        this.aClass = null;
    }
}
