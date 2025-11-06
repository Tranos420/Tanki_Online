package tanki.model;

// aici pentru mostenire cu super momentan nu este implementata

public class HeavyHull extends Hull {
    public HeavyHull(String nume, int HP, double damageMultiplier) {
        super(nume, HP, damageMultiplier); // apel super
    }

    @Override
    public double getDamageMultiplier() {
        return super.getDamageMultiplier() * 0.9;
    }
}