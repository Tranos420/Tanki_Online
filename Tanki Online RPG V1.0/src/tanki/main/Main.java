package tanki.main;

import tanki.model.*;
import tanki.battle.Battle;
import tanki.service.BotNameGenerator;
import tanki.service.FileManager;
import tanki.ui.GarageWindow;

import java.util.Random;
import java.util.Scanner;

/**
 * Punctul de pornire al aplicației Tanki Online RPG.
 * Versiune completă – integrează selecția grafică din GarageWindow.
 */
public class Main {
    public static void main(String[] args) {

        // Inițializare turete
        Tureta Smoky = new Tureta("Smoky", 25, 85, "Critical damage");
        Tureta Firebird = new Tureta("Firebird", 30, 95, "Burn effect");
        Tureta Frezze = new Tureta("Freeze", 35, 94, "Freeze effect");
        Tureta Railgun = new Tureta("Railgun", 70, 80, "Piercing damage");

        // Inițializare hulls
        Hull Hornet = new Hull("Hornet", 120, 1.2);
        Hull Viking = new Hull("Viking", 170, 1.0);
        Hull Mammoth = new Hull("Mammoth", 250, 0.8);

        // Variabile de selecție
        Tureta turetaAleasa = null;
        Hull hullAleasa = null;

        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        Tureta[] turete = {Smoky, Firebird, Frezze, Railgun};
        Hull[] hulluri = {Hornet, Viking, Mammoth};

        int opt = -1;

        while (opt != 0) {
            System.out.print("\n==== TANKI ONLINE RPG v2.0 ====\n" +
                    "1. START JOC\n" +
                    "2. Selectează tureta și hull (interfață grafică)\n" +
                    "3. Vizualizează echipamentul ales\n" +
                    "0. Ieșire program\n" +
                    "==============================\n" +
                    "Alege o opțiune: ");

            int optiune = sc.nextInt();

            switch (optiune) {

                // 🎮 START GAME
                case 1:
                    if (turetaAleasa == null || hullAleasa == null) {
                        System.out.println("\n  Trebuie să selectezi echipamentul înainte de start!");
                        break;
                    }

                    sc.nextLine(); // consumă ENTER-ul
                    System.out.print("\nIntrodu numele tău: ");
                    String numeJucator = sc.nextLine();

                    Player player = FileManager.loadPlayer(numeJucator, turete, hulluri);
                    if (player != null) {
                        System.out.println("\n Bun revenit, " + numeJucator + "!");
                        player.afiseazaProfil();
                        player.setTank(new Tank(numeJucator, turetaAleasa, hullAleasa));
                    } else {
                        System.out.println("\n Jucător nou creat: " + numeJucator);
                        player = new Player(numeJucator, new Tank(numeJucator, turetaAleasa, hullAleasa));
                    }

                    // BOT RANDOM
                    Tureta randomTureta = turete[rand.nextInt(turete.length)];
                    Hull randomHull = hulluri[rand.nextInt(hulluri.length)];
                    String botName = BotNameGenerator.getRandomBotName();
                    Tank enemyTank = new Tank(botName, randomTureta, randomHull);

                    // Calcul putere
                    double playerPower = ((Echipabil) turetaAleasa).calculeazaPutere()
                            + ((Echipabil) hullAleasa).calculeazaPutere();
                    double enemyPower = ((Echipabil) randomTureta).calculeazaPutere()
                            + ((Echipabil) randomHull).calculeazaPutere();

                    System.out.println("\n PUTEREA ECHIPAMENTULUI ");
                    System.out.println("Jucător: " + player.getNume() + " → " + playerPower);
                    System.out.println("Inamic:  " + enemyTank.getNume() + " → " + enemyPower);

                    new Battle().startBattle(player.getTank(), enemyTank);

                    if (player.getTank().esteDistrus()) {
                        player.adaugaInfrangere();
                        System.out.println("\n Ai pierdut lupta!");
                    } else {
                        player.adaugaVictorie();
                        System.out.println("\n Felicitări, ai câștigat!");
                    }

                    FileManager.savePlayer(player);
                    System.out.println("\n Progres salvat!");
                    player.afiseazaProfil();
                    break;

                case 2:
                    System.out.println("\n  Se deschide garajul pentru selectarea echipamentului...");
                    GarageWindow ui = new GarageWindow();
                    GarageWindow.SelectionResult result = ui.getSelection();
                    if (result.tureta != null && result.hull != null) {
                        System.out.println(" Ai selectat: " + result.tureta + " + " + result.hull);
                        for (Tureta t : turete)
                            if (t.getNume().equalsIgnoreCase(result.tureta))
                                turetaAleasa = t;
                        for (Hull h : hulluri)
                            if (h.getNume().equalsIgnoreCase(result.hull))
                                hullAleasa = h;
                    } else {
                        System.out.println(" Nu ai selectat complet echipamentul!");
                    }
                    break;

                // 🔎 VIZUALIZEAZĂ ECHIPAMENTUL
                case 3:
                    if (turetaAleasa != null && hullAleasa != null) {
                        System.out.println("\n--- Echipamentul curent ---");
                        System.out.println("Tureta: " + turetaAleasa.getNume());
                        System.out.println("Hull: " + hullAleasa.getNume());
                        double putere = ((Echipabil) turetaAleasa).calculeazaPutere()
                                + ((Echipabil) hullAleasa).calculeazaPutere();
                        System.out.println("Putere totală: " + putere);
                    } else {
                        System.out.println("\n  Nu ai selectat complet echipamentul!");
                    }
                    break;

                // 🚪 IEȘIRE
                case 0:
                    System.out.println("\n👋 Ieșire din joc...");
                    opt = 0;
                    break;

                default:
                    System.out.println("\nOpțiune invalidă!");
            }
        }

        sc.close();
    }
}
