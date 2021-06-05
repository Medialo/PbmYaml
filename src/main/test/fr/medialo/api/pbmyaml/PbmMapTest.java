package fr.medialo.api.pbmyaml;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

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
        System.out.println(map1);
        pbmMap.remove("message.test.a.b.c.d.e.f");
        pbmMap.remove("message.test.plugin.text");
        System.out.println(map1);
        pbmMap.set("message.test.plugin","Bonjour");
        System.out.println("->" + map1);
        pbmMap.remove("message.test.plugin");
        System.out.println(map1);
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
        System.out.println(map1);
        pbmMap.set("message.test.a.b.c.d.e.f.g","salut");
        System.out.println(map1);
        pbmMap.set("message.test.plugin.text.text3","bravo222");
        System.out.println(map1);


        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        String output = yaml.dump(map1);
        System.out.println(output);


        pbmMap.remove("message.test.plugin.text");
//        pbmMap.set("message.test.plugin.text","bravo222");


        System.out.println(">" + map1);
//        assertEquals("salut",pbmMap.getString("message.test.a.b.c.d.e.f.g"));
        pbmMap.remove("message.test.a.b.c.d.e.f.g");
        System.out.println(map1);
        pbmMap.set("je.suis.paul.est.mon.nom.est","paul");
        System.out.println(map1);
        pbmMap.set("je.suis.paul.est.mon.nom.est.et.voila","132");
        long l1 = System.nanoTime();
        pbmMap.set("a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p","132");
        pbmMap.set("a.b.c.d.e.f.g.h.i.j.k.l.m.n","456");
        pbmMap.set("a.b.c.d.e.f.g.h.i.j.k.l.m.n.a","456");
        long l2 = System.nanoTime();
        System.out.println(l2 - l1);
        System.out.println(l2);
        System.out.println(l1);
        System.out.println(map1);



//
//
//
//        Object obj = yaml.load("message:\n" +
//                "  test:\n" +
//                "    a:\n" +
//                "      b:\n" +
//                "        c:\n" +
//                "          d:\n" +
//                "            e:\n" +
//                "              f:\n" +
//                "                g: salut\n" +
//                "    plugin: ");
//        System.out.println(obj);


    }


}