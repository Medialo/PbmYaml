package fr.medialo.api.pbmyaml;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //todo ?
class PbmMapTest {

    private final String stringValue = "pbmYaml";
    private final String stringValue2 = "anotherWord";
    private final char charValue = 'p';
    private final int intValue = 86764;
    private final double doubleValue = 45757.546587;
    private PbmMap pbmMap;

    @BeforeAll
    @Test
    public void setUp() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, String> map4 = new HashMap<>();
        map4.put("text", "bravo");
        map3.put("plugin", map4);
        map2.put("test", map3);
        map1.put("message", map2);
        pbmMap = new PbmMap(map1);
    }

    @Test
    @Order(1)
    @DisplayName("Test the default value return")
    void defaultValue() {
        final String key = "doesn'tExist";
        assertNull(pbmMap.get(key));
        Assertions.assertEquals("null", pbmMap.getString(key));
        Assertions.assertEquals(0, pbmMap.getByte(key));
        Assertions.assertEquals(0.0, pbmMap.getDouble(key));
        Assertions.assertEquals(0, pbmMap.getInt(key));
        Assertions.assertEquals(0, pbmMap.getShort(key));
        Assertions.assertEquals(0, pbmMap.getLong(key));
        Assertions.assertEquals(0.0, pbmMap.getFloat(key));
        Assertions.assertEquals('\u0000', pbmMap.getChar(key));
    }

    @Test
    @Order(2)
    @DisplayName("Test set method")
    void set() {
        pbmMap.set("test.set.1", stringValue);
        Assertions.assertEquals(stringValue, ((Map<String, Map<?, ?>>) pbmMap.getData().get("test")).get("set").get("1"));
        pbmMap.set("test.set.1", stringValue2);
        Assertions.assertEquals(stringValue2, ((Map<String, Map<?, ?>>) pbmMap.getData().get("test")).get("set").get("1"));
        pbmMap.set("test.set.2", intValue);
        Assertions.assertEquals(intValue, ((Map<String, Map<?, ?>>) pbmMap.getData().get("test")).get("set").get("2"));
        pbmMap.set("test.set.3", doubleValue);
        Assertions.assertEquals(doubleValue, ((Map<String, Map<?, ?>>) pbmMap.getData().get("test")).get("set").get("3"));
        pbmMap.set("test.set.4", stringValue);
        Assertions.assertEquals(stringValue, ((Map<String, Map<?, ?>>) pbmMap.getData().get("test")).get("set").get("4"));
        pbmMap.set("test.set.4.test", stringValue);
        Assertions.assertEquals(stringValue, ((Map<String, Map<?, Map<?, ?>>>) pbmMap.getData().get("test")).get("set").get("4").get("test"));
        pbmMap.set("test.set.4", stringValue);
        Assertions.assertEquals(stringValue, ((Map<String, Map<?, ?>>) pbmMap.getData().get("test")).get("set").get("4"));
        pbmMap.set("test.set.5", charValue);
        Assertions.assertEquals(charValue, ((Map<String, Map<?, ?>>) pbmMap.getData().get("test")).get("set").get("5"));
    }

    @Test
    @Order(3)
    @DisplayName("Test get method")
    void get() {
        Assertions.assertNotEquals(stringValue, pbmMap.getString("test.set.1"));
        Assertions.assertEquals(stringValue2, pbmMap.getString("test.set.1"));
        Assertions.assertEquals(intValue, pbmMap.getInt("test.set.2"));
        Assertions.assertEquals(doubleValue, pbmMap.getDouble("test.set.3"));

    }

    @Test
    @Order(4)
    @DisplayName("Test remove method")
    void remove() {
        pbmMap.remove("message.test.plugin.text");
        assertNull(pbmMap.get("message.test.plugin.text"));
    }


}