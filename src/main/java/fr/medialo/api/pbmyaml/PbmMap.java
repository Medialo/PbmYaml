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

    private void set(StringTokenizer st, Object val, Map<String, Object> map) {
        if (st.hasMoreTokens()) {
            String str = st.nextToken();
            Object o = map.get(str);
            if (o == null && !st.hasMoreTokens()) {
                map.put(str, val);
            } else if (o == null || o instanceof Map && ((Map<?, ?>) o).isEmpty() && st.hasMoreTokens()  ) {
                Map<String, Object> newMap = new HashMap<>();
                map.put(str, newMap);
                set(st, val, newMap);
            } else if (o instanceof Map && !st.hasMoreTokens()) {
                map.put(str, val);
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
        return null;
    }

    private Object get(final String[] strs, int limit){
        return get(this.data, strs, 0, limit);
    }

    private Object get(final Map<String, Object> map, final String[] strs, int i, final int limit){
        if(strs.length == 1){
            return this.data.get(strs[0]);
        } else {
            final Object o = map.get(strs[i]);
            if(o instanceof Map){
                if(i < limit){
                    return get((Map<String, Object>) o, strs, ++i, limit);
                } else {
                    return o;
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public synchronized void remove(String key) {
//        StringTokenizer st = new StringTokenizer(key, ".");
//        remove(st, this.data);
        if(!containsKey(key))
            return;
        String[] strs = key.split("\\.");
        remove(this.data, strs, 0, -1);
    }

    private void remove(final Map<String, Object> map, final String[] strs, int i, int toDelete) {
        if (strs.length == 1) {
            this.data.remove(strs[0]);
        } else {
            final Object o = map.get(strs[i]);
            if (o instanceof Map) {
                if(toDelete == -1 && ((Map<?, ?>) o).size() == 1){
                    remove((Map<String, Object>) o, strs, ++i, i);
                } else if( ((Map<?, ?>) o).size() > 1 ){
                    remove((Map<String, Object>) o, strs, ++i, -1);
                } else {
                    remove((Map<String, Object>) o, strs, ++i, toDelete);
                }
            } else {
                map.remove(strs[i]);
                System.out.println(toDelete);
                if(map.size() == 0 && toDelete != -1){
                    Map tempMap = (Map) get(strs,toDelete-2);
                    if(tempMap != null && toDelete > 1)
                        tempMap.remove(strs[toDelete-1]);
                    else
                        this.data.clear();
                }
            }
        }
    }

    public Map<String, Object> getData() {
        return data;
    }
}