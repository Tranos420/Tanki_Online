package tanki.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clasa care generează un nume random pentru bot dintr-un fișier text.
 */
public class BotNameGenerator {

    public static String getRandomBotName() {
        List<String> names = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/NumeBoti.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    names.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului cu nume de boți!");
        }
        Random rand = new Random();
        return names.get(rand.nextInt(names.size()));
    }
}
