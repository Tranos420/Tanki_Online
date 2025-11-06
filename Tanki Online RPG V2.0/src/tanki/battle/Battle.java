package tanki.battle;

import tanki.model.Tank;
import java.util.Random;
import java.util.Scanner;

import tanki.util.SoundPlayer;

/**
 * Clasa Battle gestionează logica bătăliei 1v1 dintre jucător și inamic.
 * Include atacurile pe rând, scăderea HP-ului și afișarea rundelor în consolă.
 */
public class Battle {

    public void startBattle(Tank player, Tank enemy) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("\n--- A ÎNCEPUT BĂTĂLIA! ---");
        System.out.println(player.getNume() + " vs " + enemy.getNume() + "\n");

        int round = 1;

        while (!player.esteDistrus() && !enemy.esteDistrus()) {
            System.out.println("--- Runda " + round + " ---");

            // Atac jucător
            int playerDamage = (int) (player.getTureta().getDamage() * player.getHull().getDamageMultiplier());
            enemy.setHpCurent(enemy.getHpCurent() - playerDamage);
            if (enemy.getHpCurent() < 0) enemy.setHpCurent(0);

            System.out.println(player.getNume() + " lovește cu " + playerDamage + " damage! " +
                    "HP inamic: " + enemy.getHpCurent());

            // Atac inamic
            int enemyDamage = (int) (enemy.getTureta().getDamage() * enemy.getHull().getDamageMultiplier());
            player.setHpCurent(player.getHpCurent() - enemyDamage);
            if (player.getHpCurent() < 0) player.setHpCurent(0);

            System.out.println(enemy.getNume() + " lovește cu " + enemyDamage + " damage! " +
                    "HP jucător: " + player.getHpCurent());

            //  Sunet după fiecare rundă
            new Thread(() -> SoundPlayer.play("assets/sounds/sunet.wav")).start();

            if (enemy.esteDistrus()) {
                System.out.println("\n Good Game! " + player.getNume() + " a câștigat lupta!");
                new Thread(() -> SoundPlayer.play("assets/sounds/castig.wav")).start();
            }
            else if (player.esteDistrus()) {
                System.out.println("\n Ai pierdut! " + enemy.getNume() + " a câștigat lupta!");
                new Thread(() -> SoundPlayer.play("assets/sounds/explosion.wav")).start();
            }

            System.out.println("--- Sfârșitul bătăliei ---\n");

            System.out.println("\nApasă ENTER pentru a continua jocul: ");
            sc.nextLine();
            round++;
        }

        System.out.println("--- Sfârșitul bătăliei ---\n");
    }
}
