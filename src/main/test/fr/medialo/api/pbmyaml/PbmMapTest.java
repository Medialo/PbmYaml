package fr.medialo.api.pbmyaml;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PbmMapTest {

    @AfterAll
    @Test
    public static void setUp(){

    }

    @Test
    void set() {
    }

    @Test
    void get() {
    }

    @Test
    void getInnerTest() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, String> map4 = new HashMap<>();
        map4.put("text","bravo");
        map3.put("plugin",map4);
        map2.put("test",map3);
        map1.put("message",map2);
        PbmMap pbmMap = new PbmMap(map1);
        System.out.println(pbmMap.get("message.test.plugin.text"));
    }

    @Test
    void remove() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, String> map4 = new HashMap<>();
        map4.put("text","bravo");
        map4.put("text2","bravo2");
        map3.put("plugin",map4);
        map2.put("test",map3);
        map1.put("message",map2);
        PbmMap pbmMap = new PbmMap(map1);
        pbmMap.remove("message.test.plugin.text");
        assertNull(map4.get("text"));
        assertEquals(map4.get("text2"),"bravo2");
        pbmMap.remove("message.test.plugin.text2");
        assertTrue(((Map<String, String>) map3.get("plugin")).isEmpty());
    }

    @Test
    void remove2() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, String> map4 = new HashMap<>();
        map4.put("text","bravo");
        map3.put("plugin",map4);
        map2.put("test",map3);
        map1.put("message",map2);
        PbmMap pbmMap = new PbmMap(map1);
        pbmMap.remove("message.test.plugin");
        assertTrue(((Map<String, String>) map2.get("test")).isEmpty());
    }

    @Test
    void removeNotExist() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, String> map4 = new HashMap<>();
        map4.put("text","bravo");
        map3.put("plugin",map4);
        map2.put("test",map3);
        map1.put("message",map2);
        PbmMap pbmMap = new PbmMap(map1);
        pbmMap.remove("message.test.a.b.c.d.e.f");
        pbmMap.remove("message.test.plugin.text.a");
    }

    @Test
    void set1() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, String> map4 = new HashMap<>();
        map4.put("text","bravo");
        map3.put("plugin",map4);
        map2.put("test",map3);
        map1.put("message",map2);
        PbmMap pbmMap = new PbmMap(map1);
        pbmMap.set("message.test.a.b.c.d.e.f.g","salut");
        pbmMap.set("message.test.plugin.text.text3","bravo222");
        System.out.println(map1);
    }
}