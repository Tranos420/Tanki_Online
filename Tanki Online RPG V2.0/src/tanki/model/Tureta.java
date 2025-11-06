package tanki.model;

/**
 * Clasa Tureta extinde Echipament și implementează Echipabil.
 * Definește damage-ul, precizia și efectele speciale ale turetei.
 */
public class Tureta extends Echipament implements Echipabil {

    private int damage;
    private int precizie;
    private String tipEfect;

    public Tureta(String nume, int damage, int precizie, String tipEfect) {
        super(nume); // moștenim numele din Echipament
        this.damage = damage;
        this.precizie = precizie;
        this.tipEfect = tipEfect;
    }

    @Override
    public double calculeazaPutere() {
        return damage * (precizie / 100.0);
    }

    @Override
    public String getInfo() {
        return "Tureta " + nume +
                " | dmg=" + damage +
                " | precizie=" + precizie +
                "% | efect=" + tipEfect;
    }

    public int getDamage() {
        return damage;
    }

    public int getPrecizie() {
        return precizie;
    }

    public String getTipEfect() {
        return tipEfect;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
