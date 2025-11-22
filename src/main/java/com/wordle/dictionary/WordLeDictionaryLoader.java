package src.main.java.com.wordle.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordLeDictionaryLoader {
    public WordLeDictionary loadDictionary(String filename) {
        WordLeDictionary dictionary = new WordLeDictionary();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                if (word.length() == 5) {
                    dictionary.addWord(word);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка загрузки словаря: " + e.getMessage());
        }
        
        return dictionary;
    }
}
