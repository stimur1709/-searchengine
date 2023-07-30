package searchengine.util;

import java.util.regex.Pattern;

public class LanguageChecker {

    private LanguageChecker() {

    }

    private static final Pattern RUSSIAN_PATTERN = Pattern.compile("[А-Яа-яёЁ]+");
    private static final Pattern ENGLISH_PATTERN = Pattern.compile("[A-Za-z]+");

    public static boolean isRussian(String word) {
        return RUSSIAN_PATTERN.matcher(word).matches();
    }

    public static boolean isEnglish(String word) {
        return ENGLISH_PATTERN.matcher(word).matches();
    }

}