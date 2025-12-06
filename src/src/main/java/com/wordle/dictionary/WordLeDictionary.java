package src.main.java.com.wordle.dictionary;

import java.util.HashSet;
import java.util.Set;

public class WordLeDictionary {
    private Set<String> words;

    public WordLeDictionary() {
        this.words = new HashSet<>();
    }

    public void addWord(String word) {
        if (word != null) {
            words.add(word.toLowerCase());
        }
    }

    public boolean contains(String word) {
        return words.contains(word.toLowerCase());
    }

    public Set<String> getWords() {
        return new HashSet<>(words);
    }

    public int size() {
        return words.size();
    }
}