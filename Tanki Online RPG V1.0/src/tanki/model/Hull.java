package tanki.model;

/**
 * Clasa Hull extinde Echipament și implementează Echipabil.
 * Reprezintă corpul tancului cu HP și multiplicator de damage.
 */
public class Hull extends Echipament implements Echipabil {

    private int HP;
    private double damageMultiplier;

    public Hull(String nume, int HP, double damageMultiplier) {
        super(nume); // moștenim numele din Echipament
        this.HP = HP;
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public double calculeazaPutere() {
        return HP * damageMultiplier;
    }

    @Override
    public String getInfo() {
        return "Hull " + nume +
                " | HP=" + HP +
                " | dmg mult=" + damageMultiplier;
    }

    public int getHP() {
        return HP;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
