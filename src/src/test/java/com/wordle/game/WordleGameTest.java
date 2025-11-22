package src.test.java.com.wordle.game;

import src.main.java.com.wordle.dictionary.WordLeDictionary;
import src.main.java.com.wordle.game.WordleGame;
import java.util.HashSet;
import java.util.Set;

public class WordleGameTest {

    public static void main(String[] args) {
        testAll();
    }

    public static void testAll() {
        System.out.println("=== ТЕСТИРОВАНИЕ WORDLE GAME ===");

        testMakeGuessCorrectWord();
        testMakeGuessWrongWord();
        testGameOverAfterSixAttempts();
        testInvalidWordLength();
        testWordNotInDictionary();
        testGetHintBeforeAnyGuess();
        testGetHintAfterGuess();
        testGameOverState();

        System.out.println("=== ВСЕ ТЕСТЫ ЗАВЕРШЕНЫ ===");
    }

    private static WordLeDictionary createTestDictionary() {
        WordLeDictionary dictionary = new WordLeDictionary();
        Set<String> words = Set.of("слово", "метро", "парта", "столб", "крона");
        for (String word : words) {
            dictionary.addWord(word);
        }
        return dictionary;
    }

    static void testMakeGuessCorrectWord() {
        System.out.println("\n--- Тест: угадывание правильного слова ---");
        try {
            WordLeDictionary dictionary = createTestDictionary();
            WordleGame game = new WordleGame(dictionary);
            String secret = game.getSecretWord();
            WordleGame.GameResult result = game.makeGuess(secret);

            if (result.isWin && result.isGameOver) {
                System.out.println("✓ PASS: Правильное слово угадано");
            } else {
                System.out.println("✗ FAIL: Ожидалась победа");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Исключение: " + e.getMessage());
        }
    }

    static void testMakeGuessWrongWord() {
        System.out.println("\n--- Тест: неправильное слово ---");
        try {
            WordLeDictionary dictionary = createTestDictionary();
            WordleGame game = new WordleGame(dictionary);
            WordleGame.GameResult result = game.makeGuess("метро");

            if (!result.isWin && !result.isGameOver && result.attemptsLeft == 5) {
                System.out.println("✓ PASS: Неправильное слово обработано корректно");
            } else {
                System.out.println("✗ FAIL: Неправильная обработка ошибочной попытки");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Исключение: " + e.getMessage());
        }
    }

    static void testGameOverAfterSixAttempts() {
        System.out.println("\n--- Тест: завершение игры после 6 попыток ---");
        try {
            WordLeDictionary dictionary = createTestDictionary();
            WordleGame game = new WordleGame(dictionary);

            for (int i = 0; i < 6; i++) {
                game.makeGuess("метро");
            }

            if (game.isGameOver() && game.getAttemptsLeft() == 0) {
                System.out.println("✓ PASS: Игра завершена после 6 попыток");
            } else {
                System.out.println("✗ FAIL: Игра не завершилась после 6 попыток");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Исключение: " + e.getMessage());
        }
    }

    static void testInvalidWordLength() {
        System.out.println("\n--- Тест: слово неправильной длины ---");
        try {
            WordLeDictionary dictionary = createTestDictionary();
            WordleGame game = new WordleGame(dictionary);
            game.makeGuess("мет");
            System.out.println("✗ FAIL: Ожидалось исключение для короткого слова");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Слово должно состоять из 5 букв")) {
                System.out.println("✓ PASS: Корректное исключение для короткого слова");
            } else {
                System.out.println("✗ FAIL: Неправильное сообщение исключения: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Неожиданное исключение: " + e.getMessage());
        }
    }

    static void testWordNotInDictionary() {
        System.out.println("\n--- Тест: слово отсутствует в словаре ---");
        try {
            WordLeDictionary dictionary = createTestDictionary();
            WordleGame game = new WordleGame(dictionary);
            game.makeGuess("абвгд");
            System.out.println("✗ FAIL: Ожидалось исключение для отсутствующего слова");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Слово отсутствует в словаре")) {
                System.out.println("✓ PASS: Корректное исключение для отсутствующего слова");
            } else {
                System.out.println("✗ FAIL: Неправильное сообщение исключения: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Неожиданное исключение: " + e.getMessage());
        }
    }

    static void testGetHintBeforeAnyGuess() {
        System.out.println("\n--- Тест: подсказка до первой попытки ---");
        try {
            WordLeDictionary dictionary = createTestDictionary();
            WordleGame game = new WordleGame(dictionary);
            String hint = game.getHint();

            if ("Сделайте первую попытку".equals(hint)) {
                System.out.println("✓ PASS: Корректная подсказка до первой попытки");
            } else {
                System.out.println("✗ FAIL: Неправильная подсказка: " + hint);
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Исключение: " + e.getMessage());
        }
    }

    static void testGetHintAfterGuess() {
        System.out.println("\n--- Тест: подсказка после попытки ---");
        try {
            WordLeDictionary dictionary = createTestDictionary();
            WordleGame game = new WordleGame(dictionary);
            game.makeGuess("метро");
            String hint = game.getHint();

            if (hint != null && !hint.isEmpty() && !hint.equals("Сделайте первую попытку")) {
                System.out.println("✓ PASS: Корректная подсказка после попытки: " + hint);
            } else {
                System.out.println("✗ FAIL: Неправильная подсказка после попытки: " + hint);
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: Исключение: " + e.getMessage());
        }
    }

    static void testGameOverState() {
        System.out.println("\n--- Тест: состояние после завершения игры ---");
        try {
            WordLeDictionary dictionary = createTestDictionary();
            WordleGame game = new WordleGame(dictionary);

            // Делаем 6 попыток
            for (int i = 0; i < 6; i++) {
                game.makeGuess("метро");
            }

            // Проверяем, что после завершения игры нельзя сделать ход
            try {
                game.makeGuess("слово");
                System.out.println("✗ FAIL: Ожидалось исключение при ходе после завершения игры");
            } catch (IllegalStateException e) {
                if (e.getMessage().equals("Игра уже завершена")) {
                    System.out.println("✓ PASS: Корректное исключение при ходе после завершения игры");
                } else {
                    System.out.println("✗ FAIL: Неправильное сообщение исключения: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("✗ FAIL: Исключение: " + e.getMessage());
        }
    }
}