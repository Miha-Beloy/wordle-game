package src.main.java.com.wordle;

import src.main.java.com.wordle.dictionary.WordLeDictionary;
import src.main.java.com.wordle.dictionary.WordLeDictionaryLoader;
import src.main.java.com.wordle.game.WordleGame;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("wordle.log", StandardCharsets.UTF_8))) {
            log.println("Запуск игры Wordle");

            WordLeDictionaryLoader loader = new WordLeDictionaryLoader();
            WordLeDictionary dictionary = loader.loadDictionary("russian_nouns.txt");

            WordleGame game = new WordleGame(dictionary);
            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

            System.out.println("Добро пожаловать в Wordle!");
            System.out.println("Угадайте слово из 5 букв. У вас 6 попыток.");
            System.out.println("Введите слово или нажмите Enter для подсказки");

            while (!game.isGameOver()) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    String hint = game.getHint();
                    System.out.println("Подсказка: " + hint);
                    log.println("Пользователь запросил подсказку: " + hint);
                    continue;
                }

                if (input.length() != 5) {
                    System.out.println("Слово должно состоять из 5 букв!");
                    continue;
                }

                try {
                    WordleGame.GameResult result = game.makeGuess(input);
                    System.out.println(result.hint);
                    log.println("Пользователь ввел: " + input + ", результат: " + result.hint);

                    if (result.isWin) {
                        System.out.println("Поздравляем! Вы угадали слово!");
                        break;
                    }

                    if (result.isGameOver) {
                        System.out.println("Игра окончена. Загаданное слово: " + game.getSecretWord());
                        break;
                    }

                    System.out.println("Осталось попыток: " + result.attemptsLeft);

                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    log.println("Ошибка: " + e.getMessage());
                }
            }

            log.println("Игра завершена. Загаданное слово: " + game.getSecretWord());

        } catch (Exception e) {
            System.err.println("Критическая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}