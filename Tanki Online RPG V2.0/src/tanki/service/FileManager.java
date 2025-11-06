package tanki.service;

import tanki.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionează salvarea și încărcarea datelor despre jucători în fișier text.
 * Format fișier: nume;rank;victorii;infrangeri;tureta;hull
 */
public class FileManager {

    private static final String PATH = "src/players.txt";

    public static void savePlayer(Player p) {
        try {
            List<String> all = readAll();
            boolean updated = false;

            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).startsWith(p.getNume() + ";")) {
                    all.set(i, p.toString());
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                all.add(p.toString());
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
                for (String line : all) {
                    bw.write(line);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Eroare la salvarea jucătorului: " + e.getMessage());
        }
    }

    public static Player loadPlayer(String nume, Tureta[] turete, Hull[] hulluri) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(nume + ";")) {
                    return Player.fromString(line, turete, hulluri);
                }
            }
        } catch (IOException e) {
            System.out.println("Fișierul nu a putut fi citit: " + e.getMessage());
        }
        return null;
    }

    private static List<String> readAll() throws IOException {
        List<String> lines = new ArrayList<>();
        File f = new File(PATH);
        if (!f.exists()) return lines;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
