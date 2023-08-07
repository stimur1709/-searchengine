package searchengine.util;

import searchengine.model.LemmaTable;

import java.util.HashMap;
import java.util.List;

public class LemmaStorage {

    private final HashMap<String, Integer> map;

    public LemmaStorage() {
        this.map = new HashMap<>();
    }

    public void add(String key) {
        add(key, 1);
    }

    public void add(String key, int value) {
        if (key != null) {
            if (contains(key)) {
                value += get(key);
            }
            map.put(key, value);
        }
    }

    public long get(String key) {
        return map.get(key);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public List<LemmaTable> getList(Long siteId) {
        return map
                .entrySet()
                .stream()
                .map(entries -> new LemmaTable(siteId, entries.getKey(), entries.getValue()))
                .toList();
    }

}
