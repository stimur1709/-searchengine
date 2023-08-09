package searchengine.util;

import org.jsoup.nodes.Document;

import java.util.regex.Pattern;

public class PageProperties {

    private PageProperties() {

    }

    private static final Pattern PATTERN_NOT_FILE = Pattern.compile("(\\S+(\\.(?i)(jpg|png|gif|bmp|pdf|usdz|exe))$)");
    private static final Pattern PATTERN_NOT_ANCHOR = Pattern.compile("#([\\w\\-]+)?$");

    public static boolean isCorrectUrl(String attr, String path) {
        return Pattern.compile("^" + attr).matcher(attr).lookingAt() && !PATTERN_NOT_FILE.matcher(attr).find()
                && !PATTERN_NOT_ANCHOR.matcher(attr).find() && attr.startsWith(path);
    }

    public static String changeUrl(String url, String attr) {
        return attr.replace(url, "");
    }

    public static int getCodeStatus(Document document) {
        return document.connection().response().statusCode();
    }

    public static int getCodeStatus(String error) {
        int code = 0;
        String[] split = error.split(",");
        for (String subs : split) {
            if (subs.contains("Status")) {
                code = Integer.parseInt(subs.substring(subs.indexOf("=") + 1));
            }
        }
        return code;
    }


}
