package searchengine.util;

public class TextParser {

    private TextParser() {

    }

    public static String replaceText(String text) {
        return text.replaceAll("[^A-Za-zА-Яа-я]", " ")
                .replaceAll(" {5}", " ")
                .replaceAll(" {4}", " ")
                .replaceAll(" {3}", " ")
                .replaceAll(" {2}", " ")
                .trim().toLowerCase();
    }
}
