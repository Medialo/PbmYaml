package fr.medialo.api.pbmyaml;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class PbmMap implements DataInteraction {

    private final Map<String, Object> data;

    public PbmMap(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public synchronized void set(String key, Object val) {
        StringTokenizer st = new StringTokenizer(key, ".");
        set(st, val, this.data);
    }

    /**
     * TODO Not working yet
     */
    private void set(StringTokenizer st, Object val, Map<String, Object> map) {
        if (st.hasMoreTokens()) {
            String str = st.nextToken();
            Object o = map.get(str);
            if (o == null) {
                Map<String, Object> newMap = new HashMap<>();
                map.put(str, newMap);
                set(st, val, newMap);
            } else if (o instanceof Map) {
                set(st, val, (Map<String, Object>) map.get(str));
            } else {
                map.put(str, val);
            }
        }
    }

    @Override
    public Object get(String key) {
        StringTokenizer st = new StringTokenizer(key, ".");
        return get(st, this.data);
    }

    private Object get(StringTokenizer st, Map<String, Object> map) {
        if (st.hasMoreTokens()) {
            Object o = map.get(st.nextToken());
            if (o instanceof Map) {
                return get(st, (Map<String, Object>) o);
            }
            return o;
        }
        throw new RuntimeException("Error 0x478");
    }

    @Override
    public synchronized void remove(String key) {
        StringTokenizer st = new StringTokenizer(key, ".");
        remove(st, this.data);
    }

    private void remove(StringTokenizer st, Map<String, Object> map) {
        if (st.hasMoreTokens()) {
            String str = st.nextToken();
            Object o = map.get(str);
            if (o != null) {
                if (st.hasMoreTokens() && o instanceof Map) {
                    remove(st, (Map<String, Object>) o);
                } else {
                    map.remove(str);
                }
            }
        }
    }
}
