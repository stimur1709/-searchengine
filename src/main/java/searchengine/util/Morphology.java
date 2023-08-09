package searchengine.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import searchengine.dto.statistics.Language;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Morphology {

    private static final LuceneMorphology russianLuceneMorph;
    private static final LuceneMorphology englishLuceneMorph;
    private static final String[] RU_PARTS = {"ПРЕДЛ", "СОЮЗ", "ЧАСТ"};
    private static final String[] EN_PARTS = {"PREP", "CONJ", "VBE", "PART"};


    static {
        try {
            englishLuceneMorph = new EnglishLuceneMorphology();
            russianLuceneMorph = new RussianLuceneMorphology();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Morphology() {

    }


    public static LemmaStorage getMorphology(String text) {
        LemmaStorage storage = new LemmaStorage();
        storage.addAll(getNormalWords(text));
        return storage;
    }

    public static List<String> getNormalWords(String text) {
        List<String> words = new ArrayList<>();
        text = TextParser.replaceText(text);
        for (String word : text.split(" ")) {
            word = word.trim();
            if (!word.isBlank() && word.length() > 2) {
                Language language = checkLanguage(word);
                if (language == Language.EN) {
                    words.add(getMorphInfo(englishLuceneMorph, word, EN_PARTS));
                } else if (language == Language.RU) {
                    words.add(getMorphInfo(russianLuceneMorph, word, RU_PARTS));
                }
            }
        }
        return words;
    }

    public static List<Integer> getIndexContains(List<String> searchWord, String comparedText) {
        List<Integer> indexes = new ArrayList<>();

        String[] split = comparedText.split(" ");
        for (int i = 0; i < split.length; i++) {
            String word = split[i];
            word = word.trim().toLowerCase();
            if (!word.isBlank() && word.length() > 2) {
                Language language = checkLanguage(word);
                if ((language == Language.EN && searchWord.contains(getMorphInfo(englishLuceneMorph, word, EN_PARTS)))
                        || (language == Language.RU && searchWord.contains(getMorphInfo(russianLuceneMorph, word, RU_PARTS)))) {
                    indexes.add(i);
                }
            }
        }
        return indexes;
    }

    private static Language checkLanguage(String word) {
        if (LanguageChecker.isEnglish(word)) {
            return Language.EN;
        } else if (LanguageChecker.isRussian(word)) {
            return Language.RU;
        } else {
            return Language.UNDEFINED;
        }
    }

    private static String getMorphInfo(LuceneMorphology luceneMorphology, String word, String[] parts) {
        List<String> morphInfo = luceneMorphology.getMorphInfo(word.toLowerCase());
        String[] s = morphInfo.get(0).split(" ");
        if (luceneMorphology.checkString(word) && Arrays.stream(parts).noneMatch(w -> w.equals(s[1]))) {
            return luceneMorphology.getNormalForms(word).get(0);
        } else {
            return null;
        }
    }


}
