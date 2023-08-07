package searchengine.util;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class UrlStorage {

    private UrlStorage() {

    }

    private static final HashMap<Long, SortedSet<String>> urls = new HashMap<>();

    private static void add(long id, String url) {
        SortedSet<String> strings = get(id);
        strings.add(url);
        urls.put(id, strings);
    }

    public static SortedSet<String> get(long id) {
        return urls.containsKey(id) ? urls.get(id) : new TreeSet<>();
    }

    public static void removeKey(long id) {
        urls.remove(id);
    }

    public static boolean containsUrl(long id, String url) {
        boolean result = get(id).contains(url);
        if (!result) {
            add(id, url);
        }
        return result;
    }

}
